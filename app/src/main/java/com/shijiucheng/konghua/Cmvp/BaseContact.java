package com.shijiucheng.konghua.Cmvp;

import android.content.Context;

import org.json.JSONObject;

public class BaseContact {
    public interface baseview {
        void showLoad();

        void closeLoad();

        void showMsg(String msg);

        void showData(JSONObject jsonObject);
        void showDataRenZhen(JSONObject jsonObject);
    }

    public interface BaseModel {
        void getData(Context context, String cook, BaseCallbackListener<BaseResult> callbackListener);
    }

    public interface BasePresent extends BasePresenter {
        void getData(Context context,String cook);
    }

}
