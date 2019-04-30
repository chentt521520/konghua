package com.shijiucheng.konghua.Cmvp.OrderSYMVPNew;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseContact;
import com.shijiucheng.konghua.Cmvp.BaseResult;

import org.json.JSONException;
import org.json.JSONObject;

public class HomePagePresent implements BaseContact.BasePresent {
    BaseContact.baseview baseview;
    BaseContact.BaseModel model;

    public HomePagePresent(BaseContact.baseview baseview) {
        this.baseview = baseview;
        this.model = new HomePageModel();
    }

    @Override
    public void getData(Context context, String cook) {
        model.getData(context,cook, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                baseview.showLoad();
            }

            @Override
            public void onNext(BaseResult result) {
                if (result.getCode().equals("1")) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.getData());
                        baseview.showData(jsonObject);
                        baseview.closeLoad();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(result.getData());
                        baseview.showDataRenZhen(jsonObject);
                        baseview.closeLoad();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                baseview.closeLoad();
            }

            @Override
            public void onError(Throwable errorMsg) {
                baseview.closeLoad();
                baseview.showMsg("请求失败");
            }
        });
    }

    @Override
    public void onDestroy() {
        baseview = null;
    }
}
