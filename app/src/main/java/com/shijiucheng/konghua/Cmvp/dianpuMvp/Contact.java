package com.shijiucheng.konghua.Cmvp.dianpuMvp;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;

public class Contact {
    //V
    public interface IdianPuView {
        void shouload();

        void closeload();

        void showPic(int pos, String filestr);


        void saveDataSucc();

        void showToast(String msg);

    }

    //M
    public interface IdianPuModle {

        void uploadPic(int pos, String cook, String filestr, BaseCallbackListener<BaseResult> baseResultBaseCallbackListener);

    }

    //P
    public interface IdianPuPresent extends BasePresenter {

        void uploadPic(int pos, String cook, String filestr);


    }
}
