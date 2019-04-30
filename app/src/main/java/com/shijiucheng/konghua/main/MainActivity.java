package com.shijiucheng.konghua.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.shijiucheng.konghua.Banben_;
import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.Login_konghua;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.gotoAuthen;
import com.shijiucheng.konghua.main.HomePage.OrderHomePage;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements View.OnClickListener, Banben_.fuluebanben, gotoAuthen.gotoautheninterface {
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
    gotoAuthen gotoauthen = new gotoAuthen();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar immersionBar=  ImmersionBar.with(this);

        ImmersionBar immersionBar = ImmersionBar.with(this);//先初始化，不然高版本会有底部导航栏跑上去了
        immersionBar.fitsSystemWindows(true).statusBarColor(R.color.zhu)
                .statusBarDarkFont(false, 0.0f).init();

        setContentView(R.layout.activity_main2);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        EventBus.getDefault().register(this);

        munbinder = ButterKnife.bind(this);
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
//                if (setact())
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
//        if (setact())
        changeTab(v.getId());
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
                paramsDataBean dataper = new paramsDataBean();
                dataper.setMsg(configParams.perRrefresh);
                EventBus.getDefault().post(dataper);
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

    @Override
    public void fuluebanben() {

    }

    @Override
    public void installapk(File file) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.syrenzhen)) {
                if (!gotoauthen.isAdded()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("statusstr", "还未认证，为了不影响接单及提现功能需完成店铺认证。");
                    bundle.putString("status", "0");//0未认证，1认证中，2认证失败
                    gotoauthen.setArguments(bundle);
                    gotoauthen.show(getSupportFragmentManager(), "sygoto");
                }
                return;
            } else if (data.getMsg().equals(configParams.sylogin)) {

                if (preferences.getString("name", "0").equals("0")) {
                    startActivity(new Intent(MainActivity.this, Login_konghua.class));
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                } else {
                    login();
                }
                return;
            } else if (data.getMsg().equals(configParams.sycloserenzhen)) {
                if (gotoauthen != null)
                    if (gotoauthen.isAdded()) {
                        gotoauthen.dismiss();
                    }
                return;
            } else if (data.getMsg().equals(configParams.syrenzhenzhong)) {
                if (!gotoauthen.isAdded()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("statusstr", "该账号正在认证中");
                    bundle.putString("status", "1");//0未认证，1认证中，2认证失败
                    gotoauthen.setArguments(bundle);
                    gotoauthen.show(getSupportFragmentManager(), "renzhenzhong");
                }
                return;
            } else if (data.getMsg().equals(configParams.syrenzhenfail)) {
                if (!gotoauthen.isAdded()) {

                    Bundle bundle = new Bundle();
                    bundle.putString("statusstr", "很抱歉该账号认证失败了");
                    bundle.putString("status", "2");//0未认证，1认证中，2认证失败
                    gotoauthen.setArguments(bundle);

                    gotoauthen.show(getSupportFragmentManager(), "renzhensb");
                }
                return;
            }
        }
    }

    @Override
    public void toauthen() {
        //弹窗去认证页面
        Intent i = new Intent();
        i.setClass(this, authen_RZ.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    @Override
    public void tologin() {
        //弹窗去登录
        Intent i = new Intent();
        i.setClass(this, Login_konghua.class);
        startActivity(i);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    @Override
    public void tofinishapp() {
        finish();
        System.exit(0);
    }

    public void login() {
        Retro_Intf service1 = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("user_name", preferences.getString("name", "0"));
        maps.put("user_pwd", preferences.getString("pwd", "0"));
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(this));//公共参数
        Call<ResponseBody> login = service1.getLogin(retrofit_Single.getInstence().getOpenid(this), maps);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(
                    Call<ResponseBody> call, Response<ResponseBody> response) {
                String Result = null;
                if (response.body() == null)
                    return;
                try {
                    Result = response.body().string();
                    try {
                        if (Result != null && Result.startsWith("\ufeff")) {
                            Result = Result.substring(1);
                        }
                        JSONObject jsonObject = new JSONObject(Result);
                        if (jsonObject.getString("status").equals("1")) {
                            paramsDataBean datahp = new paramsDataBean();
                            datahp.setMsg(configParams.refreshhp);
                            EventBus.getDefault().post(datahp);
                        } else {
                            startActivity(new Intent(MainActivity.this, Login_konghua.class));
                            overridePendingTransition(R.anim.push_left_in,
                                    R.anim.push_left_out);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(e.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}
