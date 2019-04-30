package com.shijiucheng.konghua.main.per_.setingact;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
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

public class verifyidentity extends BaseActivity_konghua  {
    @BindView(R.id.verify_dh)
    DaoHang_top verifyDh;
    @BindView(R.id.sfyz_tepho)
    TextView sfyzTepho;
    @BindView(R.id.sfyz_edcode)
    EditText sfyzEdcode;
    @BindView(R.id.sfyz_teget)
    TimerTextView sfyzTeget;
    @BindView(R.id.ywpho_teok)
    TextView ywphoTeok;
    public static verifyidentitysuc ver;

    @Override
    protected void AddView() {
        verifyDh.settext_("身份验证");
        sfyzTepho.setText(getIntent().getExtras().getString("pho"));//需要传入验证手机
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_verifyidentity;
    }

    @OnClick({R.id.sfyz_teget, R.id.ywpho_teok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sfyz_teget:
                TimerTextView.isc = true;
                getcode();
                break;
            case R.id.ywpho_teok:

                if (TextUtils.isEmpty(sfyzEdcode.getText().toString())) {
                    Toast.makeText(verifyidentity.this, "请输入短信验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                sfyz();

                break;
        }
    }

    private void sfyz() {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(this));//公共参数
        int bundle = getIntent().getExtras().getInt("type");
        if (bundle == 0)
            maps.put("act", "modify_login_pwd");
        else if (bundle == 1)
            maps.put("act", "modify_pay_pwd");
        else if (bundle == 2)
            maps.put("act", "modify_service_mobile");
        else if (bundle == 3)
            maps.put("act", "modify_safe_mobile");

        maps.put("code", sfyzEdcode.getText().toString());
        Call<ResponseBody> getdata = serivce.sfyz(retrofit_Single.getInstence().getOpenid(verifyidentity.this), maps);
        getdata.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(
                    Call<ResponseBody> call, Response<ResponseBody> response) {
                String Result = null;
                if (response.body() == null)
                    return;
                try {
                    Result = response.body().string();
                    try {
                        if (Result != null && Result.startsWith("\ufeff")) {
                            Result = Result.substring(1);
                        }
                        JSONObject jsonObject = new JSONObject(Result);
                        BaseResult result = new BaseResult();

                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(verifyidentity.this, "验证成功", Toast.LENGTH_SHORT).show();
                            if (ver != null) {
                                ver.verifysucc();
                            }
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        } else {
                            Toast.makeText(verifyidentity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(e.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void getcode() {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(this));//公共参数
        int bundle = getIntent().getIntExtra("type", 0);
        if (bundle == 0)
            maps.put("act", "modify_login_pwd");
        else if (bundle == 1)
            maps.put("act", "modify_pay_pwd");
        else if (bundle == 2)
            maps.put("act", "modify_service_mobile");
        else if (bundle == 3)
            maps.put("act", "modify_safe_mobile");

        Call<ResponseBody> getdata = serivce.sfyz_getcode(retrofit_Single.getInstence().getOpenid(verifyidentity.this), maps);
        getdata.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(
                    Call<ResponseBody> call, Response<ResponseBody> response) {
                String Result = null;
                if (response.body() == null)
                    return;
                try {
                    Result = response.body().string();
                    try {
                        if (Result != null && Result.startsWith("\ufeff")) {
                            Result = Result.substring(1);
                        }
                        JSONObject jsonObject = new JSONObject(Result);
                        BaseResult result = new BaseResult();

                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(verifyidentity.this, "发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(verifyidentity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(e.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    public interface verifyidentitysuc {
        void verifysucc();//身份验证成功
    }

    public static void setVer(verifyidentitysuc ver) {
        verifyidentity.ver = ver;
    }

}
