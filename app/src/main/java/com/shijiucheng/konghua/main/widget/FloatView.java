package com.shijiucheng.konghua.main.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DensityUtil;


public class FloatView {
    private Context mContext;
    private static boolean isShow = false;//悬浮框是否已经显示

    //定义浮动窗口布局  
    private View mView;
    private WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象  
    private WindowManager mWindowManager;
    private TextView mFloatView;
    private int width;
    private int height;

    private FloatViewClickListener mListener;//view的点击回调listener

    public void setOnClickListener(FloatViewClickListener listener) {
        mListener = listener;
    }

    public FloatView(Context context) {
        mContext = context;
        //获取浮动窗口视图所在布局  
        mView = LayoutInflater.from(context).inflate(R.layout.float_view, null);
    }

    /**
     * 显示悬浮框
     */
    public void showFloatView(final Context context) {
        if (isShow) {
            return;
        }

        wmParams = new WindowManager.LayoutParams();
//通过getApplication获取的是WindowManagerImpl.CompatModeWrapper  
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = mWindowManager.getDefaultDisplay().getWidth();

//        int tabHeight = DensityUtil.dip2px(context, 60);
        height = mWindowManager.getDefaultDisplay().getHeight();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_DITHER;

//        设置window type  
//        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        //设置图片格式，效果为背景透明  
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）  
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶  
        wmParams.gravity = Gravity.START | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity  
        wmParams.x = width;
        wmParams.y = height / 2;

        //设置悬浮窗口长宽数据    
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
        //添加mFloatLayout  
        mWindowManager.addView(mView, wmParams);
        //浮动窗口按钮  
        mFloatView = mView.findViewById(R.id.order_count);

        mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));


        mView.setOnTouchListener(new View.OnTouchListener() {
            int lastx = 0;
            int lasty = 0;
            int movex = 0;
            int movey = 0;
            boolean isMove;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastx = (int) event.getRawX();
                        lasty = (int) event.getRawY();
                        isMove = false;
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        int curx = (int) event.getRawX();
                        int cury = (int) event.getRawY();
                        int x;
                        int y;
                        x = Math.abs(curx - lastx);
                        y = Math.abs(cury - lasty);
                        if (x < 5 || y < 5) {
                            isMove = false;
                            return false;
                        } else {
                            isMove = true;
                        }

                        // getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                        wmParams.x = curx - mFloatView.getMeasuredWidth() / 2;
                        // 减25为状态栏的高度
                        wmParams.y = cury - mFloatView.getMeasuredHeight() / 2;
                        // 刷新
                        mWindowManager.updateViewLayout(mView, wmParams);
                        return true;
                    case MotionEvent.ACTION_UP:
                        int finalX = (int) event.getRawX();
                        int finalY = (int) event.getRawY();
                        boolean isok = false;

                        if (finalY < mFloatView.getMeasuredHeight()) {
                            movey = 0;
                            movex = finalX - mFloatView.getMeasuredWidth() / 2;
                        }

                        if (finalY > height - mFloatView.getMeasuredHeight()) {
                            movey = height - mFloatView.getMeasuredHeight();
                            movex = finalX - mFloatView.getMeasuredWidth() / 2;
                        }

                        if (finalY > mFloatView.getMeasuredHeight() && finalY < height - mFloatView.getMeasuredHeight()) {
                            isok = true;
                        }
                        if (isok && finalX - mFloatView.getMeasuredWidth() / 2 < width / 2) {
                            movex = 0;
                            movey = finalY - mFloatView.getMeasuredHeight() / 2;
                        } else if (isok && finalX - mFloatView.getMeasuredWidth() / 2 > width / 2) {
                            movex = width - mFloatView.getMeasuredWidth();
                            movey = finalY - mFloatView.getMeasuredHeight() / 2;
                        }

                        wmParams.x = movex;
                        wmParams.y = movey - 45;
                        if (isMove) {
                            mWindowManager.updateViewLayout(mView, wmParams);
                        } else {
                            mListener.onFloatViewClickListener(mView);
                        }
                        return isMove;//false 为点击 true 为移动
                    default:
                        break;
                }
                return true;
            }

        });
        isShow = true;
    }

    public void setmFloatViewText(String text) {
        if (mFloatView != null)
            mFloatView.setText(text);
    }

    /**
     * 隐藏悬浮窗
     */
    public void hideFloatView() {
        if (mWindowManager != null && isShow) {
            mWindowManager.removeView(mView);
            isShow = false;
        }
    }
}
