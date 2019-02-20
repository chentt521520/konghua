package com.shijiucheng.konghua.main.per.payandget.gongdan;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.shijiucheng.konghua.Cmvp.gondan_Mvp.GonDanMvp.Contact;
import com.shijiucheng.konghua.Cmvp.gondan_Mvp.GonDanMvp.GonDanPrestent;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg.simpleArrayAdapter;
import com.shijiucheng.konghua.renzheng.getpicdialogfragment;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class mygondan extends com.shijiucheng.konghua.Cmvp.BaseActivity_konghua implements TakePhoto.TakeResultListener, InvokeListener, Contact.GonDanIView, getpicdialogfragment.paizao {
    /**
     * created 2018/8/21 0021 16:55
     * author ldl
     */

    @BindView(R.id.tjgd_dh)
    DaoHang_top tjgdDh;
    @BindView(R.id.tjgd_spwtleixin)
    Spinner tjgdSpwtleixin;
    @BindView(R.id.tjgd_spwttezhen)
    Spinner tjgdSpwttezhen;
    @BindView(R.id.tjgd_imtp1)
    ImageView tjgdImtp1;
    @BindView(R.id.tjgd_imtp2)
    ImageView tjgdImtp2;
    @BindView(R.id.tjgd_imtp3)
    ImageView tjgdImtp3;
    @BindView(R.id.tjgd_lintp)
    LinearLayout tjgdLintp;
    @BindView(R.id.tjgd_edtxt)
    EditText tjgdEdtxt;
    @BindView(R.id.tjgd_teok)
    TextView tjgdTeok;

    int pos_tp = 0;//设置操作图片的下标
    String pos1 = "0", pos2 = "0", pos3 = "0";

    gondansctupian gondansctupian;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    Contact.GonDanIPrestent prestent = new GonDanPrestent(this);
    Retro_Intf serivce;
    List<mygondandatabean1> listQustion = new ArrayList<>();
    List<mygondandatabean> list = new ArrayList<>();

    getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
    String question1 = "0", queston2 = "0";
    int questionpos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        SetViewListen();
    }

    @Override
    protected void AddView() {


        serivce = retrofit_Single.getInstence().getserivce(2);
        tjgdDh.settext_("填写工单");
        gondansctupian = new gondansctupian();
        prestent.getQustion(retrofit_Single.getInstence().getOpenid(mygondan.this));

    }

    @Override
    protected void SetViewListen() {
        tjgdSpwttezhen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                queston2 = listQustion.get(questionpos).getList().get(position).getKey();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tjgdSpwtleixin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question1 = list.get(position).getKey();
                questionpos = position;
                List<String> data_list = new ArrayList<String>();
                for (int i = 0; i < listQustion.get(position).getList().size(); i++) {
                    data_list.add(listQustion.get(position).getList().get(i).getText());
                }
                data_list.add("请选择问题类型");
                simpleArrayAdapter arrAdapter = new simpleArrayAdapter<String>(mygondan.this, R.layout.myspinner, data_list);
                tjgdSpwttezhen.setAdapter(arrAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tjgdImtp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos_tp = 0;
                getpicdialogfragment.show(getFragmentManager(), "pzdzp");

            }
        });
        tjgdImtp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos_tp = 1;
                getpicdialogfragment.show(getFragmentManager(), "pzdzp");
            }
        });
        tjgdImtp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos_tp = 2;
                getpicdialogfragment.show(getFragmentManager(), "pzdzp");
            }
        });
        tjgdTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tjgdEdtxt.getText().toString())) {
                    toaste_ut(mygondan.this, "请填写工单内容");
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
                    prestent.applayMsg(retrofit_Single.getInstence().getOpenid(mygondan.this), question1, queston2, urls, tjgdEdtxt.getText().toString());
                }

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mygondan;
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
                            prestent.upLoadimg(retrofit_Single.getInstence().getOpenid(mygondan.this), str);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (jdt.isAdded())
                                jdt.dismiss();
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

    @Override
    protected BasePresenter bindPresent() {
        return prestent;
    }

    @Override
    public void shouwload() {
        if (!jdt.isAdded()) jdt.show(getFragmentManager(), "tjgd");
    }

    @Override
    public void closeload_() {
        if (jdt.isAdded()) jdt.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        toaste_ut(this, msg);
    }

    @Override
    public void finishApplay() {
        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.gondanlist);
        EventBus.getDefault().post(databean);
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void showQustion(List<mygondandatabean> list, List<mygondandatabean1> list1) {
        this.list.addAll(list);
        List<String> lis_ = new ArrayList<>();
        if (list.size() >= 1) {
            for (int i = 0; i < list.size(); i++) {
                lis_.add(list.get(i).getText());
            }
            lis_.add("请选择问题类型");
            simpleArrayAdapter arrAdapter = new simpleArrayAdapter<String>(this, R.layout.myspinner, lis_);
            tjgdSpwtleixin.setAdapter(arrAdapter);

            listQustion.addAll(list1);
        }
    }

    @Override
    public void showUrl(String url) {
        if (pos_tp == 0) {
            pos1 = url;
            Glide.with(mygondan.this).load(url).into(tjgdImtp1);
        } else if (pos_tp == 1) {
            pos2 = url;
            Glide.with(mygondan.this).load(url).into(tjgdImtp2);
        } else if (pos_tp == 2) {
            pos3 = url;
            Glide.with(mygondan.this).load(url).into(tjgdImtp3);
        }
    }


}
