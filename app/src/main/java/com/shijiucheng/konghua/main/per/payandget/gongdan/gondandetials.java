package com.shijiucheng.konghua.main.per.payandget.gongdan;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top1;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.gdxqada;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.gdxqdata;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.guanbigd;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.huifugd;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.pinjiagd;
import com.shijiucheng.konghua.renzheng.getpicdialogfragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

public class gondandetials extends BaseActivity_konghua implements guanbigd.querenClose, TakePhoto.TakeResultListener, InvokeListener, getpicdialogfragment.paizao, huifugd.huifugdre, pinjiagd.getRefershwo {

    @BindView(R.id.gdxq_dh)
    DaoHang_top1 gdxqDh;
    @BindView(R.id.gdxq_teysl)
    TextView gdxqTeysl;
    @BindView(R.id.gdxq_teclz)
    TextView gdxqTeclz;
    @BindView(R.id.gdxq_teyqr)
    TextView gdxqTeyqr;
    @BindView(R.id.gdxq_tedpj)
    TextView gdxqTedpj;
    @BindView(R.id.gdxq_recyc)
    RecyclerView gdxqRecyc;
    @BindView(R.id.gdxq_tehuifu)
    TextView gdxqTehuifu;
    @BindView(R.id.gdxq_teguanbi)
    TextView gdxqTeguanbi;
    @BindView(R.id.gdxq_lincz)
    LinearLayout gdxqLincz;
    @BindView(R.id.gdxq_tebhao)
    TextView gdxqTebhao;
    @BindView(R.id.gdxq_teleibie)
    TextView gdxqTeleibie;

    @BindView(R.id.gdxq_tepj)
    TextView gdxqTepj;

    gdxqada gdxqada;
    List<gdxqdata> list = new ArrayList<>();

    Retro_Intf serivce;
    String id = "";
    String type = "";
//            //工单提交待受理
//	          'WORK_ORDER_STATUS_NEW'=>0,
//            //工单已受理
//            'WORK_ORDER_STATUS_ACCEPTED'=>5,
//            //工单处理中(管理员已回复)
//            'WORK_ORDER_STATUS_REPLY_ADMIN'=>10,
//            //工单处理中(花店已回复)
//            'WORK_ORDER_STATUS_REPLY_STORE'=>11,
//            //工单已确认
//            'WORK_ORDER_STATUS_CLOSED'=>15,
//            //工单已评价
//            'WORK_ORDER_STATUS_EVALUATE'=>20,

    guanbigd gdclose;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    huifugd huifugdz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void AddView() {
        EventBus.getDefault().register(this);
        gdclose = new guanbigd();
        huifugdz = new huifugd();
        gdxqDh.settext_("工单详情");
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");

        if (type.equals("已关闭"))
            gdxqTepj.setVisibility(View.GONE);
        else if (type.equals("已评价")) {
            gdxqLincz.setVisibility(View.GONE);
            gdxqTepj.setVisibility(View.GONE);
        } else {
            gdxqLincz.setVisibility(View.VISIBLE);
            gdxqTepj.setVisibility(View.GONE);
        }

        getStatus();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        gdxqRecyc.setLayoutManager(manager);
        gdxqada = new gdxqada(this, list);
        gdxqRecyc.setAdapter(gdxqada);
        serivce = retrofit_Single.getInstence().getserivce(2);
        getDetails();
    }

    @Override
    protected void SetViewListen() {

        gdxqTeguanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gdclose.isAdded())
                    gdclose.show(getFragmentManager(), "hfgd");
            }
        });
        gdxqTehuifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("gdid", id);
                    bundle.putString("type", "1");
                    huifugdz.setArguments(bundle);
                    huifugdz.show(getFragmentManager(), "hfgd");
                }
            }
        });
        gdxqTepj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinjiagd pinjiagd = new pinjiagd();
                Bundle bundle = new Bundle();
                bundle.putString("gdid", id);
                bundle.putInt("pos", getIntent().getIntExtra("position", 0));
                pinjiagd.setArguments(bundle);
                pinjiagd.show(getFragmentManager(), "hfgd");
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gondandetials;
    }

    public void getDetails() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        retrofit2.Call<ResponseBody> call = serivce.getWorkOrderDetils(retrofit_Single.getInstence().getOpenid(gondandetials.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        JSONArray jsa = jso.getJSONObject("data").getJSONArray("reply_list");
                        JSONObject jsaOr = jso.getJSONObject("data").getJSONObject("work_order_info");
                        gdxqTebhao.setText("工单编号：" + jsaOr.getString("id"));
                        gdxqTeleibie.setText(jsaOr.getString("cate1_text") + ">" + jsaOr.getString("cate2_text"));
                        type = jsaOr.getString("work_order_status");
                        getStatus();
                        if (jsa.length() >= 1) {
                            list.removeAll(list);
                            list.add(new gdxqdata("0", jsaOr.getString("add_time_text"), jsaOr.getString("content_text"), jsaOr.getString("work_order_images")));
                            for (int i = 0; i < jsa.length(); i++) {
                                JSONObject jsoList = jsa.getJSONObject(i);
                                list.add(new gdxqdata(jsoList.getString("reply_type"), jsoList.getString("add_time_text"), jsoList.getString("reply_content"), jsoList.getString("reply_images")));
                            }
                            gdxqada.notifyDataSetChanged();
                        }
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

    public void getStatus() {
        if (type.equals("0")) {
            gdxqLincz.setVisibility(View.GONE);
            gdxqTepj.setVisibility(View.GONE);
        } else if (type.equals("5")) {
            gdxqLincz.setVisibility(View.VISIBLE);
            gdxqTehuifu.setVisibility(View.GONE);
            gdxqTepj.setVisibility(View.GONE);
            gdxqTeysl.setSelected(true);

        } else if (type.equals("10") || type.equals("11")) {
            gdxqLincz.setVisibility(View.VISIBLE);
            gdxqTepj.setVisibility(View.GONE);

            gdxqTeysl.setSelected(true);
            gdxqTeclz.setSelected(true);

        } else if (type.equals("15")) {
            gdxqLincz.setVisibility(View.GONE);
            gdxqTepj.setVisibility(View.VISIBLE);
            gdxqTeysl.setSelected(true);
            gdxqTeclz.setSelected(true);
            gdxqTeyqr.setSelected(true);

        } else if (type.equals("20")) {
            gdxqLincz.setVisibility(View.GONE);
            gdxqTepj.setVisibility(View.GONE);

            gdxqTeysl.setSelected(true);
            gdxqTeclz.setSelected(true);
            gdxqTeyqr.setSelected(true);
            gdxqTedpj.setSelected(true);

        } else {
            gdxqLincz.setVisibility(View.VISIBLE);
            gdxqTepj.setVisibility(View.GONE);
        }
    }

    @Override
    public void querenclose() {
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("id", id);
        Call<ResponseBody> call = serivce.closeWorkorder(retrofit_Single.getInstence().getOpenid(gondandetials.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        if (jso.getString("status").equals("1")) {
                            toaste_ut(gondandetials.this, "该工单关闭成功");

                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.wodetalisrefresh1);
                            int pos = getIntent().getIntExtra("position", 0);
                            databean.setT(pos);
                            EventBus.getDefault().post(databean);

                            finish();
                            overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
                        } else
                            toaste_ut(gondandetials.this, jso.getString("msg"));
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
                                String str = "";
                                try {
                                    str = encodeBase64File(file);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                upLoadImgs(str);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (jdt.isAdded())
                                    jdt.dismiss();
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("file_content", urlEncode);
        Call<ResponseBody> call = serivce.WOuploadimg(retrofit_Single.getInstence().getOpenid(gondandetials.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        if (jso.getString("status").equals("1")) {
                            if (huifugdz != null) {
                                huifugdz.showimgs(jso.getJSONObject("data").getString("url"));
                            }
                            toaste_ut(gondandetials.this, "图片上传成功");
                        } else
                            toaste_ut(gondandetials.this, jso.getString("msg"));
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
        if (jdt != null)
            jdt.dismiss();
    }

    @Override
    public void huifugd() {
        getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
        getpicdialogfragment.show(getFragmentManager(), "pzdzp");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.wodetalisrefresh)) {
                getDetails();
                return;
            }

        }
    }

    @Override
    public void refreshpj(int position) {

        getDetails();

        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.wodetalisrefresh2);
        int pos = getIntent().getIntExtra("position", 0);
        databean.setT(pos);
        EventBus.getDefault().post(databean);
    }
}
