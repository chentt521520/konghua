package com.shijiucheng.konghua.Cmvp.HomePageMvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;

public class HPContact {
    //View
    public interface HPView {
        void showLoading();

        void closeLoading();

        void showMsg(String msg);

        void toLogin();


        void toRenZhen();
    }

    //Modle
    public interface HPModle {
        void getData(Context context, String cookie, BaseCallbackListener<BaseResult> callbackListener);

    }

    //Prestent
    public interface HPPrestent extends BasePresenter {
        void getData(Context context,String cookie);
    }
}
