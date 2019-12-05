package com.shijiucheng.konghua.Cmvp.grabOrderMvp;

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

public class GrabOrderModel implements GrabOrderContact.IGrabOrderModel {

    @Override
    public void getOrderList(Context context, int page, String order_status, String sort, String order, BaseCallbackListener<BaseResult> callback) {
        callback.onStart();
        Retro_Intf retro_intf = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("page",page+"");
        maps.put("order_status",order_status);
        maps.put("sort",sort);
        maps.put("order",order);

        maps.putAll(retrofit_Single.getInstence().retro_postParameter(context));
        String cookie = retrofit_Single.getInstence().getOpenid(context);
        Call<ResponseBody> call = retro_intf.getGrabOrder(cookie, maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response == null)
                        return;
                    String Result = response.body().string();
                    JSONObject jsonObject = new JSONObject(Result);
                    BaseResult result = new BaseResult();
                    result.setCode(jsonObject.getString("status"));
                    result.setData(jsonObject.getString("data"));
                    result.setMsg(jsonObject.getString("msg"));
                    callback.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
