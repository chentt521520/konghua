package com.shijiucheng.konghua.Cmvp.gondan_Mvp.GonDanMvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.per.payandget.gongdan.mygondandatabean;
import com.shijiucheng.konghua.main.per.payandget.gongdan.mygondandatabean1;

import java.util.List;

public class Contact {
    //View
    public interface GonDanIView {
        void shouwload();

        void closeload_();

        void showMsg(String msg);

        void finishApplay();

        void showQustion(List<mygondandatabean> list, List<mygondandatabean1> list1);

        void showUrl(String url);

    }

    //Modle
    public interface GonDanIModle {
        void applayMsg(Context context, String cook, String cate1, String cate2, String work_order_images, String work_order_content, BaseCallbackListener<BaseResult> baseResultBaseCallbackListener);

        void getQustion(Context context,String cook, BaseCallbackListener<BaseResult> baseResultBaseCallbackListener);

        void upLoadimg(Context context,String cook, String urlEnc, BaseCallbackListener<BaseResult> baseResultBaseCallbackListener);
    }

    //Prestent
    public interface GonDanIPrestent extends BasePresenter {
        void applayMsg(Context context,String cook, String cate1, String cate2, String work_order_images, String work_order_content);

        void getQustion(Context context,String cook);

        void upLoadimg(Context context,String cook, String urlEnc);
    }
}
