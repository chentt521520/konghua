package com.shijiucheng.konghua.Cmvp.loginmvp;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

public class LoginPresenerIml implements longincontract.IloginPresent {
    longincontract.Iloginview iloginview;
    longincontract.IloginModel iloginModel;

    public LoginPresenerIml(longincontract.Iloginview iloginview) {
        this.iloginview = iloginview;
        iloginModel = new LoginModeliml();
    }

    @Override
    public void startLogin(String cookie, String username, String password) {
        iloginModel.login(cookie, username, password, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                if (iloginview == null) {
                    return;
                }
                iloginview.showLoading();
            }

            @Override
            public void onNext(BaseResult result) {
                if (iloginview == null) {
                    return;
                }
                iloginview.closeLoading();
                if (result.getCode().equals("1")) {
                    iloginview.totoast("登录成功");
                    iloginview.toComple(result.getData());
                } else
                    iloginview.totoast("登录失败，请检查账号与密码是否有误");
            }

            @Override
            public void onError(Throwable errorMsg) {
                if (iloginview == null) {
                    return;
                }
                iloginview.closeLoading();
                iloginview.totoast("登录失败,未知错误");
            }
        });
    }

    @Override
    public void onDestroy() {
        iloginview = null;
    }
}
