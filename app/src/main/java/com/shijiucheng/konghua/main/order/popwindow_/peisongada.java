package com.shijiucheng.konghua.main.order.popwindow_;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

public class peisongada extends RecyclerView.Adapter<peisongada.viewH_> {
    List<peisonadadata> list;
    Context context;

    selectionPos selectionPos;

    public peisongada(List<peisonadadata> list,
                      Context context) {
        this.context = context;
        this.list = list;
    }

    public void setselectx(selectionPos selectionPos) {
        this.selectionPos = selectionPos;
    }

    @NonNull
    @Override
    public viewH_ onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.peisonada, parent, false);
        viewH_ viewH_ = new viewH_(v);
        return viewH_;
    }

    @Override
    public void onBindViewHolder(@NonNull viewH_ holder, final int position) {
        final peisonadadata data = list.get(position);
        holder.te_name.setText(data.getStr());
        if (data.isselect.equals("1"))
            holder.te_name.setSelected(true);
        else holder.te_name.setSelected(false);

        holder.te_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionPos.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewH_ extends RecyclerView.ViewHolder {

        TextView te_name;

        public viewH_(View itemView) {
            super(itemView);
            te_name = itemView.findViewById(R.id.psada_tename);
        }
    }


    public interface selectionPos {
        void onItemClick(int postion);
    }
}
