package com.shijiucheng.konghua.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shijiucheng.konghua.Cmvp.BaseFragment_konghua;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.OrderSYMVP.SYContact;
import com.shijiucheng.konghua.Cmvp.OrderSYMVP.SYPrestent;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.HomePage.OrderTwoPage;
import com.shijiucheng.konghua.main.HomePage.OrderTwoPage1;
import com.shijiucheng.konghua.main.HomePage.frag.rili_frag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSY extends BaseFragment_konghua implements SYContact.SYView {
    @BindView(R.id.ordersy_linall)
    LinearLayout ordersyLinall;
    @BindView(R.id.ordersy_tejrdd)
    TextView ordersyTejrdd;
    @BindView(R.id.ordersy_temrdd)
    TextView ordersyTemrdd;
    @BindView(R.id.ordersy_tedjd)
    TextView ordersyTedjd;
    @BindView(R.id.ordersy_tedps)
    TextView ordersyTedps;
    @BindView(R.id.ordersy_tedqs)
    TextView ordersyTedqs;
    @BindView(R.id.ordersy_tesqtk)
    TextView ordersyTesqtk;
    @BindView(R.id.ordersy_tedjs)
    TextView ordersyTedjs;
    @BindView(R.id.ordersy_teywc)
    TextView ordersyTeywc;
    @BindView(R.id.order_edssbh)
    EditText orderEdssbh;
    @BindView(R.id.order_edsslxr)
    EditText orderEdsslxr;
    @BindView(R.id.order_edsslxrph)
    EditText orderEdsslxrph;
    @BindView(R.id.order_edssrq1)
    TextView orderEdssrq1;
    @BindView(R.id.order_edssrq2)
    TextView orderEdssrq2;
    @BindView(R.id.ordersy_tess)
    TextView ordersyTess;

    @BindView(R.id.ordersy_tecz)
    TextView te_cz;

    @BindView(R.id.ordersy_smtr)
    SmartRefreshLayout refreshLayout;


    SYContact.SYPrestent prestent = new SYPrestent(this);
    @BindView(R.id.ordersy_teqqzj)
    TextView ordersyTeqqzj;
    Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);

    com.shijiucheng.konghua.main.HomePage.frag.rili_frag rili_frag;


    @Override
    protected void AddView() {
        rili_frag = new rili_frag();
        EventBus.getDefault().register(this);
        getnum();
    }

    @Override
    protected void SetViewListen() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                getnum();
                refreshlayout.finishRefresh(1000);
            }
        });

        orderEdssrq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                if (!orderEdssrq2.getText().toString().contains("-"))
                    bundle.putString("time", "");
                else
                    bundle.putString("time", orderEdssrq2.getText().toString());
                rili_frag.setArguments(bundle);
                if (!rili_frag.isAdded())
                    rili_frag.show(getChildFragmentManager(), "rili");
            }
        });
        orderEdssrq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                if (!orderEdssrq1.getText().toString().contains("-"))
                    bundle.putString("time", "");
                else
                    bundle.putString("time", orderEdssrq1.getText().toString());
                rili_frag.setArguments(bundle);
                if (!rili_frag.isAdded())
                    rili_frag.show(getChildFragmentManager(), "rili");
            }
        });
        setczvis(orderEdssbh);//监听输入变化
        setczvis(orderEdsslxr);
        setczvis(orderEdsslxrph);
        te_cz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setempettxt();
                te_cz.setVisibility(View.GONE);
            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.ordersy;
    }

    @Override
    protected BasePresenter bindPresent() {
        return prestent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.ordersy_linall, R.id.ordersy_linjrdd, R.id.ordersy_linmrdd, R.id.ordersy_lindjd, R.id.ordersy_lindps, R.id.ordersy_lindqs, R.id.ordersy_linsqtk, R.id.ordersy_lindjs, R.id.ordersy_linywc, R.id.ordersy_tess, R.id.ordersy_linqqzj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ordersy_linall:
                geotoOrder(0, "全部订单", "");
                break;
            case R.id.ordersy_linjrdd:
                geotoOrder(0, "今日订单", "today");
                break;
            case R.id.ordersy_linmrdd:
                geotoOrder(0, "明日订单", "tomorrow");
                break;
            case R.id.ordersy_lindjd:
                geotoOrder(0, "待接单", "non_receive");
                break;
            case R.id.ordersy_lindps:
                geotoOrder(0, "待配送", "receive_non_delivery");
                break;
            case R.id.ordersy_lindqs:
                geotoOrder(0, "待签收", "delivering");
                break;
            case R.id.ordersy_linsqtk:
                geotoOrder(1, "申请退单", "cancel");
                break;
            case R.id.ordersy_lindjs:
                geotoOrder(0, "待结算", "sign");
                break;
            case R.id.ordersy_linywc:
                geotoOrder(0, "已完成", "balance");
                break;

            case R.id.ordersy_linqqzj:
                geotoOrder(0, "请求涨价", "amount_add");
                break;

            case R.id.ordersy_tess:
                //'/ssddd
                boolean t1 = orderEdssrq1.getText().toString().contains("开");
                boolean t2 = orderEdssrq2.getText().toString().contains("结束");
                if (t1 && (!t2)) {
                    toaste_ut(getActivity(), "请完成开始于结束日期填写");
                    return;
                }
                if (t2 && (!t1)) {
                    toaste_ut(getActivity(), "请完成开始于结束日期填写");
                    return;
                }
                geotoOrder(2, "搜索结果", "");
                break;
        }
    }

    /**
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.orderSYrefr)) {
                getnum();
                return;
            }
        }
    }

    /**
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setstartdate(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.orderSYsetdate1)) {
                Bundle bundle = (Bundle) data.getT();
                orderEdssrq1.setText(bundle.getString("time"));
                if (!te_cz.isShown())
                te_cz.setVisibility(View.VISIBLE);
            }
            return;
        }
    }

    /**
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setenddate(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.orderSYsetdate2)) {
                Bundle bundle = (Bundle) data.getT();
                orderEdssrq2.setText(bundle.getString("time"));
                if (!te_cz.isShown())
                    te_cz.setVisibility(View.VISIBLE);
            }
            return;
        }
    }


    @Override
    public void showLoad() {
        if (!jdt.isAdded()) {
            jdt.show(getActivity().getFragmentManager(), "jdt");
        }
    }

    @Override
    public void closeLoad() {
        if (jdt.isAdded()) {
            jdt.dismiss();
        }
    }

    @Override
    public void showMsg(String msg) {
        toaste_ut(getActivity(), msg);
    }

    @Override
    public void showData(JSONObject jsonObject) {

    }

    private void geotoOrder(int type, String tit, String status) {//区分是申请退款还是其他

        Bundle bundle = new Bundle();
        bundle.putString("tit", tit);
        bundle.putString("status", status);
        Intent intent;
        if (type == 0) {
            intent = new Intent(getActivity(), OrderTwoPage.class);
            bundle.putString("keywords", "");
            bundle.putString("receiver", "");
            bundle.putString("receiver_tel", "");
            bundle.putString("delivery_start_date", "");
            bundle.putString("delivery_end_date", "");
        } else if (type == 2) {
            bundle.putString("keywords", orderEdssbh.getText().toString());
            bundle.putString("receiver", orderEdsslxr.getText().toString());
            bundle.putString("receiver_tel", orderEdsslxrph.getText().toString());
            if (!orderEdssrq1.getText().toString().contains("开始"))
                bundle.putString("delivery_start_date", orderEdssrq1.getText().toString());
            else bundle.putString("delivery_start_date", "");
            if (!orderEdssrq2.getText().toString().contains("结束"))
                bundle.putString("delivery_end_date", orderEdssrq2.getText().toString());
            else bundle.putString("delivery_end_date", "");
            intent = new Intent(getActivity(), OrderTwoPage.class);
        } else
            intent = new Intent(getActivity(), OrderTwoPage1.class);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    public void getnum() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call = serivce.getOrderNumList(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    JSONObject jsa = new JSONObject(response.body().string());
                    if (jsa.getString("status").equals("1")) {
                        JSONObject jsonObject = jsa.getJSONObject("data");
                        ordersyTejrdd.setText(jsonObject.getString("today_date_count"));
                        ordersyTemrdd.setText(jsonObject.getString("tomorrow_date_count"));
                        ordersyTedjd.setText(jsonObject.getString("non_receive_count"));
                        ordersyTeqqzj.setText(jsonObject.getString("amount_add_count"));

                        ordersyTedps.setText(jsonObject.getString("receive_non_delivery_count"));
                        ordersyTedqs.setText(jsonObject.getString("delivering_count"));
                        String x1 = jsonObject.getString("admin_cancel_count");
                        String x2 = jsonObject.getString("cancel_count");
                        int numtk = Integer.valueOf(x1).intValue() + Integer.valueOf(x2).intValue();
                        ordersyTesqtk.setText(numtk + "");
                        ordersyTedjs.setText(jsonObject.getString("sign_count"));

                        ordersyTeywc.setText(jsonObject.getString("balance_count"));
                    } else {
                        ordersyTejrdd.setText("0");
                        ordersyTemrdd.setText("0");
                        ordersyTedjd.setText("0");
                        ordersyTeqqzj.setText("0");

                        ordersyTedps.setText("0");
                        ordersyTedqs.setText("0");
                        ordersyTesqtk.setText("0");
                        ordersyTedjs.setText("0");

                        ordersyTeywc.setText("0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public boolean isshowcs() {
        boolean t = false;//true显示
        if (!TextUtils.isEmpty(orderEdssbh.getText().toString())) {
            t = true;
            return t;
        }
        if (!TextUtils.isEmpty(orderEdsslxr.getText().toString())) {
            t = true;
            return t;
        }
        if (!TextUtils.isEmpty(orderEdsslxrph.getText().toString())) {
            t = true;
            return t;
        }
        if (!orderEdssrq1.getText().toString().equals("开始日期")) {
            t = true;
            return t;
        }
        if (!orderEdssrq2.getText().toString().equals("结束日期")) {
            t = true;
            return t;
        }
        return t;
    }

    public void setempettxt() {
        orderEdssbh.setText("");
        orderEdsslxr.setText("");
        orderEdsslxrph.setText("");
        orderEdssrq1.setText("开始日期");
        orderEdssrq2.setText("结束日期");
    }

    public void setczvis(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isshowcs()) {
                    if (!te_cz.isShown())
                        te_cz.setVisibility(View.VISIBLE);
                } else {
                    if (te_cz.isShown())
                        te_cz.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}


