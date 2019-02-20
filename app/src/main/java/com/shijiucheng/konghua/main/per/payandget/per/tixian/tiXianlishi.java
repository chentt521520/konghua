package com.shijiucheng.konghua.main.per.payandget.per.tixian;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;

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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tiXianlishi extends BaseActivity_konghua {
    txianlsada txianlsada;
    List<txianlsadadatat> list = new ArrayList<>();
    @BindView(R.id.txls_dh)
    DaoHang_top txlsDh;
    @BindView(R.id.txls_recyc)
    RecyclerView txlsRecyc;
    @BindView(R.id.txls_smtr)
    SmartRefreshLayout txlsSmtr;
    Retro_Intf serivce;
    int page = 1;
    int smtrStatus = 1;


    @Override
    protected void AddView() {
        txlsDh.settext_("提现历史记录");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        txianlsada = new txianlsada(this, list);
        txlsRecyc.setLayoutManager(manager);
        txlsRecyc.setAdapter(txianlsada);

        serivce = retrofit_Single.getInstence().getserivce(2);
        getList();

    }

    @Override
    protected void SetViewListen() {
        txlsSmtr.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smtrStatus = 1;
                page++;
                getList();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smtrStatus = 0;
                page = 1;
                list.removeAll(list);
                getList();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ti_xianlishi;
    }

    public void getList() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("page", page + "");
        Call<ResponseBody> call = serivce.getListTX(retrofit_Single.getInstence().getOpenid(tiXianlishi.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray jsa = jsonObject.getJSONObject("data").getJSONArray("rows");
                        if (jsa.length() > 0) {
                            for (int i = 0; i < jsa.length(); i++) {
                                JSONObject jsonObject1 = jsa.getJSONObject(i);
                                list.add(new txianlsadadatat(jsonObject1.getString("add_time_text"), jsonObject1.getString("withdraw_amount"), jsonObject1.getJSONObject("bank_info").getString("bank_type_text") + jsonObject1.getJSONObject("bank_info").getString("bank_no_last"), jsonObject1.getString("withdraw_status_text"), jsonObject1.getString("fail_reason")));
                            }
                            txianlsada.notifyDataSetChanged();
                        }
                    }
                    if (smtrStatus == 1) {
                        if (txlsSmtr != null)
                            txlsSmtr.finishLoadmore();
                    } else {
                        if (txlsSmtr != null)
                            txlsSmtr.finishRefresh();
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


}
