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
import android.widget.TextView;

import com.shijiucheng.konghua.R;

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
        if (data.getStatus().equals("0")) {
            holder.im_wd.setSelected(true);
        } else
            holder.im_wd.setSelected(false);

        if (list.get(position).getType().equals("0")) {
            holder.im_wd.setVisibility(View.VISIBLE);
            holder.te_tit.setText("站内信息");
        } else {
            holder.im_wd.setVisibility(View.INVISIBLE);
            holder.te_tit.setText("公告");
        }


        holder.te_txt.setText(data.getTxt());
        holder.te_time.setText(data.getTime());

        holder.te_ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setStatus("1");
                notifyDataSetChanged();
                Intent i = new Intent(context, newsDetials.class);
                if (list.get(position).getType().equals("0"))
                    i.putExtra("tit", "站内信");
                else {
                    i.putExtra("tit", "公告信息");
                }
                i.putExtra("id", list.get(position).getId());
                if (data.getStatus().equals("0")) {
                    data.setStatus("1");
                    notifyDataSetChanged();
                }
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

        @BindView(R.id.znxit_imwd)
        ImageView im_wd;
        @BindView(R.id.znxit_tetit)
        TextView te_tit;
        @BindView(R.id.znxit_tetxt)
        TextView te_txt;
        @BindView(R.id.znxit_tetime)
        TextView te_time;
        @BindView(R.id.znxit_teck)
        TextView te_ck;


        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
