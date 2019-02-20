package com.shijiucheng.konghua.main.per_;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.IsLoginOrAuthor;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.per.mima.PayPwd;
import com.shijiucheng.konghua.main.per.mima.loginPwd;
import com.shijiucheng.konghua.main.per.mima.safepho;
import com.shijiucheng.konghua.main.per.mima.yewupho;

import org.greenrobot.eventbus.EventBus;
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

public class Setings extends BaseActivity_konghua implements identityAuth.yanzhensucc, IsLoginOrAuthor.refresh, PayPwd.getpwddata, loginPwd.loginmm, yewupho.yewufrag, safepho.loginmm {
    @BindView(R.id.setig_dh)
    DaoHang_top setigDh;
    @BindView(R.id.seting_quit)
    TextView setingQuit;
    Retro_Intf retro_intf;
    @BindView(R.id.setig_temm)
    TextView setigTemm;
    @BindView(R.id.setig_temmpay)
    TextView setigTemmpay;
    @BindView(R.id.setig_temmpay1)
    TextView setigTemmpay1;
    @BindView(R.id.setig_teywpho)
    TextView setigTeywpho;
    @BindView(R.id.setig_teywpho1)
    TextView setigTeywpho1;
    @BindView(R.id.setig_teywphots)
    TextView setigTeywphots;
    @BindView(R.id.setig_teaqpho)
    TextView setigTeaqpho;
    @BindView(R.id.setig_teaqpho1)
    TextView setigTeaqpho1;
    @BindView(R.id.setig_teaqphots)
    TextView setigTeaqphots;

    identityAuth identityAuth;
    int type = 0;//0通过且是修改密码的操作，1通过是支付密码的操作,2业务手机3安全手机
    PayPwd payPwd;
    com.shijiucheng.konghua.main.per.mima.loginPwd loginPwd;
    yewupho yewupho;
    safepho safepho;
    String pho = "";


    @Override
    protected void AddView() {
        identityAuth = new identityAuth();
        payPwd = new PayPwd();
        loginPwd = new loginPwd();
        yewupho = new yewupho();
        safepho = new safepho();

        setigDh.settext_("设置中心");
        retro_intf = retrofit_Single.getInstence().getserivce(2);
        IsLoginOrAuthor.setfr(this);
        getinfo(0);
    }

    @Override
    protected void SetViewListen() {
        setigTemm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginPwd.isAdded()) {
                    type = 0;
                    loginPwd.show(getSupportFragmentManager(), "pwd");
                }
            }
        });
        setigTemmpay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!payPwd.isAdded()) {
                    type = 1;
                    payPwd.show(getSupportFragmentManager(), "pwd");
                }
            }
        });
        setigTeywpho1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!yewupho.isAdded()) {
                    type = 2;
                    yewupho.show(getSupportFragmentManager(), "pwd");
                }
            }
        });
        setigTeaqpho1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!safepho.isAdded()) {
                    type = 3;
                    safepho.show(getSupportFragmentManager(), "pwd");
                }
            }
        });

        setingQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    getquit();
                }
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_setings;
    }


    @Override
    public void getsucc() {
        //验证身份通过
        if (type == 0) {
            if (loginPwd.isAdded()) {
                loginPwd.xiugaimm();
            }
            return;
        }
        if (type == 2) {
            if (yewupho.isAdded()) {
                yewupho.getpho();
            }
            return;
        }
        if (type == 3) {
            if (safepho.isAdded()) {
                safepho.getpho();
            }
            return;
        }
    }


    private void getinfo(final int typ) {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "jdt");
        HashMap<String, String> maps = new HashMap<>();
        maps.putAll(retrofit_Single.getInstence().retro_postParameter());//公共参数
        Call<ResponseBody> getdata = retro_intf.setinginter(retrofit_Single.getInstence().getOpenid(Setings.this), maps);
        getdata.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(
                    Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt.isAdded()) jdt.dismiss();
                String Result = null;
                if (response.body()==null)
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
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (data.getJSONObject("account_security_info").getString("is_set_pay_pwd").equals("0")) {
                                setigTemmpay.setText("未设置");
                                setigTemmpay1.setText("|添加");
                            } else {
                                setigTemmpay.setText("已设置");
                                setigTemmpay1.setText("|修改");
                            }
                            setigTeywphots.setText(" 该手机号码用于接收日常业务消息，方便订单及时处理，当前接收手机" + data.getJSONObject("account_security_info").getString("service_mobile_text"));
                            setigTeaqphots.setText(" 该手机号码用于接收资金、安全消息，防止恶意操作敏感数据，当前接收手机" + data.getJSONObject("account_security_info").getString("service_mobile_text"));
                            pho = data.getJSONObject("account_security_info").getString("service_mobile_text");
                            if (typ == 1) {
                                if (!identityAuth.isAdded()) {
                                    if (pho.length() > 0) {
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("type", type);
                                        bundle.putString("pho", pho);
                                        identityAuth.setArguments(bundle);
                                        identityAuth.show(getFragmentManager(), "sfyz");
                                    }
                                }
                            }
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
                if (jdt.isAdded()) jdt.dismiss();
            }
        });

    }

    private void getquit() {
        HashMap<String, String> maps = new HashMap<>();
        maps.putAll(retrofit_Single.getInstence().retro_postParameter());//公共参数
        Call<ResponseBody> getdata = retro_intf.quitLoigin(retrofit_Single.getInstence().getOpenid(Setings.this), maps);
        getdata.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(
                    Call<ResponseBody> call, Response<ResponseBody> response) {
                String Result = null;
                if (response.body()==null)
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
                            toaste_ut(Setings.this, jsonObject.getString("msg"));
                            sharePre("name", "0", Setings.this);
                            sharePre("pwd", "0", Setings.this);

                            IsLoginOrAuthor.getInstence().goToLogin(Setings.this);
                        } else {
                            if (jsonObject.getString("msg").contains("未登录")) {
                                IsLoginOrAuthor.getInstence().goToLogin(Setings.this);
                                toaste_ut(Setings.this, jsonObject.getString("msg"));
                            } else toaste_ut(Setings.this, jsonObject.getString("msg"));
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

    @Override
    public void refresh() {
        if (!getSharePre("name", Setings.this).equals("0")) {
            if (getSharePre("isauther", Setings.this).equals("0"))
                IsLoginOrAuthor.getInstence().goToAuthor(Setings.this);
            else {
                IsLoginOrAuthor.refresh = null;
                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.perRrefresh);
                EventBus.getDefault().post(databean);
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        } else {

            IsLoginOrAuthor.getInstence().goToLogin(Setings.this);
        }
    }

    @Override
    public void getpwddatax() {
        getinfo(0);
    }

    @Override
    public void loginmm() {
        getinfo(1);
    }

    @Override
    public void loginmmsuc() {
        //修改登录密码成功
        getquit();
    }

    @Override
    public void yewufrag() {
        getinfo(1);
    }
}
