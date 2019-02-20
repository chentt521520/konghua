package com.shijiucheng.konghua.Cmvp.DPLianxiren;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;

public class Contact {
    //V
    public interface IDPLxrView {
        void showload();

        void closeload();

        void showtoast(String msg);

        void savedata();
    }

    //M
    public interface IDPLxrModle {
        void saveData(String name1, String pho1, String QQ, BaseCallbackListener<BaseResult> baseCallbackListener);
    }

    //P
    public interface IDPLxrPresent extends BasePresenter{
        void saveData(String name1, String pho1, String QQ);
    }

}
