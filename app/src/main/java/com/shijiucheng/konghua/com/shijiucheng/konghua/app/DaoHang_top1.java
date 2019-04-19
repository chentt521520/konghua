package com.shijiucheng.konghua.com.shijiucheng.konghua.app;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

public class DaoHang_top1 extends RelativeLayout {
    protected int w_, h_;
    RelativeLayout lin_top;
    public ImageView ima_fh, ima_tp;
    public TextView te_tit, teadd;

    public DaoHang_top1(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.daohang_top, this);
        lin_top = view.findViewById(R.id.dh_lintop);
        ima_fh = view.findViewById(R.id.dh_imreturn);
        ima_tp = view.findViewById(R.id.dh_im);
        te_tit = view.findViewById(R.id.dh_tetit);
        teadd = view.findViewById(R.id.dh_teadd);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        w_ = dm.widthPixels;
        setViewHw_Lin(lin_top, w_, (int) (w_ * 88 / 750.0), 0, 0, 0, 0);
        setViewHw_Re(ima_fh, (int) (w_ * 44 / 750.0), (int) (w_ * 44 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 22 / 750.0), 0, (int) (w_ * 22 / 750.0));
//        setViewHw_Re(ima_tp, (int) (w_ * 36 / 750.0), (int) (w_ * 22 / 750.0), (int) (w_ * 684 / 750.0), (int) (w_ * 10 / 750.0), (int) (w_ * 22 / 750.0), (int) (w_ * 22 / 750.0));
        te_tit.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_ * 36 / 750.0));

        te_tit.setText("ccccccc");
        ima_fh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });
    }

    protected void setViewHw_Re(View v, int width, int height, int left,
                                int top, int right, int bottom) {
        LayoutParams lp = new LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    protected void setViewHw_Lin(View v, int width, int height, int left,
                                 int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    public void settext_(String str) {
        te_tit.setText(str);
    }

    public void setvis() {
        ima_tp.setVisibility(GONE);
        teadd.setVisibility(GONE);
    }
}
