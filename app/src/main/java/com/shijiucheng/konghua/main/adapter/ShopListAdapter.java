package com.shijiucheng.konghua.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.entity.OwnerShop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopListAdapter extends BaseAdapter {

    private Context context;
    private List<OwnerShop> shopList;
    private String shopId;

    public ShopListAdapter(Context context, List<OwnerShop> shopList) {
        this.context = context;
        this.shopList = shopList;
    }

    public void refresh(List<OwnerShop> shopList) {
        this.shopList = shopList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shopList == null ? 0 : shopList.size();
    }

    @Override
    public Object getItem(int position) {
        return shopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShopHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_list, null);
            holder = new ShopHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ShopHolder) convertView.getTag();
        }

        OwnerShop ownerShop = shopList.get(position);

        holder.shopName.setText(ownerShop.getStore_name());
        holder.accountName.setText("(" + ownerShop.getStore_account() + ")");

        holder.checkBox.setChecked(ownerShop.isCheck());
        holder.view.setOnClickListener((View view) -> {
            for (OwnerShop shop : shopList) {
                shop.setCheck(false);
            }
            shopId = ownerShop.getStore_id();
            ownerShop.setCheck(true);

            notifyDataSetChanged();
        });

        return convertView;
    }

    public String getShopId() {
        return shopId;
    }

    class ShopHolder {
        //默认账号选择控件
        @BindView(R.id.ui_default_account_check)
        CheckBox checkBox;
        //店铺名称
        @BindView(R.id.ui_default_shop_name)
        TextView shopName;
        //账户名称
        @BindView(R.id.ui_default_account_name)
        TextView accountName;
        //item
        @BindView(R.id.ui_default_account_view)
        RelativeLayout view;

        ShopHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
