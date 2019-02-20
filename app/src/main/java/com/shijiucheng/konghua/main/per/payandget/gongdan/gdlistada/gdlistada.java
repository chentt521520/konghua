package com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gondandetials;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class gdlistada extends RecyclerView.Adapter<gdlistada.viewholder> {
    Context context;
    List<gdlistdata> list;

    closewo1 closewo;

    public gdlistada(Context context,
                     List<gdlistdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gdlistada, null);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        final gdlistdata data = list.get(position);
        holder.gdlistitTebh.setText("工单编号:" + data.getId());
        holder.gdlistitTetime.setText(data.getTime());
        holder.gdlistitTetxt.setText(data.getContent());
        holder.gdlistitTelb.setText(data.getStatus());
        holder.gdlistitTestatus.setText(data.getTypeStr());

        if (data.getStatus1().equals("0")) {
            holder.gdlistitLincz.setVisibility(View.GONE);
            holder.gdlistitTepj.setVisibility(View.GONE);
        } else if (data.getStatus1().equals("5")) {
            holder.gdlistitLincz.setVisibility(View.VISIBLE);
            holder.gdlistitTehuifu.setVisibility(View.GONE);
            holder.gdlistitTepj.setVisibility(View.GONE);
            holder.gdlistitTestatus.setText("已受理");
        } else if (data.getStatus1().equals("10") || data.getStatus1().equals("11")) {
            holder.gdlistitLincz.setVisibility(View.VISIBLE);
            holder.gdlistitTepj.setVisibility(View.GONE);
            holder.gdlistitTestatus.setText("处理中");
        } else if (data.getStatus1().equals("15")) {
            holder.gdlistitLincz.setVisibility(View.GONE);
            holder.gdlistitTepj.setVisibility(View.VISIBLE);
            holder.gdlistitTestatus.setText("已关闭");
        } else if (data.getStatus1().equals("20")) {
            holder.gdlistitLincz.setVisibility(View.GONE);
            holder.gdlistitTepj.setVisibility(View.GONE);
            holder.gdlistitTestatus.setText("已评价");
        } else {
            holder.gdlistitLincz.setVisibility(View.VISIBLE);
            holder.gdlistitTepj.setVisibility(View.GONE);
        }

        holder.gdlistitTehuifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closewo.showHuifu(data.getId());
            }
        });
        holder.gdlistitTeguanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guanbigd guanbigd = new guanbigd();
                Bundle bundle = new Bundle();
                bundle.putString("gdid", "1");
                guanbigd.setArguments(bundle);
                guanbigd.show(((Activity) context).getFragmentManager(), "hfgd");
            }
        });
        holder.gdlistitTepj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinjiagd pinjiagd = new pinjiagd();
                Bundle bundle = new Bundle();
                bundle.putString("gdid", data.getId());
                bundle.putInt("pos", position);
                pinjiagd.setArguments(bundle);
                pinjiagd.show(((Activity) context).getFragmentManager(), "hfgd");
            }
        });
        holder.gdlistitLinddbh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data.getStatus1().equals("0")) {

                    Intent i = new Intent();
                    i.setClass(context, gondandetials.class);
                    i.putExtra("id", data.getId());
                    i.putExtra("type", data.getStatus1());
                    i.putExtra("position", position);


                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });
        holder.gdlistitTeguanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closewo.closewo1(data.getId(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.gdlistit_tebh)
        TextView gdlistitTebh;
        @BindView(R.id.gdlistit_tetime)
        TextView gdlistitTetime;
        @BindView(R.id.gdlistit_linddbh)
        LinearLayout gdlistitLinddbh;
        @BindView(R.id.gdlistit_tetxt)
        TextView gdlistitTetxt;
        @BindView(R.id.gdlistit_telb)
        TextView gdlistitTelb;
        @BindView(R.id.gdlistit_tehuifu)
        TextView gdlistitTehuifu;
        @BindView(R.id.gdlistit_teguanbi)
        TextView gdlistitTeguanbi;
        @BindView(R.id.gdlistit_lincz)
        LinearLayout gdlistitLincz;
        @BindView(R.id.gdlistit_tepj)
        TextView gdlistitTepj;
        @BindView(R.id.gdlistit_testatus)
        TextView gdlistitTestatus;


        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface closewo1 {
        void closewo1(String id, int position);

        void showHuifu(String id);
    }

    public void setclosewoI(closewo1 closewo) {
        this.closewo = closewo;
    }
}
