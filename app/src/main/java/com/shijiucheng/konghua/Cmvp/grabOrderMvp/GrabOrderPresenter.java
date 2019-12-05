package com.shijiucheng.konghua.Cmvp.grabOrderMvp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.entity.GrabOrderDetail;


public class GrabOrderPresenter implements GrabOrderContact.IGrabOrderPresent {

    private GrabOrderContact.IGrabOrderView view;
    private GrabOrderContact.IGrabOrderModel model;

    public GrabOrderPresenter(GrabOrderContact.IGrabOrderView view) {
        this.view = view;
        this.model = new GrabOrderModel();
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void startOrderList(Context context, int page, String order_status, String sort, String order) {
        model.getOrderList(context, page, order_status, sort, order, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                view.closeLoading();
            }

            @Override
            public void onNext(BaseResult result) {
                view.closeLoading();
                if (result.getCode().equals("1")) {
                    Log.e("~~~~~~~~~", "result: " + result.getData());
                    Gson gson = new Gson();
                    GrabOrderDetail orderDetail = gson.fromJson(result.getData(), GrabOrderDetail.class);
                    view.result(orderDetail);
                } else {
                    view.toast(result.getMsg());
                }
            }

            @Override
            public void onError(Throwable errorMsg) {
                view.toast(errorMsg.getMessage());
            }
        });
    }
}
