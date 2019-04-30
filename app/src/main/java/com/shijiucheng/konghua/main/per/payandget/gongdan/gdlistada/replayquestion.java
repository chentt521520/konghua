package com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.shijiucheng.konghua.renzheng.getpicdialogfragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

public class replayquestion extends BaseActivity_konghua implements TakePhoto.TakeResultListener, InvokeListener, getpicdialogfragment.paizao {

    @BindView(R.id.hfgd_edtxt)
    EditText hfgdEdtxt;
    @BindView(R.id.hfgd_tenum)
    TextView hfgdTenum;
    @BindView(R.id.fhgd_imtp1)
    ImageView fhgdImtp1;
    @BindView(R.id.fhgd_imtp2)
    ImageView fhgdImtp2;
    @BindView(R.id.fhgd_imtp3)
    ImageView fhgdImtp3;
    @BindView(R.id.fhgd_lintp)
    LinearLayout fhgdLintp;
    @BindView(R.id.fhgd_teok)
    TextView fhgdTeok;
    @BindView(R.id.hfgd_dh)
    DaoHang_top daoHang_top;

    getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    int pos_tp = 0;//设置操作图片的下标
    String pos1 = "", pos2 = "", pos3 = "";

    String questionid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void AddView() {
        questionid = getIntent().getStringExtra("id");
        daoHang_top.settext_("提交问题");
    }

    @Override
    protected void SetViewListen() {
        hfgdEdtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hfgdTenum.setText(hfgdEdtxt.getText().toString().length() + "/1000");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_replayquestion;
    }

    @OnClick({R.id.fhgd_imtp1, R.id.fhgd_imtp2, R.id.fhgd_imtp3, R.id.fhgd_teok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fhgd_imtp1:
                if (!getpicdialogfragment.isAdded()) {
                    pos_tp = 0;
                    getpicdialogfragment.show(getFragmentManager(), "pzdzp");
                }
                break;
            case R.id.fhgd_imtp2:
                if (!getpicdialogfragment.isAdded()) {
                    pos_tp = 1;
                    getpicdialogfragment.show(getFragmentManager(), "pzdzp");
                }
                break;
            case R.id.fhgd_imtp3:
                if (!getpicdialogfragment.isAdded()) {
                    pos_tp = 2;
                    getpicdialogfragment.show(getFragmentManager(), "pzdzp");
                }
                break;
            case R.id.fhgd_teok:

                if (TextUtils.isEmpty(hfgdEdtxt.getText().toString())) {
                    toaste_ut(replayquestion.this, "请填写回复内容");
                } else {
                    String urls = "";
                    if (!TextUtils.isEmpty(pos1))
                        urls += pos1 + ",";
                    if (!TextUtils.isEmpty(pos2))
                        urls += pos2 + ",";
                    if (!TextUtils.isEmpty(pos3))
                        urls += pos3 + ",";
                    if (urls.contains(".")) {
                        urls = urls.substring(0, urls.length() - 1);
                    }
                    uploadReplay(questionid, urls, hfgdEdtxt.getText().toString());
                }
                break;
        }
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
//                            if (!jdt.isAdded())
//                                jdt.show(getFragmentManager(), "jdt");
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onSuccess(File file) {
                            String str = "";
                            try {
//                                if (jdt.isAdded())
//                                    jdt.dismiss();
                                str = encodeBase64File(file);
                                upLoadimg(retrofit_Single.getInstence().getOpenid(replayquestion.this), str);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
//                            if (jdt.isAdded())
//                                jdt.dismiss();
                        }
                    }).launch();    //启动压缩
        }
    }


    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    public String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
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


    Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);

    public void upLoadimg(String cook, String urlEnc) {
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("file_content", urlEnc);
        Call<ResponseBody> call = serivce.WOuploadimg(cook, map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    JSONObject jsonObject = new JSONObject(str);
                    if (jsonObject.getString("status").equals("1")) {
                        showUrl(jsonObject.getJSONObject("data").getString("url"));
                    } else toaste_ut(replayquestion.this, "上传图片失败，请重试");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public void showUrl(String url) {
        if (pos_tp == 0) {
            pos1 = url;
            Glide.with(replayquestion.this).load(url).into(fhgdImtp1);
        } else if (pos_tp == 1) {
            pos2 = url;
            Glide.with(replayquestion.this).load(url).into(fhgdImtp2);
        } else if (pos_tp == 2) {
            pos3 = url;
            Glide.with(replayquestion.this).load(url).into(fhgdImtp3);
        }
    }


    private void uploadReplay(String id, String urls, String str) {
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("id", id);
        map.put("reply_images", urls);
        map.put("reply_content", str);
        System.out.println(id + "  " + urls + " " + str);
        Call<ResponseBody> call = serivce.WOupload(retrofit_Single.getInstence().getOpenid(this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        if (jso.getString("status").equals("1")) {
                            toaste_ut(replayquestion.this, "回复成功，请等待管理员回复");
                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.wodetalisrefresh11);
                            int pos = getIntent().getIntExtra("pos", 0);
                            databean.setT(pos);
                            EventBus.getDefault().post(databean);
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        } else
                            toaste_ut(replayquestion.this, "提交失败");
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
}
