package com.shijiucheng.konghua.main.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Order_detrecyc extends RecyclerView.Adapter<Order_detrecyc.viewholder> {

    Context context;
    List<order_detrecycdata> list;

    public Order_detrecyc(Context context,
                          List<order_detrecycdata> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_spxqspxx, null);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.ddxq_linhcx)
        LinearLayout lin_hc;
        @BindView(R.id.ddxq_tehcx)
        TextView te_hc;
        @BindView(R.id.ddxq_tehc1x)
        TextView te_hc1;

        @BindView(R.id.ddxq_linbzhx)
        LinearLayout lin_bzh;
        @BindView(R.id.ddxq_tebzhx)
        TextView te_bzh;
        @BindView(R.id.ddxq_tebzh1x)
        TextView te_bzh1;
        @BindView(R.id.ddxq_tenumx)
        TextView te_num;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int w_ = dm.widthPixels;
            setViewHw_Lin(lin_hc, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 100 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 5 / 750.0), (int) (w_ * 24 / 750.0), 0);
            setViewHw_Lin(lin_bzh, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 100 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
            setViewHw_Lin(te_num, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);

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
}
