package com.shijiucheng.konghua.main.HomePage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.HomePage.frag.riliTime_frag;
import com.shijiucheng.konghua.renzheng.getpicdialogfragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class qianshou extends BaseActivity_konghua implements TakePhoto.TakeResultListener, InvokeListener, getpicdialogfragment.paizao, riliTime_frag.setdatetime {

    @BindView(R.id.qs_dh)
    DaoHang_top qsDh;
    @BindView(R.id.qs_teqianshousj)
    TextView qsTeqianshousj;
    @BindView(R.id.qs_teqsr)
    TextView qsTeqsr;
    @BindView(R.id.qs_tebrqs)
    ImageView qsTebrqs;
    @BindView(R.id.qs_tebrqs1)
    TextView qsTebrqs1;
    @BindView(R.id.qs_tetrqs)
    ImageView qsTetrqs;
    @BindView(R.id.qs_tetrqs1)
    TextView qsTetrqs1;
    @BindView(R.id.qs_tp1)
    ImageView qsImqstp1;
    @BindView(R.id.qs_teqsts)
    TextView qsTeqsts;

    @BindView(R.id.qs_teqsts1)
    TextView qsTeqsts1;

    @BindView(R.id.qs_teok)
    TextView te_qs;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
    riliTime_frag riliTime_frag = new riliTime_frag();
    int ix = 1;
    String order_sign_time = "", dataurl = "", order_sign_personnel_type = "1";

    com.shijiucheng.konghua.main.HomePage.frag.riliTime_frag riliTime_fra = new riliTime_frag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void AddView() {
        qsDh.settext_("确认签收");

        SpannableStringBuilder builder = new SpannableStringBuilder(qsTeqsts1.getText().toString());
        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(getResources().getColor(R.color.danhei));
        builder.setSpan(yellowSpan, 22, qsTeqsts1.getText().toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        qsTeqsts1.setText(builder);

    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_qianshou;
    }

    @OnClick({R.id.qs_teqianshousj, R.id.qs_tebrqs, R.id.qs_tebrqs1, R.id.qs_tetrqs, R.id.qs_tetrqs1, R.id.qs_teok, R.id.qs_tp1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qs_teqianshousj:
                if (!riliTime_frag.isAdded()) {
                    riliTime_frag.show(getSupportFragmentManager(), "rit");
                }
                break;
            case R.id.qs_tebrqs:

                qsTebrqs.setImageResource(R.mipmap.xzhong);
                qsTetrqs.setImageResource(R.mipmap.xzhong1);
                order_sign_personnel_type = "1";

                break;
            case R.id.qs_tebrqs1:
                qsTebrqs.setImageResource(R.mipmap.xzhong);
                qsTetrqs.setImageResource(R.mipmap.xzhong1);
                order_sign_personnel_type = "1";
                break;
            case R.id.qs_tetrqs:
                qsTebrqs.setImageResource(R.mipmap.xzhong1);
                qsTetrqs.setImageResource(R.mipmap.xzhong);
                order_sign_personnel_type = "2";
                break;
            case R.id.qs_tetrqs1:
                qsTebrqs.setImageResource(R.mipmap.xzhong1);
                qsTetrqs.setImageResource(R.mipmap.xzhong);
                order_sign_personnel_type = "2";
                break;
            case R.id.qs_teok:

                if (TextUtils.isEmpty(order_sign_time)) {
                    toaste_ut(qianshou.this, "请选择签收时间");
                    if (!riliTime_frag.isAdded()) {
                        riliTime_frag.show(getSupportFragmentManager(), "rit");
                    }
                    return;
                }
                if (TextUtils.isEmpty(dataurl)) {
                    toaste_ut(qianshou.this, "请上传配送图片");
                    return;
                }
                qianshoup(dataurl);
                break;
            case R.id.qs_tp1:
                ix = 1;
                getpicdialogfragment.show(getFragmentManager(), "pzdzp");
                break;
        }
    }


    private void qianshoup(String url) {
        jdt.show(getFragmentManager(), "jdt");
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("order_id", getIntent().getStringExtra("id"));
        map.put("order_sign_personnel_type", order_sign_personnel_type);
        map.put("order_sign_images", url);
        map.put("order_sign_time", order_sign_time);
        Call<ResponseBody> call = serivce.qianshou(retrofit_Single.getInstence().getOpenid(this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt != null)
                    if (jdt.isAdded())
                        jdt.dismiss();
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            toaste_ut(qianshou.this, "操作成功");
                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.orderqianshou);
                            Bundle bundle = new Bundle();
                            databean.setT(bundle);
                            EventBus.getDefault().post(databean);
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        } else {

                            toaste_ut(qianshou.this, jsonObject.getString("msg"));
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
                if (jdt != null)
                    if (jdt.isAdded())
                        jdt.dismiss();
            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void takeSuccess(TResult result) {
        File test = new File(result.getImage().getOriginalPath());
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
                            uploadpic(ix, str);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (jdt.isAdded())
                                jdt.dismiss();
                        }
                    }).launch();    //启动压缩
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


    private void uploadpic(final int xx, String url) {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "qstp");
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("file_content", url);
        Call<ResponseBody> call = serivce.uploadpeisongtp(retrofit_Single.getInstence().getOpenid(this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt.isAdded()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (jdt.isAdded())
                                jdt.dismiss();
                        }
                    }, 500);
                }
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            if (jdt != null)
                                if (jdt.isAdded())
                                    jdt.dismiss();

                            JSONObject jso = jsonObject.getJSONObject("data");
                            Glide.with(qianshou.this)
                                    .load(jso.getString("url"))
                                    .into(qsImqstp1);
                            dataurl = jso.getString("url");


                        } else {

                            toaste_ut(qianshou.this, jsonObject.getString("msg"));
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
                jdt.dismiss();
            }
        });

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getPaiZ(int pz) {
        if (pz == 1) {
            getpermiss(1);
        } else if (pz == 2) {
            getpermiss(2);
        }
    }

    public void getpermiss(int type) {
        getpic(type);
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
    public void setdatetime(String time) {
        order_sign_time = time;
        qsTeqianshousj.setText("签收时间:  " + time);
    }
}
