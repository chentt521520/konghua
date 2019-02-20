package com.shijiucheng.konghua.main.order;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.order.popwindow_.caozuo_adadata;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class Order_detalis extends BaseActivity_konghua {

    String[] imgs = {"https://konghua.oss-cn-shanghai.aliyuncs.com//Upload/20180628/1530149477_69689.jpg", "https://konghua.oss-cn-shanghai.aliyuncs.com//Upload/20180628/1530149477_69689.jpg"};
    @BindView(R.id.oddtails_dh)
    DaoHang_top dh;
    @BindView(R.id.ddxq_refrag1)
    RelativeLayout refragment;

    @BindView(R.id.ddxq_teddxx)
    TextView te_ddxxin;
    @BindView(R.id.ddxq_teddbh)
    TextView te_bh;
    @BindView(R.id.ddxq_teddzt)
    TextView te_zt;

    @BindView(R.id.ddxq_teddxdtime)
    TextView te_xdtime;
    @BindView(R.id.ddxq_teddprice)
    TextView te_xdprice;

    @BindView(R.id.ddxq_teddpsxx)
    TextView te_psxxin;
    @BindView(R.id.ddxq_tepssj)
    TextView te_pssj;
    @BindView(R.id.ddxq_teshr)
    TextView te_shr;
    @BindView(R.id.ddxq_teshrph)
    TextView te_shrph;
    @BindView(R.id.ddxq_lindz)//配送地址
            LinearLayout lin_shdz;
    @BindView(R.id.ddxq_tedz)
    TextView te_shrdz;
    @BindView(R.id.ddxq_tedz1)
    TextView te_shrdz1;

    @BindView(R.id.ddxq_linpsjl)//配送距离
            LinearLayout lin_shjl;
    @BindView(R.id.ddxq_tepsjl)
    TextView te_psjl;
    @BindView(R.id.ddxq_tepsjl1)
    TextView te_psjl1;
    @BindView(R.id.ddxq_imdt)
    ImageView im_dt;
    @BindView(R.id.ddxq_teckdt)
    TextView te_godt;

    @BindView(R.id.ddxq_tehk)
    TextView te_hk;
    @BindView(R.id.ddxq_tehk1)
    TextView te_hk1;

    @BindView(R.id.ddxq_tebz)
    TextView te_bz;
    @BindView(R.id.ddxq_tebz1)
    TextView te_bz1;

    @BindView(R.id.ddxq_tebztp)//包装图片
            TextView te_bztp;
    @BindView(R.id.ddxq_linbztp)//包装图片
            LinearLayout lin_bztp;
    @BindView(R.id.ddxq_pstp1)
    ImageView im_ps1;
    @BindView(R.id.ddxq_pstp2)
    ImageView im_ps2;
    @BindView(R.id.ddxq_pstp3)
    ImageView im_ps3;

    @BindView(R.id.ddxq_teczls)
    TextView te_czls;
    @BindView(R.id.ddxq_recyc)
    RecyclerView recyclerView;

    @BindView(R.id.ddxq_teqs)
    TextView te_qs;
    @BindView(R.id.ddxq_teddqstime)
    TextView te_qssj;
    @BindView(R.id.ddxq_teddqsren)
    TextView te_qsren;

    @BindView(R.id.ddxq_linbot)
    LinearLayout lin_bot;
    @BindView(R.id.ddxq_tecz1)
    TextView te_cz1;
    @BindView(R.id.ddxq_tecz2)
    TextView te_cz2;

    @BindView(R.id.order_recycy)
    RecyclerView recyclerView_spxx;

    Order_detrecyc ada_spxx;
    List<order_detrecycdata> list_spxx = new ArrayList<>();

    private String[] images;
    private ArrayList<ImageView> views;
    private PagerAdapter pagerAdapter;//轮播

    caozuo_ada ada;
    List<caozuo_adadata> list = new ArrayList<>();

    public int ztChange = 0;//1的时候要刷新订单列表

    @Override
    protected void AddView() {
        dh.settext_("订单详情");

        setViewHw_Lin(refragment, w_, w_, 0, 0, 0, 0);
        new lunbo_tp().initialize(Order_detalis.this, imgs);
        setViewHw_Lin(te_ddxxin, w_, (int) (w_ * 70 / 750.0), 0, (int) (w_ * 0 / 750.0), 0, 0);
        te_ddxxin.setPadding((int) (w_ * 24 / 750.0), 0, 0, 0);

        setViewHw_Lin(te_bh, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(te_zt, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(te_xdtime, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(te_xdprice, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);

        setViewHw_Lin(te_psxxin, w_, (int) (w_ * 70 / 750.0), 0, (int) (w_ * 0 / 750.0), 0, 0);
        setViewHw_Lin(te_pssj, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(te_shr, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(te_shrph, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(lin_shdz, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(lin_shjl, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(im_dt, (int) (w_ * 48 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);

        setViewHw_Lin(te_hk, w_, (int) (w_ * 70 / 750.0), 0, (int) (w_ * 0 / 750.0), 0, 0);
        setViewHw_Lin(te_hk1, w_ - (int) (w_ * 48 / 750.0), LinearLayout.LayoutParams.WRAP_CONTENT, (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        te_hk1.setMinHeight((int) (w_ * 70 / 750.0));
        setViewHw_Lin(te_bz, w_, (int) (w_ * 70 / 750.0), 0, (int) (w_ * 0 / 750.0), 0, 0);
        setViewHw_Lin(te_bz1, w_ - (int) (w_ * 48 / 750.0), LinearLayout.LayoutParams.WRAP_CONTENT, (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        te_bz1.setMinHeight((int) (w_ * 70 / 750.0));

        setViewHw_Lin(te_bztp, w_ - (int) (w_ * 24 / 750.0), (int) (w_ * 70 / 750.0), 0, (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(lin_bztp, w_ - (int) (w_ * 24 / 750.0), (int) (w_ * 200 / 750.0), 0, (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(im_ps1, (int) (w_ * 200 / 750.0), (int) (w_ * 200 / 750.0), 0, (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(im_ps2, (int) (w_ * 200 / 750.0), (int) (w_ * 200 / 750.0), 0, (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(im_ps3, (int) (w_ * 200 / 750.0), (int) (w_ * 200 / 750.0), 0, (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);

        setViewHw_Lin(te_czls, w_, (int) (w_ * 70 / 750.0), 0, (int) (w_ * 0 / 750.0), (int) (w_ * 0 / 750.0), 0);

        setViewHw_Lin(te_qs, w_ - (int) (w_ * 24 / 750.0), (int) (w_ * 70 / 750.0), 0, (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        te_qs.setPadding((int) (w_ * 24 / 750.0), 0, 0, 0);
        setViewHw_Lin(te_qssj, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);
        setViewHw_Lin(te_qsren, w_ - (int) (w_ * 48 / 750.0), (int) (w_ * 70 / 750.0), (int) (w_ * 24 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 24 / 750.0), 0);

        setViewHw_Lin(lin_bot, w_, (int) (w_ * 100 / 750.0), 0, 0, 0, 0);

        refragment.setFocusable(true);
        refragment.setFocusableInTouchMode(true);
        addviewData();
    }

    private void addviewData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView_spxx.setLayoutManager(layoutManager1);
        ada = new caozuo_ada(list, this);
        ada_spxx = new Order_detrecyc(this, list_spxx);
        recyclerView.setAdapter(ada);
        recyclerView_spxx.setAdapter(ada_spxx);

        for (int i = 0; i < 11; i++) {
            list.add(new caozuo_adadata("", ""));
            if (i <= 2)
                list_spxx.add(new order_detrecycdata());
        }
        ada.notifyDataSetChanged();
        ada_spxx.notifyDataSetChanged();
    }

    @Override
    protected void SetViewListen() {
        te_cz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paramsDataBean bean = new paramsDataBean();
                bean.setMsg(configParams.orderRefresh);

                EventBus.getDefault().post(bean);
                finish();

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.order_detalis;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (ztChange == 1) {
                paramsDataBean bean = new paramsDataBean();
                bean.setMsg(configParams.orderRefresh);
                HashMap<String, String> maps = new HashMap<>();
                bean.setT(maps);

                EventBus.getDefault().post(bean);
                finish();
            }
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
            return false;
        }
        return false;
    }
}
