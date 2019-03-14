package com.shijiucheng.konghua.main.HomePage.viewPagerUtils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class pagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    int[] img;
    Context context;
    imageOncl imageOncl;


    public pagerAdapter(Context context, int[] img) {
        this.img = img;
        this.context = context;
    }

    public void setImgOncl(imageOncl imgOncl) {
        this.imageOncl = imgOncl;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //因为要无限轮循播放
        final int newPosition = position % img.length;

        ImageView imageView = new ImageView(context);
        Glide.with(context).load(img[newPosition]).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageOncl != null) {
                    imageOncl.imageOnclIm(newPosition);
                }
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface imageOncl {
        void imageOnclIm(int pos);
    }
}
