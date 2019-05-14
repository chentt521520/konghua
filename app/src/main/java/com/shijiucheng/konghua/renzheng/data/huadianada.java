package com.shijiucheng.konghua.renzheng.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class huadianada extends RecyclerView.Adapter<huadianada.viewholder> {
    Context context;
    List<huadiandata> list;

    huadianonc huadianonc;

    public void sethuadianinterface(huadianonc huadianonc) {
        this.huadianonc = huadianonc;
    }

    public huadianada(Context context,
                      List<huadiandata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.huadianada, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        huadiandata data = list.get(position);
        holder.mapTename.setText(data.getName());
        holder.mapTejuli.setText(data.getJuli());
        holder.mapTedz.setText(data.getDizhi());
        if (data.getIsselsect().equals("1"))
            holder.imselect.setVisibility(View.VISIBLE);
        else
            holder.imselect.setVisibility(View.GONE);
        holder.lin_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == position)
                        list.get(i).setIsselsect("1");
                    else list.get(i).setIsselsect("0");
                }
                notifyDataSetChanged();
                huadianonc.huadianonc_(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.map_tename)
        TextView mapTename;
        @BindView(R.id.map_tejuli)
        TextView mapTejuli;
        @BindView(R.id.map_tedz)
        TextView mapTedz;
        @BindView(R.id.map_lin)
        LinearLayout lin_;
        @BindView(R.id.map_imselsect)
        ImageView imselect;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface huadianonc {
        void huadianonc_(int pos);
    }
}
