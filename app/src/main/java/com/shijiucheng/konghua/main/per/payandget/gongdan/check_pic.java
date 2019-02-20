package com.shijiucheng.konghua.main.per.payandget.gongdan;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.main.BasePagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class check_pic extends BaseActivity_konghua {

    @BindView(R.id.check_imreturn)
    ImageView im_return;
    @BindView(R.id.check_tetitl)
    TextView te_tile;
    @BindView(R.id.check_vipg)
    ViewPager vpg;

    private String[] images;
    private ArrayList<ImageView> views;
    private PagerAdapter pagerAdapter;


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
    }

    private void setviewdata() {
        Intent i_ = getIntent();
        int pos = Integer.valueOf(i_.getStringExtra("pos")).intValue();

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

}
