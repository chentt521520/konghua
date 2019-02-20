package com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.per.payandget.gongdan.check_pic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class gdxqada extends RecyclerView.Adapter<gdxqada.viewholder> {
    Context context;
    List<gdxqdata> list;


    public gdxqada(Context context,
                   List<gdxqdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gdxqada, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        final gdxqdata data = list.get(position);
        if (data.getReply_type().equals("1")) {
            holder.gdxqitName.setTextColor(Color.parseColor("#da0000"));
            holder.gdxqitName.setText("管理员");
        } else {
            holder.gdxqitName.setTextColor(Color.parseColor("#f77b06"));
            holder.gdxqitName.setText("我");
        }
        holder.gdxqitTetime.setText(data.getTime());
        holder.gdxqitTetxt.setText(data.getContent());

        holder.gdxqitLintp.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(data.getImgs())) {
            if (data.getImgs().contains(",")) {
                holder.gdxqitImtp1.setVisibility(View.VISIBLE);
                holder.gdxqitImtp2.setVisibility(View.VISIBLE);
                holder.gdxqitImtp3.setVisibility(View.VISIBLE);
                String[] url = data.getImgs().split(",");
                if (url.length == 2) {
                    Glide.with(context).load(url[0]).into(holder.gdxqitImtp1);
                    Glide.with(context).load(url[1]).into(holder.gdxqitImtp2);
                    holder.gdxqitImtp3.setVisibility(View.INVISIBLE);
                } else {
                    Glide.with(context).load(url[0]).into(holder.gdxqitImtp1);
                    Glide.with(context).load(url[1]).into(holder.gdxqitImtp2);
                    Glide.with(context).load(url[2]).into(holder.gdxqitImtp3);
                }

            } else {
                Glide.with(context).load(data.getImgs()).into(holder.gdxqitImtp1);
                holder.gdxqitImtp2.setVisibility(View.INVISIBLE);
                holder.gdxqitImtp3.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.gdxqitLintp.setVisibility(View.GONE);
        }

        holder.gdxqitImtp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(context, check_pic.class);
                i.putExtra("pos", 0 + "");
                i.putExtra("urls", data.getImgs());
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        holder.gdxqitImtp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(context, check_pic.class);
                i.putExtra("pos", 1 + "");
                i.putExtra("urls", data.getImgs());
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        holder.gdxqitImtp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(context, check_pic.class);
                i.putExtra("pos", 2 + "");
                i.putExtra("urls", data.getImgs());
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.gdxqit_name)
        TextView gdxqitName;
        @BindView(R.id.gdxqit_tetime)
        TextView gdxqitTetime;
        @BindView(R.id.gdxqit_tetxt)
        TextView gdxqitTetxt;
        @BindView(R.id.gdxqit_imtp1)
        ImageView gdxqitImtp1;
        @BindView(R.id.gdxqit_imtp2)
        ImageView gdxqitImtp2;
        @BindView(R.id.gdxqit_imtp3)
        ImageView gdxqitImtp3;
        @BindView(R.id.gdxqit_lintp)
        LinearLayout gdxqitLintp;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
