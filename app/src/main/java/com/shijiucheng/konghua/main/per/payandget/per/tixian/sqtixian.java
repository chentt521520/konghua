package com.shijiucheng.konghua.main.per.payandget.per.tixian;

import android.content.Intent;
import android.text.TextUtils;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
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

    @Override
    protected void AddView() {
        sqtxDh.settext_("申请提现");
        serivce = retrofit_Single.getInstence().getserivce(2);
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

                if (TextUtils.isEmpty(sqtxEdnum.getText().toString())) {
                    toaste_ut(sqtixian.this, "请输入有效的提现金额");
                    return;
                }

                if (Integer.parseInt(sqtxEdnum.getText().toString()) == 0) {
                    toaste_ut(sqtixian.this, "请输入有效的提现金额");
                    return;
                }
                if (Integer.valueOf(sqtxEdnum.getText().toString()).intValue() > tixiannum) {
                    toaste_ut(sqtixian.this, "可提现金额不足");
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
                    i.putExtra("type", "1");
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
                    toaste_ut(sqtixian.this, "请输入小于可提现金额的数字");
                    return;
                }
                if (Integer.parseInt(sqtxEdnum.getText().toString()) == 0) {
                    toaste_ut(sqtixian.this, "请输入有效的提现金额");
                    return;
                }
                if (Integer.valueOf(sqtxEdnum.getText().toString()).intValue() > tixiannum) {
                    toaste_ut(sqtixian.this, "请输入小于可提现金额的数字");
                    return;
                }
                if (sqtxTeyhk.getText().toString().equals("请选择提现银行卡")) {
                    toaste_ut(sqtixian.this, "请选择银行卡");
                    return;
                }
                if (sqtxEdyzm.getText().toString().length() < 6) {
                    toaste_ut(sqtixian.this, "请输入店主手机号获取的验证码");
                    return;
                }
                sqtx();
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


    public void getNumTX() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        retrofit2.Call<ResponseBody> call = serivce.getNumTX(retrofit_Single.getInstence().getOpenid(sqtixian.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            sqtxTenum1.setText(jsonObject.getJSONObject("data").getString("balance_amount")+"元");
                            tets.setText("*为保证您的资金安全，请输入" + jsonObject.getJSONObject("data").getString("safe_mobile_text") + "获取到的验证码");
                            tixiannum = jsonObject.getJSONObject("data").getDouble("balance_amount");
                            String bank = jsonObject.getJSONObject("data").getJSONObject("default_bank_info").getString("bank_type_text") +
                                    jsonObject.getJSONObject("data").getJSONObject("default_bank_info").getString("bank_no_last");
                            if (!TextUtils.isEmpty(bank))
                                sqtxTeyhk.setText(bank);
                            else sqtxTeyhk.setText("请选择提现银行卡");
                            bankid = jsonObject.getJSONObject("data").getJSONObject("default_bank_info").getString("bank_id");
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        retrofit2.Call<ResponseBody> call = serivce.getBankist(retrofit_Single.getInstence().getOpenid(sqtixian.this), map);
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("act", "finance_withdraw");
        retrofit2.Call<ResponseBody> call = serivce.getCode(retrofit_Single.getInstence().getOpenid(sqtixian.this), map);
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("bank_id", bankid);
        map.put("amount", sqtxEdnum.getText().toString());
        map.put("code", sqtxEdyzm.getText().toString());

        retrofit2.Call<ResponseBody> call = serivce.shenqingtixian(retrofit_Single.getInstence().getOpenid(sqtixian.this), map);
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
