package com.shijiucheng.konghua.com.shijiucheng.konghua.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.shijiucheng.konghua.R;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.smtt.sdk.QbSdk;

import org.xutils.x;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Random;

import Retrofit2.retrofit_Single;

import static org.xutils.common.util.MD5.md5;


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
        XGPushConfig.enableOtherPush(this, true);
        XGPushConfig.setMzPushAppId(this, APP_ID);
        XGPushConfig.setMzPushAppKey(this, APP_KEY);
//        XGPushConfig.setHuaweiDebug(true);
        XGPushConfig.setMiPushAppId(getApplicationContext(), "2882303761517890832");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "5531789089832");

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

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        StubAppUtils.attachBaseContext(this);
//    }

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

    private void initX5WebView() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

}
