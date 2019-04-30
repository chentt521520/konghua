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

public class loginPwd extends DialogFragment {

    int wxx = 0;
    Retro_Intf serivce;
    View view;

    Unbinder unbinder;
    @BindView(R.id.xgmm_edjmm)
    EditText xgmmEdjmm;
    @BindView(R.id.xgmm_edmmre)
    EditText xgmmEdmmre;
    @BindView(R.id.xgmm_edqrmre)
    EditText xgmmEdqrmre;
    @BindView(R.id.xgmm_teok)
    TextView xgmmTeok;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        view = inflater.inflate(R.layout.loginpwd, container, false);
        serivce = retrofit_Single.getInstence().getserivce(2);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);
        setviewlisten();
        return view;
    }

    private void setviewlisten() {
        xgmmTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xgmmEdjmm.getText().toString().length() < 6) {
                    Toast.makeText(getActivity(), "密码长度不对", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (xgmmEdmmre.getText().toString().length() < 6) {
                    Toast.makeText(getActivity(), "请输入长度大于6位的新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!xgmmEdmmre.getText().toString().equals(xgmmEdqrmre.getText().toString())) {
                    Toast.makeText(getActivity(), "新密码前后不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isLetterDigit(xgmmEdmmre.getText().toString())) {
                    Toast.makeText(getActivity(), "新密码需要包含数字和字母", Toast.LENGTH_SHORT).show();
                    return;
                }
                xiugaimm();
            }
        });
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


    public interface loginmm {
        void loginmm();

        void loginmmsuc();

    }

    public void xiugaimm() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(getActivity()));
        map.put("old_pwd", xgmmEdjmm.getText().toString());
        map.put("new_pwd", xgmmEdmmre.getText().toString());
        Call<ResponseBody> call = serivce.xiugaimm(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                            loginmm loginmm = (loginPwd.loginmm) getActivity();
                            loginmm.loginmmsuc();
                            dismiss();
                        } else {
                            String msgx = jsonObject.getString("msg");
                            if ((msgx.equals("validate_code_is_error") )) {
                                Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT).show();
                            }else
                            if ((msgx.equals("validate_code_is_null") )) {
                                yewupho.yewufrag yewufrag = (yewupho.yewufrag) getActivity();
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



    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;

    }

}
