package com.shijiucheng.konghua.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.HomePage.Orderdatels;
import com.shijiucheng.konghua.main.order.Order_detalis;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gondandetials;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.tiXianlishi;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Newsdetails extends BaseActivity_konghua {


    @BindView(R.id.ndet_tedh)
    DaoHang_top ndetTedh;
    @BindView(R.id.ndet_tetit)
    TextView ndetTetit;
    @BindView(R.id.ndet_tetime)
    TextView ndetTetime;
    @BindView(R.id.ndet_tetxt)
    TextView ndetTetxt;
    @BindView(R.id.ndet_tego)
    TextView ndetTego;

    Retro_Intf service;
    JSONObject jso_data;

    @Override
    protected void AddView() {
        ndetTedh.settext_("站内信息");
        getdata();
    }

    private void getdata() {
        service = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.put("m_id", getIntent().getStringExtra("m_id"));
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        Call<ResponseBody> call = service.news(retrofit_Single.getInstence().getOpenid(Newsdetails.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String data = response.body().string();
                    JSONObject jso = new JSONObject(data);
                    if (jso.getString("status").equals("1")) {
                        JSONObject jso_ = jso.getJSONObject("data").getJSONObject("message_info");
                        ndetTetit.setText(jso_.getString("message_title"));
                        ndetTetime.setText("发件时间：" + jso_.getString("add_time_text"));
                        String txt = jso_.getString("message_content").replace("&nbsp;", "").replace("<br>", "");
                        ndetTetxt.setText(txt);
                        jso_data = jso_;
                        if (TextUtils.isEmpty(jso_data.getString("message_obj"))) {
                            ndetTego.setVisibility(View.GONE);
                        } else ndetTego.setVisibility(View.VISIBLE);

                    } else {
                        finish();
                        overridePendingTransition(R.anim.push_right_out,
                                R.anim.push_right_in);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_newsdetails;
    }


    @OnClick(R.id.ndet_tego)
    public void onViewClicked() {
        Intent i;
        if (jso_data != null) {
            try {
                if (jso_data.getString("message_obj").equals("order_detail")) {
                    i = new Intent(Newsdetails.this, Orderdatels.class);
                    i.putExtra("id", jso_data.getString("message_obj_pk_id"));
                    System.out.println(jso_data.getString("message_obj_pk_id"));
                    startActivity(i);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                } else if (jso_data.getString("message_obj").equals("withdraw_list")) {
                    i = new Intent(Newsdetails.this, tiXianlishi.class);
                    i.putExtra("id", jso_data.getString("message_obj_pk_id"));
                    startActivity(i);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                } else if (jso_data.getString("message_obj").equals("work_order_detail")) {
                    i = new Intent();
                    i.setClass(Newsdetails.this, gondandetials.class);
                    i.putExtra("id", jso_data.getString("message_obj_pk_id"));
                    i.putExtra("type", "");
                    i.putExtra("position", 0);
                    startActivity(i);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);

                } else if (jso_data.getString("message_obj").equals("index")) {
                    paramsDataBean databean1 = new paramsDataBean();
                    databean1.setMsg(configParams.totherpager);
                    databean1.setT(0);
                    EventBus.getDefault().post(databean1);
                    finish();
                    overridePendingTransition(R.anim.push_right_out,
                            R.anim.push_right_in);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
