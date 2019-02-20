package com.shijiucheng.konghua.Cmvp.OrderSYMVP;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;

public class SYModleIml implements SYContact.SYModle{
    @Override
    public void GetData(String cook, BaseCallbackListener<BaseResult> callbackListener) {
        callbackListener.onStart();
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);

        BaseResult result = new BaseResult();
        callbackListener.onNext(result);

    }
}
