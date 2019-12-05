package com.shijiucheng.konghua.main.HomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.HomePage.viewPagerUtils.CopyButtonLibrary;
import com.shijiucheng.konghua.main.Utils.StringUtils;
import com.shijiucheng.konghua.main.per.payandget.gongdan.check_pic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailGoodsAdapter extends BaseAdapter {

    private Context context;
    private List<ord_detadata> list;
    private CopyButtonLibrary copyButtonLibrary;

    public OrderDetailGoodsAdapter(Context context, List<ord_detadata> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<ord_detadata> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.orderdetailsadaptersp, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int width = (int) StringUtils.getTextWidth(context, holder.goodPicView.getText().toString(), 13);
        holder.flowerTypeView.setWidth(width);
        holder.packTypeView.setWidth(width);

        final ord_detadata data = list.get(position);
        holder.te_num.setText("数量：" + data.getNum());
        holder.spxqitTehuacai.setText(data.getHuacai());
        holder.spxqitTebaozhuang.setText(data.getBaozhuang());
        Glide.with(context).load(data.getUrl()).into(holder.spxqitIm);

        holder.spxqitIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(context, check_pic.class);
                i.putExtra("pos", 0 + "");
                i.putExtra("urls", data.getUrl1());
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        holder.te_fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(context, check_pic.class);
                i.putExtra("pos", 0 + "");
                i.putExtra("urls", data.getUrl1());
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });

        holder.te_fz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyButtonLibrary = new CopyButtonLibrary(context);
                copyButtonLibrary.init("花材：  " + holder.spxqitTehuacai.getText().toString() + "\n" + "包装：  " +
                        holder.spxqitTebaozhuang.getText().toString() + "\n数量：" + data.getNum());
            }
        });
        return convertView;
    }

    public class ViewHolder {
        @BindView(R.id.spxqit_im)
        ImageView spxqitIm;
        @BindView(R.id.spxqit_tehuacai)
        TextView spxqitTehuacai;
        @BindView(R.id.spxqit_tebaozhuang)
        TextView spxqitTebaozhuang;
        @BindView(R.id.spxqit_tenum)
        TextView te_num;

        @BindView(R.id.spxqit_tefd)
        TextView te_fd;
        @BindView(R.id.spxqit_tepsfz)
        TextView te_fz;

        @BindView(R.id.good_pic_view)
        TextView goodPicView;
        @BindView(R.id.spxqit_tehuacai_view)
        TextView flowerTypeView;
        @BindView(R.id.spxqit_tebaozhuang_view)
        TextView packTypeView;


        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
