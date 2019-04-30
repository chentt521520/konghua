package com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tianjiayhk extends DialogFragment implements tjyhkada.getBank {

    //银行卡列表选择银行卡

    View view;
    TextView tjyhkTetianjia;
    @BindView(R.id.tjyhk_recyc)
    RecyclerView tjyhkRecyc;
    Unbinder unbinder;

    tjyhkada tjyhkada;
    List<tjyhkadadata> list = new ArrayList<>();
    int mHeight = 0;
    Retro_Intf serivce;
    int wxx = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        view = inflater.inflate(R.layout.tianjiayhk, container, false);

        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;

        serivce = retrofit_Single.getInstence().getserivce(2);
        unbinder = ButterKnife.bind(this, view);
        addviewdata();
        setviewlisten();
        return view;
    }

    private void setviewlisten() {

    }

    public void addviewdata() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        tjyhkRecyc.setLayoutManager(manager);
        tjyhkada = new tjyhkada(getActivity(), list);
        tjyhkRecyc.setAdapter(tjyhkada);
        tjyhkada.setbank(this);
        getBankList();

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, (int) (wxx * 600 / 750.0));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }

    @Override
    public void getBank(String id, String name) {
        setmoren(id, name);
    }

    public interface chooseBank {

        void chooseBank(String id, String name);
    }

    public void getBankList() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(getActivity()));
        retrofit2.Call<ResponseBody> call = serivce.getBankist(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("bank_list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                list.add(new tjyhkadadata(object.getString("bank_id"), object.getString("bank_type"), object.getString("bank_type_text"), object.getString("bank_no")));
                            }
                            tjyhkada.notifyDataSetChanged();
                        }
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

    public void setmoren(final String bankid, final String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bank_id", bankid);
        map.putAll(retrofit_Single.getInstence().retro_postParameter(getActivity()));
        retrofit2.Call<ResponseBody> call = serivce.setMoRen(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            chooseBank bank = (chooseBank) getActivity();
                            bank.chooseBank(bankid, name);
                            dismiss();
                        } else
                            Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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
