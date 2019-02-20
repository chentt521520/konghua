package com.shijiucheng.konghua.main.HomePage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class orderdetailsadaptercaozuolis extends RecyclerView.Adapter<orderdetailsadaptercaozuolis.viewholder> {
    Context context;
    List<ord_detadataczls> list;


    public orderdetailsadaptercaozuolis(Context context,
                                        List<ord_detadataczls> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orderdetailsadaptercaozuolis, parent, false);
        viewholder viewholder = new viewholder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        ord_detadataczls data = list.get(position);

        String txt = (data.getText());
        txt =  txt.replace("&nbsp", "");
        txt = txt.replace("<br>", "");
        txt = txt.replace("</span>", "");
        txt = txt.replace("<span>", "");

        holder.orddetitCzls.setText(txt);

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.orddetit_czls)
        TextView orddetitCzls;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
