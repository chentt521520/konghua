package com.shijiucheng.konghua.main.HomePage;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BDMap1 extends BaseActivity_konghua {
    Unbinder munbind;
    @BindView(R.id.map_lintop)
    RelativeLayout re_top;
    @BindView(R.id.map_imreturn)
    ImageView ima_fh;
    @BindView(R.id.map_tetit)
    TextView te_tit;

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker mMarkerF;
    private LatLng llF;
    DecimalFormat df;

    BitmapDescriptor bdF = BitmapDescriptorFactory
            .fromResource(R.mipmap.qidian);
    BitmapDescriptor bdF1 = BitmapDescriptorFactory
            .fromResource(R.mipmap.zhongdian);
    private Point mScreenCenterPoint;

    String address_ = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        munbind = ButterKnife.bind(this);
        mMapView = findViewById(R.id.bmapViewxx);
        mBaiduMap = mMapView.getMap();


        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                initOverlay();
            }
        });

        df = new DecimalFormat("###.000000");
        AddView();
        SetViewListen();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapView.setVisibility(View.VISIBLE);
            }
        }, 2000);


        List<OverlayOptions> options = new ArrayList<OverlayOptions>(); //设置坐标点
        // 在地图上批量添加

        Intent i = getIntent();

        LatLng p1 = new LatLng(i.getDoubleExtra("la1",0), i.getDoubleExtra("lo1",0));
        LatLng p2 = new LatLng(i.getDoubleExtra("la2",0), i.getDoubleExtra("lo2",0));
        List<LatLng> points = new ArrayList<>();
        points.add(p2);
        points.add(p1);


        System.out.println(i.getDoubleExtra("la1",0)+"    "+i.getDoubleExtra("lo1",0));
        System.out.println(i.getDoubleExtra("la2",0)+"    "+i.getDoubleExtra("lo2",0));

        OverlayOptions option1 = new MarkerOptions().position(p1).icon(bdF1);

        OverlayOptions option2 = new MarkerOptions().position(p2).icon(bdF);

        options.add(option1);
        options.add(option2);
        mBaiduMap.addOverlays(options);

        OverlayOptions ooPolyline = new PolylineOptions().width(10).color(0xAAFF0000).points(points);
        Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);


    }


    /**
     * 初始化Overlay
     */
    public void initOverlay() {
        // add marker overlay
        Intent i = getIntent();

        LatLng p1 = new LatLng(i.getDoubleExtra("la2",0), i.getDoubleExtra("lo2",0));

        LatLng cenpt = new LatLng(i.getDoubleExtra("la2",0),  i.getDoubleExtra("lo2",0)); //设定中心点坐标
        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(cenpt)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态



        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);

    }

    /**
     * 开启动画
     *
     * @param view
     */
    public void startAnimation(View view) {
    }

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
//        mMarkerF.cancelAnimation();

        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
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
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_bdmap1;
    }

}
