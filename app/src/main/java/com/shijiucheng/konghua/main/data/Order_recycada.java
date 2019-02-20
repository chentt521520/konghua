package com.shijiucheng.konghua.main.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.order.Order_detalis;
import com.shijiucheng.konghua.main.order.popwindow_.orderjiedan_popwindow;
import com.shijiucheng.konghua.main.order.popwindow_.orderjujue_popwindow;
import com.shijiucheng.konghua.main.order.popwindow_.queRenQianShou_popwindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Order_recycada extends RecyclerView.Adapter<Order_recycada.myViewHolder> {
    Context context;
    List<Order_recycdata> list;

    orderjiedan_popwindow popwindow;
    orderjujue_popwindow popjujue;
    queRenQianShou_popwindow popqianshou;


    public Order_recycada(Context context, List<Order_recycdata> list) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_recycada, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Order_recycdata data = list.get(position);
        System.out.println("ddd  " + data.getIx());
        holder.te_jd.setTextColor(context.getResources().getColor(R.color.zhu));
        holder.te_hk.setText("xx " + data.getIx());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        holder.recyclerView_zi.setLayoutManager(layoutManager);
        List<Order_recycdatazi> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Order_recycdatazi());
        }
        Order_recycadazi ada = new Order_recycadazi(context, list);
        holder.recyclerView_zi.setAdapter(ada);


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderre_linddbh)
        LinearLayout lin_bh;
        @BindView(R.id.orderre_tebh)
        TextView te_bh;
        @BindView(R.id.orderre_tezt)
        TextView te_zt;
        @BindView(R.id.orderre_linxdsj)
        LinearLayout lin_xd;
        @BindView(R.id.orderre_texdsj)
        TextView te_xd;
        @BindView(R.id.orderre_texdsj1)
        TextView te_xd1;
        @BindView(R.id.orderre_teprice)
        TextView te_price;
        @BindView(R.id.orderre_tedz)
        TextView te_dz;
        @BindView(R.id.orderre_reit)
        RecyclerView recyclerView_zi;
        @BindView(R.id.orderre_linbq)
        LinearLayout lin_bq;
        @BindView(R.id.orderre_tehk)
        TextView te_hk;
        @BindView(R.id.orderre_tebz)
        TextView te_bz;
        @BindView(R.id.orderre_lincz)
        LinearLayout lin_cz;
        @BindView(R.id.orderre_tejj)
        TextView te_jj;
        @BindView(R.id.orderre_tejd)
        TextView te_jd;


        @OnClick(R.id.orderre_tebh1)
        public void to_dddetail(View view) {
            if (fastClick()) {
                ((Activity) context).startActivity(new Intent(context, Order_detalis.class));
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        }

        @OnClick(R.id.orderre_tejd)
        public void tejd(View view) {
            if (fastClick()) {
                if (te_jd.getText().toString().equals("立即接单")) {

                    popwindow.jiedan(te_jd, te_jj, 2);
                    return;
                }
                if (te_jd.getText().toString().equals("开始配送")) {
                    te_jd.setText("确认签收");
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    return;
                }
                if (te_jd.getText().toString().equals("确认签收")) {
                    popqianshou.show_pop(te_jd);
                    te_jd.setText("提醒下单方结算");
                    return;
                }
                if (te_jd.getText().toString().equals("提醒下单方结算")) {
                    te_jj.setVisibility(View.GONE);
                    popwindow.jiedan(te_jd, te_jj, 1);
                    return;
                }
            }
        }

        @OnClick(R.id.orderre_tejj)
        public void tejj(View view) {
            if (fastClick()) {
                if (te_jj.getText().toString().equals("拒绝接单")) {
                    popjujue.jujue(lin_bh, 1);
                    return;
                }
                if (te_jj.getText().toString().equals("申请退单")) {
                    popjujue.jujue(lin_bh, 2);
                    return;
                }
            }
        }

        public myViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            popwindow = new orderjiedan_popwindow(context);
            popjujue = new orderjujue_popwindow(context);
            popqianshou = new queRenQianShou_popwindow(context);

            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int w_ = dm.widthPixels;
            setViewHw_Lin(lin_bh, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0));
            te_bh.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_ * 28 / 750.0));
            te_zt.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_ * 28 / 750.0));

            setViewHw_Lin(lin_xd, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0));
            te_xd.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_ * 28 / 750.0));
            te_xd1.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_ * 28 / 750.0));
            te_price.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_ * 28 / 750.0));

            setViewHw_Lin(te_dz, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 60 / 750.0), (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0));

            setViewHw_Lin(lin_bq, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 60 / 750.0), (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0));
            setViewHw_Lin(te_hk, (int) (w_ * 88 / 750.0), (int) (w_ * 60 / 750.0), (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), 0);
            setViewHw_Lin(te_bz, (int) (w_ * 88 / 750.0), (int) (w_ * 60 / 750.0), (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), 0);

            setViewHw_Lin(lin_cz, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), (int) (w_ * 10 / 750.0));
            setViewHw_Lin(te_jj, (int) (w_ * 180 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), (int) (w_ * 5 / 750.0));
            setViewHw_Lin(te_jd, (int) (w_ * 180 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), 0);
        }
    }


    protected void setViewHw_Lin(View v, int width, int height, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    protected void setViewHw_Re(View v, int width, int height, int left, int top, int right, int bottom) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    long lastClick = 0;

    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

}
