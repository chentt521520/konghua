package com.shijiucheng.konghua.main.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.HomePage.frag.jiesuan_frag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderTwoPage extends BaseActivity_konghua implements ordertwoAdapter.tixijieshuan, jiesuan_frag.jieshuan {

    ordertwoAdapter adapter;
    List<ordertwoData> list = new ArrayList<>();
    @BindView(R.id.ordertwo_dh)
    DaoHang_top ordertwoDh;
    @BindView(R.id.ordertwo_recy)
    RecyclerView ordertwoRecy;
    @BindView(R.id.ordertwo_smtr)
    SmartRefreshLayout ordertwoSmtr;
    @BindView(R.id.ordertwo_nodata)
    View layout;

    @BindView(R.id.ordertwo_kb)
    View v_kb;

    String status = "";
    int page = 1;

    com.shijiucheng.konghua.main.HomePage.frag.jiesuan_frag jiesuan_frag;
    int pos_id = 0;

    Retro_Intf serivce;
    Call<ResponseBody> call;
    Bundle bundle;

    @Override
    protected void AddView() {
        serivce = retrofit_Single.getInstence().getserivce(2);
        jiesuan_frag = new jiesuan_frag();
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        ordertwoDh.settext_(bundle.getString("tit"));
        status = bundle.getString("status");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        ordertwoRecy.setLayoutManager(manager);
        adapter = new ordertwoAdapter(this, list);
        ordertwoRecy.setAdapter(adapter);
        adapter.settxjs(this);
        getnum();
    }

    @Override
    protected void SetViewListen() {
        ordertwoSmtr.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getnum();
                if (ordertwoSmtr != null)
                    ordertwoSmtr.finishLoadmore(100);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                list.removeAll(list);
                getnum();
                if (ordertwoSmtr != null)
                    ordertwoSmtr.finishRefresh(500);

            }
        });
    }

    /**
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setenddate(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.orderSYrefr1)) {
                page = 1;
                list.removeAll(list);
                getnum();
            }
            return;
        }
    }


    public void getnum() {
        if (jdt != null)
            jdt.show(getFragmentManager(), "xx");
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("page", page + "");
        map.put("order_status", status);
        map.put("keywords", bundle.getString("keywords"));
        map.put("receiver", bundle.getString("receiver"));
        map.put("receiver_tel", bundle.getString("receiver_tel"));
        map.put("delivery_start_date", bundle.getString("delivery_start_date"));
        map.put("delivery_end_date", bundle.getString("delivery_end_date"));
        call = serivce.getOrderNumList(retrofit_Single.getInstence().getOpenid(this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt.isAdded())
                    jdt.dismiss();
                if (response.body() == null) {
                    return;
                }
                try {
                    JSONObject jsa = new JSONObject(response.body().string());
                    if (jsa.getString("status").equals("1")) {
                        JSONObject jsonObject = jsa.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jso1 = jsonArray.getJSONObject(i);
                            list.add(new ordertwoData(jso1.getString("order_id"), jso1.getString("order_sn"), jso1.getString("order_status"), jso1.getString("delivery_time"), ""
                                    , jso1.getString("order_amount"), jso1.getString("receiver_province_text") + jso1.getString("receiver_city_text") + jso1.getString("receiver_district_text"),
                                    jso1.getString("receiver_address"), jso1.getString("receiver"), jso1.getString("receiver_tel"), jso1.getString("is_order_balance_remind")
                                    , jso1.getString("order_status_text"), jso1.getString("order_amount_add")));
                        }
                        adapter.notifyDataSetChanged();
                        if (list.size() <= 0) {
                            if (layout != null) {
                                layout.setVisibility(View.VISIBLE);
                                v_kb.setVisibility(View.GONE);
                            }
                        } else {
                            if (layout != null) {
                                layout.setVisibility(View.GONE);
                                v_kb.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (jdt.isAdded())
                    jdt.dismiss();
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_two_page;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelinter(call);
        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.orderSYrefr);
        EventBus.getDefault().post(databean);

        //refreshhp

        paramsDataBean databean1 = new paramsDataBean();
        databean1.setMsg(configParams.refreshhp);
        EventBus.getDefault().post(databean1);
    }

    @Override
    public void tixijieshuan(int pos) {
        pos_id = pos;
        if (!jiesuan_frag.isAdded()) {
            jiesuan_frag.show(getSupportFragmentManager(), "js");
        }
    }

    private void jieshuan(String id, final int pos) {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", id);
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        Call<ResponseBody> call = serivce.jiesuan(retrofit_Single.getInstence().getOpenid(OrderTwoPage.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        if (jso.getString("status").equals("1")) {
                            Toast.makeText(OrderTwoPage.this, "提醒成功", Toast.LENGTH_SHORT).show();
                        }
                        list.get(pos).setIstixin("1");
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public void jieshuan(boolean t) {
        jieshuan(list.get(pos_id).getId(), pos_id);
    }

}
