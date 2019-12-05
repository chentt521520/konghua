package com.shijiucheng.konghua.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.entity.CityInfo;

import java.util.List;

public class GridCityAdapter extends BaseAdapter {

    private Context context;
    private List<CityInfo> list;

    public GridCityAdapter(Context context, List<CityInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<CityInfo> list) {
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
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_send_district, null);
            holder.view = convertView.findViewById(R.id.item);
            holder.district = convertView.findViewById(R.id.item_district);
            holder.checkBox = convertView.findViewById(R.id.item_district_check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CityInfo area = list.get(position);
        holder.district.setText(area.getCity());
        if (area.isCheck()) {
            holder.checkBox.setImageResource(R.mipmap.selected);
        } else {
            holder.checkBox.setImageResource(R.mipmap.unselect);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area.isCheck()) {
                    holder.checkBox.setImageResource(R.mipmap.unselect);
                    area.setCheek(false);
                } else {
                    holder.checkBox.setImageResource(R.mipmap.selected);
                    area.setCheek(true);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView district;
        ImageView checkBox;
        LinearLayout view;
    }
}
