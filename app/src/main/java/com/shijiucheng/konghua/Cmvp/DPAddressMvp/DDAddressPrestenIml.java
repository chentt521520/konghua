package com.shijiucheng.konghua.Cmvp.DPAddressMvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.entity.CityInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DDAddressPrestenIml implements Contact.IdpAddressPrestent {
    Contact.IdpAddressView idpAddressView;
    Contact.IdpAddressModle idpAddressModle;

    public DDAddressPrestenIml(Contact.IdpAddressView idpAddressView) {
        this.idpAddressView = idpAddressView;
        idpAddressModle = new DDAddressModelIml();
    }

    @Override
    public void getSSQ(Context context, String cook, final int pos, final String id) {
        idpAddressModle.getSSQ(context,cook, pos, id, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                idpAddressView.showload();

            }

            @Override
            public void onNext(BaseResult result) {
                idpAddressView.closeload();
                if (result.getCode().equals("1")) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.getData());
                        JSONArray jsonArray = jsonObject.getJSONArray("region_list");
                        List<CityInfo> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            list.add(new CityInfo(jsonObject1.getString("region_id"),jsonObject1.getString("region_name")));
                        }
                        idpAddressView.getAddress(pos, list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(Throwable errorMsg) {
                idpAddressView.closeload();
            }
        });
    }

    @Override
    public void saveData(String ssq, String address, String zuobiao, List<String> listArea) {
        idpAddressModle.saveData(ssq, address, zuobiao, listArea, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onNext(BaseResult result) {
            }

            @Override
            public void onError(Throwable errorMsg) {

            }
        });
    }


    @Override
    public void onDestroy() {
        idpAddressView = null;
    }
}
