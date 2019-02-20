package com.shijiucheng.konghua.Cmvp.DPLianxiren;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

public class DPLxrPresentIml implements Contact.IDPLxrPresent {
    Contact.IDPLxrModle idpLxrModle;
    Contact.IDPLxrView idpLxrView;

    public DPLxrPresentIml(Contact.IDPLxrView idpLxrView) {
        this.idpLxrView = idpLxrView;
        idpLxrModle = new DPLxrModleIml();
    }

    @Override
    public void saveData(String name1, String pho1, String QQ) {
        idpLxrModle.saveData(name1, pho1, QQ, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                idpLxrView.showload();
            }

            @Override
            public void onNext(BaseResult result) {
                idpLxrView.closeload();
            }

            @Override
            public void onError(Throwable errorMsg) {
                idpLxrView.showtoast("保存失败");
            }
        });
    }

    @Override
    public void onDestroy() {
        idpLxrView = null;
    }
}
