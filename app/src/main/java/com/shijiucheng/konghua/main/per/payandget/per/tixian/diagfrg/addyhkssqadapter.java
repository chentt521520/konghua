package com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shijiucheng.konghua.R;

import java.util.List;

public class addyhkssqadapter extends RecyclerView.Adapter<addyhkssqadapter.viewholder> {

    List<addyhkdata> list;
    Context contact;
    getSSQidx ssQid;

    public addyhkssqadapter(List<addyhkdata> data,
                            Context contact) {
        this.list = data;
        this.contact = contact;
    }

    public void setSSQidInterface(getSSQidx ssQid) {
        this.ssQid = ssQid;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contact).inflate(R.layout.addyhkssqadapter, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final addyhkdata addyhkdata = list.get(position);
        holder.textView.setText(addyhkdata.getText());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssQid.getSSQid(addyhkdata.getId(), addyhkdata.getText(), addyhkdata.getType());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        AppCompatTextView textView;

        public viewholder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.addyhkssq_itte);
        }
    }

    public interface getSSQidx {
        void getSSQid(String id, String ssqStr, String type);
    }
}
