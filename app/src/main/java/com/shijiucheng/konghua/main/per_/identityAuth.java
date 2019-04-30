package com.shijiucheng.konghua.main.per_;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.Cmvp.BaseResult;
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

/**
 * created 2018/9/12 0012 15:23
 * author ldl
 * 验证身份界面
 */
public class identityAuth extends DialogFragment {
    @BindView(R.id.sfyz_tepho)
    TextView sfyzTepho;
    @BindView(R.id.sfyz_edcode)
    EditText sfyzEdcode;
    @BindView(R.id.sfyz_teget)
    TimerTextView sfyzTeget;
    @BindView(R.id.sfyz_teok)
    TextView sfyzTeok;
    @BindView(R.id.sfyz_tequxiao)
    TextView sfyzTequxiao;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
//        setCancelable(false);//控制点击屏幕外和返回键，点击不消失
        View view = inflater.inflate(R.layout.identityauth, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        sfyzTepho.setText(bundle.getString("pho"));
        setViewListen();
        return view;
    }

    private void setViewListen() {
        sfyzTeget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerTextView.isc = true;
                getcode();
            }
        });
        sfyzTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(sfyzEdcode.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入短信验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                sfyz();
            }
        });
        sfyzTequxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void sfyz() {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(getActivity()));//公共参数
        Bundle bundle = getArguments();
        if (bundle.getInt("type") == 0)
            maps.put("act", "modify_login_pwd");
        else if (bundle.getInt("type") == 1)
            maps.put("act", "modify_pay_pwd");
        else if (bundle.getInt("type") == 2)
            maps.put("act", "modify_service_mobile");
        else if (bundle.getInt("type") == 3)
            maps.put("act", "modify_safe_mobile");

        maps.put("code", sfyzEdcode.getText().toString());
        Call<ResponseBody> getdata = serivce.sfyz(retrofit_Single.getInstence().getOpenid(getActivity()), maps);
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
                            Toast.makeText(getActivity(), "验证成功", Toast.LENGTH_SHORT).show();
                            yanzhensucc yanzhensucc = (identityAuth.yanzhensucc) getActivity();
                            yanzhensucc.getsucc();
                            dismiss();
                        } else {
                            Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(getActivity()));//公共参数
        Bundle bundle = getArguments();
        if (bundle.getInt("type") == 0)
            maps.put("act", "modify_login_pwd");
        else if (bundle.getInt("type") == 1)
            maps.put("act", "modify_pay_pwd");
        else if (bundle.getInt("type") == 2)
            maps.put("act", "modify_service_mobile");
        else if (bundle.getInt("type") == 3)
            maps.put("act", "modify_safe_mobile");

        Call<ResponseBody> getdata = serivce.sfyz_getcode(retrofit_Single.getInstence().getOpenid(getActivity()), maps);
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
                            Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
    public void show(FragmentManager manager, String tag) {
        //解决反复调用show方法抛出异常
        try {
            super.show(manager, tag);
        } catch (IllegalStateException ignore) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        getDialog().getWindow().setLayout(w - 50, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Bundle bundle = getArguments();
        sfyzTepho.setText(bundle.getString("pho"));
        sfyzEdcode.setText("");
    }


    public interface yanzhensucc {
        void getsucc();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TimerTextView.isc = false;
        unbinder.unbind();
    }

}
