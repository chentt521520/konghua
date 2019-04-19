package com.shijiucheng.konghua.main.per_.setingact;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;

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

public class changepwd extends BaseActivity_konghua implements verifyidentity.verifyidentitysuc {


    @BindView(R.id.changepwd_dh)
    DaoHang_top changepwdDh;
    @BindView(R.id.xgmm_edjmm)
    EditText xgmmEdjmm;
    @BindView(R.id.xgmm_edmmre)
    EditText xgmmEdmmre;
    @BindView(R.id.xgmm_edqrmre)
    EditText xgmmEdqrmre;
    @BindView(R.id.xgmm_teok)
    TextView xgmmTeok;

    public static loginmm loginmm;

    @Override
    protected void AddView() {
        changepwdDh.settext_("修改密码");
        verifyidentity.setVer(this);
    }

    @Override
    protected void SetViewListen() {

    }

    public static void setchangpwdinterface(loginmm loginmm) {
        changepwd.loginmm = loginmm;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_changepwd;
    }


    @OnClick(R.id.xgmm_teok)
    public void onViewClicked() {
        if (xgmmEdjmm.getText().toString().length() < 6) {
            Toast.makeText(changepwd.this, "密码长度不对", Toast.LENGTH_SHORT).show();
            return;
        }
        if (xgmmEdmmre.getText().toString().length() < 6) {
            Toast.makeText(changepwd.this, "请输入长度大于6位的新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!xgmmEdmmre.getText().toString().equals(xgmmEdqrmre.getText().toString())) {
            Toast.makeText(changepwd.this, "新密码前后不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isLetterDigit(xgmmEdmmre.getText().toString())) {
            Toast.makeText(changepwd.this, "新密码需要包含数字和字母", Toast.LENGTH_SHORT).show();
            return;
        }
        xiugaimm();
    }

    Retro_Intf serivce;

    public void xiugaimm() {
        serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("old_pwd", xgmmEdjmm.getText().toString());
        map.put("new_pwd", xgmmEdmmre.getText().toString());
        Call<ResponseBody> call = serivce.xiugaimm(retrofit_Single.getInstence().getOpenid(changepwd.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(changepwd.this, "修改成功", Toast.LENGTH_SHORT).show();
                            if (loginmm != null)
                                loginmm.loginmmsuc();
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);

                        } else {
                            String msgx = jsonObject.getString("msg");
                            if ((msgx.equals("validate_code_is_error"))) {
                                Toast.makeText(changepwd.this, "验证码错误", Toast.LENGTH_SHORT).show();
                            } else if ((msgx.equals("validate_code_is_null"))) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", 0);
                                bundle.putString("pho", getIntent().getExtras().getString("pho"));
                                startActivityByIntent(changepwd.this, verifyidentity.class, bundle);
                            } else
                                Toast.makeText(changepwd.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

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


    @Override
    public void verifysucc() {
        xiugaimm();
    }


    public interface loginmm {
        void loginmmsuc();//设置密码成功

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verifyidentity.ver = null;
    }
}
