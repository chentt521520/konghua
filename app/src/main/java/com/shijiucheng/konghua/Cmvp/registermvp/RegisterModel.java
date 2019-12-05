package com.shijiucheng.konghua.Cmvp.registermvp;

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

public class RegisterModel implements RegisterContact.IRegisterModel {
    BaseCallbackListener<BaseResult> callbackListener;
    Retro_Intf retro_intf;

    @Override
    public void register(Context context, String shopName, String loginAccount, String loginPassword, String ownerName,
                         String ownerPhone, String ownerQQ, String inviteCode, String provinceId, String cityId, String districtId, String address, String deliveryIds, BaseCallbackListener<BaseResult> callbackListener) {
        callbackListener.onStart();
        retro_intf = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("store_name", shopName);
        maps.put("store_account", loginAccount);
        maps.put("store_pwd", loginPassword);
        maps.put("address", address);
        maps.put("store_master_uname", ownerName);
        maps.put("store_master_tel", ownerPhone);
        maps.put("store_master_qq", ownerQQ);
        maps.put("join_code", inviteCode);
        maps.put("province_id", provinceId);
        maps.put("city_id", cityId);
        maps.put("district_id", districtId);
        maps.put("delivery_district_ids", deliveryIds);
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(context));
        String cookie = retrofit_Single.getInstence().getOpenid(context);
        Call<ResponseBody> call = retro_intf.getRegister(cookie, maps);
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
                    callbackListener.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    callbackListener.onError(e);
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
    public void getDistrict(Context context, String cook, int pos, String parent_id, BaseCallbackListener<BaseResult> callbackListener) {
        callbackListener.onStart();
        retro_intf = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("parent_id", parent_id);
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
                    callbackListener.onError(e);
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

}
