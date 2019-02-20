package com.shijiucheng.konghua.Cmvp.OrderSYMVP;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

public class SYPrestent implements SYContact.SYPrestent {
    SYContact.SYModle modle;
    SYContact.SYView syView;

    public SYPrestent(SYContact.SYView syView) {
        this.syView = syView;
        modle = new SYModleIml();
    }

    @Override
    public void GetData(String cook, BaseCallbackListener<BaseResult> callbackListener) {
        modle.GetData(cook, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                syView.showLoad();
            }

            @Override
            public void onNext(BaseResult result) {
                syView.closeLoad();
            }

            @Override
            public void onError(Throwable errorMsg) {
                syView.showMsg(errorMsg.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        syView = null;
    }
}
