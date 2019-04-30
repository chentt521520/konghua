package com.shijiucheng.konghua.renzheng;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.dianpuMvp.Contact;
import com.shijiucheng.konghua.Cmvp.dianpuMvp.DianpuPresentIml;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Retrofit2.retrofit_Single;
import butterknife.BindView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class DianPu extends com.shijiucheng.konghua.Cmvp.BaseActivity_konghua implements Contact.IdianPuView, TakePhoto.TakeResultListener, InvokeListener, getpicdialogfragment.paizao {
    @BindView(R.id.dianpu_dh)
    DaoHang_top dh;

    @BindView(R.id.dianpu_linqy)
    LinearLayout lin_qy;
    @BindView(R.id.dianpu_teqy)
    TextView te_qy;
    @BindView(R.id.dianpu_edqy)
    EditText ed_qy;

    @BindView(R.id.dianpu_linzch)
    LinearLayout lin_zch;
    @BindView(R.id.dianpu_tezch)
    TextView te_zch;
    @BindView(R.id.dianpu_edzch)
    EditText ed_zch;

    @BindView(R.id.dianpu_tesfzt)
    TextView te_zztp;
    @BindView(R.id.dianpu_imzzhao)
    ImageView ima_zztp;
    @BindView(R.id.dianzhu_tezzts)
    TextView te_zzts;

    @BindView(R.id.dianzhu_temmzx)
    TextView te_zdphj;

    @BindView(R.id.pinjia_retp1)
    RelativeLayout re_tp1;
    @BindView(R.id.pinjia_imtp1)
    ImageView im_tp1;
    @BindView(R.id.pinjia_imtp1de)
    ImageView im_tp1de;

    @BindView(R.id.pinjia_retp2)
    RelativeLayout re_tp2;
    @BindView(R.id.pinjia_imtp2)
    ImageView im_tp2;
    @BindView(R.id.pinjia_imtp2de)
    ImageView im_tp2de;

    @BindView(R.id.pinjia_retp3)
    RelativeLayout re_tp3;
    @BindView(R.id.pinjia_imtp3)
    ImageView im_tp3;
    @BindView(R.id.pinjia_imtp3de)
    ImageView im_tp3de;

    @BindView(R.id.dianpu_teok)
    TextView te_ok;

    List<String> url = new ArrayList<>();

    Contact.IdianPuPresent puPresent = new DianpuPresentIml(this);
    int pos = 0;//0不是是营业执照图片，1是身份证图片的第一张，依次

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    String file0 = "", file1 = "", file2 = "", file3 = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void AddView() {
        dh.settext_("填写店铺信息");
        if (authen_RZ.jsonAuthor != null) {
            ed_qy.setText(authen_RZ.jsonAuthor.getCompany_name());
            ed_zch.setText(authen_RZ.jsonAuthor.getCompany_resigter_number());
            if (!TextUtils.isEmpty(authen_RZ.jsonAuthor.getBusiness_licence_scan())) {
                Glide.with(getApplicationContext()).load(authen_RZ.jsonAuthor.getBusiness_licence_scan()).into(ima_zztp);
            }
            if (!TextUtils.isEmpty(authen_RZ.jsonAuthor.getBusiness_licence_scan())) {
                Glide.with(getApplicationContext()).load(authen_RZ.jsonAuthor.getFacade_photo1()).into(im_tp1);
            }
            if (!TextUtils.isEmpty(authen_RZ.jsonAuthor.getBusiness_licence_scan())) {
                Glide.with(getApplicationContext()).load(authen_RZ.jsonAuthor.getFacade_photo2()).into(im_tp2);
            }
            if (!TextUtils.isEmpty(authen_RZ.jsonAuthor.getBusiness_licence_scan())) {
                Glide.with(getApplicationContext()).load(authen_RZ.jsonAuthor.getFacade_photo3()).into(im_tp3);
            }
            file0 = authen_RZ.jsonAuthor.getBusiness_licence_scan();
            file1 = authen_RZ.jsonAuthor.getFacade_photo1();
            file2 = authen_RZ.jsonAuthor.getFacade_photo2();
            file3 = authen_RZ.jsonAuthor.getFacade_photo3();
        }


        setViewHw_Lin(lin_qy, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 30 / 750.0), (int) (w_ * 14 / 750.0), 0);
//        setTextSize(ed_qy, (int) (w_ * 28 / 750.0));
//        setTextSize(ed_qy, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(lin_zch, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 30 / 750.0), (int) (w_ * 14 / 750.0), 0);
//        setTextSize(te_zch, (int) (w_ * 28 / 750.0));
//        setTextSize(ed_zch, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(te_zztp, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 30 / 750.0), (int) (w_ * 14 / 750.0), 0);
//        setTextSize(te_zztp, (int) (w_ * 28 / 750.0));


        setViewHw_Lin(re_tp1, (int) (w_ * 231 / 750.0), (int) (w_ * 231 / 750.0),
                (int) (w_ * 14 / 750.0), 0, 0, (int) (w_ * 7 / 750.0));
        setViewHw_Lin(re_tp2, (int) (w_ * 231 / 750.0), (int) (w_ * 231 / 750.0),
                (int) (w_ * 7 / 750.0), 0, 0, (int) (w_ * 7 / 750.0));
        setViewHw_Lin(re_tp3, (int) (w_ * 231 / 750.0), (int) (w_ * 231 / 750.0),
                (int) (w_ * 7 / 750.0), 0, 0, (int) (w_ * 14 / 750.0));
        setViewHw_Lin(ima_zztp, w_, (int) (w_ * 260 / 750.0), 0, 0, 0, 0);

        setViewHw_Lin(te_zzts, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 14 / 750.0), 0);
//        setTextSize(te_zzts, (int) (w_ * 28 / 750.0));

//        setTextSize(te_ok, (int) (w_ * 46 / 750.0));
    }

    @Override
    protected void SetViewListen() {
        ima_zztp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = 0;
                if (fastClick()) {
                    getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
                    getpicdialogfragment.show(getFragmentManager(), "pzdzp");
                }
            }
        });
        te_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(ed_qy.getText().toString())) {
                            toaste_ut(DianPu.this, "请填写企业名字");
                            return;
                        }
                        if (TextUtils.isEmpty(ed_zch.getText().toString())) {
                            toaste_ut(DianPu.this, "请填写营业执照号码");
                            return;
                        }
                        if (TextUtils.isEmpty(file0)) {
                            toaste_ut(DianPu.this, "请上传营业执照");
                            return;
                        }
                        if (TextUtils.isEmpty(file1) || TextUtils.isEmpty(file2) || TextUtils.isEmpty(file3)) {
                            toaste_ut(DianPu.this, "请上3张门店图片");
                            return;
                        }
                        authen_RZ.jsonAuthor.setCompany_name(ed_qy.getText().toString());
                        authen_RZ.jsonAuthor.setCompany_resigter_number(ed_zch.getText().toString());
                        paramsDataBean databean = new paramsDataBean();
                        databean.setMsg(configParams.dprzStep2);
                        EventBus.getDefault().post(databean);
                        finish();
                        overridePendingTransition(R.anim.push_right_out,
                                R.anim.push_right_in);
                    }
                }, 1000);
            }
        });
        im_tp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = 1;
                if (fastClick()) {
                    getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
                    getpicdialogfragment.show(getFragmentManager(), "pzdzp");
                }
            }
        });
        im_tp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = 2;
                if (fastClick()) {
                    getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
                    getpicdialogfragment.show(getFragmentManager(), "pzdzp");
                }
            }
        });

        im_tp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = 3;
                if (fastClick()) {
                    getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
                    getpicdialogfragment.show(getFragmentManager(), "pzdzp");
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_dian_pu;
    }

    @Override
    protected BasePresenter bindPresent() {
        return puPresent;
    }

    private void xianyin(int i) {
        switch (i) {
            case 0:
                re_tp2.setVisibility(View.INVISIBLE);
                re_tp3.setVisibility(View.INVISIBLE);
                re_tp1.setVisibility(View.INVISIBLE);

                break;
            case 1:
                im_tp1.setTag(url.get(0));
                re_tp2.setVisibility(View.INVISIBLE);
                re_tp3.setVisibility(View.INVISIBLE);
                re_tp1.setVisibility(View.VISIBLE);
                im_tp1.setVisibility(View.VISIBLE);
                ImageUtils.imageViewSetPic(im_tp1, url.get(0));

                break;
            case 2:
                im_tp1.setTag(url.get(0));
                im_tp2.setTag(url.get(1));
                re_tp3.setVisibility(View.INVISIBLE);
                re_tp2.setVisibility(View.VISIBLE);
                re_tp1.setVisibility(View.VISIBLE);
                ImageUtils.imageViewSetPic(im_tp1, url.get(0));
                ImageUtils.imageViewSetPic(im_tp2, url.get(1));
                break;
            case 3:
                im_tp1.setTag(url.get(0));
                im_tp2.setTag(url.get(1));
                im_tp3.setTag(url.get(2));
                re_tp2.setVisibility(View.VISIBLE);
                re_tp3.setVisibility(View.VISIBLE);
                re_tp1.setVisibility(View.VISIBLE);
                ImageUtils.imageViewSetPic(im_tp1, url.get(0));
                ImageUtils.imageViewSetPic(im_tp2, url.get(1));
                ImageUtils.imageViewSetPic(im_tp3, url.get(2));
                break;

            default:
                break;
        }
    }

    @Override
    public void shouload() {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "dianpu");
    }

    @Override
    public void closeload() {
        if (jdt.isAdded())
            jdt.dismiss();
    }

    @Override
    public void showPic(int pos, String filestr) {
        if (pos == 0) {
            authen_RZ.jsonAuthor.setBusiness_licence_scan(filestr);
            file0 = filestr;
            Glide.with(getApplicationContext()).load(filestr).into(ima_zztp);
        } else if (pos == 1) {
            file1 = filestr;
            authen_RZ.jsonAuthor.setFacade_photo1(filestr);
            Glide.with(getApplicationContext()).load(filestr).into(im_tp1);
        } else if (pos == 2) {
            file2 = filestr;
            authen_RZ.jsonAuthor.setFacade_photo2(filestr);
            Glide.with(getApplicationContext()).load(filestr).into(im_tp2);
        } else if (pos == 3) {
            file3 = filestr;
            authen_RZ.jsonAuthor.setFacade_photo3(filestr);
            Glide.with(getApplicationContext()).load(filestr).into(im_tp3);
        }

    }


    @Override
    public void saveDataSucc() {
        finish();
        overridePendingTransition(R.anim.push_right_out,
                R.anim.push_right_in);
    }

    @Override
    public void showToast(String msg) {
        toaste_ut(this, msg);
    }

    /**
     * save data
     */
    public void saveData() {

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
                            puPresent.uploadPic(DianPu.this,pos, retrofit_Single.getInstence().getOpenid(DianPu.this), str);
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
//        if (type == 1) {
//            if (PermissionsUtil.hasPermission(DianPu.this, Manifest.permission.CAMERA)) {
//                //有访问摄像头的权限
//                getpic(1);
//            } else {
//                PermissionsUtil.requestPermission(DianPu.this, new PermissionListener() {
//                    @Override
//                    public void permissionGranted(@NonNull String[] permissions) {
//                        getpic(1);
//                    }
//
//                    @Override
//                    public void permissionDenied(@NonNull String[] permissions) {
//                        //用户拒绝了访问摄像头的申请
//                    }
//                }, new String[]{Manifest.permission.CAMERA});
//            }
//        } else if (type == 2) {
//            if (PermissionsUtil.hasPermission(DianPu.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                //有访问摄像头的权限
//                getpic(2);
//            } else {
//                PermissionsUtil.requestPermission(DianPu.this, new PermissionListener() {
//                    @Override
//                    public void permissionGranted(@NonNull String[] permissions) {
//                        getpic(2);
//                    }
//
//                    @Override
//                    public void permissionDenied(@NonNull String[] permissions) {
//                        //用户拒绝了访问摄像头的申请
//                    }
//                }, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
//            }
//        }
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
}
