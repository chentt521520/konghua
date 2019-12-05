package com.shijiucheng.konghua.renzheng;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.EditText;
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
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.dianZhuMvp.Contact;
import com.shijiucheng.konghua.Cmvp.dianZhuMvp.DianZuPresentIml;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.Utils.StringUtils;
import com.shijiucheng.konghua.renzheng.data.dianzhufra;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Retrofit2.retrofit_Single;
import butterknife.BindView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class DianZhu extends com.shijiucheng.konghua.Cmvp.BaseActivity_konghua implements TakePhoto.TakeResultListener, InvokeListener, Contact.IDianZuView, getpicdialogfragment.paizao, dianzhufra.openorfulueGps {

    @BindView(R.id.dianzu_dh)
    DaoHang_top dh;

    @BindView(R.id.dianzhu_edxm)
    EditText ed_xm;

    @BindView(R.id.dianzhu_edpho)
    EditText ed_pho;

    @BindView(R.id.dianzhu_edqq)
    EditText ed_qq;

    @BindView(R.id.dianzhu_edsfz)
    EditText ed_sfz;


    @BindView(R.id.dianzhu_imsfz1)
    ImageView im_sfz1;
    @BindView(R.id.dianzhu_imsfz2)
    ImageView im_sfz2;

    @BindView(R.id.dianzhu_teok)
    TextView te_ok;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    Contact.IDianZuPresent present = new DianZuPresentIml(this);

    String file1 = "", file2 = "";
    int picpos = 0;//判断上传图片位置
    String PHONE_PATTERN = "^(1)\\d{10}$";
    String namestr = "";
    dianzhufra dzfra = new dianzhufra();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        AddView();
    }

    @Override
    protected void AddView() {
        x.view().inject(this);

        if (authen_RZ.jsonAuthor != null) {

            namestr = getIntent().getStringExtra("status");
            if (namestr.equals("3")) {
                ed_xm.setEnabled(false);
                ed_pho.setEnabled(false);
                ed_qq.setEnabled(false);
                ed_sfz.setEnabled(false);
                im_sfz1.setEnabled(false);
                im_sfz2.setEnabled(false);
            }

            ed_xm.setText(authen_RZ.jsonAuthor.getStore_master_uname());
            ed_pho.setText(authen_RZ.jsonAuthor.getStore_master_tel());
            ed_qq.setText(authen_RZ.jsonAuthor.getStore_master_qq());
            ed_sfz.setText(authen_RZ.jsonAuthor.getStore_master_idcard());
            file1 = authen_RZ.jsonAuthor.getStore_master_idcard_front();
            file2 = authen_RZ.jsonAuthor.getStore_master_idcard_back();
            if (!TextUtils.isEmpty(authen_RZ.jsonAuthor.getStore_master_idcard_front())) {
                Glide.with(getApplicationContext()).load(authen_RZ.jsonAuthor.getStore_master_idcard_front()).into(im_sfz1);
            }
            if (!TextUtils.isEmpty(authen_RZ.jsonAuthor.getStore_master_idcard_front())) {
                Glide.with(getApplicationContext()).load(authen_RZ.jsonAuthor.getStore_master_idcard_back()).into(im_sfz2);
            }
        }

        dh.settext_("填写店主信息");
    }

    @Override
    protected void SetViewListen() {

        im_sfz1.setOnClickListener(view -> {
            picpos = 1;
            if (fastClick()) {
                getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
                getpicdialogfragment.show(getFragmentManager(), "pzdz");
            }
        });
        im_sfz2.setOnClickListener(v -> {
            picpos = 2;
            if (fastClick()) {
                getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
                getpicdialogfragment.show(getFragmentManager(), "pzdz");

            }
        });

        te_ok.setOnClickListener(v -> {
            if (fastClick()) {
                if (namestr.equals("3")) {
                    if (!dzfra.isAdded()) {
                        dzfra.show(getSupportFragmentManager(), "dzhu");
                    }
                } else {
                    String ownerName = ed_xm.getText().toString();
                    if (TextUtils.isEmpty(ownerName)) {
                        toast("请输入店主姓名");
                        return;
                    }
                    String phone = ed_pho.getText().toString();
                    if (!isMatchered(PHONE_PATTERN, phone)) {
                        toast("请输入正确的店主手机号");
                        return;
                    }
                    String qq = ed_qq.getText().toString();
                    if (TextUtils.isEmpty(qq)) {
                        toast("请输入店主QQ");
                        return;
                    }
                    String identity = ed_sfz.getText().toString();
                    if (TextUtils.isEmpty(identity)) {
                        toast("请输入店主身份证");
                        return;
                    } else if (!StringUtils.isIdentity(identity)) {
                        toast("身份证格式错误");
                        return;
                    }

                    if (TextUtils.isEmpty(file1)) {
                        toast("请上传身份证正面照");
                        return;
                    }
                    if (TextUtils.isEmpty(file2)) {
                        toast("请上传身份证背面照");
                        return;
                    }

                    authen_RZ.jsonAuthor.setStore_master_uname(ownerName);
                    authen_RZ.jsonAuthor.setStore_master_tel(phone);
                    authen_RZ.jsonAuthor.setStore_master_qq(qq);
                    authen_RZ.jsonAuthor.setStore_master_idcard(identity);
                    present.saveData(ownerName, phone, qq, identity, file1, file2);
                }
            }
        });

    }


    @Override
    protected int getLayout() {
        return R.layout.activity_dian_zhu;
    }

    @Override
    protected BasePresenter bindPresent() {
        return present;
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

    public void getpermiss(int type) {
        getpic(type);
    }

    /**
     * 获取bitmap大小
     *
     * @param bitmap
     * @return
     */
    public int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
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
                            if (picpos == 1) {
                                present.uploadPic(DianZhu.this, "sfz1", retrofit_Single.getInstence().getOpenid(DianZhu.this), str);
                                file1 = file.toString();
                            } else if (picpos == 2) {
                                present.uploadPic(DianZhu.this, "sfz2", retrofit_Single.getInstence().getOpenid(DianZhu.this), str);
                                file2 = file.toString();
                            }
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

    @Override
    public void showload() {
        if (jdt != null)
            if (!jdt.isAdded())
                jdt.show(getFragmentManager(), "dianzu");
    }

    @Override
    public void closeload() {
        if (jdt != null)
            if (jdt.isAdded())
                jdt.dismiss();
    }

    @Override
    public void showtoast(String msg) {
        toaste_ut(this, msg);
    }

    @Override
    public void gotosave() {

        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.dprzStep1);
        EventBus.getDefault().post(databean);
        finish();
        overridePendingTransition(R.anim.push_right_out,
                R.anim.push_right_in);
    }

    @Override
    public void savePicurl(String url, String key) {
        System.out.println(url);
        if (key.equals("sfz1")) {
            Glide.with(getApplicationContext()).load(url).into(im_sfz1);
            authen_RZ.jsonAuthor.setStore_master_idcard_front(url);
        } else if (key.equals("sfz2")) {
            Glide.with(getApplicationContext()).load(url).into(im_sfz2);
            authen_RZ.jsonAuthor.setStore_master_idcard_back(url);
        }
    }

    @Override
    public void getPaiZ(int pz) {
        if (pz == 1) {
            getpermiss(1);
        } else if (pz == 2) {
            getpermiss(2);
        }
    }

    public boolean isMatchered(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    @Override
    public void opengps() {
        if (dzfra.isAdded())
            dzfra.dismiss();
        finish();
        overridePendingTransition(R.anim.push_right_out,
                R.anim.push_right_in);
    }

    @Override
    public void fuluegps() {
        if (dzfra.isAdded())
            dzfra.dismiss();
    }
}
