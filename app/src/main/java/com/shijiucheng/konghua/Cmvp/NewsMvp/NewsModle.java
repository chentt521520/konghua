package com.shijiucheng.konghua.Cmvp.NewsMvp;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsModle implements contact.IModle {
    Retro_Intf service;

    @Override
    public void getNews(String cook, int page, int type, String is_read, final BaseCallbackListener<BaseResult> callbackListener) {
        callbackListener.onStart();
        service = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("is_read", is_read);
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call = service.getNews(cook, map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String data = response.body().string();
                    BaseResult result = new BaseResult();
                    result.setCode("1");
                    result.setData(data);
                    callbackListener.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackListener.onError(t);
            }
        });

    }

    @Override
    public void getNotice(String cook, int page, int type, String is_read, final BaseCallbackListener<BaseResult> callbackListener) {
        callbackListener.onStart();
        service = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("is_read", is_read);
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call = service.getNotice(cook, map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String data = response.body().string();
                    BaseResult result = new BaseResult();
                    result.setCode("1");
                    result.setData(data);
                    callbackListener.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackListener.onError(t);
            }
        });
    }
}
