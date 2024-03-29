package com.shijiucheng.konghua.main.HomePage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class orderdetailsadaptercaozuolis extends RecyclerView.Adapter<orderdetailsadaptercaozuolis.viewholder> {
    Context context;
    List<ord_detadataczls> list;


    public orderdetailsadaptercaozuolis(Context context,
                                        List<ord_detadataczls> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orderdetailsadaptercaozuolis, parent, false);
        viewholder viewholder = new viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        ord_detadataczls data = list.get(position);

        String txt = (data.getText());
        txt = txt.replace("&nbsp", "");
        txt = txt.replace("<br>", "");
        txt = txt.replace("</span>", "");
        txt = txt.replace("<span>", "");

        holder.orddetitCzls.setText(txt);
        if (position == 0) {
            holder.ima_.setVisibility(View.VISIBLE);
            holder.ima_1.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(R.drawable.yuanzhufu).into(holder.ima_);
            holder.ima_1.setVisibility(View.VISIBLE);
            holder.ima_.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.orddetit_czls)
        TextView orddetitCzls;
        @BindView(R.id.orddetit_im)
        ImageView ima_;
        @BindView(R.id.orddetit_im1)
        ImageView ima_1;

        @BindView(R.id.orddetit_view)
        View view;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    protected void setViewHw_Lin(View v, int width, int height, int left,
                                 int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

}
