package com.shijiucheng.konghua.Cmvp.dianZhuMvp;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;

public class Contact {
    //V
    public interface IDianZuView {
        void showload();

        void closeload();

        void showtoast(String msg);

        //保存数据后返回
        void gotosave();

        void savePicurl(String url, String key);

    }

    //P
    public interface IDianZuPresent extends BasePresenter {

        //保存当前数据到sql
        void saveData(String name, String pho, String qq, String idcar, String pic1, String pic2);

        //上传图片 key表示存储的图片key值
        void uploadPic(String key, String cook, String urlStr);
    }

    //M
    public interface IDianZuModle {

        void saveData(String name, String pho, String qq, String idcar, String pic1, String pic2, BaseCallbackListener<BaseResult> baseResultBaseCallbackListener);

        void uploadPic(String key, String cook, String urlStr, BaseCallbackListener<BaseResult> baseResultBaseCallbackListener);
    }
}
