package com.shijiucheng.konghua.com.shijiucheng.konghua.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.xutils.x;

import java.util.LinkedList;

import Retrofit2.retrofit_Single;


public class APP extends MultiDexApplication {

    private static final String APP_ID = "116347";
    private static final String APP_KEY = "3081bb58b188412abee00b2eb4d7d5aa";
    public static LinkedList<Activity> activityLinkedList;

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


        activityLinkedList = new LinkedList<>();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                activityLinkedList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityLinkedList.remove(activity);
            }
        });

    }


    public void exitApp() {
        Log.d("app", "容器内的Activity列表如下 ");
        // 先打印当前容器内的Activity列表
        for (Activity activity : activityLinkedList) {
            Log.d("app", activity.getLocalClassName());
        }
        Log.d("app", "依次所有Activity");
        // 逐个退出Activity
        for (Activity activity : activityLinkedList) {
            activity.finish();
        } // 结束进程 //
        System.exit(0);
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
