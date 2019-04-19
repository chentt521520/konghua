package com.shijiucheng.konghua.main.per_.setingact;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.main.per.mima.yewupho;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.TimerTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class businesspho extends BaseActivity_konghua implements verifyidentity.verifyidentitysuc {

    public static businessphointerface busine;
    @BindView(R.id.business_dh)
    DaoHang_top businessDh;
    @BindView(R.id.paypwd_edyzm)
    EditText paypwdEdyzm;//手机
    @BindView(R.id.paypwd_timerte)
    TimerTextView paypwdTimerte;
    @BindView(R.id.ywpho_edmmre)
    EditText ywphoEdmmre;//验证码
    @BindView(R.id.ywpho_teok)
    TextView ywphoTeok;
    Retro_Intf serivce;

    @Override
    protected void AddView() {
        businessDh.settext_("修改业务手机");
        verifyidentity.setVer(this);
        serivce = retrofit_Single.getInstence().getserivce(2);
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_businesspho;
    }


    @OnClick({R.id.paypwd_timerte, R.id.ywpho_teok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.paypwd_timerte:

                if (TextUtils.isEmpty(paypwdEdyzm.getText().toString()) || paypwdEdyzm.getText().toString().length() < 11) {
                    Toast.makeText(businesspho.this, "请输入11位手机号", Toast.LENGTH_SHORT).show();
                    TimerTextView.isc = false;
                    return;
                }
                TimerTextView.isc = true;
                getCode();

                break;
            case R.id.ywpho_teok:

                if (paypwdEdyzm.getText().toString().length() < 11) {
                    Toast.makeText(businesspho.this, "请输入11位手机号", Toast.LENGTH_SHORT).show();
                    TimerTextView.isc = false;
                    return;
                }
                if (ywphoEdmmre.getText().toString().length() < 6) {
                    Toast.makeText(businesspho.this, "请输入6位验证码", Toast.LENGTH_SHORT).show();
                    TimerTextView.isc = false;
                    return;
                }
                getpho();

                break;
        }
    }

    @Override
    public void verifysucc() {
        getpho();
    }

    public interface businessphointerface {
        void businessphoSetingSuc();
    }

    public static void setBusine(businessphointerface busine) {
        businesspho.busine = busine;
    }

    public void getpho() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("service_mobile", paypwdEdyzm.getText().toString());
        map.put("code", paypwdTimerte.getText().toString());
        Call<ResponseBody> call = serivce.yewu_pho(retrofit_Single.getInstence().getOpenid(businesspho.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(businesspho.this, "更换成功", Toast.LENGTH_SHORT).show();
                            if (busine != null) {
                                busine.businessphoSetingSuc();
                            }
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);

                        } else {
                            String msgx = jsonObject.getString("msg");
                            if ((msgx.equals("validate_code_is_error"))) {
                                Toast.makeText(businesspho.this, "验证码错误", Toast.LENGTH_SHORT).show();
                            } else if ((msgx.equals("validate_code_is_null"))) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", 2);
                                bundle.putString("pho", getIntent().getExtras().getString("pho"));
                                startActivityByIntent(businesspho.this, verifyidentity.class, bundle);
                            } else
                                Toast.makeText(businesspho.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
        map.put("service_mobile", paypwdEdyzm.getText().toString());
        Call<ResponseBody> call = serivce.yewu_getcode(retrofit_Single.getInstence().getOpenid(businesspho.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(businesspho.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(businesspho.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
    protected void onDestroy() {
        super.onDestroy();
        verifyidentity.ver = null;
    }
}
