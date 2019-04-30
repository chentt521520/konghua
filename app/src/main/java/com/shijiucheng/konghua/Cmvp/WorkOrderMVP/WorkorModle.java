package com.shijiucheng.konghua.Cmvp.WorkOrderMVP;

import android.content.Context;

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

public class WorkorModle implements WorkorContact.Modle {
    Retro_Intf serivcce;

    @Override
    public void getList(Context context, String keyword, String cook, int type, final int page, final BaseCallbackListener<BaseResult> callbackListener) {

        callbackListener.onStart();
        serivcce = retrofit_Single.getInstence().getserivce(2);
        Map<String, String> maps = new HashMap<>();
        if (type == 0)
            maps.put("search_work_order_status", "");
        else
            maps.put("search_work_order_status", "wait_reply");
        maps.put("page", page + "");
        maps.put("keywords", keyword);
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(context));

        Call<ResponseBody> call = serivcce.getWorkOrderList(cook, maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    BaseResult result = new BaseResult();
                    result.setCode("1");
                    result.setData(str);
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
