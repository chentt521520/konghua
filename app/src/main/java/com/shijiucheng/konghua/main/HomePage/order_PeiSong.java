package com.shijiucheng.konghua.main.HomePage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.View;
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
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.order.popwindow_.peisonadadata;
import com.shijiucheng.konghua.main.order.popwindow_.peisongada;
import com.shijiucheng.konghua.renzheng.getpicdialogfragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 开始配送页面
 * created 2018/7/27 0027 10:22
 * author ldl
 */
public class order_PeiSong extends BaseActivity_konghua implements TakePhoto.TakeResultListener, InvokeListener, peisongada.selectionPos, getpicdialogfragment.paizao {
    @BindView(R.id.ksps_daoh)
    DaoHang_top daoHang_top;
    @BindView(R.id.ksps_recycpsy)
    RecyclerView recyclerView;
    @BindView(R.id.ksps_edxm)
    EditText ed_xm;
    @BindView(R.id.ksps_edpho)
    EditText ed_pho;

    @BindView(R.id.ksps_tp1)
    ImageView im_tp1;
    @BindView(R.id.ksps_tp2)
    ImageView im_tp2;
    @BindView(R.id.ksps_tp3)
    ImageView im_tp3;

    @BindView(R.id.ksps_teik)
    TextView te_ok;

    String[] dataurl = {"", "", ""};
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();

    @BindView(R.id.ksps_tebaozats)
    TextView tets;

    int ix = 1;

    peisongada ada;
    List<peisonadadata> list = new ArrayList<>();
    String order_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void AddView() {
        daoHang_top.settext_("开始配送");
        order_id = getIntent().getStringExtra("id");
        getpeison();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        ada = new peisongada(list, this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(ada);
        ada.setselectx(this);

        SpannableStringBuilder builder = new SpannableStringBuilder(tets.getText().toString());
        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(getResources().getColor(R.color.danhei));
        builder.setSpan(yellowSpan, 69, tets.getText().toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tets.setText(builder);
    }

    @Override
    protected void SetViewListen() {
        im_tp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    ix = 1;
                    getpicdialogfragment.show(getFragmentManager(), "pzdzp");
//                    startToPhoto();
                }
            }
        });
        im_tp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    ix = 2;
                    getpicdialogfragment.show(getFragmentManager(), "pzdzp");
//                    startToPhoto();
                }
            }
        });
        im_tp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    ix = 3;
                    getpicdialogfragment.show(getFragmentManager(), "pzdzp");
                }
            }
        });


        te_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    paramsDataBean databean = new paramsDataBean();

                    Bundle bundle = new Bundle();

                    if (TextUtils.isEmpty(dataurl[0]) & TextUtils.isEmpty(dataurl[1]) & TextUtils.isEmpty(dataurl[2])) {
                        toaste_ut(order_PeiSong.this, "请至少上传1张配送图片");
                        return;
                    } else {
                        String tp = "";
                        if (!TextUtils.isEmpty(dataurl[0]))
                            tp += dataurl[0] + ",";
                        if (!TextUtils.isEmpty(dataurl[1]))
                            tp += dataurl[1] + ",";
                        if (!TextUtils.isEmpty(dataurl[2]))
                            tp += dataurl[2] + ",";
                        tp = tp.substring(0, tp.length() - 1);
                        bundle.putString("store_pack_images", tp);

                        if (TextUtils.isEmpty(ed_xm.getText().toString()) && TextUtils.isEmpty(ed_pho.getText().toString())) {
                            bundle.putString("delivery_emp_uname", "");
                            bundle.putString("delivery_emp_tel", "");
                        } else if ((!TextUtils.isEmpty(ed_xm.getText().toString())) && (!TextUtils.isEmpty(ed_pho.getText().toString()))) {
                            bundle.putString("delivery_emp_uname", ed_xm.getText().toString());
                            bundle.putString("delivery_emp_tel", ed_pho.getText().toString());
                        } else {
                            toaste_ut(order_PeiSong.this, "配送员姓名及手机请填写完整");
                            return;
                        }


                        databean.setT(bundle);
                        databean.setMsg(configParams.orderpeisong);
                        EventBus.getDefault().post(databean);
                        finish();
                        overridePendingTransition(R.anim.push_right_out,
                                R.anim.push_right_in);
                    }
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order__pei_song;
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
            jdt.show(getFragmentManager(), "jdtxor");
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
                            if (xx == 1) {
                                Glide.with(order_PeiSong.this)
                                        .load(jso.getString("url"))
                                        .into(im_tp1);
                                dataurl[0] = jso.getString("url");
                            } else if (xx == 2) {
                                Glide.with(order_PeiSong.this)
                                        .load(jso.getString("url"))
                                        .into(im_tp2);
                                dataurl[1] = jso.getString("url");
                            } else if (xx == 3) {
                                Glide.with(order_PeiSong.this)
                                        .load(jso.getString("url"))
                                        .into(im_tp3);
                                dataurl[2] = jso.getString("url");
                            }


                        } else {

                            toaste_ut(order_PeiSong.this, jsonObject.getString("msg"));
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

    private void getpeison() {
        jdt.show(getFragmentManager(), "jdt");
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("order_id", order_id);
        Call<ResponseBody> call = serivce.peisonglxr(retrofit_Single.getInstence().getOpenid(this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            jdt.dismiss();

                            JSONArray jsa = jsonObject.getJSONObject("data").getJSONArray("delivery_emp_list");
                            for (int i = 0; i < jsa.length(); i++) {
                                JSONObject jso = jsa.getJSONObject(i);
                                list.add(new peisonadadata(jso.getString("emp_uname"), jso.getString("emp_tel"), "0"));
                            }
                            ada.notifyDataSetChanged();

                        } else {
                            jdt.dismiss();
                            toaste_ut(order_PeiSong.this, jsonObject.getString("msg"));
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
    public void onItemClick(int postion) {
        ed_xm.setText(list.get(postion).getStr());
        ed_pho.setText(list.get(postion).getPho());

        for (int i = 0; i < list.size(); i++) {
            if (i == postion)
                list.get(i).setIsselect("1");
            else
                list.get(i).setIsselect("0");
        }
        ada.notifyDataSetChanged();

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
}
