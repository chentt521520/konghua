package com.shijiucheng.konghua.Cmvp.registermvp;

import android.content.Context;
import android.util.Log;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.entity.CityInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口结果处理
 */
public class RegisterPresenter implements RegisterContact.IRegisterPresent {
    RegisterContact.IRegisterView view;
    RegisterContact.IRegisterModel model;

    public RegisterPresenter(RegisterContact.IRegisterView view) {
        this.view = view;
        model = new RegisterModel();
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void startRegister(Context context, String shopName, String loginAccount, String loginPassword, String ownerName, String ownerPhone, String ownerQQ, String inviteCode, String provinceId, String cityId, String districtId, String address, String deliveryIds) {
        model.register(context, shopName, loginAccount, loginPassword, ownerName, ownerPhone, ownerQQ, inviteCode, provinceId, cityId, districtId, address, deliveryIds, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                if (view == null) {
                    return;
                }
                view.showLoading();
            }

            @Override
            public void onNext(BaseResult result) {
                if (view == null) {
                    return;
                }
                if (result.getCode().equals("1")) {
                    view.complete(null);
                } else {
                    view.toast(result.getMsg());
                }
            }

            @Override
            public void onError(Throwable errorMsg) {
                if (view == null) {
                    return;
                }
                view.toast("登录失败");
            }
        });
    }

    @Override
    public void getDistrict(Context context, String cook, int pos, String parent_id) {
        model.getDistrict(context, cook, pos, parent_id, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onNext(BaseResult result) {
                if (result.getCode().equals("1")) {
                    try {
                        Log.e("~~~~~~~~~", "result: " + result.getData());
                        JSONObject jsonObject = new JSONObject(result.getData());
                        JSONArray jsonArray = jsonObject.getJSONArray("region_list");
                        List<CityInfo> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            CityInfo cityInfo = new CityInfo(jsonObject1.getString("region_id"), jsonObject1.getString("region_name"));
                            list.add(cityInfo);
                        }
                        view.getAddress(pos, list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(Throwable errorMsg) {

            }
        });
    }
}
