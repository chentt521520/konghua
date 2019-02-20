package com.shijiucheng.konghua.main.per_.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg.addyhk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class banklist_guanli extends BaseActivity_konghua implements banklistada.getdetel, bkdetel_frag.bkdetelfrag {

    @BindView(R.id.banklist_ggdh)
    DaoHang_top banklistGgdh;
    @BindView(R.id.banklist_recyc)
    RecyclerView banklistRecyc;

    @BindView(R.id.banklist_teadd)
    TextView te_add;

    banklistada bkada;
    List<tjyhkadadata> list = new ArrayList<>();
    bkdetel_frag bkdetel_frag = new bkdetel_frag();

    String bkid = "";

    @Override
    protected void AddView() {
        banklistGgdh.settext_("银行卡管理");
        EventBus.getDefault().register(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        banklistRecyc.setLayoutManager(manager);
        bkada = new banklistada(this, list);
        banklistRecyc.setAdapter(bkada);
        bkada.setbank(this);
        getBankList();
    }

    @Override
    protected void SetViewListen() {
        te_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(banklist_guanli.this, addyhk.class);
                i.putExtra("type", "3");//3是个人中心添加银行卡
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
    }

    public void getBankList() {

        if (!jdt.isAdded()) {
            jdt.show(getFragmentManager(), "jdtbk");
        }
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        retrofit2.Call<ResponseBody> call = serivce.getBankist(retrofit_Single.getInstence().getOpenid(banklist_guanli.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt.isAdded()) {
                    jdt.dismiss();
                }
                if (response.body() == null)
                    return;
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("bank_list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                list.add(new tjyhkadadata(object.getString("bank_id"), object.getString("bank_type"), object.getString("bank_type_text"), object.getString("bank_no"), object.toString()));
                            }
                            bkada.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (jdt.isAdded()) {
                    jdt.dismiss();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.bianjiyhksuc)) {
                list.removeAll(list);
                getBankList();
                return;
            } else if (data.getMsg().equals(configParams.perdizhirefr)) {
                list.removeAll(list);
                getBankList();
                return;
            }

        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_banklist_guanli;
    }

    @Override
    public void getdetel(String id, String name) {
        if (!bkdetel_frag.isAdded()) {
            bkid = id;
            bkdetel_frag.show(getSupportFragmentManager(), "det");
        }
    }

    @Override
    public void bkdetelfrag(boolean t) {
        bkdetel();
    }

    public void bkdetel() {

        if (!jdt.isAdded()) {
            jdt.show(getFragmentManager(), "jdtbk");
        }
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("bank_id", bkid);
        retrofit2.Call<ResponseBody> call = serivce.bkdetel(retrofit_Single.getInstence().getOpenid(banklist_guanli.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt.isAdded()) {
                    jdt.dismiss();
                }
                if (response.body() == null)
                    return;
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            toaste_ut(banklist_guanli.this, "删除操作成功");
                            list.removeAll(list);
                            getBankList();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (jdt.isAdded()) {
                    jdt.dismiss();
                }
            }
        });
    }
}
