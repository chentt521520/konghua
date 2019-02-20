package com.shijiucheng.konghua.main.order;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.order.popwindow_.caozuo_adadata;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class caozuo_ada extends RecyclerView.Adapter<caozuo_ada.vh> {
    List<caozuo_adadata> list;
    Context context;

    public caozuo_ada(List<caozuo_adadata> list,
                      Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.caozuo_ada, parent, false);
        vh vh = new vh(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull vh holder, int position) {
        if (position == 0)
            holder.te1.setTextColor(Color.parseColor("#666666"));
        else
            holder.te1.setTextColor(Color.parseColor("#999999"));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class vh extends RecyclerView.ViewHolder {
        @BindView(R.id.caozuoit_im)
        ImageView ima;
        @BindView(R.id.caozuoit_lin)
        LinearLayout lin;
        @BindView(R.id.caozuoit_te1)
        TextView te1;
        @BindView(R.id.caozuoit_te2)
        TextView te2;

        public vh(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int w_ = dm.widthPixels;
            setViewHw_Lin(ima, (int) (w_ * 20 / 750.0), LinearLayout.LayoutParams.WRAP_CONTENT, (int) (w_ * 14 / 750.0), 0, (int) (w_ * 14 / 750.0), 0);
            setViewHw_Lin(lin, LinearLayout.LayoutParams.MATCH_PARENT, (int) (w_ * 140 / 750.0), 0, (int) (w_ * 14 / 750.0), (int) (w_ * 14 / 750.0), 0);
            setViewHw_Lin(te1, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0, 0, 0, (int) (w_ * 10 / 750.0));
            setViewHw_Lin(te2, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, (int) (w_ * 14 / 750.0), 0, 0, (int) (w_ * 10 / 750.0));
        }
    }

    protected void setViewHw_Lin(View v, int width, int height, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }
}
