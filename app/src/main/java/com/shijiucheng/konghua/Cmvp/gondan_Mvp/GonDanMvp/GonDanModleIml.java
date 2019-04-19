package com.shijiucheng.konghua.Cmvp.gondan_Mvp.GonDanMvp;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GonDanModleIml implements Contact.GonDanIModle {
    Retro_Intf serivce;


    @Override
    public void applayMsg(String cook, String cate1, String cate2, String work_order_images, String work_order_content, final BaseCallbackListener<BaseResult> baseResultBaseCallbackListener) {
        baseResultBaseCallbackListener.onStart();
        serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap();
        map.put("cate1", cate1);
        map.put("cate2", cate2);
        map.put("work_order_images", work_order_images);
        map.put("work_order_content", work_order_content);
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call = serivce.WOuploadwork(cook, map);
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
                    baseResultBaseCallbackListener.onNext(result);
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

    @Override
    public void getQustion(String cook, final BaseCallbackListener<BaseResult> baseResultBaseCallbackListener) {
        baseResultBaseCallbackListener.onStart();
        serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call = serivce.getWoQuestion(cook, map);
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
                    baseResultBaseCallbackListener.onNext(result);
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

    @Override
    public void upLoadimg(String cook, String urlEnc, final BaseCallbackListener<BaseResult> baseResultBaseCallbackListener) {
        baseResultBaseCallbackListener.onStart();
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("file_content", urlEnc);
        Call<ResponseBody> call = serivce.WOuploadimg(cook, map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                if (response.body() == null){
                    BaseResult result = new BaseResult();
                    result.setCode("0");
                    result.setData("");
                    baseResultBaseCallbackListener.onNext(result);
                    return;}
                try {
                    String str = response.body().string();
                    BaseResult result = new BaseResult();
                    result.setCode("1");
                    result.setData(str);
                    baseResultBaseCallbackListener.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                baseResultBaseCallbackListener.onError(t);
            }
        });
    }
}
