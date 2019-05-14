package com.shijiucheng.konghua.renzheng.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import java.util.List;

public class bdsearchada extends RecyclerView.Adapter<bdsearchada.viewholder> {

    Context context;
    List<bdsearchdata> data;
    getnewaddress gad;

    public bdsearchada(Context context,
                       List<bdsearchdata> data) {
        this.context = context;
        this.data = data;

    }
    public void getnewaddressintf( getnewaddress gad){
        this.gad = gad;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bdsearchada, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        bdsearchdata bdsearchdata = data.get(position);
        holder.textView.setText(bdsearchdata.getTxt());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gad.getnewaddress_(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView textView;

        public viewholder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.bdit_te);
        }
    }
    public interface getnewaddress{
        void getnewaddress_(int pos);
    }
}
