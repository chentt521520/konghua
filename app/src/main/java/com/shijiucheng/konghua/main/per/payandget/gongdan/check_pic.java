package com.shijiucheng.konghua.main.per.payandget.gongdan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.main.BasePagerAdapter;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class check_pic extends BaseActivity_konghua {

    @BindView(R.id.check_imreturn)
    ImageView im_return;
    @BindView(R.id.check_tetitl)
    TextView te_tile;
    @BindView(R.id.check_vipg)
    ViewPager vpg;

    @BindView(R.id.check_imdowload)
    ImageView im_dowload;
    @BindView(R.id.check_tedowload)
    TextView checkTedowload;
    private String[] images;
    private ArrayList<ImageView> views;
    private PagerAdapter pagerAdapter;

    Bitmap bt = null;
    int pos = 0;


    @Override
    protected void AddView() {
        setviewdata();
        setviewlisten();
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_check_pic2;
    }

    @SuppressWarnings("deprecation")
    private void setviewlisten() {
        im_return.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);

            }
        });
        vpg.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                pos = arg0;
                te_tile.setText((arg0 + 1) + "/" + images.length);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        im_dowload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick())
                    Glide.with(check_pic.this)
                            .load(images[pos])
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                    BitmapDrawable bd = (BitmapDrawable) resource;
                                    bt = bd.getBitmap();
                                    getpermission(bt);
                                }
                            });
            }
        });
        checkTedowload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick())
                    Glide.with(check_pic.this)
                            .load(images[pos])
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                    BitmapDrawable bd = (BitmapDrawable) resource;
                                    bt = bd.getBitmap();
                                    getpermission(bt);
                                }
                            });
            }
        });

    }

    private void setviewdata() {
        Intent i_ = getIntent();
        pos = Integer.valueOf(i_.getStringExtra("pos")).intValue();

        String[] urls = i_.getStringExtra("urls").split(",");
        images = new String[urls.length];
        for (int i = 0; i < urls.length; i++) {
            if (urls[i].contains(",")) {
                images[i] = urls[i].replace("", "");
            } else {
                images[i] = urls[i];
            }
        }
        views = new ArrayList<ImageView>();
        for (int i = 0; i < images.length; i++) {
            // 循环加入图片
            ImageView imageView = new ImageView(check_pic.this);

            Glide.with(check_pic.this).load(images[i])
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            views.add(imageView);
        }
        pagerAdapter = new BasePagerAdapter(views, check_pic.this);
        vpg.setAdapter(pagerAdapter); // 设置适配器

        vpg.setCurrentItem(pos);
        te_tile.setText((pos + 1) + "/" + images.length);

    }


    private void setviewhw_re(View v, int width, int height, int left, int top,
                              int right, int bottom) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,
                height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);

            return false;
        }
        return false;
    }

    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void getpermission(Bitmap bt) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        PERMISSIONS_STORAGE,
                        1);
            } else {
                saveImageToGallery(check_pic.this, bt);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImageToGallery(check_pic.this, bt);
            } else {
                Toast.makeText(check_pic.this, "请设置app允许读写权限，然后重试", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {

        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "cp");
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "花脉保存的图片");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        System.out.println(fileName);
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(check_pic.this, "保存成功", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (jdt.isAdded())
                    jdt.dismiss();
            }
        }, 1000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            if (Build.VERSION.SDK_INT > 23) { // 判断SDK版本是不是4.4或者高于4.4
                String[] paths = new String[]{file.getAbsolutePath()};
                MediaScannerConnection.scanFile(context, paths, null, null);
            } else {
                final Intent intent;
                if (file.isDirectory()) {
                    intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
                    intent.setClassName("com.android.providers.media", "com.android.providers.media.MediaScannerReceiver");
                    intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
                } else {
                    intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(file));
                }
                context.sendBroadcast(intent);
            }
        }
    }

}
