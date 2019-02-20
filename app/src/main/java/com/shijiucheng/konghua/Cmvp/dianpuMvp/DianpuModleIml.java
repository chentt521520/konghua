package com.shijiucheng.konghua.Cmvp.dianpuMvp;

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

public class DianpuModleIml implements Contact.IdianPuModle {
    Retro_Intf service;

    @Override
    public void uploadPic(int pos, String cook, String filestr, final BaseCallbackListener<BaseResult> baseResultBaseCallbackListener) {
        baseResultBaseCallbackListener.onStart();
        service = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("file_content", filestr);
        maps.putAll(retrofit_Single.getInstence().retro_postParameter());//公共参数
        Call<ResponseBody> call = service.uploadPic(cook, maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() == null)
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