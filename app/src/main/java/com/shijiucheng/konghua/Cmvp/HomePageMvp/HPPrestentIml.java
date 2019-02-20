package com.shijiucheng.konghua.Cmvp.HomePageMvp;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

import org.json.JSONException;
import org.json.JSONObject;

public class HPPrestentIml implements HPContact.HPPrestent {
    HPContact.HPModle modle;
    HPContact.HPView view;

    public HPPrestentIml(HPContact.HPView view) {
        this.view = view;
        modle = new HPModelIml();
    }

    @Override
    public void getData(String cookie) {
        modle.getData(cookie, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                view.showLoading();
            }

            @Override
            public void onNext(BaseResult result) {
                if (view == null) {
                    return;
                }
                view.closeLoading();
                if (result.getCode().equals("1")) {
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(result.getData());
                        if (jsonObject.getString("msg").contains("未认证") || jsonObject.getString("msg").contains("审核")) {
                            view.toRenZhen();
                        } else {
                            view.toLogin();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable errorMsg) {
                view.closeLoading();
            }
        });
    }


    @Override
    public void onDestroy() {
        view = null;
    }
}
