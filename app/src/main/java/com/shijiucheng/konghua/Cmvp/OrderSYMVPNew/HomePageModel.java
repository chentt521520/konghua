package com.shijiucheng.konghua.Cmvp.OrderSYMVPNew;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseContact;
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

public class HomePageModel implements BaseContact.BaseModel {
    @Override
    public void getData(Context context, String cook, BaseCallbackListener<BaseResult> callbackListener) {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(context));//公共参数
        Call<ResponseBody> call = serivce.getHomePageData(cook, maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String Result = null;
                if (response.body() == null)
                    return;
                try {
                    Result = response.body().string();
                    if (Result != null && Result.startsWith("\ufeff")) {
                        Result = Result.substring(1);
                    }
                    callbackListener.onStart();
                    JSONObject jso = new JSONObject(Result);
                    BaseResult baseResult = new BaseResult();

                    if (jso.getString("status").equals("1")) {
                        baseResult.setCode("1");
                        baseResult.setData(jso.getJSONObject("data").toString());
                    } else {
                        baseResult.setCode("0");
                        baseResult.setData(jso.toString());
                    }
                    callbackListener.onNext(baseResult);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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
