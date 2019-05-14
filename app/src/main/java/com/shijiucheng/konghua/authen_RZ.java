package com.shijiucheng.konghua;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.renzhenmvp.conttract;
import com.shijiucheng.konghua.Cmvp.renzhenmvp.renZhenPresentIml;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.Json_.Json_Author;
import com.shijiucheng.konghua.renzheng.AutherFail;
import com.shijiucheng.konghua.renzheng.DPAddress;
import com.shijiucheng.konghua.renzheng.DianPu;
import com.shijiucheng.konghua.renzheng.DianZhu;
import com.shijiucheng.konghua.renzheng.LianXiRen;
import com.shijiucheng.konghua.renzheng.authen_cannotfrag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Retrofit2.retrofit_Single;
import butterknife.BindView;

public class authen_RZ extends com.shijiucheng.konghua.Cmvp.BaseActivity_konghua implements ActivityCompat.OnRequestPermissionsResultCallback, conttract.IrezhenView {

    private static final String TAG = "authen_RZ";
    @BindView(R.id.renzhen_teTit)
    DaoHang_top te_tit;
    @BindView(R.id.renzhen_Lin)
    LinearLayout lin_dz;
    @BindView(R.id.renzhen_Imdz)
    ImageView im_dz;
    @BindView(R.id.renzhen_Tedz)
    TextView te_dz;
    @BindView(R.id.renzhen_Tedz1)
    TextView te_dz1;
    @BindView(R.id.renzhen_Tedzgo)
    TextView te_dzgo;

    @BindView(R.id.renzhen_Lindp)
    LinearLayout lin_dp;
    @BindView(R.id.renzhen_Imdp)
    ImageView im_dp;
    @BindView(R.id.renzhen_Tedp)
    TextView te_dp;
    @BindView(R.id.renzhen_Tedp1)
    TextView te_dp1;
    @BindView(R.id.renzhen_Tedpgo)
    TextView te_dpgo;

    @BindView(R.id.renzhen_Lindplxr)
    LinearLayout lin_dpwz;
    @BindView(R.id.renzhen_Imdplxr)
    ImageView im_wz;
    @BindView(R.id.renzhen_Tedplxr)
    TextView te_dpwz;
    @BindView(R.id.renzhen_Tedplxr1)
    TextView te_dpwz1;
    @BindView(R.id.renzhen_Tedplxrgo)
    TextView te_dpwzgo;

    @BindView(R.id.renzhen_Lindpwz)
    LinearLayout lin_dplxr;
    @BindView(R.id.renzhen_Imdpwz)
    ImageView im_lxr;
    @BindView(R.id.renzhen_Tedpwz)
    TextView te_dplxr;
    @BindView(R.id.renzhen_Tedpwz1)
    TextView te_dplxr1;
    @BindView(R.id.renzhen_Tedpwzgo)
    TextView te_dplxrgo;

    @BindView(R.id.renzhen_tets)
    TextView te_dpts;//认证失败提示

    @BindView(R.id.renzhen_Teok)
    TextView te_ok;

    @BindView(R.id.renzhen_shenheiz)
    View v_shenheiz;

    //    private SDKReceiver mReceiver;
    double exitTime = 0;


    conttract.irezhenPresent present = new renZhenPresentIml(this);
    public static Json_Author jsonAuthor;
    int rzzt = 0;//认证状态0未申请过，1审核中，2审核未通过3通过申请
    int rzzt1 = 0, rzzt2 = 0, rzzt3 = 0, rzzt4 = 0;//0表示未填写完成1表示填写完成
    AutherFail autherFail;
    String failMsg = "";

    authen_cannotfrag cannotfrag = new authen_cannotfrag();

    @Override
    protected void AddView() {
        te_tit.settext_("店铺基本信息");
        autherFail = new AutherFail();
        present.getAuthorData(this, retrofit_Single.getInstence().getOpenid(this));
        EventBus.getDefault().register(this);

    }

    @Override
    protected void SetViewListen() {
        te_tit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        lin_dz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("status", rzzt + "");
                startActivityByIntent(authen_RZ.this, DianZhu.class,bundle);
            }
        });
        lin_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityByIntent(authen_RZ.this, DianPu.class);
            }
        });
        lin_dplxr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityByIntent(authen_RZ.this, LianXiRen.class);
            }
        });
        lin_dpwz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityByIntent(authen_RZ.this, DPAddress.class);
            }
        });

        te_dpts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!autherFail.isAdded()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", failMsg);
                    autherFail.setArguments(bundle);
                    autherFail.show(getFragmentManager(), "authen");
                }
            }
        });

        te_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fastClick()) {
                    if (rzzt == 2 || rzzt == 3) {
                        JSONArray jsonArray = new JSONArray(authen_RZ.jsonAuthor.getStore_contact());
                        JSONArray jsa = new JSONArray();
                        List<Object> list = new ArrayList<>();
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonArray1 = jsonArray.getJSONObject(i);
                                    JSONObject jso1 = new JSONObject();
                                    jso1.put("store_contact_uname", jsonArray1.getString("store_contact_uname"));
                                    jso1.put("store_contact_tel", jsonArray1.getString("store_contact_tel"));
                                    jso1.put("store_contact_qq", jsonArray1.getString("store_contact_qq"));
                                    jsa.put(jso1);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            for (int i = 0; i < jsa.length(); i++) {
                                try {
                                    list.add(jsa.get(i));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            authen_RZ.jsonAuthor.setStore_contact(list);
                        }
                    }
                    HashMap<String, String> map = new HashMap<>();

                    map.putAll(toMap(jsonAuthor));
                    if (rzzt == 0) {
                        boolean all = true;
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            entry.getKey();
                            entry.getValue();
                            if (entry.getKey().toString().equals("store_master_idcard_front")) {
                            }
                            if (entry.getKey().toString().equals("store_contact")) {
                                if (entry.getValue().toString().length() < 4)
                                    all = false;
                            } else {
                                if (entry.getValue().toString().length() < 1) {
                                    all = false;
                                }
                            }
                        }
                        if (all) {
                            present.applayRZ(authen_RZ.this, retrofit_Single.getInstence().getOpenid(authen_RZ.this), map);
                        } else {
                            toaste_ut(authen_RZ.this, "请填写完整认证信息");
                        }
                    } else if (rzzt == 2 || rzzt == 3) {
                        present.applayRZ(authen_RZ.this, retrofit_Single.getInstence().getOpenid(authen_RZ.this), map);
                    }

                }
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_authen__rz;
    }

    @Override
    protected BasePresenter bindPresent() {
        return present;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        // 取消监听 SDK 广播
//        unregisterReceiver(mReceiver);
        setContentView(R.layout.view_null);
        if (present != null) {
            present.onDestroy();
            present = null;
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.dprzStep1)) {
                toaste_ut(authen_RZ.this, "11111");
                te_dz1.setText("已填写");
                te_dzgo.setText("查看 >");
                return;
            }
            if (data.getMsg().equals(configParams.dprzStep2)) {
                te_dp1.setText("已填写");
                te_dpgo.setText("查看 >");
                return;
            }
            if (data.getMsg().equals(configParams.dprzStep3)) {
                te_dpwz1.setText("已填写");
                te_dpwzgo.setText("查看 >");
                return;
            }
            if (data.getMsg().equals(configParams.dprzStep4)) {
                te_dplxr1.setText("已填写");
                te_dplxrgo.setText("查看 >");
                return;
            }
        }
    }

    @Override
    public void showload() {
        if (jdt != null)
            if (!jdt.isAdded())
                jdt.show(getFragmentManager(), "jdt");
    }

    @Override
    public void closeload() {
        if (jdt != null)
            if (jdt.isAdded())
                jdt.dismiss();
    }

    @Override
    public void showtoaste(String msg) {
        toaste_ut(this, msg);
    }


    @Override
    public void toMainact() {
        sharePre("isauther", "0", authen_RZ.this);
        jsonAuthor = null;

        paramsDataBean databean1 = new paramsDataBean();
        databean1.setMsg(configParams.totherpager);
        databean1.setT(0);
        EventBus.getDefault().post(databean1);

        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.refreshhp);
        EventBus.getDefault().post(databean);
        finish();
        overridePendingTransition(R.anim.push_right_out,
                R.anim.push_right_in);

    }

    //获取是否填写过认证信息与认证状态
    @Override
    public void getAuthorData(JSONObject jsonObject, Json_Author json_author) {

        try {
            if (jsonObject.getString("status").equals("1")) {
                if (v_shenheiz != null)
                    if (v_shenheiz.isShown())
                        v_shenheiz.setVisibility(View.GONE);
                try {
                    JSONObject jso = jsonObject.getJSONObject("data").getJSONObject("store_info");
                    sharePre("latitude", jso.getString("latitude"), authen_RZ.this);
                    sharePre("longitude", jso.getString("longitude"), authen_RZ.this);
                    sharePre("isauther", "1", authen_RZ.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonAuthor = json_author;
                rzzt = 3;
//                te_dzgo.setText("查看 >");
//                te_dpgo.setText("查看 >");
//                te_dpwzgo.setText("查看 >");
//                te_dplxrgo.setText("查看 >");
//                te_ok.setText("审核通过/修改");

                return;
            }
            jsonAuthor = json_author;
            if (jsonObject.getJSONObject("data").getString("authentication_status").equals("nauthorized")) {
                rzzt = 0;
                sharePre("isauther", "0", authen_RZ.this);
                return;
            }

            if (jsonObject.getJSONObject("data").getString("authentication_status").equals("authentication_not_pass")) {
                rzzt = 2;
                te_dpts.setVisibility(View.VISIBLE);
                failMsg = jsonObject.getString("msg");
                te_ok.setText("重新提交");
                sharePre("isauther", "0", authen_RZ.this);

                if (!autherFail.isAdded()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", failMsg);
                    autherFail.setArguments(bundle);
                    autherFail.show(getFragmentManager(), "authen");
                }

                return;
            }
            if (jsonObject.getJSONObject("data").getString("authentication_status").equals("authenticating")) {
                rzzt = 1;
                sharePre("isauther", "0", authen_RZ.this);
                te_ok.setText("认证中，待管理员审核");
                te_ok.setBackgroundResource(R.drawable.yuanjiaoall1);
//                te_dzgo.setText("查看 >");
//                te_dpgo.setText("查看 >");
//                te_dpwzgo.setText("查看 >");
//                te_dplxrgo.setText("查看 >");

                v_shenheiz.setVisibility(View.VISIBLE);
                return;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void shouwCannotToAuth(JSONObject jsonObject) {
        //审核通过，重新提交
        if (!cannotfrag.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putString("object", jsonObject.toString());
            cannotfrag.setArguments(bundle);
            cannotfrag.show(getSupportFragmentManager(), "cannot");
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            jsonAuthor = null;
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
            return false;
        }
        return false;
    }


    public Map<String, String> toMap(Object javaBean) {
        Map<String, String> result = new HashMap<String, String>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[]) null);
                    result.put(field, null == value ? "" : value.toString());
                }
            } catch (Exception e) {
            }
        }

        return result;
    }


}
