package com.shijiucheng.konghua.Cmvp.OrderSYMVP;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;

import org.json.JSONObject;

public class SYContact {
    //View
    public interface SYView {
        void showLoad();

        void closeLoad();

        void showMsg(String msg);

        void showData(JSONObject jsonObject);
    }

    //modle
    public interface SYModle{
        void GetData(String cook, BaseCallbackListener<BaseResult> callbackListener);
    }

    //Prestent
    public  interface  SYPrestent extends BasePresenter {
        void GetData(String cook, BaseCallbackListener<BaseResult> callbackListener);
    }
}
