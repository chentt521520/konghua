package com.shijiucheng.konghua.main.per.payandget.per.tixian;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
        holder.txlsitTetime.setText("提现时间 " + data.getTime());

        if (list.size() - 1 == position) {
            holder.v_hen.setVisibility(View.GONE);
        } else
            holder.v_hen.setVisibility(View.VISIBLE);
        if (data.getNum().contains("-"))
            holder.txlsitTenum.setText(data.getNum());
        else
            holder.txlsitTenum.setText("+" + data.getNum());
        holder.txlsitZt.setText(data.getStatusstr());
        holder.te_status.setText(data.getStatusstr());

        System.out.println(data.getStatuscode());
        if (data.getStatuscode().equals("2")) {
            holder.te_status.setBackgroundResource(R.mipmap.txsb);
        } else if (data.getStatuscode().equals("0")) {
            holder.te_status.setBackgroundResource(R.mipmap.txshz);
        } else
            holder.te_status.setBackgroundResource(R.mipmap.txcg);

        if (TextUtils.isEmpty(data.getYuanying())) {
            holder.textyuanying.setVisibility(View.GONE);
        } else {
            holder.textyuanying.setVisibility(View.VISIBLE);
            holder.textyuanying.setText("  原因：" + data.getYuanying());
        }

        getpic(holder.ima, data.getBankstr());

    }

    private void getpic(ImageView tjyhkitImtj, String img) {
        if (img.equals("boc"))
            Glide.with(context).load(R.mipmap.boc).into(tjyhkitImtj);
        else if (img.equals("cmb"))
            Glide.with(context).load(R.mipmap.cmb).into(tjyhkitImtj);
        else if (img.equals("cmb"))
            Glide.with(context).load(R.mipmap.cmb).into(tjyhkitImtj);
        else if (img.equals("cib"))
            Glide.with(context).load(R.mipmap.cib).into(tjyhkitImtj);
        else if (img.equals("abc"))
            Glide.with(context).load(R.mipmap.abc).into(tjyhkitImtj);
        else if (img.equals("cmbc"))
            Glide.with(context).load(R.mipmap.cmbc).into(tjyhkitImtj);
        else if (img.equals("bcm"))
            Glide.with(context).load(R.mipmap.bcm).into(tjyhkitImtj);
        else if (img.equals("ceb"))
            Glide.with(context).load(R.mipmap.ceb).into(tjyhkitImtj);
        else if (img.equals("ccb"))
            Glide.with(context).load(R.mipmap.ccb).into(tjyhkitImtj);
        else if (img.equals("icbc"))
            Glide.with(context).load(R.mipmap.icbc).into(tjyhkitImtj);
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
        @BindView(R.id.txlsit_tekahtype)
        TextView te_nankname;//银行名字
        @BindView(R.id.txlsit_tezt)
        TextView te_status;//提现状态

        @BindView(R.id.txlsit_teyuanying)
        TextView textyuanying;
        @BindView(R.id.txls_view)
        View v_hen;

        @BindView(R.id.txls_im)
        ImageView ima;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
