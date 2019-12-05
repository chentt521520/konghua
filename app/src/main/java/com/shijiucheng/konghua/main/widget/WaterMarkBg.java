package com.shijiucheng.konghua.main.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DensityUtil;
import com.shijiucheng.konghua.main.Utils.StringUtils;


/**
 * 水印的背景图
 *
 * @author Renjy
 */
public class WaterMarkBg extends Drawable {

    private Paint paint = new Paint();

    private Context mContext;
    String text1, text2;

    public WaterMarkBg(Context context, String text1, String text2) {
        this.mContext = context;
        this.text1 = text1;
        this.text2 = text2;
    }

    @Override
    public void draw(Canvas canvas) {
        int width = getBounds().right;
        int height = getBounds().bottom;
        canvas.drawColor(mContext.getResources().getColor(R.color.transparent_color));
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(DensityUtil.sp2px(mContext, 12));
        canvas.save();

        //水印倾斜的角度
        float degrees = 45f;
        double radians = Math.toRadians(degrees);

        float w1 = StringUtils.getTextWidth(mContext, text1, 12);
        float w2 = StringUtils.getTextWidth(mContext, text2, 12);
        float textH = getFontHeight(paint);
        double sin = Math.sin(45);

        //对角线长度
        float lon = (float) (width * Math.sqrt(2));

        float pad1 = (lon - w1) / 2;
        float pad2 = (lon - w2) / 2;


        Path path1 = new Path();
        Path path2 = new Path();
        path1.moveTo(0, 0);
        path2.moveTo(0, 0);

        path1.lineTo(width, height);
        path2.lineTo(width, height);
        path1.close();
        path2.close();

        canvas.drawTextOnPath(text1, path1, pad1, -textH/2, paint);
        canvas.drawTextOnPath(text2, path2, pad2, textH/2, paint);

        canvas.restore();
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    /**
     * @return 返回指定的文字高度
     */
    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }
}
