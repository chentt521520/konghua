package com.shijiucheng.konghua.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.NewsMvp.NewsPrestent;
import com.shijiucheng.konghua.Cmvp.NewsMvp.contact;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.News_.znxada_data;
import com.shijiucheng.konghua.main.News_.znxrecyc_ada;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import Retrofit2.retrofit_Single;
import butterknife.BindView;

/**
 * created 2018/8/10 0010 13:37
 * author ldl
 */
public class News extends com.shijiucheng.konghua.Cmvp.BaseFragment_konghua implements contact.IView {
    View view;

    @BindView(R.id.news_recyc)
    RecyclerView recyc;
    @BindView(R.id.news_qb)
    TextView te_qb;
    @BindView(R.id.news_sr)
    TextView te_wdu;
    @BindView(R.id.news_zc)
    TextView te_ydu;
    @BindView(R.id.news_smartLayoutxx)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.news_znxx)
    TextView newsZnxx;
    @BindView(R.id.news_ggao)
    TextView newsGgao;

    @BindView(R.id.view_1)
    View v_1;
    @BindView(R.id.view_2)
    View v_2;

    znxrecyc_ada ada;
    List<znxada_data> list = new ArrayList<>();
    contact.IPrestent prestent = new NewsPrestent(this);

    int type = 1;//123表示站内信，456表示公告
    int page = 1;
    int refreshstatus = 0;//0没有动作，1刷新，2加载更多


    @Override
    protected void AddView() {
        EventBus.getDefault().register(this);
        newsZnxx.setSelected(true);
        newsGgao.setSelected(false);
        te_qb.setSelected(true);
        smartRefreshLayout.setDisableContentWhenRefresh(true);

        v_1.setBackgroundColor(getResources().getColor(R.color.zhu));
        v_2.setBackgroundColor(getResources().getColor(R.color.danhei));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyc.setLayoutManager(manager);
        ada = new znxrecyc_ada(getActivity(), list);
        recyc.setAdapter(ada);
        prestent.getNews(retrofit_Single.getInstence().getOpenid(getActivity()), page, type, "");
        newsZnxx.setSelected(true);
        SetViewListen();
    }

    @Override
    protected void SetViewListen() {
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                refreshstatus = 2;
                getTypeValue(type);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                refreshstatus = 1;
                list.removeAll(list);
                getTypeValue(type);
            }
        });

        newsZnxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick())
                    if (!newsZnxx.isSelected()) {
                        newsZnxx.setSelected(true);
                        newsGgao.setSelected(false);

                        v_1.setBackgroundColor(getResources().getColor(R.color.zhu));
                        v_2.setBackgroundColor(getResources().getColor(R.color.danhei));
                        type = 1;
                        page = 1;
                        refreshstatus = 1;
                        list.removeAll(list);
                        ada.notifyDataSetChanged();
                        getTypeValue(type);
                    }
            }
        });
        newsGgao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick())
                    if (!newsGgao.isSelected()) {
                        newsGgao.setSelected(true);
                        newsZnxx.setSelected(false);

                        v_2.setBackgroundColor(getResources().getColor(R.color.zhu));
                        v_1.setBackgroundColor(getResources().getColor(R.color.danhei));
                        type = 4;
                        page = 1;
                        refreshstatus = 1;
                        list.removeAll(list);
                        ada.notifyDataSetChanged();
                        getTypeValue(type);
                    }
            }
        });

        te_qb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!te_qb.isSelected()) {
                    te_qb.setSelected(true);
                    te_wdu.setSelected(false);
                    te_ydu.setSelected(false);
                    list.removeAll(list);
                    if (newsZnxx.isSelected())
                        type = 1;
                    else type = 4;
                    smartRefreshLayout.autoRefresh(100);
                }
            }
        });
        te_wdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!te_wdu.isSelected()) {
                    te_wdu.setSelected(true);
                    te_qb.setSelected(false);
                    te_ydu.setSelected(false);
                    list.removeAll(list);
                    if (newsZnxx.isSelected())
                        type = 2;
                    else type = 5;
                    smartRefreshLayout.autoRefresh(100);
                }
            }
        });
        te_ydu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!te_ydu.isSelected()) {
                    te_ydu.setSelected(true);
                    te_qb.setSelected(false);
                    te_wdu.setSelected(false);
                    list.removeAll(list);
                    if (newsZnxx.isSelected())
                        type = 3;
                    else type = 6;
                    smartRefreshLayout.autoRefresh(100);
                }
            }
        });

    }

    private void getTypeValue(int type) {
        if (type == 1)
            prestent.getNews(retrofit_Single.getInstence().getOpenid(getActivity()), page, type, "");
        else if (type == 2)
            prestent.getNews(retrofit_Single.getInstence().getOpenid(getActivity()), page, type, "0");
        else if (type == 3)
            prestent.getNews(retrofit_Single.getInstence().getOpenid(getActivity()), page, type, "1");
        else if (type == 4)
            prestent.getNotice(retrofit_Single.getInstence().getOpenid(getActivity()), page, type, "");
        else if (type == 5)
            prestent.getNotice(retrofit_Single.getInstence().getOpenid(getActivity()), page, type, "0");
        else if (type == 6)
            prestent.getNotice(retrofit_Single.getInstence().getOpenid(getActivity()), page, type, "1");

    }

    @Override
    protected int getLayout() {
        return R.layout.news;
    }

    @Override
    protected BasePresenter bindPresent() {
        return prestent;
    }

    @Override
    public void showloading() {
        if (!jdt.isAdded())
            jdt.show(getActivity().getFragmentManager(), "news");
    }

    @Override
    public void closeloading() {
        if (jdt.isAdded())
            jdt.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        toaste_ut(getActivity(), msg);
    }

    @Override
    public void showNews(int type, List<znxada_data> list) {

        this.list.addAll(list);
        ada.notifyDataSetChanged();
        if (refreshstatus == 1) {
            smartRefreshLayout.finishRefresh();
        } else if (refreshstatus == 2) {
            smartRefreshLayout.finishLoadmore();
        }
    }

    /**
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setenddate(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.symsg)) {
                prestent.getNews(retrofit_Single.getInstence().getOpenid(getActivity()), page, type, "");
            }
            return;
        }
    }
}
