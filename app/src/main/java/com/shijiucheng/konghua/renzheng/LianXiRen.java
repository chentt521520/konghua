package com.shijiucheng.konghua.renzheng;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.Cmvp.BaseActivity_konghua;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.DPLianxiren.Contact;
import com.shijiucheng.konghua.Cmvp.DPLianxiren.DPLxrPresentIml;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LianXiRen extends BaseActivity_konghua implements Contact.IDPLxrView {
    @BindView(R.id.lxr_dh)
    DaoHang_top dh;
    @BindView(R.id.lxr_tets1)
    TextView te_ts1;
    @BindView(R.id.lxr_lin1)
    LinearLayout lin_xm1;
    @BindView(R.id.lxr_teqy1)
    TextView te_xm1;
    @BindView(R.id.lxr_edqy1)
    EditText ed_xm1;
    @BindView(R.id.lxr_linpho)
    LinearLayout lin_sjh1;
    @BindView(R.id.lxr_tepho)
    TextView te_sjh1;
    @BindView(R.id.lxr_edpho)
    EditText ed_sjh1;
    @BindView(R.id.lxr_linqq)
    LinearLayout lin_qq1;
    @BindView(R.id.lxr_teqq)
    TextView te_qq1;
    @BindView(R.id.lxr_edqq)
    EditText ed_qq1;

    @BindView(R.id.lxr_tets2)
    TextView te_ts2;
    @BindView(R.id.lxr_lin2)
    LinearLayout lin_xm2;
    @BindView(R.id.lxr_teqy2)
    TextView te_xm2;
    @BindView(R.id.lxr_edqy2)
    EditText ed_xm2;
    @BindView(R.id.lxr_linpho2)
    LinearLayout lin_sjh2;
    @BindView(R.id.lxr_tepho2)
    TextView te_sjh2;
    @BindView(R.id.lxr_edpho2)
    EditText ed_sjh2;
    @BindView(R.id.lxr_linqq2)
    LinearLayout lin_qq2;
    @BindView(R.id.lxr_teqq2)
    TextView te_qq2;
    @BindView(R.id.lxr_edqq2)
    EditText ed_qq2;

    @BindView(R.id.lxr_tets3)
    TextView te_ts3;
    @BindView(R.id.lxr_lin3)
    LinearLayout lin_xm3;
    @BindView(R.id.lxr_teqy3)
    TextView te_xm3;
    @BindView(R.id.lxr_edqy3)
    EditText ed_xm3;
    @BindView(R.id.lxr_linpho3)
    LinearLayout lin_sjh3;
    @BindView(R.id.lxr_tepho3)
    TextView te_sjh3;
    @BindView(R.id.lxr_edpho3)
    EditText ed_sjh3;
    @BindView(R.id.lxr_linqq3)
    LinearLayout lin_qq3;
    @BindView(R.id.lxr_teqq3)
    TextView te_qq3;
    @BindView(R.id.lxr_edqq3)
    EditText ed_qq3;

    @BindView(R.id.lxr_teok)
    TextView te_ok;

    String[] data1 = {"-0", "-0", "-0"}, data2 = {"-0", "-0", "-0"}, data3 = {"-0", "-0", "-0"};

    Contact.IDPLxrPresent present = new DPLxrPresentIml(this);


    @Override
    protected void AddView() {
        dh.settext_("填写联系人信息");

        setViewHw_Lin(te_ts1, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 20 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_ts1, (int) (w_ * 28 / 750.0));
        setViewHw_Lin(lin_xm1, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_xm1, (int) (w_ * 28 / 750.0));
        setTextSize(ed_xm1, (int) (w_ * 28 / 750.0));
        setViewHw_Lin(lin_sjh1, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_sjh1, (int) (w_ * 28 / 750.0));
        setTextSize(ed_sjh1, (int) (w_ * 28 / 750.0));
        setViewHw_Lin(lin_qq1, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_qq1, (int) (w_ * 28 / 750.0));
        setTextSize(ed_qq1, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(te_ts2, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 20 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_ts2, (int) (w_ * 28 / 750.0));
        setViewHw_Lin(lin_xm2, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_xm2, (int) (w_ * 28 / 750.0));
        setTextSize(ed_xm2, (int) (w_ * 28 / 750.0));
        setViewHw_Lin(lin_sjh2, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_sjh2, (int) (w_ * 28 / 750.0));
        setTextSize(ed_sjh2, (int) (w_ * 28 / 750.0));
        setViewHw_Lin(lin_qq2, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_qq2, (int) (w_ * 28 / 750.0));
        setTextSize(ed_qq2, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(te_ts3, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 20 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_ts3, (int) (w_ * 28 / 750.0));
        setViewHw_Lin(lin_xm3, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_xm3, (int) (w_ * 28 / 750.0));
        setTextSize(ed_xm3, (int) (w_ * 28 / 750.0));
        setViewHw_Lin(lin_sjh3, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_sjh3, (int) (w_ * 28 / 750.0));
        setTextSize(ed_sjh3, (int) (w_ * 28 / 750.0));
        setViewHw_Lin(lin_qq3, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_qq3, (int) (w_ * 28 / 750.0));
        setTextSize(ed_qq3, (int) (w_ * 28 / 750.0));

        setTextSize(te_ok, (int) (w_ * 46 / 750.0));

        JSONArray jsonArray = new JSONArray(authen_RZ.jsonAuthor.getStore_contact());
        if (jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (i == 0) {
                        ed_xm1.setText(jsonObject.getString("store_contact_uname"));
                        ed_sjh1.setText(jsonObject.getString("store_contact_tel"));
                        ed_qq1.setText(jsonObject.getString("store_contact_qq"));
                    } else if (i == 1) {
                        ed_xm2.setText(jsonObject.getString("store_contact_uname"));
                        ed_sjh2.setText(jsonObject.getString("store_contact_tel"));
                        ed_qq2.setText(jsonObject.getString("store_contact_qq"));
                    } else if (i == 2) {
                        ed_xm3.setText(jsonObject.getString("store_contact_uname"));
                        ed_sjh3.setText(jsonObject.getString("store_contact_tel"));
                        ed_qq3.setText(jsonObject.getString("store_contact_qq"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    @Override
    protected void SetViewListen() {
        te_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ed_xm1.getText().toString())) {
                    toaste_ut(LianXiRen.this, "请输入姓名");
                } else {
                    data1[0] = ed_xm1.getText().toString();
                    if (TextUtils.isEmpty(ed_sjh1.getText().toString()) || ed_sjh1.getText().toString().length() < 11) {
                        toaste_ut(LianXiRen.this, "请输入正确的手机号");
                    } else {
                        data1[1] = ed_sjh1.getText().toString();
                        if (TextUtils.isEmpty(ed_qq1.getText().toString())) {
                            toaste_ut(LianXiRen.this, "请输入qq号");
                        } else {
                            data1[2] = ed_qq1.getText().toString();//当第一条数据data1填写完成后判断其他的有无填写，只要填写一个，那么3个都必须填写

                            if (TextUtils.isEmpty(ed_xm2.getText().toString())) {
                                data2[0] = "-0";
                            } else {
                                data2[0] = ed_xm2.getText().toString();
                                if (TextUtils.isEmpty(ed_sjh2.getText().toString()) || ed_sjh2.getText().toString().length() < 11) {
                                    data2[1] = "-0";
                                } else {
                                    data2[1] = ed_sjh2.getText().toString();
                                    if (TextUtils.isEmpty(ed_qq2.getText().toString())) {
                                        data2[2] = "-0";
                                    } else {
                                        data2[2] = ed_qq2.getText().toString();
                                    }
                                }
                            }

                            if (TextUtils.isEmpty(ed_xm3.getText().toString())) {
                            } else {
                                data3[0] = ed_xm3.getText().toString();
                                if (TextUtils.isEmpty(ed_sjh3.getText().toString()) || ed_sjh3.getText().toString().length() < 11) {
                                    data3[1] = "-0";
                                } else {
                                    data3[1] = ed_sjh3.getText().toString();
                                    data3[2] = "-0";
                                    if (TextUtils.isEmpty(ed_qq3.getText().toString())) {
                                        data3[2] = "-0";
                                    } else {
                                        data3[2] = ed_qq3.getText().toString();
                                    }
                                }
                            }
                            boolean data2_tj = false;
                            if (data2[0].equals("-0") && data2[1].equals("-0") && data2[2].equals("-0")) {
                                data2_tj = false;
                            } else {
                                if ((!data2[0].equals("-0")) && (!data2[1].equals("-0")) && (!data2[2].equals("-0"))) {
                                    data2_tj = true;
                                } else {
                                    toaste_ut(LianXiRen.this, "请完善联系人2的信息");
                                    return;
                                }
                            }
                            boolean data3_tj = false;
                            if (data3[0].equals("-0") && data3[1].equals("-0") && data3[2].equals("-0")) {
                                data3_tj = false;
                            } else {
                                if ((!data3[0].equals("-0")) && (!data3[1].equals("-0")) && (!data3[2].equals("-0"))) {
                                    data3_tj = true;
                                } else {
                                    toaste_ut(LianXiRen.this, "请完善联系人3的信息");
                                    return;
                                }
                            }
                            JSONArray jsonArray = new JSONArray();
                            JSONObject jso1 = new JSONObject();
                            JSONObject jso2 = new JSONObject();
                            JSONObject jso3 = new JSONObject();
                            try {
                                jso1.put("store_contact_uname", data1[0]);
                                jso1.put("store_contact_tel", data1[1]);
                                jso1.put("store_contact_qq", data1[2]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            jsonArray.put(jso1);
                            if (data2_tj) {
                                try {
                                    jso2.put("store_contact_uname", data2[0]);
                                    jso2.put("store_contact_tel", data2[1]);
                                    jso2.put("store_contact_qq", data2[2]);
                                    jsonArray.put(jso2);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (data3_tj) {
                                try {
                                    jso3.put("store_contact_uname", data3[0]);
                                    jso3.put("store_contact_tel", data3[1]);
                                    jso3.put("store_contact_qq", data3[2]);
                                    jsonArray.put(jso3);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            List<Object> list = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    list.add(jsonArray.get(i));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (list != null)
                                authen_RZ.jsonAuthor.setStore_contact(list);
                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.dprzStep4);
                            EventBus.getDefault().post(databean);
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_lian_xi_ren;
    }

    @Override
    protected BasePresenter bindPresent() {
        return present;
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.view_null);
        super.onDestroy();
    }

    @Override
    public void showload() {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "lianxiren");
    }

    @Override
    public void closeload() {
        if (jdt.isAdded())
            jdt.dismiss();
    }

    @Override
    public void showtoast(String msg) {
        toaste_ut(getApplicationContext(), msg);
    }

    @Override
    public void savedata() {
        finish();
        overridePendingTransition(R.anim.push_right_out,
                R.anim.push_right_in);
    }
}
