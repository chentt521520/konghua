package com.shijiucheng.konghua.main.per.mima;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class yewupho extends DialogFragment {

    int wxx = 0;
    Retro_Intf serivce;
    View view;

    Unbinder unbinder;
    @BindView(R.id.paypwd_edyzm)
    EditText paypho;
    @BindView(R.id.paypwd_timerte)
    TimerTextView paypwdTimerte;
    @BindView(R.id.ywpho_edmmre)
    EditText ywphoEdmmre;
    @BindView(R.id.ywpho_teok)
    TextView ywphoTeok;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        view = inflater.inflate(R.layout.yewupho, container, false);
        serivce = retrofit_Single.getInstence().getserivce(2);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);
        setviewlisten();
        return view;
    }

    private void setviewlisten() {
        paypwdTimerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(paypho.getText().toString()) || paypho.getText().toString().length() < 11) {
                    Toast.makeText(getActivity(), "请输入11位手机号", Toast.LENGTH_SHORT).show();
                    TimerTextView.isc = false;
                    return;
                }
                TimerTextView.isc = true;
                getCode();
            }
        });
        ywphoTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paypho.getText().toString().length() < 11) {
                    Toast.makeText(getActivity(), "请输入11位手机号", Toast.LENGTH_SHORT).show();
                    TimerTextView.isc = false;
                    return;
                }
                if (ywphoEdmmre.getText().toString().length() < 6) {
                    Toast.makeText(getActivity(), "请输入6位验证码", Toast.LENGTH_SHORT).show();
                    TimerTextView.isc = false;
                    return;
                }
                getpho();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public interface yewufrag {
        void yewufrag();

    }

    public void getpho() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("service_mobile", paypho.getText().toString());
        map.put("code", paypwdTimerte.getText().toString());
        Call<ResponseBody> call = serivce.yewu_pho(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(getActivity(), "更换成功", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else {
                            String msgx = jsonObject.getString("msg");

                            if ((msgx.equals("validate_code_is_error") )) {
                                Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT).show();
                            }else
                            if ((msgx.equals("validate_code_is_null") )) {
                                yewufrag yewufrag = (yewufrag) getActivity();
                                yewufrag.yewufrag();
                            } else
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

    public void getCode() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("service_mobile", paypho.getText().toString());
        Call<ResponseBody> call = serivce.yewu_getcode(retrofit_Single.getInstence().getOpenid(getActivity()), map);
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


}
