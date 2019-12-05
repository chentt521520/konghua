package com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class tjyhkada extends RecyclerView.Adapter<tjyhkada.viewholder> {
    Context context;
    List<tjyhkadadata> list;

    getBank getBank;

    public void setbank(getBank getBank) {
        this.getBank = getBank;
    }

    public tjyhkada(Context context,
                    List<tjyhkadadata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tjyhkada, null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {

        final tjyhkadadata data = list.get(position);

        String img = data.getIcon().toLowerCase();

        if (img.equals("boc"))//中国银行
            Glide.with(context).load(R.mipmap.boc).into(holder.tjyhkitImtj);
        else if (img.equals("cmb"))//招商银行
            Glide.with(context).load(R.mipmap.cmb).into(holder.tjyhkitImtj);
        else if (img.equals("cib"))//兴业银行
            Glide.with(context).load(R.mipmap.cib).into(holder.tjyhkitImtj);
        else if (img.equals("abc"))//农业银行
            Glide.with(context).load(R.mipmap.abc).into(holder.tjyhkitImtj);
        else if (img.equals("cmbc"))//民生银行
            Glide.with(context).load(R.mipmap.cmbc).into(holder.tjyhkitImtj);
        else if (img.equals("bcm"))//交通银行
            Glide.with(context).load(R.mipmap.bcm).into(holder.tjyhkitImtj);
        else if (img.equals("ceb"))//光大银行
            Glide.with(context).load(R.mipmap.ceb).into(holder.tjyhkitImtj);
        else if (img.equals("ccb"))//建设银行
            Glide.with(context).load(R.mipmap.ccb).into(holder.tjyhkitImtj);
        else if (img.equals("icbc"))//工商银行
            Glide.with(context).load(R.mipmap.icbc).into(holder.tjyhkitImtj);
        else if (img.equals("psbc"))//邮政银行
            Glide.with(context).load(R.mipmap.psbc).into(holder.tjyhkitImtj);
        holder.tjyhkitTeyh.setText(data.bankname);
        holder.tjyhkitTeyhk.setText(data.bankNum);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBank.getBank(data.getId(), data.getBankname() + data.getBankNum());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tjyhkit_imtj)
        ImageView tjyhkitImtj;
        @BindView(R.id.tjyhkit_teyh)
        TextView tjyhkitTeyh;
        @BindView(R.id.tjyhkit_teyhk)
        TextView tjyhkitTeyhk;
        @BindView(R.id.tjyhkitlin)
        RelativeLayout linearLayout;


        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface getBank {
        void getBank(String id, String name);
    }
}
