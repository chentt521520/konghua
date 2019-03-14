package com.shijiucheng.konghua.Cmvp.OrderSYMVPNew.orderrecyc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class orderadapter extends RecyclerView.Adapter<orderadapter.viewholder> {

    Context context;
    List<orderdata> list;


    public orderadapter(Context context,
                        List<orderdata> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orderadapter, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        orderdata data = list.get(position);
        holder.textView.setText(data.getOrderstr());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(context, gondandetials.class);
                i.putExtra("id", data.getOrderid());
                i.putExtra("type", "处理中");
                i.putExtra("position", position);
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

        @BindView(R.id.hpadaNew_linorder)
        LinearLayout linearLayout;
        @BindView(R.id.hpadaNew_teorder)
        TextView textView;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
