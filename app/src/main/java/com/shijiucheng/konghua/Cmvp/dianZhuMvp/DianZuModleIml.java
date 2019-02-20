package com.shijiucheng.konghua.Cmvp.dianZhuMvp;

import android.os.Handler;

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

public class DianZuModleIml implements Contact.IDianZuModle {
    BaseCallbackListener<BaseResult> baseResultBaseCallbackListener;
    Retro_Intf service;


    @Override
    public void saveData(final String name, String pho, String qq, String idcar, String pic1, String pic2, final BaseCallbackListener<BaseResult> baseResultBaseCallbackListener) {
        this.baseResultBaseCallbackListener = baseResultBaseCallbackListener;
        baseResultBaseCallbackListener.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BaseResult result = new BaseResult();
                result.setCode("pic");
                result.setData("xx");
                baseResultBaseCallbackListener.onNext(result);
            }
        }, 1000);
    }

    @Override
    public void uploadPic(String key, String cook, String urlStr, final BaseCallbackListener<BaseResult> baseResultBaseCallbackListener) {
        baseResultBaseCallbackListener.onStart();
        service = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("file_content", urlStr);
        maps.putAll(retrofit_Single.getInstence().retro_postParameter());//公共参数
        Call<ResponseBody> call = service.uploadPic(cook, maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response == null)
                        return;
                    String Result = response.body().string();
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
                        baseResultBaseCallbackListener.onNext(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(e.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    baseResultBaseCallbackListener.onError(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
