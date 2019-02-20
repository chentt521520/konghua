package com.shijiucheng.konghua.main.per_.bank;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class banklistada extends RecyclerView.Adapter<banklistada.viewholder> {
    Context context;
    List<tjyhkadadata> list;

    getdetel getBank;

    public void setbank(getdetel getBank) {
        this.getBank = getBank;
    }

    public banklistada(Context context,
                       List<tjyhkadadata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.banklistada, null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {

        final tjyhkadadata data = list.get(position);

        String img = data.getIcon().toLowerCase();

        if (img.equals("boc"))
            Glide.with(context).load(R.mipmap.boc).into(holder.tjyhkitImtj);
        else if (img.equals("cmb"))
            Glide.with(context).load(R.mipmap.cmb).into(holder.tjyhkitImtj);
        else if (img.equals("cmb"))
            Glide.with(context).load(R.mipmap.cmb).into(holder.tjyhkitImtj);
        else if (img.equals("cib"))
            Glide.with(context).load(R.mipmap.cib).into(holder.tjyhkitImtj);
        else if (img.equals("abc"))
            Glide.with(context).load(R.mipmap.abc).into(holder.tjyhkitImtj);
        else if (img.equals("cmbc"))
            Glide.with(context).load(R.mipmap.cmbc).into(holder.tjyhkitImtj);
        else if (img.equals("bcm"))
            Glide.with(context).load(R.mipmap.bcm).into(holder.tjyhkitImtj);
        else if (img.equals("ceb"))
            Glide.with(context).load(R.mipmap.ceb).into(holder.tjyhkitImtj);
        else if (img.equals("ccb"))
            Glide.with(context).load(R.mipmap.ccb).into(holder.tjyhkitImtj);
        else if (img.equals("icbc"))
            Glide.with(context).load(R.mipmap.icbc).into(holder.tjyhkitImtj);
        holder.tjyhkitTeyh.setText(data.bankname);
        holder.tjyhkitTeyhk.setText(data.bankNum);
        holder.tjyhkitTebji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, bk_bji.class);
                i.putExtra("object", data.getObject());
                context.startActivity(i);
            }
        });
        holder.tjyhkitTeschu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBank.getdetel(data.getId(), data.getBankname() + data.getBankNum());
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
        LinearLayout linearLayout;

        @BindView(R.id.tjyhkit_tebji)
        TextView tjyhkitTebji;
        @BindView(R.id.tjyhkit_teschu)
        TextView tjyhkitTeschu;


        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface getdetel {
        void getdetel(String id, String name);
    }
}
