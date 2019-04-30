package com.shijiucheng.konghua.main.HomePage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.shijiucheng.konghua.Cmvp.BaseContact;
import com.shijiucheng.konghua.Cmvp.BaseFragment_konghua;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.OrderSYMVPNew.HomePagePresent;
import com.shijiucheng.konghua.Cmvp.OrderSYMVPNew.orderrecyc.orderdata;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.HomePage.viewPagerUtils.DensityUtil;
import com.shijiucheng.konghua.main.HomePage.viewPagerUtils.InfiniteShufflingViewPager;
import com.shijiucheng.konghua.main.HomePage.viewPagerUtils.pagerAdapter;
import com.shijiucheng.konghua.main.News_.newsDetials;
import com.shijiucheng.konghua.main.Newsdetails;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.sqtixian;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.tiXianlishi;
import com.shijiucheng.konghua.main.per_.bank.banklist_guanli;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrderHomePage extends BaseFragment_konghua implements BaseContact.baseview, pagerAdapter.imageOncl {

    HomePagePresent pagePresent;
    @BindView(R.id.hpNew_tePhone)
    TextView hpNewTePhone;
    @BindView(R.id.hpNew_tecustomservice)
    TextView hpNewTecustomservice;
    @BindView(R.id.hpage_vp)
    InfiniteShufflingViewPager hpageVp;
    @BindView(R.id.hpage_container)
    LinearLayout hpageContainer;
    @BindView(R.id.hpNew_imtodayoders)
    ImageView hpNewImtodayoders;
    @BindView(R.id.hpNew_imWaitingfoders)
    ImageView hpNewImWaitingfoders;
    @BindView(R.id.hpNew_imtobedelivered)
    ImageView hpNewImtobedelivered;
    @BindView(R.id.hpNew_imsign)
    ImageView hpNewImsign;
    @BindView(R.id.hpNew_tetodayoders)
    TextView hpNewTetodayoders;
    @BindView(R.id.hpNew_teWaitingfoders)
    TextView hpNewTeWaitingfoders;
    @BindView(R.id.hpNew_tetobedelivereders)
    TextView hpNewTetobedelivereders;
    @BindView(R.id.hpNew_tesign)
    TextView hpNewTesign;
    @BindView(R.id.hpNew_imrefund)
    ImageView hpNewImrefund;
    @BindView(R.id.hpNew_imbalance)
    ImageView hpNewImbalance;
    @BindView(R.id.hpNew_imcomplete)
    ImageView hpNewImcomplete;
    @BindView(R.id.hpNew_imnews)
    ImageView hpNewImnews;
    @BindView(R.id.hpNew_terefund)
    TextView hpNewTerefund;
    @BindView(R.id.hpNew_tebalance)
    TextView hpNewTebalance;
    @BindView(R.id.hpNew_tecomplete)
    TextView hpNewTecomplete;
    @BindView(R.id.hpNew_tenews)
    TextView hpNewTenews;
    @BindView(R.id.hpNew_teswit)
    TextSwitcher hpNewTeswit;
    @BindView(R.id.hpNew_teswit1)
    TextSwitcher hpNewTeswit1;
    @BindView(R.id.hpNew_temoney)
    TextView hpNewTemoney;
    @BindView(R.id.hpNew_temoney1)
    TextView hpNewTemoney1;
    @BindView(R.id.hpNew_temoney2)
    TextView hpNewTemoney2;
    @BindView(R.id.hpage_tecashwithdrawal)
    TextView hpageTecashwithdrawal;
    @BindView(R.id.hpNew_tecashwithdrawalhostory)
    TextView hpNewTecashwithdrawalhostory;
    @BindView(R.id.hpNew_tebankcards)
    TextView hpNewTebankcards;
    @BindView(R.id.hpNew_recycorders)
    RecyclerView hpNewRecycorders;
    Unbinder unbinder;

    @BindView(R.id.hpNew_relat)
    LinearLayout relativeLayout;
    @BindView(R.id.hpNew_relat1)
    RelativeLayout relativeLayout1;

    private String[] img;
    private JSONObject[] imgobj;
    private int previousSelectedPosition;
    private long startTime;
    private long endTime;
    private Handler mHandler;
    pagerAdapter pagerAdapter;

    List<orderdata> list = new ArrayList<>();
    com.shijiucheng.konghua.Cmvp.OrderSYMVPNew.orderrecyc.orderadapter orderadapter;
    String Phone = "", QQ = "";
    int fristinit = 0, bannersize = 0, logincunt = 0;


    @Override
    protected void AddView() {
        pagePresent = new HomePagePresent(this);


        EventBus.getDefault().register(this);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        hpNewRecycorders.setLayoutManager(manager);
        orderadapter = new com.shijiucheng.konghua.Cmvp.OrderSYMVPNew.orderrecyc.orderadapter(getActivity(), list);
        hpNewRecycorders.setAdapter(orderadapter);

        pagePresent.getData(getActivity(), retrofit_Single.getInstence().getOpenid(getActivity()));
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.orderhomepage;
    }

    @Override
    protected BasePresenter bindPresent() {
        return pagePresent;
    }

    @Override
    public void showLoad() {
//        if (!jdt.isAdded()) {
//            jdt.show(getActivity().getFragmentManager(), "newsyx");
//        }
    }

    @Override
    public void closeLoad() {
//        if (jdt.isAdded()) {
//            jdt.dismiss();
//        }
    }

    @Override
    public void showMsg(String msg) {
        toaste_ut(getActivity(), msg);
    }

    @Override
    public void showData(JSONObject jsonObject) {
        paramsDataBean dataBean = new paramsDataBean();
        dataBean.setMsg(configParams.sycloserenzhen);
        dataBean.setT("");
        EventBus.getDefault().post(dataBean);

        try {
            JSONArray jso_banner = jsonObject.getJSONArray("banner_list");
            JSONArray notice_list = jsonObject.getJSONArray("notice_list");
            if (fristinit == 0) {
                fristinit = 1;
                if (notice_list.length() >= 1) {
                    textswitcher1(notice_list);
                }
                JSONArray message_list = jsonObject.getJSONArray("message_list");
                if (message_list.length() >= 1) {
                    textswitcher2(message_list);
                }
            }
            if (hpNewTemoney != null) {
                hpNewTemoney.setText("总收入：" + jsonObject.getString("income_amount") + "元");
                hpNewTemoney1.setText(jsonObject.getString("balance_amount") + "元");
                hpNewTemoney2.setText(jsonObject.getString("expenditure_amount") + "元");
            }

            Phone = jsonObject.getString("kf_tel");
            QQ = jsonObject.getString("kf_qq");
            sharePre("qq", QQ, getActivity());

            JSONArray jsa_order = jsonObject.getJSONArray("work_order_list");
            list.removeAll(list);
            for (int i = 0; i < jsa_order.length(); i++) {
                JSONObject jso = jsa_order.getJSONObject(i);
                list.add(new orderdata(jso.getString("id"), jso.getString("content_text")));
            }
            orderadapter.notifyDataSetChanged();
            if (list.size() == 0) {
                relativeLayout1.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.GONE);
            } else {
                relativeLayout1.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
            }

            if (jso_banner.length() >= 1) {
                imgobj = new JSONObject[jso_banner.length()];
                img = new String[jso_banner.length()];
                for (int i = 0; i < jso_banner.length(); i++) {
                    JSONObject jsob = jso_banner.getJSONObject(i);
                    imgobj[i] = jsob;
                    img[i] = jsob.getString("img");
                }
                if (img.length >= 1 && bannersize == 0) {
                    bannersize = 1;
                    pagerAdapter = new pagerAdapter(getActivity(), img);
                    pagerAdapter.setImgOncl(this);
                    initPoint();
                    initViewPager();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showDataRenZhen(JSONObject jsonObject) {
        try {
            paramsDataBean dataBean = new paramsDataBean();
            String msg = jsonObject.getString("msg");
            if (msg.contains("登录")) {
                dataBean.setMsg(configParams.sylogin);
                EventBus.getDefault().post(dataBean);
                return;
            }
            JSONObject jso = jsonObject.getJSONObject("data");

            dataBean.setT(jsonObject.getString("msg"));

            if (jso.getString("authentication_status").equals("unauthorized")) {
                dataBean.setMsg(configParams.syrenzhen);
                EventBus.getDefault().post(dataBean);
            } else if (jso.getString("authentication_status").equals("authenticating")) {
                dataBean.setMsg(configParams.syrenzhenzhong);
                EventBus.getDefault().post(dataBean);
            } else if (jso.getString("authentication_status").equals("authentication_not_pass")) {
                dataBean.setMsg(configParams.syrenzhenfail);
                EventBus.getDefault().post(dataBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.refreshhp)) {
                if (pagePresent != null) {
                    System.out.println("hh11xx");
                    pagePresent.getData(getActivity(), retrofit_Single.getInstence().getOpenid(getActivity()));
                }
                return;
            }
        }
    }


    @Override
    public void imageOnclIm(int pos) {
//轮播点击
        JSONObject jso = imgobj[pos];
        try {
            if (jso.getString("type").equals("view")) {
                Intent i = new Intent();
                i.putExtra("titl", jso.getString("title"));
                i.putExtra("url",
                        jso.getString("url"));
                i.setClass(getActivity(), other_web1.class);
                startActivity(i);
                getActivity().overridePendingTransition(
                        R.anim.push_left_in, R.anim.push_left_out);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化小圆点
     */
    private void initPoint() {
        if (img != null && img.length > 0) {
            for (int i = 0; i < img.length; i++) {
                //创建指示器（小白点）
                View pointView = new View(getActivity());
                //设置背景选择器
                pointView.setBackgroundResource(R.drawable.selector_bg_point);
                //设置宽高
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(8, getActivity()), DensityUtil.dip2px(8, getActivity()));
                //设置左外间距（除了第一个）
                if (i != 0) {
                    params.leftMargin = DensityUtil.dip2px(10, getActivity());
                }

                if (hpageContainer != null)
                    hpageContainer.addView(pointView, params);
                //设置都不可用(默认是可用的，即亮的)
                pointView.setEnabled(false);
            }
        }
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        //异常处理
        if (img == null || img.length <= 0) {
            return;
        }

        //设置数据适配器
        hpageVp.setAdapter(pagerAdapter);
        if (img.length > 1) {
            if (hpageContainer.getChildCount() > 0) {
                //设置到某个位置，使左右都可无限滑动
                hpageVp.setCurrentItem(50000 + hpageContainer.getChildCount() - 50000 % hpageContainer.getChildCount());
                //设置第一个圆点默认是可用的
                hpageContainer.getChildAt(0).setEnabled(true);
                previousSelectedPosition = 0;
            }

            hpageVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    int newPosition = position % img.length;

                    //指示器：将之前的禁用，把最新的启用，更新指示器
                    hpageContainer.getChildAt(newPosition).setEnabled(true);
                    hpageContainer.getChildAt(previousSelectedPosition).setEnabled(false);

                    //记录之前的位置和时间
                    previousSelectedPosition = newPosition;
                    startTime = System.currentTimeMillis();
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            //通过Handler使ViewPager无限轮播
            if (mHandler == null) {
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        endTime = System.currentTimeMillis();

                        if (endTime - startTime >= 3000) {
                            hpageVp.setCurrentItem(hpageVp.getCurrentItem() + 1);
                            //继续发送延时3秒的消息，形成内循环
                            mHandler.sendEmptyMessageDelayed(0, 4000);
                        }
                    }
                };

                new Thread() {
                    @Override
                    public void run() {
                        //启动自动轮播逻辑（保证只执行一次）
                        mHandler.sendEmptyMessageDelayed(0, 4000);
                    }
                }.start();
            }

            //当手指按住viewpager时，停止自动轮播；
            //当手指松开viewpager时，开启自动轮播；
            hpageVp.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //停止广告自动轮播，删除handler的所有消息
                            mHandler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            //启动广告
                            mHandler.sendEmptyMessageDelayed(0, 3000);
                            break;
                        case MotionEvent.ACTION_UP:
                            //启动广告
                            mHandler.sendEmptyMessageDelayed(0, 3000);
                            break;
                    }
                    return false;
                }
            });
        } else {
            //当轮播图数量为1时，禁止ViewPager滑动
            hpageVp.setCanScroll(false);
        }
    }


    @OnClick({R.id.hpNew_tePhone, R.id.hpNew_tecustomservice, R.id.hpNew_imtodayoders, R.id.hpNew_imWaitingfoders, R.id.hpNew_imtobedelivered, R.id.hpNew_imsign, R.id.hpNew_tetodayoders, R.id.hpNew_teWaitingfoders, R.id.hpNew_tetobedelivereders, R.id.hpNew_tesign, R.id.hpNew_imrefund, R.id.hpNew_imbalance, R.id.hpNew_imcomplete, R.id.hpNew_imnews, R.id.hpNew_terefund, R.id.hpNew_tebalance, R.id.hpNew_tecomplete, R.id.hpNew_tenews, R.id.hpNew_teswit, R.id.hpNew_teswit1, R.id.hpage_tecashwithdrawal, R.id.hpNew_tecashwithdrawalhostory, R.id.hpNew_tebankcards, R.id.hpNew_relat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hpNew_tePhone:
                if (!TextUtils.isEmpty(Phone))
                    requestCemera(Phone);

                break;
            case R.id.hpNew_tecustomservice:
                if (checkApkExist(getActivity(), "com.tencent.mobileqq")) {

                    if (!TextUtils.isEmpty(QQ)) {
                        String urlQQ = "mqqwpa://im/chat?chat_type=wpa&uin=" + QQ + "&version=1";
                        getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
                        getActivity().overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                } else {
                    toaste_ut(getActivity(), "未检测到QQ");
                }

                break;
            case R.id.hpNew_imtodayoders:

                geotoOrder(0, "今日订单", "today");
                break;
            case R.id.hpNew_imWaitingfoders:
                geotoOrder(0, "待接单", "non_receive");
                break;
            case R.id.hpNew_imtobedelivered:
                geotoOrder(0, "待配送", "receive_non_delivery");
                break;
            case R.id.hpNew_imsign:
                geotoOrder(0, "待签收", "delivering");
                break;
            case R.id.hpNew_tetodayoders:
                geotoOrder(0, "今日订单", "today");
                break;
            case R.id.hpNew_teWaitingfoders:
                geotoOrder(0, "待接单", "non_receive");
                break;
            case R.id.hpNew_tetobedelivereders:
                geotoOrder(0, "待配送", "receive_non_delivery");
                break;
            case R.id.hpNew_tesign:
                geotoOrder(0, "待签收", "delivering");
                break;


            case R.id.hpNew_imrefund:
                geotoOrder(1, "申请退单", "cancel");
                break;
            case R.id.hpNew_imbalance:
                geotoOrder(0, "待结算", "sign");
                break;
            case R.id.hpNew_imcomplete:
                geotoOrder(0, "已完成", "balance");
                break;
            case R.id.hpNew_imnews:

                paramsDataBean databean1 = new paramsDataBean();
                databean1.setMsg(configParams.symsg);
                EventBus.getDefault().post(databean1);
                totherpage(2);
                break;
            case R.id.hpNew_terefund:
                geotoOrder(1, "申请退单", "cancel");
                break;
            case R.id.hpNew_tebalance:
                geotoOrder(0, "待结算", "sign");
                break;
            case R.id.hpNew_tecomplete:
                geotoOrder(0, "已完成", "balance");
                break;
            case R.id.hpNew_tenews:

                paramsDataBean databean2 = new paramsDataBean();
                databean2.setMsg(configParams.symsg);
                EventBus.getDefault().post(databean2);
                totherpage(2);
                break;

            case R.id.hpNew_teswit:
                break;
            case R.id.hpNew_teswit1:
                break;
            case R.id.hpage_tecashwithdrawal:
                startActivityByIntent(getActivity(), sqtixian.class, false);
                break;
            case R.id.hpNew_tecashwithdrawalhostory:
                startActivityByIntent(getActivity(), tiXianlishi.class, false);
                break;
            case R.id.hpNew_tebankcards:

                startActivityByIntent(getActivity(), banklist_guanli.class, false);
                break;

            case R.id.hpNew_relat:

                startActivityByIntent(getActivity(), authen_RZ.class, false);
                break;
        }
    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    //切换其他页面
    private void totherpage(int page) {
        paramsDataBean dataBean = new paramsDataBean();
        dataBean.setMsg(configParams.totherpager);
        dataBean.setT(page);
        EventBus.getDefault().post(dataBean);
    }


    private void requestCemera(String phone) {
        if (PermissionsUtil.hasPermission(getActivity(), Manifest.permission.CALL_PHONE)) {
            callphone(phone);
        } else {
            PermissionsUtil.requestPermission(getActivity(), new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    callphone(phone);
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    //用户拒绝了访问摄像头的申请
                }
            }, new String[]{Manifest.permission.CALL_PHONE});
        }
    }

    void callphone(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }


    private void geotoOrder(int type, String tit, String status) {//区分是申请退款还是其他

        Bundle bundle = new Bundle();
        bundle.putString("tit", tit);
        bundle.putString("status", status);
        Intent intent;
        if (type == 0) {
            intent = new Intent(getActivity(), OrderTwoPage.class);
            bundle.putString("keywords", "");
            bundle.putString("receiver", "");
            bundle.putString("receiver_tel", "");
            bundle.putString("delivery_start_date", "");
            bundle.putString("delivery_end_date", "");
        } else
            intent = new Intent(getActivity(), OrderTwoPage1.class);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    void textswitcher1(JSONArray array) throws JSONException {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jso = array.getJSONObject(i);
            list1.add(jso.getString("notice_title"));
            list2.add(jso.getString("n_id"));
        }
        TextSwitcherAnimation textSwitcherAnimation = new TextSwitcherAnimation(hpNewTeswit, list1, list2);
        hpNewTeswit.removeAllViews();
        textSwitcherAnimation.stop();
        hpNewTeswit.setFactory(() -> {
            TextView textView = new TextView(getActivity());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textView.setTextColor(Color.BLACK);
            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setSingleLine(true);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER_VERTICAL;
            textView.setLayoutParams(lp);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = textSwitcherAnimation.getTxtId();
                    if (!TextUtils.isEmpty(id)) {

                        Intent i = new Intent(getActivity(), newsDetials.class);
                        i.putExtra("tit", "公告信息");
                        i.putExtra("id", id);
                        getActivity().startActivity(i);
                        getActivity().overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                }
            });

            return textView;
        });

        textSwitcherAnimation.create();
    }

    void textswitcher2(JSONArray array) throws JSONException {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jso = array.getJSONObject(i);
            list1.add(jso.getString("message_title"));
            list2.add(jso.getString("m_id"));
        }
        TextSwitcherAnimation textSwitcherAnimation = new TextSwitcherAnimation(hpNewTeswit1, list1, list2);
        hpNewTeswit1.removeAllViews();
        textSwitcherAnimation.stop();
        hpNewTeswit1.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getActivity());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                textView.setTextColor(Color.BLACK);
                textView.setSingleLine();
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.CENTER_VERTICAL;
                textView.setLayoutParams(lp);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = textSwitcherAnimation.getTxtId();
                        if (!TextUtils.isEmpty(id)) {
                            Intent i = i = new Intent(getActivity(), Newsdetails.class);
                            i.putExtra("m_id", id);
                            getActivity().startActivity(i);
                            getActivity().overridePendingTransition(R.anim.push_left_in,
                                    R.anim.push_left_out);
                        }
                    }
                });

                return textView;
            }
        });

        textSwitcherAnimation.create();
    }


}
