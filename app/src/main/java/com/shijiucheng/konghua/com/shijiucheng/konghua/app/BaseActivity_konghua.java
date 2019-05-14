package com.shijiucheng.konghua.com.shijiucheng.konghua.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.shijiucheng.konghua.R;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

public abstract class BaseActivity_konghua extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    protected int w_, h_;

    Unbinder mubinder;
    public progressbar_ jdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImmersionBar immersionBar = ImmersionBar.with(this);//要先初始化
        immersionBar.fitsSystemWindows(true).statusBarColor(R.color.zhu)
                .statusBarDarkFont(false, 0.0f).init();
        setContentView(getLayout());
        DaoHangLan(this);

        mubinder = ButterKnife.bind(this);
        SetViewListen();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        w_ = dm.widthPixels;
        h_ = dm.heightPixels;

        jdt = new progressbar_();
        AddView();
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
            window.setStatusBarColor(Color.parseColor("#ec702d"));
        }
    }

    /**
     * @param context
     * @param cls
     * @param isFinish 跳转方法
     *                 isfinish是否结束act
     */
    protected void startActivityByIntent(Context context, Class<?> cls, Boolean isFinish, Bundle data) {
        Intent i = new Intent();
        i.setClass(context, cls);
        i.putExtras(data);
        startActivity(i);
        overridePendingTransition(R.anim.push_right_out,
                R.anim.push_right_in);
        if (isFinish) {
            finish();
        }
    }

    protected void startActivityByIntent(Context context, Class<?> cls) {
        Intent i = new Intent();
        i.setClass(context, cls);
        startActivity(i);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    protected void startActivityByIntent(Context context, Class<?> cls, Bundle bundle) {
        Intent i = new Intent();
        i.setClass(context, cls);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        startActivity(i);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    long lastClick = 0;

    /**
     * @return 判断是否快速点击
     * true不是快速点击
     */
    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * @param context
     * @param str     提示语显示
     */
    protected void toaste_ut(Context context, String str) {

//        Toast toast = new Toast(context);
//        toast.setGravity(Gravity.CENTER,0,0);
//        toast.setText(str);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();

        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param key
     * @param value
     * @param context 保存键值对
     */
    protected void sharePre(String key, String value, Context context) {

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        if (!TextUtils.isEmpty(value)) {
            editor.putString(key, value);
        }
        editor.apply();
    }

    /**
     * @param key
     * @param context
     * @return 根据key获取值
     */
    protected String getSharePre(String key, Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString(key, "0");
    }

    protected void setTextSize(TextView textView, int size) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }


    boolean isShow = true;

    /**
     * @param TAG
     * @param msg 打印日志 true显示日志
     */
    protected void LogT_(String TAG, String msg) {
        if (isShow) {
            Log.i(TAG, msg);
        }
    }

    protected void setViewHw_Lin(View v, int width, int height, int left,
                                 int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    protected void setViewHw_Re(View v, int width, int height, int left,
                                int top, int right, int bottom) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
            return false;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.view_null);
        ImmersionBar.with(this).destroy();
        mubinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 抽象加载view
     */
    protected abstract void AddView();

    /**
     * 抽象设置view点击事件
     */
    protected abstract void SetViewListen();

    /**
     * @return 获取layoutview
     */
    protected abstract int getLayout();

    public void cancelinter(Call call) {
        if (call != null)
            call.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
