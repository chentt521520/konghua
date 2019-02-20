package com.shijiucheng.konghua.main.per.payandget.per.tixian;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class txianlsada extends RecyclerView.Adapter<txianlsada.viewholder> {
    Context context;
    List<txianlsadadatat> list;


    public txianlsada(Context context,
                      List<txianlsadadatat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.txianlsada, null);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        txianlsadadatat data = list.get(position);
        holder.txlsitTetime.setText(data.getTime());
        if (data.getNum().contains("-"))
            holder.txlsitTenum.setText(data.getNum());
        else
            holder.txlsitTenum.setText("+" + data.getNum());
        holder.txlsitZt.setText(data.getStatusstr());

        if (TextUtils.isEmpty(data.getYuanying())) {
            holder.textyuanying.setVisibility(View.GONE);
        } else {
            holder.textyuanying.setVisibility(View.VISIBLE);
            holder.textyuanying.setText(data.getYuanying());
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.txlsit_tetime)
        TextView txlsitTetime;
        @BindView(R.id.txlsit_tenum)
        TextView txlsitTenum;
        @BindView(R.id.txlsit_tekah)
        TextView txlsitTekah;
        @BindView(R.id.txlsit_zt)
        TextView txlsitZt;

        @BindView(R.id.txlsit_teyuanying)
        TextView textyuanying;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
