package com.shijiucheng.konghua.Cmvp.WorkOrderMVP;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.gdlistdata;

import java.util.List;

public class WorkorContact {
    //View
    public interface View {
        void showloading();

        void closeload();

        void showMsg(String msg);

        void showList(int type, List<gdlistdata> list);
    }

    //Modle
    public interface Modle {
        void getList(Context context, String keyword, String cook, int type, int page, BaseCallbackListener<BaseResult> callbackListener);

    }

    //Prestent
    public interface Prestent extends BasePresenter {
        void getList(Context context,String keyword, String cook, int type, int page);

    }
}
