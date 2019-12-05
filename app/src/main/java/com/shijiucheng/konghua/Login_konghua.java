package com.shijiucheng.konghua;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.loginmvp.LoginPresenerIml;
import com.shijiucheng.konghua.Cmvp.loginmvp.longincontract;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.APP;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_konghua extends com.shijiucheng.konghua.Cmvp.BaseActivity_konghua implements longincontract.Iloginview {


    @BindView(R.id.login_imhead)
    ImageView loginImhead;
    @BindView(R.id.login_edZHn)
    EditText loginEdZHn;
    @BindView(R.id.login_edmmn)
    EditText loginEdmmn;
    @BindView(R.id.login_te)
    TextView loginTe;
    @BindView(R.id.login_register)
    TextView btnRegister;
    @BindView(R.id.login_forget_pwd)
    TextView login_forget_pwd;
    final longincontract.IloginPresent present = new LoginPresenerIml(this);

    private APP app;

    @Override
    protected void AddView() {
        app = (APP) getApplication();
    }

    @Override
    protected void SetViewListen() {
        loginTe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(loginEdZHn.getText().toString())) {
                    if (!TextUtils.isEmpty(loginEdmmn.getText().toString())) {
                        if (fastClick())
                            getquit();
                    } else
                        toaste_ut(Login_konghua.this, "请输入密码");
                } else
                    toaste_ut(Login_konghua.this, "请输入正确的账号");

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityByIntent(Login_konghua.this, Register_konghua.class);
            }
        });
        login_forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityByIntent(Login_konghua.this, ForgetPwd_konghua.class);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login_konghua;
    }

    @Override
    protected BasePresenter bindPresent() {
        return present;
    }


    @Override
    public void showLoading() {
        if (jdt != null)
            if (!jdt.isAdded())
                jdt.show(getFragmentManager(), "login");
    }

    @Override
    public void closeLoading() {
        if (jdt != null)
            if (jdt.isAdded())
                jdt.dismiss();
    }

    @Override
    public void toComple(String json) {
        sharePre("name", loginEdZHn.getText().toString(), this);
        sharePre("pwd", loginEdmmn.getText().toString(), this);
//        sharePre("pwd", loginEdmmn.getText().toString(), this);

        if (!(json.contains("未") || json.contains("不"))) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject jso = jsonObject.getJSONObject("data").getJSONObject("store_info");
                sharePre("latitude", jso.getString("latitude"), Login_konghua.this);
                sharePre("longitude", jso.getString("longitude"), Login_konghua.this);
                sharePre("user_name", jso.getString("company_name"), Login_konghua.this);//用户名
                sharePre("user_pic", jso.getString("facade_photo1"), Login_konghua.this);//门店照片1

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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

    Retro_Intf retro_intf = retrofit_Single.getInstence().getserivce(2);

    private void getquit() {
        HashMap<String, String> maps = new HashMap<>();
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(this));//公共参数
        Call<ResponseBody> getdata = retro_intf.quitLoigin(retrofit_Single.getInstence().getOpenid(Login_konghua.this), maps);
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
                        sharePre("name", "0", Login_konghua.this);
                        sharePre("pwd", "0", Login_konghua.this);
                        present.startLogin(Login_konghua.this, retrofit_Single.getInstence().getOpenid(Login_konghua.this), loginEdZHn.getText().toString(), loginEdmmn.getText().toString());
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
    public void totoast(String msg) {
        toaste_ut(this, msg);
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            refresh.refresh1(Login_konghua.this);
//            finish();
//            overridePendingTransition(R.anim.push_right_out,
//                    R.anim.push_right_in);
//            return false;
//        }
//        return false;
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次，退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {

            app.exitApp();
        }
    }
}
