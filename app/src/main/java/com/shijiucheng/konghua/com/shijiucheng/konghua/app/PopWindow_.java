package com.shijiucheng.konghua.com.shijiucheng.konghua.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.shijiucheng.konghua.R;

public class PopWindow_ {
    PopupWindow window;

    /**
     * @param context
     * @param view
     * @param w_
     * @param h_
     * @param isbottom pop的宽度高度 view布局
     *                 true是底部 false中间
     *                 view1是目标控件
     */
    public PopWindow_(final Context context, View view, View view1, int w_, int h_, boolean isbottom) {

        DisplayMetrics dm = ((Activity) context).getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        window = new PopupWindow(view, w_,
                h_);
        ColorDrawable cd = new ColorDrawable(0x000000);// 设置背景变暗
        window.setBackgroundDrawable(cd);
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);
        // 设置背景
        window.setBackgroundDrawable(((Activity) context).getResources()
                .getDrawable(R.mipmap.bsbj));
        window.setClippingEnabled(false);
        // 设置透明度
        window.getBackground().setAlpha(200);
        // 设置动画,从底部出来
        window.setAnimationStyle(android.R.style.Animation_Dialog);
        // 点击空白区域消失
        window.setOutsideTouchable(true);

        // 设置焦点
        window.setFocusable(true);
        // 可以被触摸
        window.setTouchable(true);
        // 设置软键盘
        // window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 显示的位置,从底部显示
        // 设置popwindow显示位置
        if (isbottom) {
            if (android.os.Build.VERSION.SDK_INT >= 24) {
                int[] a = new int[2];
                view1.getLocationInWindow(a);
                window.showAtLocation((((Activity) context).getWindow().getDecorView()),
                        Gravity.NO_GRAVITY, 0, height
                                - h_);
                window.update();
            } else {
                window.showAtLocation(view1, Gravity.BOTTOM, 0, 0);
                window.update();
            }
        } else {
            if (android.os.Build.VERSION.SDK_INT >= 24) {
                int[] a = new int[2];
                view1.getLocationInWindow(a);
                window.showAtLocation((((Activity) context).getWindow().getDecorView()),
                        Gravity.NO_GRAVITY, (width - w_) / 2, (height - h_) / 2);
                window.update();
            } else {
                window.showAtLocation(view1, Gravity.CENTER, 0, 0);
                window.update();
            }
        }
        window.update();
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
    }

    public PopWindow_(final Context context, View view, View view1, int w_, int h_, int py) {

        DisplayMetrics dm = ((Activity) context).getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        window = new PopupWindow(view, w_,
                h_);

        ColorDrawable cd = new ColorDrawable(0x000000);// 设置背景变暗
        window.setBackgroundDrawable(cd);
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(lp);
        // 设置背景
        window.setBackgroundDrawable(((Activity) context).getResources()
                .getDrawable(R.mipmap.bsbj));
        window.setClippingEnabled(false);
        // 设置透明度
        window.getBackground().setAlpha(200);
        // 设置动画,从底部出来
        window.setAnimationStyle(android.R.style.Animation_Dialog);
        // 点击空白区域消失
        window.setOutsideTouchable(true);

        // 设置焦点
        window.setFocusable(true);
        // 可以被触摸
        window.setTouchable(true);
        // 设置软键盘
        // window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 显示的位置,从底部显示
        // 设置popwindow显示位置

        if (android.os.Build.VERSION.SDK_INT >= 24) {
            int[] a = new int[2];
            view1.getLocationInWindow(a);
            window.showAtLocation((((Activity) context).getWindow().getDecorView()),
                    Gravity.NO_GRAVITY, (width - w_) / 2, py);
            window.update();
        } else {
            window.showAtLocation(view1, Gravity.CENTER, 0, 0);
            window.update();
        }
        window.update();
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
    }


    /**
     * window消失
     */
    public void disssmiss_window() {
        if (window != null)
            window.dismiss();
    }
}
