package com.shijiucheng.konghua.main.order;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class orderShaix_ extends LinearLayout {
    @BindView(R.id.shax_linddbh)
    LinearLayout lin_bh;
    @BindView(R.id.shax_teddbh)
    TextView te_bh;
    @BindView(R.id.shax_edbh)
    public EditText ed_bh;

    @BindView(R.id.shax_linshrxm)
    LinearLayout lin_xm;
    @BindView(R.id.shax_teshrxm)
    TextView te_xm;
    @BindView(R.id.shax_edshrxm)
    public EditText ed_xm;

    @BindView(R.id.shax_linshrpho)
    LinearLayout lin_pho;
    @BindView(R.id.shax_teshrpho)
    TextView te_pho;
    @BindView(R.id.shax_edshrpho)
    public EditText ed_pho;

    @BindView(R.id.shax_linxzrq)
    LinearLayout lin_rq;
    @BindView(R.id.shax_texzrq)
    public TextView te_rqqi;
    @BindView(R.id.shax_texzrqks)
    public TextView te_rqqian;
    @BindView(R.id.shax_texzrqh)
    public TextView te_rqhou;

    @BindView(R.id.shax_linbot)
    LinearLayout lin_bot;
    @BindView(R.id.shax_teok)
    public TextView te_ok;
    @BindView(R.id.shax_teqc)
    public TextView te_qc;

    @BindView(R.id.shax_vkb)
    public View v_kb;

    Unbinder munbinder;

    int w_ = 0;

    public orderShaix_(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.shaixuan, this);
        munbinder = ButterKnife.bind(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        w_ = dm.widthPixels;
        setviewhw();
    }


    private void setviewhw() {
        setViewHw_Lin(lin_bh, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 20 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_bh, (int) (w_ * 28 / 750.0));
        setTextSize(ed_bh, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(lin_xm, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 20 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_xm, (int) (w_ * 28 / 750.0));
        setTextSize(ed_xm, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(lin_pho, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 20 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_pho, (int) (w_ * 28 / 750.0));
        setTextSize(ed_pho, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(lin_rq, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 20 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_rqqian, (int) (w_ * 28 / 750.0));
        setTextSize(te_rqqi, (int) (w_ * 28 / 750.0));
        setTextSize(te_rqhou, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(lin_bot, w_, (int) (w_ * 90 / 750.0), 0, (int) (w_ * 20 / 750.0), 0, (int) (w_ * 24 / 750.0));
        setTextSize(te_ok, (int) (w_ * 45 / 750.0));
        setTextSize(te_qc, (int) (w_ * 45 / 750.0));
    }

    protected void setViewHw_Lin(View v, int width, int height, int left,
                                 int top, int right, int bottom) {
        LayoutParams lp = new LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    long lastClick = 0;

    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 2000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    protected void setTextSize(TextView textView, int size) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }
}
