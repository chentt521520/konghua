package com.shijiucheng.konghua.Cmvp.loginmvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;

public class longincontract {
    //M
    public interface IloginModel {
        void login(Context context,String cookie, String username, String password, BaseCallbackListener<BaseResult> callbackListener);
    }

    //V
    public interface Iloginview {
        void showLoading();

        void closeLoading();

        void toComple(String json);

        void totoast(String msg);

    }

    //P
    public interface IloginPresent extends BasePresenter {
        void startLogin(Context context, String cookie, String username, String password);
    }

}
