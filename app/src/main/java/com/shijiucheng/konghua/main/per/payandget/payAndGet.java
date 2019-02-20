package com.shijiucheng.konghua.main.per.payandget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.PerMvp.PayAndGetMvp.Contact;
import com.shijiucheng.konghua.Cmvp.PerMvp.PayAndGetMvp.PGPrestentIml;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;

import java.util.ArrayList;
import java.util.List;

import Retrofit2.retrofit_Single;
import butterknife.BindView;

public class payAndGet extends com.shijiucheng.konghua.Cmvp.BaseActivity_konghua implements Contact.IView {
    @BindView(R.id.szmx_dh)
    DaoHang_top daoHang_top;
    @BindView(R.id.szmx_qb)
    TextView te_qb;
    @BindView(R.id.szmx_sr)
    TextView te_sr;
    @BindView(R.id.szmx_zc)
    TextView te_zc;

    @BindView(R.id.szmx_clz)
    TextView te_clz;


    @BindView(R.id.szmx_tenum)
    TextView teNum;
    @BindView(R.id.szmx_smtr)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.szmx_recyc)
    RecyclerView recyclerView;

    szmxada ada;
    List<szmxdata> list = new ArrayList<>();
    Contact.IPrestent prestent = new PGPrestentIml(this);

    int page = 1;
    String strStatus = "";
    int refrsh = 1;//刷新，1加载更多

    @Override
    protected void AddView() {
        daoHang_top.settext_("收支明细");
        te_qb.setSelected(true);
        te_sr.setSelected(false);
        te_zc.setSelected(false);
        te_clz.setSelected(false);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ada = new szmxada(this, list);
        recyclerView.setAdapter(ada);
        prestent.getList(strStatus, retrofit_Single.getInstence().getOpenid(payAndGet.this), page);

    }

    @Override
    protected void SetViewListen() {
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                refrsh = 1;
                prestent.getList(strStatus, retrofit_Single.getInstence().getOpenid(payAndGet.this), page);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refrsh = 0;
                page = 1;
                list.removeAll(list);
                prestent.getList(strStatus, retrofit_Single.getInstence().getOpenid(payAndGet.this), page);
            }
        });

        te_qb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!te_qb.isSelected()) {
                    te_qb.setSelected(true);
                    te_sr.setSelected(false);
                    te_zc.setSelected(false);
                    te_clz.setSelected(false);
                    page = 1;
                    strStatus = "";
                    list.removeAll(list);
                    prestent.getList(strStatus, retrofit_Single.getInstence().getOpenid(payAndGet.this), page);
                }
            }
        });
        te_sr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!te_sr.isSelected()) {
                    te_sr.setSelected(true);
                    te_qb.setSelected(false);
                    te_zc.setSelected(false);
                    te_clz.setSelected(false);
                    page = 1;
                    list.removeAll(list);
                    strStatus = "income";
                    prestent.getList(strStatus, retrofit_Single.getInstence().getOpenid(payAndGet.this), page);
                }
            }
        });
        te_zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!te_zc.isSelected()) {
                    te_zc.setSelected(true);
                    te_sr.setSelected(false);
                    te_qb.setSelected(false);
                    te_clz.setSelected(false);
                    page = 1;
                    list.removeAll(list);
                    strStatus = "expenditure";
                    prestent.getList(strStatus, retrofit_Single.getInstence().getOpenid(payAndGet.this), page);
                }
            }
        });
        te_clz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!te_clz.isSelected()) {
                    te_zc.setSelected(false);
                    te_sr.setSelected(false);
                    te_qb.setSelected(false);
                    te_clz.setSelected(true);
                    page = 1;
                    list.removeAll(list);
                    strStatus = "freeze";
                    prestent.getList(strStatus, retrofit_Single.getInstence().getOpenid(payAndGet.this), page);
                }
            }
        });


    }

    @Override
    protected int getLayout() {
        return R.layout.pay_and_get;
    }

    @Override
    protected BasePresenter bindPresent() {
        return prestent;
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void closeLoad() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showList(String num, List<szmxdata> list) {
        if (num.contains(",")) {
            //账户余额：46.00  =   账户总入账：100.00  -   账户总支出：54.00
            String[] str = num.split(",");
            teNum.setText("账户余额：" + str[0] + "  =   账户总入账：" + str[1] + "  -   账户总支出：" + str[2]);
        }
        if (list.size() >= 1) {
            this.list.addAll(list);
            ada.notifyDataSetChanged();
        }
        if (refrsh == 0) {
            if (smartRefreshLayout != null)
                smartRefreshLayout.finishRefresh();
        } else {
            if (smartRefreshLayout != null)
                smartRefreshLayout.finishLoadmore();
        }
    }
}
