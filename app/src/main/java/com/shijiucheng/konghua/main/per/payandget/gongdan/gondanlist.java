package com.shijiucheng.konghua.main.per.payandget.gongdan;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.WorkOrderMVP.WorkorContact;
import com.shijiucheng.konghua.Cmvp.WorkOrderMVP.WorkorPrestent;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top1;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.gdlistada;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.gdlistdata;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.guanbigd;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.huifugd;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.pinjiagd;
import com.shijiucheng.konghua.renzheng.getpicdialogfragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class gondanlist extends com.shijiucheng.konghua.Cmvp.BaseActivity_konghua implements WorkorContact.View, gdlistada.closewo1, guanbigd.querenClose, TakePhoto.TakeResultListener, InvokeListener, getpicdialogfragment.paizao, huifugd.huifugdre, pinjiagd.getRefershwo {

    @BindView(R.id.gdlist_dh)
    DaoHang_top1 gdlistDh;
    @BindView(R.id.gdlist_recyc)
    RecyclerView gdlistRecyc;
    @BindView(R.id.gdlist_smtr)
    SmartRefreshLayout gdlistSmtr;

    gdlistada ada;
    List<gdlistdata> list = new ArrayList<>();
    @BindView(R.id.gdlistit_teqb)
    TextView gdlistitTeqb;
    @BindView(R.id.gdlistit_tewwc)
    TextView gdlistitTewwc;

    WorkorPrestent prestent = new WorkorPrestent(this);
    int refreshStatus = 0, page = 1, type = 0;
    String keyWord = "";
    guanbigd closewo;
    String closegdId = "";
    int closegdPos = 0;
    Retro_Intf serivce;


    @BindView(R.id.view_1)
    View v_1;
    @BindView(R.id.view_2)
    View v_2;


    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    huifugd huifugdz;

    @BindView(R.id.ordertwo_nodata)
    View layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void AddView() {
        EventBus.getDefault().register(this);
        closewo = new guanbigd();
        serivce = retrofit_Single.getInstence().getserivce(2);
        gdlistDh.settext_("问题列表");
        Glide.with(this).load(R.mipmap.wtadd).into(gdlistDh.ima_tp);

        gdlistitTeqb.setSelected(true);
        gdlistitTewwc.setSelected(false);
        v_1.setBackgroundColor(getResources().getColor(R.color.zhu));
        v_2.setBackgroundColor(getResources().getColor(R.color.danhei));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        gdlistRecyc.setLayoutManager(layoutManager);
        ada = new gdlistada(this, list);
        ada.setclosewoI(this);
        gdlistRecyc.setAdapter(ada);
        prestent.getList(this,keyWord, retrofit_Single.getInstence().getOpenid(gondanlist.this), type, page);
        huifugdz = new huifugd();

    }

    @Override
    protected void SetViewListen() {
        gdlistDh.ima_tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByIntent(gondanlist.this, mygondan.class);
            }
        });
        gdlistDh.teadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByIntent(gondanlist.this, mygondan.class);
            }
        });
        gdlistSmtr.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshStatus = 2;
                page++;
                prestent.getList(gondanlist.this,keyWord, retrofit_Single.getInstence().getOpenid(gondanlist.this), type, page);

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshStatus = 1;
                page = 1;
                list.removeAll(list);
                prestent.getList(gondanlist.this,keyWord, retrofit_Single.getInstence().getOpenid(gondanlist.this), type, page);
            }
        });

        gdlistitTeqb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gdlistitTeqb.isSelected()) {
                    gdlistitTeqb.setSelected(true);
                    gdlistitTewwc.setSelected(false);
                    v_1.setBackgroundColor(getResources().getColor(R.color.zhu));
                    v_2.setBackgroundColor(getResources().getColor(R.color.danhei));
                    list.removeAll(list);
                    type = 0;
                    gdlistSmtr.autoRefresh();
                }
            }
        });

        gdlistitTewwc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gdlistitTewwc.isSelected()) {
                    gdlistitTewwc.setSelected(true);
                    gdlistitTeqb.setSelected(false);
                    v_2.setBackgroundColor(getResources().getColor(R.color.zhu));
                    v_1.setBackgroundColor(getResources().getColor(R.color.danhei));
                    type = 1;
                    gdlistSmtr.autoRefresh();
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gondanlist;
    }

    @Override
    protected BasePresenter bindPresent() {
        return prestent;
    }

    @Override
    public void showloading() {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "gdlist");
    }

    @Override
    public void closeload() {
        if (jdt.isAdded())
            jdt.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        toaste_ut(gondanlist.this, msg);
    }

    @Override
    public void showList(int type, List<gdlistdata> list) {
        this.list.addAll(list);
        ada.notifyDataSetChanged();

        if (this.list.size() <= 0) {
            if (layout != null)
                layout.setVisibility(View.VISIBLE);
        } else {
            if (layout != null)
                layout.setVisibility(View.GONE);
        }

        if (refreshStatus == 1) {
            {
                if (gdlistSmtr != null)
                    gdlistSmtr.finishRefresh();
            }
        } else {
            if (gdlistSmtr != null)
                gdlistSmtr.finishLoadmore();
        }
    }


    @Override
    public void closewo1(String id, int position) {
        closegdId = id;
        closegdPos = position;
        if (!closewo.isAdded()) {
            closewo.show(getFragmentManager(), "gdclose");
        }
    }

    @Override
    public void showHuifu(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("gdid", id);
        bundle.putString("type", "0");
        huifugdz.setArguments(bundle);
        if (!huifugdz.isAdded())
            huifugdz.show(getFragmentManager(), "gdfh");
    }


    @Override
    public void querenclose() {
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("id", closegdId);
        Call<ResponseBody> call = serivce.closeWorkorder(retrofit_Single.getInstence().getOpenid(gondanlist.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        if (jso.getString("status").equals("1")) {
                            list.get(closegdPos).setStatus1("15");
                            ada.notifyDataSetChanged();
                            toaste_ut(gondanlist.this, "该问题关闭成功");
                        } else
                            toaste_ut(gondanlist.this, jso.getString("msg"));
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


    /**
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setenddate(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.gondanlist)) {
                if (gdlistSmtr != null) {
                    list.removeAll(list);
                    gdlistSmtr.autoRefresh();
                }
            }
            return;
        }
    }


    //获取手机图片
    public void getpic(int flag) {
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
        Uri uri = Uri.fromFile(file);
        int size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        CropOptions cropOptions = new CropOptions.Builder().setOutputX(size).setOutputX(size).setWithOwnCrop(false).create();
        if (flag == 1) { //相机获取照片并剪裁
//                        takePhoto.onPickFromCaptureWithCrop(uri, cropOptions);
            //相机获取不剪裁
            takePhoto.onPickFromCapture(uri);
        } else if (flag == 2) { //相册获取照片并剪裁
//            takePhoto.onPickFromGalleryWithCrop(uri, cropOptions); //相册获取不剪裁
            takePhoto.onPickFromGallery();
        } else if (flag == 3) { //多选，并剪裁
//            takePhoto.onPickMultipleWithCrop(9, cropOptions); //多选，不剪裁 //
            takePhoto.onPickMultiple(1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        //设置压缩规则，最大500kb
//        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).create(), true);
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        File test = new File(result.getImage().getOriginalPath());
        if (test.exists()) {
            if (test.exists()) {
                Luban.with(this).load(test)
                        .ignoreBy(100)
                        .setTargetDir("")
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                if (!jdt.isAdded())
                                    jdt.show(getFragmentManager(), "jdt");
                                try {
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onSuccess(File file) {
                                if (jdt.isAdded())
                                    jdt.dismiss();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String str = "";
                                        try {
                                            str = encodeBase64File(file);
                                            upLoadImgs(str);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 500);
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        }).launch();    //启动压缩
            }
        }
    }

    public String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }


    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    public void getpermiss(int type) {
        getpic(type);
    }

    @Override
    public void getPaiZ(int pz) {
        if (pz == 1) {
            getpermiss(1);
        } else if (pz == 2) {
            getpermiss(2);
        }
    }

    public void upLoadImgs(String urlEncode) {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "jdt");
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("file_content", urlEncode);
        Call<ResponseBody> call = serivce.WOuploadimg(retrofit_Single.getInstence().getOpenid(gondanlist.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt.isAdded())
                    jdt.dismiss();
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        if (jso.getString("status").equals("1")) {
                            if (huifugdz.isAdded()) {
                                huifugdz.showimgs(jso.getJSONObject("data").getString("url"));
                            }
                            toaste_ut(gondanlist.this, "图片上传成功");
                        } else
                            toaste_ut(gondanlist.this, jso.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (jdt.isAdded())
                    jdt.dismiss();
            }
        });

    }

    @Override
    public void huifugd() {
        getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
        getpicdialogfragment.show(getFragmentManager(), "pzdzp");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//回复后刷新当前item数据
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.wodetalisrefresh1)) {
                int pos = (int) data.getT();
                list.get(pos).setStatus1("15");
                ada.notifyDataSetChanged();
                return;
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//评价后刷新当前item数据
    public void getmess1(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.wodetalisrefresh2)) {
                int pos = (int) data.getT();
                list.get(pos).setStatus1("20");
                ada.notifyDataSetChanged();
                return;
            }

        }
    }

    @Override
    public void refreshpj(int position) {
        list.get(position).setStatus1("20");
        ada.notifyDataSetChanged();
    }
}
