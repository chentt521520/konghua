package com.shijiucheng.konghua.Cmvp.DPLianxiren;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

public class DPLxrModleIml implements Contact.IDPLxrModle {
    @Override
    public void saveData(String name1, String pho1, String QQ, BaseCallbackListener<BaseResult> baseCallbackListener) {
        baseCallbackListener.onStart();
        BaseResult result = new BaseResult();
        result.setCode("1");
        result.setData("xx");
        baseCallbackListener.onNext(result);
    }
}
