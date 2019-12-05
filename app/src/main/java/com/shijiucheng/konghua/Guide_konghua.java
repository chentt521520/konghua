package com.shijiucheng.konghua;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua_;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.internate_if;
import com.shijiucheng.konghua.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.MD5;

import java.io.File;
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

public class Guide_konghua extends BaseActivity_konghua_ implements Banben_.fuluebanben, internate_if.internetagain {

    Banben_ banben_ = new Banben_();
    internate_if internate_if = new internate_if();

    int INSTALL_PACKAGES_REQUEST_CODE = 0x001;

    @Override
    protected void AddView() {
        setCustomDensity(this, getApplication());
        DaoHangLan(this);
        requestorge();
    }


    public void getappvis() {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
                            int version=data.getInt("versionCode");
                            Log.e("~~~~~~~~~~~~~~",getVersion1()+"，，"+data.getInt("versionCode"));
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

    private void requestorge() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //读写权限
            if (isNetworkConnected(this)) {
                getappvis();
            } else {
                internate_if.show(getFragmentManager(), "xx");
            }

        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    //读写权限
                    if (isNetworkConnected(Guide_konghua.this)) {
                        getappvis();
                    } else {
                        internate_if.show(getFragmentManager(), "xx");
                    }

                }


                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    //用户拒绝了访问摄像头的申请
                    toaste_ut(Guide_konghua.this, "app没有读写权限无法下载，请在设置里面允许读写权限");
                }
            }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }


    public void getinstalapk(File file) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == INSTALL_PACKAGES_REQUEST_CODE) {

        }
    }

    @Override
    public void installapk(File file) {
        getinstalapk(file);
    }
}
