package com.shijiucheng.konghua.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.Utils.StringUtils;
import com.shijiucheng.konghua.main.entity.GrabOrder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GrabOrderAdapter extends BaseAdapter {
    private Context context;
    private List<GrabOrder> list;
    private int flag = 0;

    public GrabOrderAdapter(Context context, List<GrabOrder> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<GrabOrder> list, int flag) {
        this.list = list;
        this.flag = flag;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grab_order, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.expect_price_layout.setVisibility(flag == 1 ? View.VISIBLE : View.GONE);

        int width = (int) StringUtils.getTextWidth(context, holder.receiver_information_view.getText().toString(), 12);
        holder.receiverInfo.setWidth(width);
        holder.orderNumber.setWidth(width);
        holder.deliveryTime.setWidth(width);
        holder.expect_price_view.setWidth(width);

        GrabOrder grabOrder = list.get(position);
        holder.order_number.setText(grabOrder.getOrder_sn());
        holder.grabbing_order_btn.setText(grabOrder.getOrder_status_text());
        holder.delivery_time.setText(grabOrder.getDelivery_time());
        String address = grabOrder.getReceiver_province_text() + grabOrder.getReceiver_city_text() + grabOrder.getReceiver_district_text() + grabOrder.getReceiver_address();
        holder.delivery_address.setText(address);
        holder.expect_price.setText(grabOrder.getOrder_amount_add() + "元");
        holder.receiver_information.setText(grabOrder.getReceiver());
        holder.receiver_information.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.order_price.setText(grabOrder.getOrder_amount() + "元");
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.order_number_view)
        TextView receiverInfo;
        @BindView(R.id.order_number)
        TextView order_number;
        @BindView(R.id.grabbing_order_btn)
        TextView grabbing_order_btn;
        @BindView(R.id.delivery_time_view)
        TextView orderNumber;
        @BindView(R.id.delivery_time)
        TextView delivery_time;
        @BindView(R.id.order_price)
        TextView order_price;
        @BindView(R.id.expect_price_layout)
        LinearLayout expect_price_layout;
        @BindView(R.id.expect_price_view)
        TextView expect_price_view;
        @BindView(R.id.expect_price)
        TextView expect_price;
        @BindView(R.id.delivery_address_view)
        TextView deliveryTime;
        @BindView(R.id.delivery_address)
        TextView delivery_address;
        @BindView(R.id.receiver_information_view)
        TextView receiver_information_view;
        @BindView(R.id.receiver_information)
        TextView receiver_information;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
