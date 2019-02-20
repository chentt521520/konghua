package com.shijiucheng.konghua.renzheng.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    List<Recyc_data> list;
    Context context;

    public MyRecyclerAdapter(List<Recyc_data> list,
                             Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.psqy_, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tv.setText(list.get(position).getTxt());
        holder.tv.setSelected(list.get(position).isselect);

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.tv.isSelected()) {
                    holder.tv.setSelected(true);
                    list.get(position).setIsselect(true);
                } else {
                    holder.tv.setSelected(false);
                    list.get(position).setIsselect(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.psqy_itte);
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int w_ = dm.widthPixels;
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w_ * 28 / 750.0));
            setViewHw_Lin(tv, (int) (w_ * 200 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 25 / 750.0), (int) (w_ * 5 / 750.0), (int) (w_ * 25 / 750.0), (int) (w_ * 20 / 750.0));
        }

    }

    protected void setViewHw_Lin(View v, int width, int height, int left,
                                 int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

}
