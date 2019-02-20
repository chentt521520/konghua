package com.shijiucheng.konghua.main.order.popwindow_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import java.util.List;

public class jujueada extends BaseAdapter {
    List<jujuedata> list;
    Context context;

    public jujueada(List<jujuedata> listn,
                    Context context) {
        this.list = listn;
        this.context = context;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final addview add;
        final jujuedata data;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.jujue_yy, null);
            add = new addview();
            add.imageView = convertView.findViewById(R.id.jujueyy_im);
            add.textView = convertView.findViewById(R.id.jujueyy_te);
            add.lin_ = convertView.findViewById(R.id.jujueyy_lin);
            convertView.setTag(add);
        } else
            add = (addview) convertView.getTag();
        data = list.get(position);
        if (data.getT_().equals("0"))
            add.imageView.setSelected(false);
        else
            add.imageView.setSelected(true);
        add.textView.setText(data.getTxt());
        return convertView;
    }


    public class addview {
        ImageView imageView;
        TextView textView;
        LinearLayout lin_;
    }
}
