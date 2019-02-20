package com.shijiucheng.konghua.main.order;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.main.BasePagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class Check_pic extends BaseActivity_konghua {
    @BindView(R.id.check_top)
    DaoHang_top daoHang_top;
    @BindView(R.id.check_vipg)
    ViewPager viewPager;

    private String[] images;
    private ArrayList<ImageView> views;
    private PagerAdapter pagerAdapter;

    @Override
    protected void AddView() {
        daoHang_top.settext_("图片详情");
        setviewdata();
    }

    @Override
    protected void SetViewListen() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                daoHang_top.settext_((arg0 + 1) + "/" + images.length);
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

    @Override
    protected int getLayout() {
        return R.layout.activity_check_pic;
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
        for (String image : images) {
            // 循环加入图片
            ImageView imageView = new ImageView(Check_pic.this);

            Glide.with(Check_pic.this).load(image)
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            views.add(imageView);
        }
        pagerAdapter = new BasePagerAdapter(views, Check_pic.this);
        viewPager.setAdapter(pagerAdapter); // 设置适配器

        viewPager.setCurrentItem(pos);
        daoHang_top.settext_((pos + 1) + "/" + images.length);
    }
}
