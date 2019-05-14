package Retrofit2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.shijiucheng.konghua.com.shijiucheng.konghua.app.APP;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.App;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class retrofit_Single {
    private volatile static retrofit_Single instence;
    SharedPreferences preferences;
    public static String device_push_token = "";

    String openid = "";


    public static retrofit_Single getInstence() {
        if (instence == null) {
            synchronized (retrofit_Single.class) {
                if (instence == null) {
                    instence = new retrofit_Single();
                }
            }
        }
        return instence;
    }

    public Retro_Intf getserivce(int baseUrlType) {
        Retro_Intf service;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                System.out.println("xxxxxx   " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.addInterceptor(loggingInterceptor).build();


        if (baseUrlType == 1) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.juandie.com") //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(ScalarsConverterFactory.create()) //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            service = retrofit.create(Retro_Intf.class);
        } else if (baseUrlType == 3) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://server-api-mzups.meizu.com") //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(ScalarsConverterFactory.create()) //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            service = retrofit.create(Retro_Intf.class);
        } else {
            //http://zd_store.konghua.com   http://test_zd_store.konghua.com
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://test_zd_store.konghua.com") //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(ScalarsConverterFactory.create()) //增加返回值为Gson的支持(以实体类返回)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(Retro_Intf.class);
        }
        return service;
    }

    public Map retro_postParameter(Context context) {//post的参数
        HashMap<String, String> maps = new HashMap<>();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        maps.put("timestamp", time);
        maps.put("sign", md5("vLr*1AdZvCC" + time));
        maps.put("openid", getOpenidpost(context));
        maps.put("device_os", "android");
        maps.put("device_os_channel", Build.BRAND.toLowerCase());
        maps.put("device_push_token", device_push_token);
        // TODO: 2019/4/19 0019 openid不一致
        return maps;
    }


    public String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getOpenid(Context context) {
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        openid = preferences.getString("openid", "000");
        if (openid.length() < 4) {
            SharedPreferences.Editor editor = preferences.edit();
            openid = getopenid();
            editor.putString("openid", openid);
            editor.commit();
        }
        return "PHPSESSID=" + openid;
    }

    public String getOpenidpost(Context context) {
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        openid = preferences.getString("openid", "000");
        if (openid.length() < 4) {
            SharedPreferences.Editor editor = preferences.edit();
            openid = getopenid();
            editor.putString("openid", openid);
            editor.commit();
        }
        return openid;
    }


    private String getopenid() {
        Random rd = new Random();
        String str = "";
        for (int i = 0; i < 20; i++) {
            str = str + (char) (Math.random() * 26 + 'a');
        }
        return md5(str + String.valueOf(System.currentTimeMillis() / 1000));
    }
}
