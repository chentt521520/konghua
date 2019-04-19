package com.shijiucheng.konghua.main.per.mima;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;
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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayPwd extends DialogFragment {

    int wxx = 0;
    Retro_Intf serivce;
    View view;
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
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        view = inflater.inflate(R.layout.paypwd, container, false);
        serivce = retrofit_Single.getInstence().getserivce(2);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, (int) (wxx * 500 / 750.0));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                    Toast.makeText(getActivity(), "请输入6位验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (paypwdEdmm.getText().toString().length() < 6) {
                    Toast.makeText(getActivity(), "请输入6-12位密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (paypwdEdmmre.getText().toString().length() < 6) {
                    Toast.makeText(getActivity(), "请输入6-12位密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!paypwdEdmm.getText().toString().equals(paypwdEdmmre.getText().toString())) {
                    Toast.makeText(getActivity(), "前后密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                setpwd();
                break;
        }
    }

    public interface getpwddata {
        void getpwddatax();

    }

    public void getCode() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("act", "modify_pay_pwd");
        retrofit2.Call<ResponseBody> call = serivce.getCode(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(getActivity(), "短信发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
        retrofit2.Call<ResponseBody> call = serivce.setpwd(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(getActivity(), "设置新密码成功", Toast.LENGTH_SHORT).show();
                            getpwddata getpwddat = (getpwddata) getActivity();
                            getpwddat.getpwddatax();
                            dismiss();
                        } else {
                            Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
}
