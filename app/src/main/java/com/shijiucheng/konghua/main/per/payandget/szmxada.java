package com.shijiucheng.konghua.main.per.payandget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class szmxada extends RecyclerView.Adapter<szmxada.viewholider> {
    Context context;
    List<szmxdata> list;


    public szmxada(Context context,
                   List<szmxdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.szmxada, null);
        viewholider viewholider = new viewholider(view);
        return viewholider;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholider holder, int position) {
        szmxdata szmxdata = list.get(position);
        holder.szmxitType.setText(szmxdata.getTypestr());
        if (szmxdata.getNum().contains("-")) {
            holder.szmxitNum.setText(szmxdata.getNum());
            if (szmxdata.getStatus().equals("处理中")) {
                Glide.with(context).load(R.mipmap.djzz).into(holder.imt);
            }else
            Glide.with(context).load(R.mipmap.zcszmx).into(holder.imt);
        } else {
            holder.szmxitNum.setText("+" + szmxdata.getNum());
            Glide.with(context).load(R.mipmap.szszmx).into(holder.imt);
        }
        holder.szmxitTxt.setText(szmxdata.getText());
        holder.szmxitTime.setText("收支时间" + szmxdata.getTime());
        holder.szmxitZt.setText(szmxdata.getStatus());

        if (szmxdata.getStatus().equals("处理完成")) {
            holder.szmxitZt.setTextColor(context.getResources().getColor(R.color.lv));
        } else if (szmxdata.getStatus().equals("处理失败")) {
            holder.szmxitZt.setTextColor(context.getResources().getColor(R.color.zhu));
        } else if (szmxdata.getStatus().equals("处理中")) {
            holder.szmxitZt.setTextColor(context.getResources().getColor(R.color.hei));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholider extends RecyclerView.ViewHolder {
        @BindView(R.id.szmxit_type)
        TextView szmxitType;
        @BindView(R.id.szmxit_num)
        TextView szmxitNum;
        @BindView(R.id.szmxit_txt)
        TextView szmxitTxt;
        @BindView(R.id.szmxit_time)
        TextView szmxitTime;
        @BindView(R.id.szmxit_zt)
        TextView szmxitZt;
        @BindView(R.id.szmxit_imt)
        ImageView imt;

        public viewholider(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
