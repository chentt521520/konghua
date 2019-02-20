package com.shijiucheng.konghua.Cmvp.PerMvp.PayAndGetMvp;

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

public class PGModelIml implements Contact.IModel {
    Retro_Intf serivce;

    @Override
    public void getList(String search_modification_status,String cook, int page, final BaseCallbackListener<BaseResult> callbackListener) {
        callbackListener.onStart();
        serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("page", page + "");
        map.put("search_modification_status", search_modification_status);
        Call<ResponseBody> call = serivce.getPGList(cook, map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    BaseResult result = new BaseResult();
                    result.setCode("1");
                    result.setData(str);
                    callbackListener.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    callbackListener.onError(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackListener.onError(t);
            }
        });
    }
}
