package com.shijiucheng.konghua.main;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.shijiucheng.konghua.Cmvp.grabOrderMvp.GrabOrderContact;
import com.shijiucheng.konghua.Cmvp.grabOrderMvp.GrabOrderPresenter;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DensityUtil;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.HomePage.Orderdatels;
import com.shijiucheng.konghua.main.Utils.StringUtils;
import com.shijiucheng.konghua.main.adapter.GrabOrderAdapter;
import com.shijiucheng.konghua.main.entity.GrabOrder;
import com.shijiucheng.konghua.main.entity.GrabOrderDetail;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GrabOrderList extends BaseActivity_konghua implements View.OnClickListener {

    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.title_view)
    LinearLayout title_view;
    @BindView(R.id.btn_grab_order)
    TextView btn_grab_order;
    @BindView(R.id.btn_request_price)
    TextView btn_request_price;
    @BindView(R.id.btn_order_amount)
    TextView btn_order_amount;
    @BindView(R.id.btn_delivery_time)
    TextView btn_delivery_time;
    @BindView(R.id.btn_default_option)
    TextView btn_default_option;
    @BindView(R.id.grab_order_list)
    ListView grab_order_list;
    @BindView(R.id.no_data_view)
    View no_data_view;
    @BindView(R.id.ordertwo1_smtr)
    SmartRefreshLayout ordertwo1Smtr;

    private int page = 1;
    private String order_status = "";
    private String sort = "";
    private String order = "desc";
    List<GrabOrder> list = new ArrayList<>();
    private GrabOrderAdapter adapter;

    private GrabOrderContact.IGrabOrderPresent presenter;
    private GrabOrderContact.IGrabOrderView view = new GrabOrderContact.IGrabOrderView() {
        @Override
        public void showLoading() {

        }

        @Override
        public void closeLoading() {

        }

        @Override
        public void result(GrabOrderDetail ret) {
            if (page == 1) {
                list.clear();
            }

            if (!StringUtils.listIsEmpty(ret.getRows())) {
                list.addAll(ret.getRows());
            } else {
                if (page > 1) page--;
            }

            if (StringUtils.listIsEmpty(list)) {
                no_data_view.setVisibility(View.VISIBLE);
                ordertwo1Smtr.setVisibility(View.GONE);
            } else {
                ordertwo1Smtr.setVisibility(View.VISIBLE);
                no_data_view.setVisibility(View.GONE);
                adapter.refresh(list, order_status.equals("") ? 0 : 1);
            }
        }

        @Override
        public void toast(String msg) {

        }
    };

    @Override
    protected void AddView() {
        EventBus.getDefault().register(this);
        presenter = new GrabOrderPresenter(view);
        adapter = new GrabOrderAdapter(this, list);
        grab_order_list.setAdapter(adapter);

        int width = DensityUtil.getScreenWidth(this);
        ViewGroup.LayoutParams layoutParams = title_view.getLayoutParams();
        layoutParams.width = width / 2;
        title_view.setLayoutParams(layoutParams);

        getList();
    }

    @Override
    protected void SetViewListen() {
        btn_back.setOnClickListener(this);
        btn_grab_order.setOnClickListener(this);
        btn_request_price.setOnClickListener(this);
        btn_order_amount.setOnClickListener(this);
        btn_delivery_time.setOnClickListener(this);
        btn_default_option.setOnClickListener(this);

        ordertwo1Smtr.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getList();
                ordertwo1Smtr.finishLoadmore(500);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getList();
                ordertwo1Smtr.finishRefresh(500);
            }
        });

        grab_order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.setClass(GrabOrderList.this, Orderdatels.class);
                i.putExtra("id", list.get(position).getOrder_id());
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void getList() {
        presenter.startOrderList(this, page, order_status, sort, order);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_grab_order;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                return;
            case R.id.btn_grab_order:
                setTitleStatus(0);
                break;
            case R.id.btn_request_price:
                setTitleStatus(1);
                break;
            case R.id.btn_default_option:
                setSortStatus(0);
                break;
            case R.id.btn_delivery_time:
                setSortStatus(1);
                break;
            case R.id.btn_order_amount:
                setSortStatus(2);
                break;
        }
        page = 1;
        getList();
    }

    private void setTitleStatus(int flag) {
        if (flag == 0) {
            order_status = "";
        } else if (flag == 1) {
            order_status = "order_pool_amount_add";
        }
        btn_grab_order.setBackgroundResource(flag == 0 ? R.drawable.bg_grab_order_title_pre : R.drawable.bg_grab_order_title_def);
        btn_grab_order.setTextColor(flag == 0 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.zhu));
        btn_request_price.setBackgroundResource(flag == 1 ? R.drawable.bg_request_price_title_pre : R.drawable.bg_request_price_title_def);
        btn_request_price.setTextColor(flag == 1 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.zhu));

        setSortStatus(0);
    }

    private void setSortStatus(int flag) {
        if (flag == 0) {
            sort = "";
        } else if (flag == 1) {
            sort = "delivery_time";//配送时间
        } else if (flag == 2) {
            sort = "order_amount";//订单金额
        }
        btn_default_option.setTextColor(flag == 0 ? getResources().getColor(R.color.zhu) : getResources().getColor(R.color.hei));
        btn_delivery_time.setTextColor(flag == 1 ? getResources().getColor(R.color.zhu) : getResources().getColor(R.color.hei));
        btn_order_amount.setTextColor(flag == 2 ? getResources().getColor(R.color.zhu) : getResources().getColor(R.color.hei));
    }


    /**
     *
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setenddate(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.orderSYrefr1)) {
                page = 1;
                getList();
            }
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.orderSYrefr);
        EventBus.getDefault().post(databean);

        // 刷新首页
        paramsDataBean databean1 = new paramsDataBean();
        databean1.setMsg(configParams.refreshhp);
        EventBus.getDefault().post(databean1);
        EventBus.getDefault().unregister(this);
    }
}
