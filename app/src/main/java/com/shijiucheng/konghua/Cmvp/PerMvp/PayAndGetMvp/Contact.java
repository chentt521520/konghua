package com.shijiucheng.konghua.Cmvp.PerMvp.PayAndGetMvp;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.per.payandget.szmxdata;

import java.util.List;

public class Contact extends com.shijiucheng.konghua.Cmvp.payAndGet.Contact {
    //View
    public interface IView {
        void showLoad();

        void closeLoad();

        void showMsg(String msg);

        void showList(String num,List<szmxdata> list);
    }

    //Prestent
    public interface IPrestent extends BasePresenter{
        void getList(String search_modification_status,String cook, int page);

    }

    //Model
    public interface IModel {
        void getList(String search_modification_status,String cook, int page, BaseCallbackListener<BaseResult> callbackListener);

    }

}
