package com.shijiucheng.konghua.Cmvp.grabOrderMvp;


import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.entity.GrabOrderDetail;


/**
 * 创建接口
 */
public class GrabOrderContact {
    /**
     * 界面请求数据
     */
    public interface IGrabOrderModel {
        void getOrderList(Context context, int page, String order_status, String sort, String order, BaseCallbackListener<BaseResult> callback);
    }

    /**
     * 界面更新
     */
    public interface IGrabOrderView {
        void showLoading();

        void closeLoading();

        void result(GrabOrderDetail ret);

        void toast(String msg);
    }

    /**
     * 网络请求数据
     */
    public interface IGrabOrderPresent extends BasePresenter {
        void startOrderList(Context context, int page, String order_status, String sort, String order);
    }
}
