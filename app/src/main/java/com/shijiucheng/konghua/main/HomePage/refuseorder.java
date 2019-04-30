package com.shijiucheng.konghua.main.HomePage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.HomePage.frag.jujue_frag;
import com.shijiucheng.konghua.main.order.popwindow_.jujueada;
import com.shijiucheng.konghua.main.order.popwindow_.jujuedata;
import com.shijiucheng.konghua.main.per.mima.PayPwd;
import com.shijiucheng.konghua.main.per.mima.ValidatePwd;
import com.shijiucheng.konghua.main.per_.setingact.verifyidentity;

import org.greenrobot.eventbus.EventBus;
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
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class refuseorder extends BaseActivity_konghua implements ValidatePwd.yanzhenpwd, PayPwd.getpwddata {

    @BindView(R.id.refuse_dh)
    DaoHang_top refuseDh;
    @BindView(R.id.jujue_tebc)
    TextView jujueTebc;
    @BindView(R.id.jujue_yuany)
    TextView jujueYuany;
    @BindView(R.id.jujue_yuany1)
    ListViewForScrollView jujueYuany1;
    @BindView(R.id.jujue_miaos)
    TextView jujueMiaos;
    @BindView(R.id.jujue_miaos1)
    EditText jujueMiaos1;
    @BindView(R.id.jujue_linms)
    LinearLayout jujueLinms;
    @BindView(R.id.jujue_jdteok)
    TextView jujueJdteok;

    jujueada ada;
    List<jujuedata> list = new ArrayList<>();
    int pos_cho = -1;
    String order_id = "", type = "jujue";

    PayPwd pwd = new PayPwd();
    ValidatePwd sfyz = new ValidatePwd();

    @Override
    protected void AddView() {
        Bundle bundle = getIntent().getExtras();
        order_id = bundle.getString("id");
        type = bundle.getString("type");
        if (type.equals("jujue")) {
            refuseDh.settext_("拒绝接单");
            jujueTebc.setVisibility(View.GONE);
        } else {
            refuseDh.settext_("申请退单");
            jujueTebc.setVisibility(View.VISIBLE);
            jujueYuany.setText("退单原因：");
        }

        ada = new jujueada(list, refuseorder.this);
        jujueYuany1.setAdapter(ada);
        jujueyy();

    }


    @Override
    protected void SetViewListen() {
        jujueYuany1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos_cho = position;
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        list.get(i).setT_("1");
                    } else {
                        list.get(i).setT_("0");
                    }
                }
                ada.notifyDataSetChanged();
                if (position == list.size() - 1) {
                    jujueLinms.setVisibility(View.VISIBLE);
                } else
                    jujueLinms.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_refuseorder;
    }

    @OnClick(R.id.jujue_jdteok)
    public void onViewClicked() {
        if (pos_cho == -1) {
            if (type.equals("jujue"))
                Toast.makeText(refuseorder.this, "请选择拒绝原因", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(refuseorder.this, "请选择退单原因", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pos_cho == list.size() - 1) {

            if (TextUtils.isEmpty(jujueMiaos1.getText().toString())) {
                if (type.equals("jujue")) {
                    Toast.makeText(refuseorder.this, "请输入拒绝原因", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(refuseorder.this, "请输入退单原因", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            if (pos_cho == list.size() - 1)
                jujue(list.get(pos_cho).getKye(), jujueMiaos1.getText().toString());
            else
                jujue(list.get(pos_cho).getKye(), "");
            return;
        }
        if (pos_cho == list.size() - 1)
            jujue(list.get(pos_cho).getKye(), jujueMiaos1.getText().toString());
        else
            jujue(list.get(pos_cho).getKye(), "");
    }

    public void jujueyy() {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("order_id", order_id);
        Call<ResponseBody> call;
        if (type.equals("jujue"))
            call = serivce.jujueyy(retrofit_Single.getInstence().getOpenid(refuseorder.this), map);
        else
            call = serivce.tuidanym(retrofit_Single.getInstence().getOpenid(refuseorder.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {

                            JSONArray jsonObject1;
                            if (type.equals("jujue"))
                                jsonObject1 = jsonObject.getJSONObject("data").getJSONArray("order_refuse_reason");
                            else
                                jsonObject1 = jsonObject.getJSONObject("data").getJSONArray("order_cancel_reason");
                            list.removeAll(list);
                            for (int i = 0; i < jsonObject1.length(); i++) {
                                JSONObject jso = jsonObject1.getJSONObject(i);
                                if (pos_cho != -1) {
                                    if (i == pos_cho)
                                        list.add(new jujuedata(jso.getString("text"), "1", jso.getString("key")));
                                    else
                                        list.add(new jujuedata(jso.getString("text"), "0", jso.getString("key")));
                                } else

                                    list.add(new jujuedata(jso.getString("text"), "0", jso.getString("key")));
                            }
                            ada.notifyDataSetChanged();
                        } else {
                            if ((jsonObject.getString("msg").equals("not_validate_pay_pwd"))) {
                                if (!sfyz.isAdded())
                                    sfyz.show(getFragmentManager(), "pwd");
                            }
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

            }
        });
    }

    public void jujue(String yy_type, String txt) {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        jdt.show(getFragmentManager(), "jdt");
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("order_id", order_id);
        if (type.equals("jujue")) {
            map.put("order_refuse_reason", yy_type);
            map.put("order_refuse_reason_content", txt);
        } else {
            map.put("order_cancel_reason", yy_type);
            map.put("order_cancel_reason_content", txt);
        }
        Call<ResponseBody> call;
        if (type.equals("jujue"))
            call = serivce.jujue(retrofit_Single.getInstence().getOpenid(this), map);
        else
            call = serivce.tuidan(retrofit_Single.getInstence().getOpenid(this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            jdt.dismiss();
                            toaste_ut(refuseorder.this, "操作成功");

                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.orderqianshou);//使用签收字典刷新订单详情
                            Bundle bundle = new Bundle();
                            databean.setT(bundle);
                            EventBus.getDefault().post(databean);

                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        } else {

                            if ((jsonObject.getString("msg").equals("not_validate_pay_pwd"))) {
                                jdt.dismiss();
                                if (!sfyz.isAdded())
                                    sfyz.show(getFragmentManager(), "pwd");
                            } else {

                                jdt.dismiss();
                                toaste_ut(refuseorder.this, jsonObject.getString("msg"));
                            }
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
                jdt.dismiss();
            }
        });
    }


    @Override
    public void showpaypwd() {
        if (!pwd.isAdded())
            pwd.show(getSupportFragmentManager(), "pwd1");
    }

    @Override
    public void getYanZhenResult(Object object) {
        if (pos_cho == list.size() - 1)
            jujue(list.get(pos_cho).getKye(), jujueMiaos1.getText().toString());
        else
            jujue(list.get(pos_cho).getKye(), "");
    }

    @Override
    public void getpwddatax() {
        if (!sfyz.isAdded())
            sfyz.show(getFragmentManager(), "pwd");
    }
}
