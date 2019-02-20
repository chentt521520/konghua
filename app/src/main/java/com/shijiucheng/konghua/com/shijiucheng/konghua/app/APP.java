package com.shijiucheng.konghua.com.shijiucheng.konghua.app;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.xutils.x;

import Retrofit2.retrofit_Single;


public class APP extends MultiDexApplication {

    private static final String APP_ID = "116347";
    private static final String APP_KEY = "3081bb58b188412abee00b2eb4d7d5aa";

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        MultiDex.install(this);
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);

        String phone_chan = Build.BRAND.toLowerCase();
        XGPushConfig.enableOtherPush(this, true);
        if (phone_chan.contains("meizu")) {

            XGPushConfig.setMzPushAppId(this, APP_ID);
            XGPushConfig.setMzPushAppKey(this, APP_KEY);
        } else if (phone_chan.contains("xiaomi")) {
            XGPushConfig.setMiPushAppId(getApplicationContext(), "2882303761517890832");
            XGPushConfig.setMiPushAppKey(getApplicationContext(), "5531789089832");
        } else if (phone_chan.contains("huawei")) {
            XGPushConfig.setHuaweiDebug(true);
        }


        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变

                System.out.println("注册成功，设备token为：" + data);
                retrofit_Single.device_push_token = data.toString();

            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                System.out.println("注册失败，错误码：" + errCode + ",错误信息：" + msg);
                retrofit_Single.device_push_token = "";
            }
        });

        XGPushManager.bindAccount(this, "", new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {

            }

            @Override
            public void onFail(Object o, int i, String s) {

            }
        });

    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }
}
