package com.shijiucheng.konghua.Cmvp.PerMvp.PayAndGetMvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.per.payandget.szmxdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PGPrestentIml implements Contact.IPrestent {
    Contact.IModel model;
    Contact.IView view;

    public PGPrestentIml(Contact.IView view) {
        this.view = view;
        model = new PGModelIml();
    }

    @Override
    public void getList(Context context, String search_modification_status, String cook, int page) {
        model.getList(context,search_modification_status, cook, page, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                view.showLoad();
            }

            @Override
            public void onNext(BaseResult result) {
                try {
                    JSONObject jsonObject = new JSONObject(result.getData());
                    if (jsonObject.getString("status").equals("1")) {
                        String num = jsonObject.getJSONObject("data").getString("balance_amount") + "," + jsonObject.getJSONObject("data").getString("income_amount") + "," + jsonObject.getJSONObject("data").getString("expenditure_amount") + "," + jsonObject.getJSONObject("data").getString("freeze_amount");
                        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("rows");
                        List<szmxdata> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            list.add(new szmxdata(jsonObject1.getString("transaction_type_text"), jsonObject1.getString("transaction_amount"), jsonObject1.getString("transaction_desc"), jsonObject1.getString("add_time_text"), jsonObject1.getString("transaction_status_text")));
                        }
                        view.showList(num, list);
                    }
                    view.closeLoad();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable errorMsg) {
                view.showMsg(errorMsg.getMessage());
                view.closeLoad();
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
