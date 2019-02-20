package com.shijiucheng.konghua.main.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.lunbo.ADInfo;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.lunbo.CycleViewPager;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.lunbo.ViewFactory;

import java.util.ArrayList;
import java.util.List;

public class lunbo_tp {
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    CycleViewPager cycleViewPager;
    String urls = "";

    public void initialize(Context context, String[] imageUrls) {
        cycleViewPager = (CycleViewPager) ((Activity) context).getFragmentManager()
                .findFragmentById(R.id.ddxq_viewpagerlunbos);
        for (int i = 0; i < imageUrls.length; i++) {
            urls += imageUrls[i] + ",";
        }

        views.removeAll(views);
        infos.removeAll(infos);

        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(context,
                infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(context, infos.get(i)
                    .getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory
                .getImageView(context, infos.get(0).getUrl()));
        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, imageCycleViewListener);
        // 设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认4500ms
        cycleViewPager.setTime(4000);
        // 设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();

    }

    CycleViewPager.ImageCycleViewListener imageCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(ADInfo info, int postion, View imageView) {
            Intent i = new Intent(imageView.getContext(), Check_pic.class);
            i.putExtra("pos", postion - 1 + "");
            i.putExtra("urls", urls);
            imageView.getContext().startActivity(i);
            ((Activity) (imageView.getContext())).overridePendingTransition(R.anim.push_left_in,
                    R.anim.push_left_out);
        }
    };
}
