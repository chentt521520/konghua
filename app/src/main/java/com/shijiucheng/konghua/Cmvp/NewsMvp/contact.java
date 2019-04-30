package com.shijiucheng.konghua.Cmvp.NewsMvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.News_.znxada_data;

import java.util.List;

public class contact {
    //View
    public interface IView {
        void showloading();

        void closeloading();

        void showMsg(String msg);

        void showNews(int type, List<znxada_data> list);
    }

    //Modle
    public interface IModle {
        void getNews(Context context, String cook, int page, int type, String is_read, BaseCallbackListener<BaseResult> callbackListener);

        void getNotice(Context context,String cook, int page, int type, String is_read, BaseCallbackListener<BaseResult> callbackListener);
    }

    //Present
    public interface IPrestent extends BasePresenter {
        void getNews(Context context,String cook, int page, int type, String is_read);

        void getNotice(Context context,String cook, int page, int type, String is_read);
    }
}
