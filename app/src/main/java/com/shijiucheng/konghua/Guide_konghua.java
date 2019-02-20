package com.shijiucheng.konghua;

import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.internate_if;
import com.shijiucheng.konghua.main.MainActivity;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.MD5;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Guide_konghua extends BaseActivity_konghua implements Banben_.fuluebanben, internate_if.internetagain {

    Banben_ banben_ = new Banben_();
    internate_if internate_if = new internate_if();

    @Override
    protected void AddView() {
        setCustomDensity(this, getApplication());
        DaoHangLan(this);
        if (isNetworkConnected(this)) {
            getappvis();
        } else {
            internate_if.show(getFragmentManager(), "xx");
        }
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            //从推送通知栏打开-Service打开Activity会重新执行Laucher流程
            //查看是不是全新打开的面板
            if (isTaskRoot()) {
                return;
            }
            finish();
        }
    }

    public void getappvis() {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call = serivce.appverison(retrofit_Single.getInstence().getOpenid(Guide_konghua.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strx = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(strx);
                        if (jso.getString("status").equals("1")) {
                            JSONObject data = jso.getJSONObject("data");
                            JSONObject jso_ = data
                                    .getJSONObject("android_new_version_info");
                            String str = jso_.getString("content").replace("br",
                                    "\n");
                            str = str.replace("nbsp", "  ");
                            String type = data.getString("type");// 1:不用强制更新，2：需要强制更新

                            String android_url = data.getString("android_url");
                            if (getVersion1() >= data.getInt("versionCode")) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivityByIntent(Guide_konghua.this, MainActivity.class);
                                        finish();
                                    }
                                }, 2000);
                            } else {
                                if (!banben_.isAdded()) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("txt", str);
                                    bundle.putString("type", type);
                                    bundle.putString("url", android_url);
                                    banben_.setArguments(bundle);
                                    if (!Guide_konghua.this.isFinishing())
                                        banben_.show(getFragmentManager(), "bb");
                                }
                            }

                        } else
                            toaste_ut(Guide_konghua.this, jso.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public String getVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    public int getVersion1() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }


    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    private static float sNoncompatDensity;
    private static float sNoncompatScaleDensity;

    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        sNoncompatDensity = appDisplayMetrics.density;
        sNoncompatScaleDensity = appDisplayMetrics.scaledDensity;
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                if (newConfig != null && newConfig.fontScale > 0) {
                    sNoncompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                }
            }

            @Override
            public void onLowMemory() {
            }
        });

        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaleDensity = targetDensity * (sNoncompatScaleDensity / sNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;

    }

    @Override
    public void fuluebanben() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityByIntent(Guide_konghua.this, MainActivity.class);
                finish();
            }
        }, 1000);
    }

    @Override
    public void internetagain() {
        if (isNetworkConnected(this)) {
            getappvis();
        } else {
            toaste_ut(this, "当前网络不可用,请连接有效网络重试");
        }
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    String jsonstr = "{\n" +
            "\t\"deviceId\": \"4e99fa45ad40757a0672e72d75582c4b\",\n" +
            "\t\"ticketType\": 0,\n" +
            "\t\"token\": \"5b458bbaba716587443ea20ef3c0fac209e1932b\",\n" +
            "\t\"errorCode\": 0,\n" +
            "\t\"np\": 1\n" +
            "}";


    public static String getSignature(Map<String, String> paramMap, String
            secret) {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>
                (paramMap);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {

            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        basestring.append(secret);
        // 使用MD5对待签名串求签
        return MD5.md5(basestring.toString());
    }

    /**
     * @param context 定制状态栏样色
     */
    public void DaoHangLan(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = ((Activity) context).getWindow();
            // 取消设置透明状态栏,使 ContentView 内容不再沉浸到状态栏下
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 设置状态栏颜色
            window.setStatusBarColor(Color.parseColor("#ee6e2d"));
        }
    }

}
