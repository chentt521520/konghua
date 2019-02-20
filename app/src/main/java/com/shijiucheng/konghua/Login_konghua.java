package com.shijiucheng.konghua;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.loginmvp.LoginPresenerIml;
import com.shijiucheng.konghua.Cmvp.loginmvp.longincontract;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import Retrofit2.retrofit_Single;
import butterknife.BindView;

public class Login_konghua extends com.shijiucheng.konghua.Cmvp.BaseActivity_konghua implements longincontract.Iloginview {


    @BindView(R.id.login_imhead)
    ImageView loginImhead;
    @BindView(R.id.login_edZHn)
    EditText loginEdZHn;
    @BindView(R.id.login_edmmn)
    EditText loginEdmmn;
    @BindView(R.id.login_te)
    TextView loginTe;
    final longincontract.IloginPresent present = new LoginPresenerIml(this);
    static refresh refresh;

    @Override
    protected void AddView() {

    }

    @Override
    protected void SetViewListen() {
        loginTe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(loginEdZHn.getText().toString())) {
                    if (!TextUtils.isEmpty(loginEdmmn.getText().toString())) {
                        if (fastClick())
                            present.startLogin(retrofit_Single.getInstence().getOpenid(Login_konghua.this), loginEdZHn.getText().toString(), loginEdmmn.getText().toString());
                    } else
                        toaste_ut(Login_konghua.this, "请输入密码");
                } else
                    toaste_ut(Login_konghua.this, "请输入正确的账号");

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

        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.orderSYrefr);
        EventBus.getDefault().post(databean);
        if (json.contains("未") || json.contains("不")) {
            sharePre("isauther", "0", Login_konghua.this);
            sharePre("name", loginEdZHn.getText().toString(), Login_konghua.this);
            sharePre("pwd", loginEdmmn.getText().toString(), Login_konghua.this);
            startActivityByIntent(Login_konghua.this, authen_RZ.class);
            finish();
        } else {
            sharePre("isauther", "1", Login_konghua.this);
            sharePre("name", loginEdZHn.getText().toString(), Login_konghua.this);
            sharePre("pwd", loginEdmmn.getText().toString(), Login_konghua.this);

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject jso = jsonObject.getJSONObject("data").getJSONObject("store_info");
                sharePre("latitude", jso.getString("latitude"), Login_konghua.this);
                sharePre("longitude", jso.getString("longitude"), Login_konghua.this);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            refresh.refresh1(Login_konghua.this);
            finish();
        }
    }


    @Override
    public void totoast(String msg) {
        toaste_ut(this, msg);
    }

    public static void setre(refresh refresh) {
        Login_konghua.refresh = refresh;
    }

    //提供刷新状态接口
    public interface refresh {
        void refresh1(Context context);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            refresh.refresh1(Login_konghua.this);
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
            return false;
        }
        return false;
    }
}
