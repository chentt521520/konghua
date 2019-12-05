package com.shijiucheng.konghua.main.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DensityUtil;

public class TextDrawable extends android.support.v7.widget.AppCompatTextView {
    private Drawable drawableLeft;
    private Drawable drawableRight;
    private Drawable drawableTop;
    private int leftWidth;
    private int rightWidth;
    private int topWidth;
    private int leftHeight;
    private int rightHeight;
    private int topHeight;
    private Context mContext;

    public TextDrawable(Context context) {
        super(context);
        this.mContext = context;
        init(context, null);
    }

    public TextDrawable(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context, attrs);
    }

    public TextDrawable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextDrawable);
        drawableLeft = typedArray.getDrawable(R.styleable.TextDrawable_leftDrawable);
        drawableRight = typedArray.getDrawable(R.styleable.TextDrawable_rightDrawable);
        drawableTop = typedArray.getDrawable(R.styleable.TextDrawable_topDrawable);
        if (drawableLeft != null) {
            leftWidth = typedArray.getDimensionPixelOffset(R.styleable.TextDrawable_leftDrawableWidth, DensityUtil.dip2px(context, 20));
            leftHeight = typedArray.getDimensionPixelOffset(R.styleable.TextDrawable_leftDrawableHeight, DensityUtil.dip2px(context, 20));
        }
        if (drawableRight != null) {
            rightWidth = typedArray.getDimensionPixelOffset(R.styleable.TextDrawable_rightDrawableWidth, DensityUtil.dip2px(context, 20));
            rightHeight = typedArray.getDimensionPixelOffset(R.styleable.TextDrawable_rightDrawableHeight, DensityUtil.dip2px(context, 20));
        }
        if (drawableTop != null) {
            topWidth = typedArray.getDimensionPixelOffset(R.styleable.TextDrawable_topDrawableWidth, DensityUtil.dip2px(context, 20));
            topHeight = typedArray.getDimensionPixelOffset(R.styleable.TextDrawable_topDrawableHeight, DensityUtil.dip2px(context, 20));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, leftWidth, leftHeight);
        }
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, rightWidth, rightHeight);
        }
        if (drawableTop != null) {
            drawableTop.setBounds(0, 0, topWidth, topHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, null);

    }

    /**
     * 设置左侧图片并重绘
     */
    public void setDrawableLeft(Drawable drawableLeft) {
        this.drawableLeft = drawableLeft;
        invalidate();
    }

    /**
     * 设置左侧图片并重绘
     */
    public void setDrawableLeft(int drawableLeftRes) {
        this.drawableLeft = mContext.getResources().getDrawable(drawableLeftRes);
        invalidate();
    }

    /**
     * 设置右侧图片并重绘
     */
    public void setDrawableRight(Drawable drawableRight) {
        this.drawableRight = drawableLeft;
        invalidate();
    }

    /**
     * 设置右侧图片并重绘
     */
    public void setDrawableRight(int drawableRightRes) {
        this.drawableRight = mContext.getResources().getDrawable(drawableRightRes);
        invalidate();
    }

    /**
     * 设置上部图片并重绘
     */
    public void setDrawable(Drawable drawableTop) {
        this.drawableTop = drawableTop;
        invalidate();
    }

    /**
     * 设置右侧图片并重绘
     */
    public void setDrawableTop(int drawableTopRes) {
        this.drawableTop = mContext.getResources().getDrawable(drawableTopRes);
        invalidate();
    }
}
