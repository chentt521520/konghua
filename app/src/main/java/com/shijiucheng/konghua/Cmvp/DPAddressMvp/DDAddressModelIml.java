package com.shijiucheng.konghua.Cmvp.DPAddressMvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DDAddressModelIml implements Contact.IdpAddressModle {
    Retro_Intf retro_intf;

    @Override
    public void getSSQ(Context context, String cook, int pos, String id, final BaseCallbackListener<BaseResult> callbackListener) {
        callbackListener.onStart();
        retro_intf = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("parent_id", id);
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(context));
        Call<ResponseBody> call = retro_intf.getAddress(cook, maps);
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
                    callbackListener.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    callbackListener.onError(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void saveData(String ssq, String address, String zuobiao, List<String> listArea, BaseCallbackListener<BaseResult> callbackListener) {

    }
}
