package com.shijiucheng.konghua.Cmvp.DPAddressMvp;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

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
    public void getSSQ(String cook, final int pos, final String id) {
        idpAddressModle.getSSQ(cook, pos, id, new BaseCallbackListener<BaseResult>() {
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
                        List<String> list1 = new ArrayList<>(), list2 = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            list1.add(jsonObject1.getString("region_name"));
                            list2.add(jsonObject1.getString("region_id"));
                        }
                        idpAddressView.getAddress(pos, list1, list2);
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
