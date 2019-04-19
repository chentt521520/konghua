package com.shijiucheng.konghua.main.per_.setingact;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.main.per.mima.PayPwd;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.TimerTextView;

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

public class paymentpwd extends BaseActivity_konghua implements verifyidentity.verifyidentitysuc {

    @BindView(R.id.payment_dh)
    DaoHang_top paymentDh;
    @BindView(R.id.paypwd_edyzm)
    EditText paypwdEdyzm;
    @BindView(R.id.paypwd_timerte)
    TimerTextView paypwdTimerte;
    @BindView(R.id.paypwd_edmm)
    EditText paypwdEdmm;
    @BindView(R.id.paypwd_edmmre)
    EditText paypwdEdmmre;
    @BindView(R.id.paypwd_teok)
    TextView paypwdTeok;

    Retro_Intf serivce;
    public static paymentsuc psuc;


    @Override
    protected void AddView() {
        paymentDh.settext_("重置支付密码");
        serivce = retrofit_Single.getInstence().getserivce(2);
        verifyidentity.setVer(this);
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_paymentpwd;
    }

    @OnClick({R.id.paypwd_timerte, R.id.paypwd_teok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.paypwd_timerte:
                TimerTextView.isc = true;
                getCode();
                break;
            case R.id.paypwd_teok:
                if (paypwdEdyzm.getText().toString().length() != 6) {
                    Toast.makeText(paymentpwd.this, "请输入6位验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (paypwdEdmm.getText().toString().length() < 6) {
                    Toast.makeText(paymentpwd.this, "请输入6-12位密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (paypwdEdmmre.getText().toString().length() < 6) {
                    Toast.makeText(paymentpwd.this, "请输入6-12位密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!paypwdEdmm.getText().toString().equals(paypwdEdmmre.getText().toString())) {
                    Toast.makeText(paymentpwd.this, "前后密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                setpwd();
                break;
        }
    }

    public void getCode() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("act", "modify_pay_pwd");
        retrofit2.Call<ResponseBody> call = serivce.getCode(retrofit_Single.getInstence().getOpenid(paymentpwd.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(paymentpwd.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(paymentpwd.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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

    public void setpwd() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("code", paypwdEdyzm.getText().toString());
        map.put("new_pwd", paypwdEdmm.getText().toString());
        retrofit2.Call<ResponseBody> call = serivce.setpwd(retrofit_Single.getInstence().getOpenid(paymentpwd.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(paymentpwd.this, "设置新密码成功", Toast.LENGTH_SHORT).show();
                            if (psuc != null)
                                psuc.paymentsucim();
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        } else {
                            String msgx = jsonObject.getString("msg");
                            if ((msgx.equals("validate_code_is_null"))) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", 1);
                                bundle.putString("pho", getIntent().getExtras().getString("pho"));
                                startActivityByIntent(paymentpwd.this, verifyidentity.class, bundle);
                            } else
                                Toast.makeText(paymentpwd.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
    public void verifysucc() {
        setpwd();
    }

    public interface paymentsuc {
        void paymentsucim();
    }

    public static void setPsuc(paymentsuc psuc) {
        paymentpwd.psuc = psuc;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verifyidentity.ver = null;
    }
}
