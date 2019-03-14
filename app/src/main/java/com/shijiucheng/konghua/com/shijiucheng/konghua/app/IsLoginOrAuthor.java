package com.shijiucheng.konghua.com.shijiucheng.konghua.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.shijiucheng.konghua.Login_konghua;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IsLoginOrAuthor implements Login_konghua.refresh, authen_RZ.refresh {
    private static IsLoginOrAuthor instence;
    Retro_Intf service1;

    public static refresh refresh;

    public static IsLoginOrAuthor getInstence() {
        if (instence == null) {
            synchronized (IsLoginOrAuthor.class) {
                if (instence == null) {
                    instence = new IsLoginOrAuthor();

                }
            }
        }
        return instence;
    }

    public IsLoginOrAuthor() {
        authen_RZ.setrefrxx(this);
        Login_konghua.setre(this);
    }

    public void goToLogin(Context context) {
        context.startActivity(new Intent(context, Login_konghua.class));
        ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);

    }

    public void goToAuthor(Context context) {
        context.startActivity(new Intent(context, authen_RZ.class));
        ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);

    }

    public void login(final Context context, String cookie, String name, String pwd) {
        service1 = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("user_name", name);
        maps.put("user_pwd", pwd);
        maps.putAll(retrofit_Single.getInstence().retro_postParameter());//公共参数
        Call<ResponseBody> getdata1 = service1.getLogin(cookie, maps);
        getdata1.enqueue(new Callback<ResponseBody>() {
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
                        if (jsonObject.getString("status").equals("1"))
                            refresh.refresh();
                        else {
                            goToLogin(context);
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
    public void refreshrz(Context context) {
        refresh.refresh();
    }

    @Override
    public void refresh1(Context context) {
        refresh.refresh();
    }

    public static void setfr(refresh refresh) {
        IsLoginOrAuthor.refresh = refresh;
    }

    //提供刷新状态接口
    public interface refresh {
        void refresh();
    }


}
