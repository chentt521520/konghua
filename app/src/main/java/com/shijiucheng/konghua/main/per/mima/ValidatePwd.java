package com.shijiucheng.konghua.main.per.mima;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class ValidatePwd extends DialogFragment {
    int wxx = 0;
    Retro_Intf serivce;
    View view;
    @BindView(R.id.yz_edmm)
    EditText paypwdEdmm;
    @BindView(R.id.yz_teforget)
    TextView paypwdTeforget;
    @BindView(R.id.yz_yz)
    TextView paypwdYz;

    @BindView(R.id.zhangjia_tequxiao)
    TextView te_qx;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.validatepwd, container, false);
        unbinder = ButterKnife.bind(this, view);
        serivce = retrofit_Single.getInstence().getserivce(2);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - 60, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.yz_yz, R.id.yz_teforget, R.id.zhangjia_tequxiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yz_yz:
                String pwdstr = paypwdEdmm.getText().toString();
                if (pwdstr.length() < 6) {
                    Toast.makeText(getActivity(), "支付密码6-12位", Toast.LENGTH_SHORT).show();
                    return;
                }
                valitpwd();

                break;
            case R.id.yz_teforget:
                yanzhenpwd pwd = (yanzhenpwd) getActivity();
                pwd.showpaypwd();
                break;
            case R.id.zhangjia_tequxiao:
                dismiss();
                break;
        }
    }

    public void valitpwd() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(getActivity()));
        map.put("pay_pwd", paypwdEdmm.getText().toString());
        Call<ResponseBody> call = serivce.valitpwd(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(getActivity(), "验证成功", Toast.LENGTH_SHORT).show();
                            yanzhenpwd pwd = (yanzhenpwd) getActivity();
                            pwd.getYanZhenResult(jsonObject);
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

    @OnClick(R.id.yz_yz)
    public void onViewClicked() {
    }

    public interface yanzhenpwd {
        void showpaypwd();

        void getYanZhenResult(Object object);
    }
}
