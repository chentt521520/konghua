package com.shijiucheng.konghua.Cmvp.loginmvp;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

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

public class LoginModeliml implements longincontract.IloginModel {
    BaseCallbackListener<BaseResult> callbackListener;
    Retro_Intf service1;

    @Override
    public void login(final String cookie, final String username, String password, final BaseCallbackListener<BaseResult> callbackListener) {
        this.callbackListener = callbackListener;
        callbackListener.onStart();
        service1 = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("user_name", username);
        maps.put("user_pwd", password);
        maps.putAll(retrofit_Single.getInstence().retro_postParameter());//公共参数
        Call<ResponseBody> login = service1.getLogin(cookie, maps);
        login.enqueue(new Callback<ResponseBody>() {
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
                        if (jsonObject.getString("status").equals("1"))
                            result.setCode("1");
                        else
                            result.setCode("0");
                        result.setData(jsonObject.toString());
                        callbackListener.onNext(result);
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
}
