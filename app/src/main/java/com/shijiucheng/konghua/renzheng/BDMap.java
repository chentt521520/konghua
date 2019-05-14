package com.shijiucheng.konghua.renzheng;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.animation.AlphaAnimation;
import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.animation.AnimationSet;
import com.baidu.mapapi.animation.RotateAnimation;
import com.baidu.mapapi.animation.ScaleAnimation;
import com.baidu.mapapi.animation.SingleScaleAnimation;
import com.baidu.mapapi.animation.Transformation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.renzheng.data.bdmapGpsfra;
import com.shijiucheng.konghua.renzheng.data.bdsearchada;
import com.shijiucheng.konghua.renzheng.data.bdsearchdata;
import com.shijiucheng.konghua.renzheng.data.huadianada;
import com.shijiucheng.konghua.renzheng.data.huadiandata;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BDMap extends BaseActivity_konghua implements bdsearchada.getnewaddress, huadianada.huadianonc, bdmapGpsfra.openorfulueGps {
    Unbinder munbind;
    @BindView(R.id.map_lintop)
    RelativeLayout re_top;
    @BindView(R.id.map_imreturn)
    ImageView ima_fh;
    @BindView(R.id.map_tetit)
    TextView te_tit;
    @BindView(R.id.map_teok)
    TextView te_ok;
    @BindView(R.id.bmapViewxx)
    MapView bmapViewxx;
    @BindView(R.id.map_ed)
    EditText mapEd;
    @BindView(R.id.map_recyc)
    RecyclerView mapRecyc;
    @BindView(R.id.map_imclose)
    ImageView mapImclose;
    @BindView(R.id.map_tesearch)
    TextView mapTesearch;
    @BindView(R.id.ma_imdw)
    ImageView im_location;


    @BindView(R.id.map_recychuadian)
    RecyclerView mapRecyc_huadian;
    @BindView(R.id.news_smartLayoutxx)
    SmartRefreshLayout smartRefreshLayout;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker mMarkerF;
    private LatLng llF;
    DecimalFormat df;

    BitmapDescriptor bdF = BitmapDescriptorFactory
            .fromResource(R.mipmap.icon_markf);
    private Point mScreenCenterPoint;

    String address_ = "";
    String shi = "";
    private long gotime = 0, closetype = 0;

    SuggestionSearch mSuggestionSearch;
    List<bdsearchdata> list = new ArrayList<>();
    com.shijiucheng.konghua.renzheng.data.bdsearchada bdsearchada;
    GeoCoder mSearch = GeoCoder.newInstance();

    huadianada hdada;
    List<huadiandata> list_hd = new ArrayList<>();

    String ssStr = "";

    PoiSearch mPoiSearch;
    String jd_ = "", wd_ = "";//搜索后返回的经纬度
    int page = 0;//花店列表页数
    LatLng cenpt_page;

    double jd_location = 0, wd_location = 0;
    LocationClient locationClient;
    LocationManager locationManager;

    bdmapGpsfra bdgps = new bdmapGpsfra();//打开GPS弹窗

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        munbind = ButterKnife.bind(this);
        initLocationOption();
        mMapView = findViewById(R.id.bmapViewxx);
        mBaiduMap = mMapView.getMap();
        smartRefreshLayout.setEnableRefresh(false);

        locationManager = (LocationManager) BDMap.this.getSystemService(Context.LOCATION_SERVICE);
        if (!jdt.isAdded()) {
            jdt.show(getFragmentManager(), "bddt");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    jdt.dismiss();
                }
            }, 2000);
        }
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(listener_pio);
        mSearch.setOnGetGeoCodeResultListener(listener_search);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                initOverlay();
            }
        });

        df = new DecimalFormat("###.000000");

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus status) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus status, int reason) {

            }

            @Override
            public void onMapStatusChange(MapStatus status) {
                if (null == mMarkerF) {
                    return;
                }
                String jd = df.format(status.target.latitude) + "";
                String wd = df.format(status.target.longitude) + "";
                address_ = wd + "," + jd;
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus status) {
                if (null == mMarkerF) {
                    return;
                }
                Log.d("sdfssdf", status.target.latitude + " 11111   " + status.target.longitude);
                DecimalFormat df = new DecimalFormat("###.000000");
                String jd = df.format(status.target.latitude) + "";
                String wd = df.format(status.target.longitude) + "";
                address_ = wd + "," + jd;
                mMarkerF.setAnimation(getTransformationPoint());
//                mMarkerF.startAnimation();
            }
        });
        AddView();
        SetViewListen();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapView.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }


    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            System.out.println(closetype + "  xxx");
            if (closetype == 0) {
                //处理sug检索结果
                if (suggestionResult.getAllSuggestions() == null) {
                    mapRecyc.setVisibility(View.GONE);
                    return;
                }

                list.removeAll(list);
                for (int i = 0; i < suggestionResult.getAllSuggestions().size(); i++) {
                    if (suggestionResult.getAllSuggestions().get(i).pt != null)
                        list.add(new bdsearchdata(suggestionResult.getAllSuggestions().get(i).city + suggestionResult.getAllSuggestions().get(i).district + suggestionResult.getAllSuggestions().get(i).key,
                                suggestionResult.getAllSuggestions().get(i).pt.toString(), suggestionResult.getAllSuggestions().get(i).city));
                }
                bdsearchada.notifyDataSetChanged();
                if (list.size() == 0) {
                    mapRecyc.setVisibility(View.GONE);
                } else mapRecyc.setVisibility(View.VISIBLE);
            } else {
                closetype = 0;
                mapRecyc.setVisibility(View.GONE);
            }
        }
    };

    /**
     * 开启动画
     *
     * @param view
     */
    public void startAnimation(View view) {
    }

    /**
     * 初始化Overlay
     */
    public void initOverlay() {
        // add marker overlay
        String jd = getIntent().getStringExtra("jd");
        String wd = getIntent().getStringExtra("wd");

        if (jd.equals("0"))
            requesLocation();
        else {

            shi = getIntent().getStringExtra("shi");
            llF = new LatLng(jd.length() <= 1 ? 40.022211 : Double.valueOf(jd), wd.length() <= 1 ? 116.499274 : Double.valueOf(wd));

            LatLng cenpt = new LatLng(jd.length() <= 1 ? 40.022211 : Double.valueOf(jd), wd.length() <= 1 ? 116.499274 : Double.valueOf(wd)); //设定中心点坐标
            MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                    .target(cenpt)
                    .zoom(18)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态
        }
        if (null != mBaiduMap.getMapStatus()) {
            llF = mBaiduMap.getMapStatus().target;
            mScreenCenterPoint = mBaiduMap.getProjection().toScreenLocation(llF);
            MarkerOptions ooF = new MarkerOptions().position(llF).icon(bdF).perspective(true)
                    .fixedScreenPosition(mScreenCenterPoint);
            mMarkerF = (Marker) (mBaiduMap.addOverlay(ooF));

        }


        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);

    }

    /**
     * 开启旋转动画
     */
    public void startRotateAnimation() {

    }

    /**
     * 开启缩放动画
     */
    public void startScaleAnimation() {
    }

    /**
     * 开启平移动画
     */
    public void startTransformation() {

    }

    /**
     * 开启单边缩放动画 X或Y方向
     */
    public void startSingleScaleAnimation() {

    }

    /**
     * 添加透明动画
     */
    public void startAlphaAnimation() {

    }

    /**
     * 得到单独缩放动画类
     */
    public Animation getSingleScaleAnimation() {
        SingleScaleAnimation mSingleScale =
                new SingleScaleAnimation(SingleScaleAnimation.ScaleType.SCALE_X, 1f, 2f, 1f);
        mSingleScale.setDuration(1000);
        mSingleScale.setRepeatCount(1);
        mSingleScale.setRepeatMode(Animation.RepeatMode.RESTART);
        return mSingleScale;
    }

    /**
     * 添加组合动画
     */
    public void startAnimationSet() {
        AnimationSet animationSet = new AnimationSet();
        animationSet.addAnimation(getAlphaAnimation());
        animationSet.addAnimation(getRotateAnimation());
        animationSet.addAnimation(getSingleScaleAnimation());
        animationSet.addAnimation(getScaleAnimation());
        animationSet.setAnimatorSetMode(0);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }
        });

        if (animationSet == null) {
            return;
        }

//        mMarkerE.setAnimation(animationSet);
//        mMarkerE.startAnimation();
    }

    /**
     * 创建缩放动画
     */
    private Animation getScaleAnimation() {
        ScaleAnimation mScale = new ScaleAnimation(1f, 2f, 1f);
        mScale.setDuration(2000);
        mScale.setRepeatMode(Animation.RepeatMode.RESTART);//动画重复模式
        mScale.setRepeatCount(1);//动画重复次数
        mScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }
        });

        return mScale;

    }

    /**
     * 创建旋转动画
     */
    private Animation getRotateAnimation() {
        RotateAnimation mRotate = new RotateAnimation(0f, 360f);
        mRotate.setDuration(1000);//设置动画旋转时间
        mRotate.setRepeatMode(Animation.RepeatMode.RESTART);//动画重复模式
        mRotate.setRepeatCount(1);//动画重复次数
        mRotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }
        });

        return mRotate;
    }

    /**
     * 创建平移动画
     */
    private Animation getTransformation() {
        Point point = mBaiduMap.getProjection().toScreenLocation(llF);
        LatLng latLng1 = mBaiduMap.getProjection().fromScreenLocation(new Point(point.x, point.y - 100));
        Transformation mTransforma = new Transformation(llF, latLng1, llF);
        mTransforma.setDuration(500);
        mTransforma.setRepeatMode(Animation.RepeatMode.RESTART);//动画重复模式
        mTransforma.setRepeatCount(1);//动画重复次数
        mTransforma.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {

            }
        });

        return mTransforma;
    }

    /**
     * 创建平移坐标动画
     */
    private Animation getTransformationPoint() {

        if (null != mScreenCenterPoint) {
            Point pointTo = new Point(mScreenCenterPoint.x, mScreenCenterPoint.y - 100);
            Transformation mTransforma = new Transformation(mScreenCenterPoint, pointTo, mScreenCenterPoint);
            mTransforma.setDuration(500);
            mTransforma.setRepeatMode(Animation.RepeatMode.RESTART);//动画重复模式
            mTransforma.setRepeatCount(0);//动画重复次数
            mTransforma.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart() {
                }

                @Override
                public void onAnimationEnd() {
                }

                @Override
                public void onAnimationCancel() {
                }

                @Override
                public void onAnimationRepeat() {

                }
            });
            return mTransforma;
        }

        return null;
    }

    /**
     * 创建透明度动画
     */
    private Animation getAlphaAnimation() {
        AlphaAnimation mAlphaAnimation = new AlphaAnimation(1f, 0f, 1f);
        mAlphaAnimation.setDuration(3000);
        mAlphaAnimation.setRepeatCount(1);
        mAlphaAnimation.setRepeatMode(Animation.RepeatMode.RESTART);
        mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }
        });

        return mAlphaAnimation;
    }

    @Override
    public void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMarkerF.cancelAnimation();
        mSuggestionSearch.destroy();
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        mPoiSearch.destroy();
        markerRemove();
        super.onDestroy();
        bdF.recycle();
        setContentView(R.layout.view_null);
        munbind.unbind();
    }

    public void markerRemove() {

    }


    @Override
    protected void AddView() {
        x.view().inject(this);

        setViewHw_Lin(re_top, w_, (int) (w_ * 88 / 750.0), 0, 0, 0, 0);
        setViewHw_Re(ima_fh, (int) (w_ * 44 / 750.0), (int) (w_ * 44 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 22 / 750.0), 0, (int) (w_ * 22 / 750.0));
        te_tit.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_ * 36 / 750.0));
        te_ok.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_ * 36 / 750.0));

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        bdsearchada = new bdsearchada(this, list);
        mapRecyc.setLayoutManager(manager);
        mapRecyc.setAdapter(bdsearchada);
        bdsearchada.getnewaddressintf(this);

        LinearLayoutManager manager_hd = new LinearLayoutManager(this);
        manager_hd.setOrientation(OrientationHelper.VERTICAL);
        hdada = new huadianada(BDMap.this, list_hd);
        mapRecyc_huadian.setLayoutManager(manager_hd);
        mapRecyc_huadian.setAdapter(hdada);
        hdada.sethuadianinterface(this);

    }

    @Override
    protected void SetViewListen() {
        ima_fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fastClick()) {
                    finish();
                    overridePendingTransition(R.anim.push_right_out,
                            R.anim.push_right_in);
                }
            }
        });
        te_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("dizhi", address_);
                EventBus.getDefault().post(map);

                System.out.println(address_.split(",")[0] + "  " + address_.split(",")[1]);
                authen_RZ.jsonAuthor.setLongitude(address_.split(",")[0]);
                authen_RZ.jsonAuthor.setLatitude(address_.split(",")[1]);
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });

        mapEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(mapEd.getText().toString())) {
                    if ((System.currentTimeMillis() - gotime) > 500) {
                        gotime = System.currentTimeMillis();
                        mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                                .city(shi)
                                .keyword(mapEd.getText().toString()));
                        mapImclose.setVisibility(View.VISIBLE);
                    }
                } else {
                    mapRecyc.setVisibility(View.GONE);
                    mapImclose.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mapImclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapEd.setText("");
                mapImclose.setVisibility(View.GONE);
            }
        });
        mapTesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapRecyc.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(mapEd.getText().toString())) {
                    list_hd.removeAll(list_hd);
                    page = 0;
                    ssStr = mapEd.getText().toString();
                    mapRecyc_huadian.scrollToPosition(0);
                    mSearch.geocode(new GeoCodeOption()
                            .city("")
                            .address(mapEd.getText().toString()));
                } else {
                    toaste_ut(BDMap.this, "请输入内容");
                    mapImclose.setVisibility(View.GONE);
                }
            }
        });

        mapEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    System.out.println("xxxx");
                    mapRecyc.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(mapEd.getText().toString())) {
                        list_hd.removeAll(list_hd);
                        page = 0;
                        ssStr = mapEd.getText().toString();
                        mapRecyc_huadian.scrollToPosition(0);
                        mSearch.geocode(new GeoCodeOption()
                                .city("")
                                .address(mapEd.getText().toString()));
                        hideKeyboard(mapEd);
                    } else {
                        toaste_ut(BDMap.this, "请输入内容");
                        mapImclose.setVisibility(View.GONE);
                    }
                    return true;
                }

                return false;

            }

        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (cenpt_page == null)
                    return;
                page++;
                System.out.println("ppp  " + page);
                mPoiSearch.searchNearby(new PoiNearbySearchOption()
                        .location(cenpt_page)
                        .radius(10000)
                        .keyword("花店")
                        .pageCapacity(20)
                        .pageNum(page));
                smartRefreshLayout.finishLoadmore();
            }
        });

        im_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fastClick())
                    requesLocation();
            }
        });

    }

    public static void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    OnGetGeoCoderResultListener listener_search = new OnGetGeoCoderResultListener() {

        public void onGetGeoCodeResult(GeoCodeResult result) {

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                toaste_ut(BDMap.this, "未搜索到结果");
                return;
            }

            String jd = result.getLocation().latitude + "";
            String wd = result.getLocation().longitude + "";

            System.out.println(jd + "  " + wd);
            jd_ = jd;
            wd_ = wd;
            llF = new LatLng(jd.length() <= 1 ? 40.022211 : Double.valueOf(jd), wd.length() <= 1 ? 116.499274 : Double.valueOf(wd));
            LatLng cenpt = new LatLng(jd.length() <= 1 ? 40.022211 : Double.valueOf(jd), wd.length() <= 1 ? 116.499274 : Double.valueOf(wd)); //设定中心点坐标
            MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                    .target(cenpt)
                    .zoom(18)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态

            if (null != mBaiduMap.getMapStatus()) {
                llF = mBaiduMap.getMapStatus().target;
                mScreenCenterPoint = mBaiduMap.getProjection().toScreenLocation(llF);
                MarkerOptions ooF = new MarkerOptions().position(llF).icon(bdF).perspective(true)
                        .fixedScreenPosition(mScreenCenterPoint);
                mMarkerF = (Marker) (mBaiduMap.addOverlay(ooF));

            }
            MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
            mBaiduMap.setMapStatus(msu);
            cenpt_page = cenpt;
            mapRecyc_huadian.scrollToPosition(0);
            mPoiSearch.searchNearby(new PoiNearbySearchOption()
                    .location(cenpt)
                    .radius(10000)
                    .keyword("花店")
                    .pageCapacity(20)
                    .pageNum(0));


        }

        @Override

        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
                return;
            }
        }
    };

    String s_;
    private final double EARTH_RADIUS = 6378.137;//地球半径
    OnGetPoiSearchResultListener listener_pio = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult != null) {
                if (poiResult.getAllPoi() == null)
                    return;
                for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                    String jd = poiResult.getAllPoi().get(i).location.toString().split(",")[0];
                    String wd = poiResult.getAllPoi().get(i).location.toString().split(",")[1];

                    jd = jd.substring(jd.indexOf(":") + 1, jd.length()).trim();
                    wd = wd.substring(wd.indexOf(":") + 1, wd.length()).trim();

                    if (jd_.length() <= 0) {
                        return;
                    }
                    double radLat1 = rad(Double.valueOf(wd).doubleValue());
                    double radLat2 = rad(Double.valueOf(wd_).doubleValue());
                    double a = radLat1 - radLat2;
                    double b = rad(Double.valueOf(jd).doubleValue()) - rad(Double.valueOf(jd_).doubleValue());
                    double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                            + Math.cos(radLat1) * Math.cos(radLat2)
                            * Math.pow(Math.sin(b / 2), 2)));
                    s = s * EARTH_RADIUS;
                    s = Math.round(s * 10000d) / 10000d;
                    s = s * 1000;
                    if (s <= 1000)
                        s_ = new DecimalFormat("0").format(s) + "m|";
                    else {
                        s = s / 1000;
                        s_ = new DecimalFormat("0.00").format(s) + "km|";
                    }
                    list_hd.add(new huadiandata(poiResult.getAllPoi().get(i).name, s_, poiResult.getAllPoi().get(i).address, poiResult.getAllPoi().get(i).location + "", "0"));
                }
                hdada.notifyDataSetChanged();
                if (list_hd.size() <= 0) {
                    mapRecyc_huadian.setVisibility(View.GONE);
                    smartRefreshLayout.setVisibility(View.GONE);
                } else {
                    mapRecyc_huadian.setVisibility(View.VISIBLE);
                    smartRefreshLayout.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            System.out.println(poiIndoorResult.getmArrayPoiInfo().size());
        }

        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_bdmap;
    }

    @Override
    public void getnewaddress_(int pos) {
        closetype = 1;
        mapEd.setText(list.get(pos).getTxt());
        mapRecyc.setVisibility(View.GONE);
        String jd = list.get(pos).getJwd().split(",")[0], wd = list.get(pos).getJwd().split(",")[1];
        jd = jd.substring(jd.indexOf(":") + 1, jd.length()).trim();
        wd = wd.substring(wd.indexOf(":") + 1, wd.length()).trim();
        jd_ = jd;
        wd_ = wd;
        llF = new LatLng(jd.length() <= 1 ? 40.022211 : Double.valueOf(jd), wd.length() <= 1 ? 116.499274 : Double.valueOf(wd));
        LatLng cenpt = new LatLng(jd.length() <= 1 ? 40.022211 : Double.valueOf(jd), wd.length() <= 1 ? 116.499274 : Double.valueOf(wd)); //设定中心点坐标
        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(cenpt)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态

        if (null != mBaiduMap.getMapStatus()) {
            llF = mBaiduMap.getMapStatus().target;
            mScreenCenterPoint = mBaiduMap.getProjection().toScreenLocation(llF);
            MarkerOptions ooF = new MarkerOptions().position(llF).icon(bdF).perspective(true)
                    .fixedScreenPosition(mScreenCenterPoint);
            mMarkerF = (Marker) (mBaiduMap.addOverlay(ooF));

        }
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);

        page = 0;
        list_hd.removeAll(list_hd);
        cenpt_page = cenpt;
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .location(cenpt)
                .radius(10000)
                .keyword("花店")
                .pageCapacity(20)
                .pageNum(page));
    }

    @Override
    public void huadianonc_(int pos) {
        closetype = 1;
//        mapEd.setText(list_hd.get(pos).getDizhi());
        mapRecyc.setVisibility(View.GONE);
        mapRecyc_huadian.setVisibility(View.VISIBLE);
        smartRefreshLayout.setVisibility(View.VISIBLE);

        String jd = list_hd.get(pos).getJwd().split(",")[0], wd = list_hd.get(pos).getJwd().split(",")[1];
        jd = jd.substring(jd.indexOf(":") + 1, jd.length()).trim();
        wd = wd.substring(wd.indexOf(":") + 1, wd.length()).trim();

        llF = new LatLng(jd.length() <= 1 ? 40.022211 : Double.valueOf(jd), wd.length() <= 1 ? 116.499274 : Double.valueOf(wd));
        LatLng cenpt = new LatLng(jd.length() <= 1 ? 40.022211 : Double.valueOf(jd), wd.length() <= 1 ? 116.499274 : Double.valueOf(wd)); //设定中心点坐标
        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(cenpt)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态

        if (null != mBaiduMap.getMapStatus()) {
            llF = mBaiduMap.getMapStatus().target;
            mScreenCenterPoint = mBaiduMap.getProjection().toScreenLocation(llF);
            MarkerOptions ooF = new MarkerOptions().position(llF).icon(bdF).perspective(true)
                    .fixedScreenPosition(mScreenCenterPoint);
            mMarkerF = (Marker) (mBaiduMap.addOverlay(ooF));

        }
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);
    }


    /**
     * 初始化定位参数配置
     */
    private void initLocationOption() {
//定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(getApplicationContext());
//声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        System.out.println("11111");
        MyLocationListener myLocationListener = new MyLocationListener();
//注册监听函数
        locationClient.registerLocationListener(myLocationListener);
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
//开始定位
//        locationClient.start();
    }


    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            System.out.println("hehhe");
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            //获取纬度信息
            jd_location = location.getLatitude();
            //获取经度信息
            wd_location = location.getLongitude();
            System.out.println("dw  " + jd_location + "  " + wd_location);
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();
            if (jd_location != 0) {
                llF = new LatLng(jd_location, wd_location);
                LatLng cenpt = new LatLng(jd_location, wd_location); //设定中心点坐标
                MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                        .target(cenpt)
                        .zoom(18)
                        .build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态
            }
        }
    }

    private void requesLocation() {
        if (jd_location != 0) {
            toaste_ut(BDMap.this, "定位成功");
            llF = new LatLng(jd_location, wd_location);
            LatLng cenpt = new LatLng(jd_location, wd_location); //设定中心点坐标
            MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                    .target(cenpt)
                    .zoom(18)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态

            closehuadianselect();

        } else {
            if (PermissionsUtil.hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //定位权限
                startlocation();
            } else {
                PermissionsUtil.requestPermission(this, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {
                        //定位权限
                        startlocation();
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                        //用户拒绝了
                        toaste_ut(BDMap.this, "定位失败,请开启花脉app定位功能");
                    }
                }, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION});
            }
        }
    }

    public void startlocation() {
        boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isOpen) {
            if (jd_location != 0) {
                llF = new LatLng(jd_location, wd_location);
                LatLng cenpt = new LatLng(jd_location, wd_location); //设定中心点坐标
                MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                        .target(cenpt)
                        .zoom(18)
                        .build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态

                closehuadianselect();

            } else
                locationClient.start();
        } else {
            if (!bdgps.isAdded())
                bdgps.show(getSupportFragmentManager(), "gps");
        }
    }

    @Override
    public void opengps() {
        if (bdgps.isAdded())
            bdgps.dismiss();
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 100);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void fuluegps() {
        if (bdgps.isAdded())
            bdgps.dismiss();
        toaste_ut(BDMap.this, "定位失败，请打开位置信息功能");
    }

    //当花店列表不为空时，定位成功，取消花店标记
    public void closehuadianselect() {
        if (list_hd.size() >= 1) {
            for (int i = 0; i < list_hd.size(); i++) {
                list_hd.get(i).setIsselsect("0");
            }
        }
        hdada.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            requesLocation();
        }
    }


}
