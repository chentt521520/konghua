package com.shijiucheng.konghua.main.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import java.util.List;

public class Order_recycadazi extends RecyclerView.Adapter<Order_recycadazi.myViewHolder> {
    Context context;
    List<Order_recycdatazi> list;

    public Order_recycadazi(Context context, List<Order_recycdatazi> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_recycadazi, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lin_;
        ImageView ima_tp;
        TextView te_ms;
        TextView te_num;

        public myViewHolder(View itemView) {
            super(itemView);
            lin_ = itemView.findViewById(R.id.myorderitem_lincon);
            ima_tp = itemView.findViewById(R.id.myorderitem_imtp);
            te_ms = itemView.findViewById(R.id.myorderitem_tetit);
            te_num = itemView.findViewById(R.id.myorderitem_teprice);

            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int w_ = dm.widthPixels;

            setViewHw_Lin(lin_, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 200 / 750.0), (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), (int) (w_ * 5 / 750.0));
            setViewHw_Lin(ima_tp, (int) (w_ * 300 / 750.0), (int) (w_ * 200 / 750.0), 0, 0, (int) (w_ * 14 / 750.0), 0);

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
