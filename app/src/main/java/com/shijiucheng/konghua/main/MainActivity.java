package com.shijiucheng.konghua.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.shijiucheng.konghua.Banben_;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.IsLoginOrAuthor;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.HomePage.OrderHomePage;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends FragmentActivity implements View.OnClickListener, Banben_.fuluebanben {
    Unbinder munbinder;
    private List<Fragment> fragment = new ArrayList<Fragment>();
    @BindView(R.id.mainact_vp)
    ViewPager vpage;
    @BindView(R.id.mainact_sy)
    LinearLayout lin_home;
    @BindView(R.id.mainact_fl)
    LinearLayout lin_order;
    @BindView(R.id.mainact_kf)
    LinearLayout lin_news;
    @BindView(R.id.mainact_wd)
    LinearLayout lin_per;

    @BindView(R.id.mainact_synew)
    LinearLayout lin_syenew;


    @BindView(R.id.mainact_ivsy)
    ImageView im_sy;
    @BindView(R.id.mainact_ivfl)
    ImageView im_order;
    @BindView(R.id.mainact_ivkf)
    ImageView im_news;
    @BindView(R.id.mainact_ivwd)
    ImageView im_per;

    @BindView(R.id.mainact_imsynew)
    ImageView im_syenew;

    @BindView(R.id.mainact_tesy)
    TextView te_sy;
    @BindView(R.id.mainact_tefl)
    TextView te_order;
    @BindView(R.id.mainact_tekf)
    TextView te_news;
    @BindView(R.id.mainact_tewd)
    TextView te_per;

    @BindView(R.id.mainact_tesynew)
    TextView te_synew;

    ImageView ivCurrent;
    TextView tvCurrent;
    private long exitTime = 0;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarColor(R.color.zhu).statusBarDarkFont(false, 0.0f).fitsSystemWindows(true).init();
        setContentView(R.layout.activity_main2);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, Object> maps = new HashMap<>();
        maps.put("test_zhuye", "dsd");
        maps.put("111", 2);
        maps.put("112", 112);
        EventBus.getDefault().post(maps);

        EventBus.getDefault().register(this);

        munbinder = ButterKnife.bind(this);
//        setviewhw();
        setviewdata();
        //测试版本更新
    }

    private void setviewdata() {
        fragment.add(new OrderHomePage());
        fragment.add(new OrderSY());
        fragment.add(new News());
        fragment.add(new Home());
        fragment.add(new Per());

        lin_syenew.setOnClickListener(this);
        lin_home.setOnClickListener(this);
        lin_order.setOnClickListener(this);
        lin_per.setOnClickListener(this);
        lin_news.setOnClickListener(this);

        im_syenew.setSelected(true);
        te_synew.setSelected(true);
        ivCurrent = im_syenew;
        tvCurrent = te_synew;


        vpage.setAdapter(new FragmentPagerAdapter(MainActivity.this
                .getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragment.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragment.get(arg0);
            }
        });
        vpage.setOffscreenPageLimit(4);
        vpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (setact())
                    changeTab(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toOtherPager(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.totherpager)) {
                if (!(vpage == null)) {
                    int page = (int) data.getT();
                    if (page <= fragment.size() - 1)
                        vpage.setCurrentItem(page);
                }
                return;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次，退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {

            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.view_null);
        EventBus.getDefault().register(this);

        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            //从推送通知栏打开-Service打开Activity会重新执行Laucher流程
            //查看是不是全新打开的面板
            if (isTaskRoot()) {
                return;
            }
            finish();
        }
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void onClick(View v) {
        if (setact())
            changeTab(v.getId());
    }

    private boolean setact() {
        boolean t = false;
        if (!preferences.getString("name", "0").equals("0")) {
            if (!preferences.getString("isauther", "0").equals("0"))
                t = true;
            else {
                t = false;
                startActivity(new Intent(MainActivity.this, authen_RZ.class));
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        } else {
            t = false;
            if (!preferences.getString("name", "0").equals("0")) {
                IsLoginOrAuthor.getInstence().login(this, retrofit_Single.getInstence().getOpenid(this), preferences.getString("name", ""), preferences.getString("pwd", ""));
            } else {
                IsLoginOrAuthor.getInstence().goToLogin(this);
            }
        }
        return t;
    }

    private void changeTab(int id) {
        ivCurrent.setSelected(false);
        tvCurrent.setSelected(false);
        switch (id) {

            case R.id.mainact_synew:
                paramsDataBean datahp = new paramsDataBean();
                datahp.setMsg(configParams.refreshhp);
                EventBus.getDefault().post(datahp);
                vpage.setCurrentItem(0, false);
            case 0:
                im_syenew.setSelected(true);
                ivCurrent = im_syenew;
                te_synew.setSelected(true);
                tvCurrent = te_synew;
                break;

            case R.id.mainact_fl:
                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.orderSYrefr);
                EventBus.getDefault().post(databean);
                vpage.setCurrentItem(1, false);
            case 1:
                im_order.setSelected(true);
                ivCurrent = im_order;
                te_order.setSelected(true);
                tvCurrent = te_order;
                break;

            case R.id.mainact_kf:
                paramsDataBean databean1 = new paramsDataBean();
                databean1.setMsg(configParams.symsg);
                EventBus.getDefault().post(databean1);
                vpage.setCurrentItem(2, false);
            case 2:
                im_news.setSelected(true);
                ivCurrent = im_news;
                te_news.setSelected(true);
                tvCurrent = te_news;
                break;

            case R.id.mainact_sy:

                paramsDataBean databean2 = new paramsDataBean();
                databean2.setMsg(configParams.sycw);
                EventBus.getDefault().post(databean2);
                vpage.setCurrentItem(3, false);
            case 3:
                im_sy.setSelected(true);
                ivCurrent = im_sy;
                te_sy.setSelected(true);
                tvCurrent = te_sy;
                break;
            case R.id.mainact_wd:
                vpage.setCurrentItem(4, false);
            case 4:
                im_per.setSelected(true);
                ivCurrent = im_per;
                te_per.setSelected(true);
                tvCurrent = te_per;
                break;
            default:
                break;
        }
    }

    private void setviewhw() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;

        setviewhw_lin(lin_syenew, (int) (w_screen * 150 / 750.0),
                (int) (w_screen * 98 / 750.0), (int) (w_screen * 0 / 750.0),
                (int) (w_screen * 0 / 750.0), 0, 0);
        setviewhw_lin(lin_home, (int) (w_screen * 150 / 750.0),
                (int) (w_screen * 98 / 750.0), (int) (w_screen * 0 / 750.0),
                (int) (w_screen * 0 / 750.0), 0, 0);
        setviewhw_lin(lin_order, (int) (w_screen * 150 / 750.0),
                (int) (w_screen * 98 / 750.0), (int) (w_screen * 0 / 750.0),
                (int) (w_screen * 0 / 750.0), 0, 0);
        setviewhw_lin(lin_news, (int) (w_screen * 150 / 750.0),
                (int) (w_screen * 98 / 750.0), (int) (w_screen * 0 / 750.0),
                (int) (w_screen * 0 / 750.0), 0, 0);
        setviewhw_lin(lin_per, (int) (w_screen * 150 / 750.0),
                (int) (w_screen * 98 / 750.0), (int) (w_screen * 0 / 750.0),
                (int) (w_screen * 0 / 750.0), 0, 0);

        setviewhw_lin(im_syenew, (int) (w_screen * 55 / 750.0),
                (int) (w_screen * 50 / 750.0), (int) (w_screen * 0 / 750.0),
                (int) (w_screen * 10 / 750.0), 0, 0);

//        setviewhw_lin(te_synew, (int) (w_screen * 150 / 750.0),
//                (int) (w_screen * 38 / 750.0), (int) (w_screen * 0 / 750.0),
//                (int) (w_screen * 0 / 750.0), 0, (int) (w_screen * 0 / 750.0));
        setviewhw_lin(im_sy, (int) (w_screen * 48 / 750.0),
                (int) (w_screen * 48 / 750.0), (int) (w_screen * 0 / 750.0),
                (int) (w_screen * 10 / 750.0), 0, (int) (w_screen * 6 / 750.0));
        setviewhw_lin(im_order, (int) (w_screen * 48 / 750.0),
                (int) (w_screen * 48 / 750.0), (int) (w_screen * 0 / 750.0),
                (int) (w_screen * 10 / 750.0), 0, (int) (w_screen * 6 / 750.0));
        setviewhw_lin(im_news, (int) (w_screen * 48 / 750.0),
                (int) (w_screen * 48 / 750.0), (int) (w_screen * 0 / 750.0),
                (int) (w_screen * 10 / 750.0), 0, (int) (w_screen * 6 / 750.0));
        setviewhw_lin(im_per, (int) (w_screen * 48 / 750.0),
                (int) (w_screen * 48 / 750.0), (int) (w_screen * 0 / 750.0),
                (int) (w_screen * 10 / 750.0), 0, (int) (w_screen * 6 / 750.0));
        te_synew.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_screen * 26 / 750.0));
        te_sy.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_screen * 26 / 750.0));
        te_order.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_screen * 26 / 750.0));
        te_news.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_screen * 26 / 750.0));
        te_per.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_screen * 26 / 750.0));
    }

    private void setviewhw_lin(View v, int width, int height, int left,
                               int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    @Override
    public void fuluebanben() {

    }
}
