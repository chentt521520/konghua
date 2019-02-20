package com.shijiucheng.konghua.Cmvp.DPAddressMvp;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;

import java.util.List;

public class Contact {
    //V
    public interface IdpAddressView {
        void showload();

        void closeload();


        void showToast(String msg);

        void getAddress(int pos, List ssq, List ssqid);

    }

    //M
    public interface IdpAddressModle {
        //省市区谢信息
        void getSSQ(String cook, int pos, String id, BaseCallbackListener<BaseResult> callbackListener);

        void saveData(String ssq, String address, String zuobiao, List<String> listArea, BaseCallbackListener<BaseResult> callbackListener);
    }

    //P
    public interface IdpAddressPrestent extends BasePresenter {
        void getSSQ(String cook, int pos, String id);

        void saveData(String ssq, String address, String zuobiao, List<String> listArea);
    }
}
