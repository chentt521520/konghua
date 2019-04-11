package com.shijiucheng.konghua.main.News_;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.Newsdetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class znxrecyc_ada extends RecyclerView.Adapter<znxrecyc_ada.viewholder> {

    Context context;
    List<znxada_data> list;

    public znxrecyc_ada(Context context, List<znxada_data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.znxrecyc_ada, null);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        final znxada_data data = list.get(position);
        if (list.get(position).getType().equals("0")) {
            if (data.getStatus().equals("0")) {
                Glide.with(context).load(R.mipmap.wdu).into(holder.im_wd);
                Glide.with(context).load(R.mipmap.wdt).into(holder.im_wdt);
            } else {
                Glide.with(context).load("").into(holder.im_wd);
                Glide.with(context).load(R.mipmap.ydt).into(holder.im_wdt);
            }
        } else {
            Glide.with(context).load(R.mipmap.ggao).into(holder.im_wdt);
        }

        if (list.get(position).getType().equals("0")) {
            holder.im_wd.setVisibility(View.VISIBLE);
            holder.te_tit.setText(data.getTxt());
            holder.te_txt.setText(data.getTxtstr());
            holder.lin_bot.setVisibility(View.VISIBLE);
            holder.te_time.setVisibility(View.VISIBLE);
            holder.te_time.setText(data.getTime());
        } else {
            holder.im_wd.setVisibility(View.INVISIBLE);
            holder.te_tit.setText(data.getTxt());
            holder.te_txt.setText(data.getTxt());
            Glide.with(context).load(R.mipmap.ggao).into(holder.im_wdt);

            holder.lin_bot.setVisibility(View.GONE);
            holder.im_wd.setVisibility(View.GONE);
        }


        holder.lin_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setStatus("1");
                notifyDataSetChanged();
                Intent i;
                if (list.get(position).getType().equals("0")) {
                    i = new Intent(context, Newsdetails.class);
                    i.putExtra("m_id", list.get(position).getId());
                    if (data.getStatus().equals("0")) {
                        data.setStatus("1");
                        notifyDataSetChanged();
                    }
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                } else {
                    i = new Intent(context, newsDetials.class);
                    i.putExtra("tit", "公告信息");
                    i.putExtra("id", list.get(position).getId());
                    if (data.getStatus().equals("0")) {
                        data.setStatus("1");
                        notifyDataSetChanged();
                    }
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });
        holder.lin_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setStatus("1");
                notifyDataSetChanged();
                Intent i;
                if (list.get(position).getType().equals("0")) {
                    i = new Intent(context, Newsdetails.class);
                    i.putExtra("m_id", list.get(position).getId());
                    if (data.getStatus().equals("0")) {
                        data.setStatus("1");
                        notifyDataSetChanged();
                    }
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                } else {
                    i = new Intent(context, newsDetials.class);
                    i.putExtra("tit", "公告信息");
                    i.putExtra("id", list.get(position).getId());
                    if (data.getStatus().equals("0")) {
                        data.setStatus("1");
                        notifyDataSetChanged();
                    }
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.znxit_imwd)
        ImageView im_wd;
        @BindView(R.id.znxit_imwdt)
        ImageView im_wdt;
        @BindView(R.id.znxit_tetit)
        TextView te_tit;
        @BindView(R.id.znxit_tetxt)
        TextView te_txt;
        @BindView(R.id.znxit_tetime)
        TextView te_time;
        @BindView(R.id.znxit_teck)
        TextView te_ck;
        @BindView(R.id.znxit_linbot)
        LinearLayout lin_bot;
        @BindView(R.id.znxit_lintop)
        LinearLayout lin_top;


        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
