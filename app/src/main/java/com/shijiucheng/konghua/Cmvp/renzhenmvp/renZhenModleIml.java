package com.shijiucheng.konghua.Cmvp.renzhenmvp;

import android.content.Context;

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

public class renZhenModleIml implements conttract.IrenzhenModle {
    BaseCallbackListener<BaseResult> resultBaseCallbackListener;
    Retro_Intf service;


    @Override
    public void applayRZ(Context context, String cook, final HashMap<String, String> map, final BaseCallbackListener<BaseResult> resultBaseCallbackListener) {
        this.resultBaseCallbackListener = resultBaseCallbackListener;
        resultBaseCallbackListener.onStart();
        service = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(context));//公共参数
        maps.putAll(map);
        Call<ResponseBody> call = service.AutherCommit(cook, maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String Result = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(Result);
                        BaseResult result = new BaseResult();
                        result.setCode(jsonObject.getString("status"));
                        result.setData(Result);
                        resultBaseCallbackListener.onNext(result);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    resultBaseCallbackListener.onError(e);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                resultBaseCallbackListener.onError(t);
            }
        });
    }

    @Override
    public void getAuthorData(Context context,String cook, final BaseCallbackListener<BaseResult> callbackListener) {
        callbackListener.onStart();
        service = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(context));//公共参数
        Call<ResponseBody> call = service.getAuthorData(cook, maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String Result = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(Result);
                        BaseResult result = new BaseResult();
                        result.setCode(jsonObject.getString("status"));
                        result.setData(Result);
                        callbackListener.onNext(result);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
