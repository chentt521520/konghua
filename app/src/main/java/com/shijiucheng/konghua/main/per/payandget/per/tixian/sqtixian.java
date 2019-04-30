package com.shijiucheng.konghua.main.per.payandget.per.tixian;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.per.mima.PayPwd;
import com.shijiucheng.konghua.main.per.mima.ValidatePwd;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg.addyhk;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg.tianjiayhk;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg.tixianfs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sqtixian extends BaseActivity_konghua implements tixianfs.gettxfs, tianjiayhk.chooseBank, ValidatePwd.yanzhenpwd, PayPwd.getpwddata {

    @BindView(R.id.sqtx_dh)
    DaoHang_top sqtxDh;
    @BindView(R.id.sqtx_tenum)
    TextView sqtxTenum;
    @BindView(R.id.sqtx_tenum1)
    TextView sqtxTenum1;
    @BindView(R.id.sqtx_spinner)
    TextView sqtxSpinner;
    @BindView(R.id.sqtx_teyhk)
    TextView sqtxTeyhk;
    @BindView(R.id.sqtx_yhk)
    LinearLayout sqtxYhk;
    @BindView(R.id.sqtx_ednum)
    EditText sqtxEdnum;
    @BindView(R.id.sqtx_ednum1)
    TextView te_minnum;
    @BindView(R.id.sqtx_num)
    LinearLayout sqtxNum;
    @BindView(R.id.sqtx_teok)
    TextView sqtxTeok;
    @BindView(R.id.sqtx_getyzm)
    TextView sqtxTegetyzm;


    tixianfs txfs;
    @BindView(R.id.sqtx_addyhk)
    TextView sqtxAddyhk;
    @BindView(R.id.sqtx_edyzm)
    EditText sqtxEdyzm;
    @BindView(R.id.sqtx_yzm)
    LinearLayout sqtxYzm;
    @BindView(R.id.sqtx_tets)
    TextView tets;

    Retro_Intf serivce;
    int yhk_size = 0;
    double tixiannum = 0;
    String bankid = "";

    PayPwd pwd;
    ValidatePwd yanzhenpwd;
    @BindView(R.id.sqtx_edfwf)
    TextView sqtxEdfwf;
    @BindView(R.id.sqtx_edfwf1)
    TextView sqtxEdfwf1;
    @BindView(R.id.sqtx_ednumt)
    TextView sqtxEdnumt;


    int fwf = 0, minnum = 0;


    @Override
    protected void AddView() {
        sqtxDh.settext_("申请提现");
        serivce = retrofit_Single.getInstence().getserivce(2);
        EventBus.getDefault().register(this);
        pwd = new PayPwd();
        yanzhenpwd = new ValidatePwd();
        getNumTX();
        getBankList();
    }

    @Override
    protected void SetViewListen() {

        sqtxTegetyzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerTextView.isc = false;
                if (TextUtils.isEmpty(sqtxEdnum.getText().toString())) {
                    toaste_ut(sqtixian.this, "请输入提现金额");
                    return;
                }
                if (Integer.valueOf(sqtxEdnum.getText().toString()).intValue() < minnum) {
                    toaste_ut(sqtixian.this, "提现最小额度为" + minnum + "元");
                    return;
                }
                if (Integer.valueOf(sqtxEdnum.getText().toString()).intValue() > tixiannum) {
                    toaste_ut(sqtixian.this, "输入金额超过可提现金额");
                    return;
                }
                TimerTextView.isc = true;
                getCode();

            }
        });
        sqtxTeyhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yhk_size > 0) {
                    tianjiayhk tjyhk = new tianjiayhk();
                    tjyhk.show(getFragmentManager(), "tjyhk");
                } else {
                    Intent i = new Intent(sqtixian.this, addyhk.class);
                    i.putExtra("type", "0");
                    startActivity(i);

                }
            }
        });
        sqtxAddyhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(sqtixian.this, addyhk.class);
                i.putExtra("type", "0");
                startActivity(i);
            }
        });

        sqtxTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(sqtxEdnum.getText().toString())) {
                    toaste_ut(sqtixian.this, "请输入提现金额");
                    return;
                } else if (Integer.valueOf(sqtxEdnum.getText().toString()).intValue() < minnum) {
                    toaste_ut(sqtixian.this, "提现最小额度为" + minnum + "元");
                    return;
                } else if (Integer.valueOf(sqtxEdnum.getText().toString()).intValue() > tixiannum) {
                    toaste_ut(sqtixian.this, "输入金额超过可提现金额");
                    return;
                } else if (sqtxTeyhk.getText().toString().equals("请选择提现银行卡")) {
                    toaste_ut(sqtixian.this, "请选择银行卡");
                    return;
                } else if (sqtxEdyzm.getText().toString().length() < 6) {
                    toaste_ut(sqtixian.this, "请输入6位短信验证码");
                    return;
                } else
                    sqtx();
            }
        });

        sqtxEdnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(minnum);
                if (sqtxEdnum.getText().toString().length() <= 0) {
                    sqtxEdnumt.setText(".");
                } else if (Integer.valueOf(sqtxEdnum.getText().toString()).intValue() < minnum) {
                    sqtxEdnumt.setText(".");
                } else
                    sqtxEdnumt.setText((Integer.valueOf(sqtxEdnum.getText().toString()).intValue() - fwf) + "元");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sqtixian;
    }

    @Override
    public void getfs(String txt) {

        sqtxSpinner.setText(txt);
        txfs.dismiss();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.addyhktosqtx)) {
                getBankList();
                return;
            }
        }
    }


    public void getNumTX() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        Call<ResponseBody> call = serivce.getNumTX(retrofit_Single.getInstence().getOpenid(sqtixian.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            sqtxTenum1.setText(jsonObject.getJSONObject("data").getString("balance_amount") + "元");
                            tets.setText("*为保证您的资金安全，请输入" + jsonObject.getJSONObject("data").getString("safe_mobile_text") + "获取到的验证码");
                            tixiannum = jsonObject.getJSONObject("data").getDouble("balance_amount");
                            String bank = jsonObject.getJSONObject("data").getJSONObject("default_bank_info").getString("bank_type_text") +
                                    jsonObject.getJSONObject("data").getJSONObject("default_bank_info").getString("bank_no_last");
                            if (!TextUtils.isEmpty(bank)) {
                                sqtxTeyhk.setText(bank);
                                bankid = jsonObject.getJSONObject("data").getJSONObject("default_bank_info").getString("bank_id");
                            } else {
                                sqtxTeyhk.setText("请选择提现银行卡");

                            }
                            sqtxEdfwf.setText(jsonObject.getJSONObject("data").getString("withdraw_service_fee") + "元");
                            sqtxEdfwf1.setText("*每笔提现收取" + jsonObject.getJSONObject("data").getString("withdraw_service_fee") + "元服务费用");
                            fwf = Integer.valueOf(jsonObject.getJSONObject("data").getString("withdraw_service_fee")).intValue();
                            minnum = Integer.valueOf(jsonObject.getJSONObject("data").getString("withdraw_min_amount")).intValue();

                            System.out.println(fwf + "  " + minnum);
                            te_minnum.setText("*提现最小额度为" + jsonObject.getJSONObject("data").getString("withdraw_min_amount") + "元");
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

    public void getBankList() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        Call<ResponseBody> call = serivce.getBankist(retrofit_Single.getInstence().getOpenid(sqtixian.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("bank_list");
                            if (jsonArray.length() >= 1)
                                yhk_size = jsonArray.length();
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

    public void getCode() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("act", "finance_withdraw");
        Call<ResponseBody> call = serivce.getCode(retrofit_Single.getInstence().getOpenid(sqtixian.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            toaste_ut(sqtixian.this, "短信发送成功");
                        } else
                            toaste_ut(sqtixian.this, jsonObject.getString("msg"));
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

    public void sqtx() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("bank_id", bankid);
        map.put("amount", sqtxEdnum.getText().toString());
        map.put("code", sqtxEdyzm.getText().toString());

        Call<ResponseBody> call = serivce.shenqingtixian(retrofit_Single.getInstence().getOpenid(sqtixian.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            toaste_ut(sqtixian.this, "申请提交成功");
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        } else {
                            String msg = jsonObject.getString("msg");
                            if (msg.equals("not_validate_pay_pwd"))
                                yanzhenpwd.show(getFragmentManager(), "czmm");
                            else toaste_ut(sqtixian.this, jsonObject.getString("msg"));
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


    @Override
    public void chooseBank(String id, String name) {//
        getNumTX();
        bankid = id;
    }

    @Override
    public void showpaypwd() {
        if (!pwd.isAdded())
            pwd.show(getSupportFragmentManager(), "pwd");
    }

    @Override
    public void getYanZhenResult(Object object) {
        sqtx();
    }

    @Override
    public void getpwddatax() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        paramsDataBean databean2 = new paramsDataBean();
        databean2.setMsg(configParams.sycw);
        EventBus.getDefault().post(databean2);
    }

}
