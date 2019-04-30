package com.shijiucheng.konghua.main.per.payandget.gongdan;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top1;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.evaluatequestion;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.gdxqada;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.gdxqdata;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.guanbigd;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.pinjiagd;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.replayquestion;

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
import java.util.Map;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class gondandetials extends BaseActivity_konghua implements guanbigd.querenClose {

    @BindView(R.id.gdxq_dh)
    DaoHang_top1 gdxqDh;
    @BindView(R.id.gdxq_teysl)
    TextView gdxqTeysl;
    @BindView(R.id.gdxq_teclz)
    TextView gdxqTeclz;
    @BindView(R.id.gdxq_teyqr)
    TextView gdxqTeyqr;
    @BindView(R.id.gdxq_tedpj)
    TextView gdxqTedpj;
    @BindView(R.id.gdxq_recyc)
    RecyclerView gdxqRecyc;
    @BindView(R.id.gdxq_tehuifu)
    TextView gdxqTehuifu;
    @BindView(R.id.gdxq_teguanbi)
    TextView gdxqTeguanbi;
    @BindView(R.id.gdxq_lincz)
    LinearLayout gdxqLincz;
    @BindView(R.id.gdxq_tebhao)
    TextView gdxqTebhao;
    @BindView(R.id.gdxq_teleibie)
    TextView gdxqTeleibie;

    @BindView(R.id.gdxq_tepj)
    TextView gdxqTepj;

    gdxqada gdxqada;
    List<gdxqdata> list = new ArrayList<>();

    Retro_Intf serivce;
    String id = "";
    String type = "";
//            //工单提交待受理
//	          'WORK_ORDER_STATUS_NEW'=>0,
//            //工单已受理
//            'WORK_ORDER_STATUS_ACCEPTED'=>5,
//            //工单处理中(管理员已回复)
//            'WORK_ORDER_STATUS_REPLY_ADMIN'=>10,
//            //工单处理中(花店已回复)
//            'WORK_ORDER_STATUS_REPLY_STORE'=>11,
//            //工单已确认
//            'WORK_ORDER_STATUS_CLOSED'=>15,
//            //工单已评价
//            'WORK_ORDER_STATUS_EVALUATE'=>20,

    guanbigd gdclose;

    @Override
    protected void AddView() {
        EventBus.getDefault().register(this);
        gdclose = new guanbigd();
        gdxqDh.settext_("问题详情");
        gdxqDh.setvis();
        id = getIntent().getStringExtra("id");
        getStatus();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        gdxqRecyc.setLayoutManager(manager);
        gdxqada = new gdxqada(this, list);
        gdxqRecyc.setAdapter(gdxqada);
        serivce = retrofit_Single.getInstence().getserivce(2);
        getDetails();
    }

    @Override
    protected void SetViewListen() {

        gdxqTeguanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gdclose.isAdded())
                    gdclose.show(getFragmentManager(), "hfgd");
            }
        });
        gdxqTehuifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    startActivityByIntent(gondandetials.this, replayquestion.class, bundle);
                }
            }
        });
        gdxqTepj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("gdid", id);
                    bundle.putInt("pos", getIntent().getIntExtra("position", 0));
                    startActivityByIntent(gondandetials.this, evaluatequestion.class, bundle);
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gondandetials;
    }

    public void getDetails() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        retrofit2.Call<ResponseBody> call = serivce.getWorkOrderDetils(retrofit_Single.getInstence().getOpenid(gondandetials.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        JSONArray jsa = jso.getJSONObject("data").getJSONArray("reply_list");
                        JSONObject jsaOr = jso.getJSONObject("data").getJSONObject("work_order_info");
                        if (gdxqTebhao != null) {
                            gdxqTebhao.setText("问题编号：" + jsaOr.getString("id"));
                            gdxqTeleibie.setText(jsaOr.getString("cate1_text") + ">" + jsaOr.getString("cate2_text"));

                            type = jsaOr.getString("work_order_status");

                            if (type.equals("15"))
                                gdxqTepj.setVisibility(View.GONE);
                            else if (type.equals("20")) {
                                gdxqLincz.setVisibility(View.GONE);
                            } else {
                                gdxqLincz.setVisibility(View.VISIBLE);
                                gdxqTepj.setVisibility(View.GONE);
                            }

                        }
                        type = jsaOr.getString("work_order_status");
                        getStatus();
                        list.removeAll(list);
                        list.add(new gdxqdata("0", jsaOr.getString("add_time_text"), jsaOr.getString("content_text"), jsaOr.getString("work_order_images")));
                        if (jsa.length() >= 1) {
                            for (int i = 0; i < jsa.length(); i++) {
                                JSONObject jsoList = jsa.getJSONObject(i);
                                list.add(new gdxqdata(jsoList.getString("reply_type"), jsoList.getString("add_time_text"), jsoList.getString("reply_content"), jsoList.getString("reply_images")));
                            }

                        }
                        gdxqada.notifyDataSetChanged();
                        if (gdxqRecyc != null)
                            if (list != null)
                            gdxqRecyc.scrollToPosition(list.size() - 1);
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

    public void getStatus() {
        if (gdxqLincz != null) {
            if (type.equals("0")) {

                gdxqLincz.setVisibility(View.GONE);
            } else if (type.equals("5")) {
                gdxqLincz.setVisibility(View.VISIBLE);
                gdxqTehuifu.setVisibility(View.GONE);
                gdxqTepj.setVisibility(View.GONE);
                gdxqTeysl.setSelected(true);

            } else if (type.equals("10") || type.equals("11")) {
                gdxqLincz.setVisibility(View.VISIBLE);
                gdxqTepj.setVisibility(View.GONE);

                gdxqTeysl.setSelected(true);
                gdxqTeclz.setSelected(true);

            } else if (type.equals("15")) {
                gdxqLincz.setVisibility(View.VISIBLE);
                gdxqTepj.setVisibility(View.VISIBLE);
                gdxqTeguanbi.setVisibility(View.GONE);
                gdxqTehuifu.setVisibility(View.GONE);
                gdxqTeysl.setSelected(true);
                gdxqTeclz.setSelected(true);
                gdxqTeyqr.setSelected(true);

            } else if (type.equals("20")) {
                gdxqLincz.setVisibility(View.GONE);

                gdxqTeysl.setSelected(true);
                gdxqTeclz.setSelected(true);
                gdxqTeyqr.setSelected(true);
                gdxqTedpj.setSelected(true);

            } else {
                gdxqLincz.setVisibility(View.VISIBLE);
                gdxqTepj.setVisibility(View.GONE);
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.wodetalisrefresh)) {
                getDetails();
                return;
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//评价后刷新当前item数据
    public void getmess1(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.wodetalisrefresh11)) {
                getDetails();
                return;
            }

        }
    }

    @Override
    public void querenclose() {
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("id", id);
        Call<ResponseBody> call = serivce.closeWorkorder(retrofit_Single.getInstence().getOpenid(gondandetials.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        if (jso.getString("status").equals("1")) {
                            toaste_ut(gondandetials.this, "该工单关闭成功");

                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.wodetalisrefresh1);
                            int pos = getIntent().getIntExtra("position", 0);
                            databean.setT(pos);
                            EventBus.getDefault().post(databean);

                            finish();
                            overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
                        } else
                            toaste_ut(gondandetials.this, jso.getString("msg"));
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


}