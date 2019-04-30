package com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;

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

public class addyhkType extends DialogFragment implements addyhkssqadapter.getSSQidx {
    View view;
    @BindView(R.id.addyhkssq_tetit)
    TextView addyhkssqTetit;
    @BindView(R.id.addyhkssq_teok)
    AppCompatTextView addyhkssqTeok;
    @BindView(R.id.addyhkssq_recyc)
    RecyclerView addyhkssqRecyc;
    Unbinder unbinder;

    addyhkssqadapter addyhkssqadapter;
    List<addyhkdata> list = new ArrayList<>();

    String id = "", name = "";

    Retro_Intf serivce;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        view = inflater.inflate(R.layout.addyhkssq, container, false);
        unbinder = ButterKnife.bind(this, view);
        setrecycData();
        setviewlisten();
        return view;
    }

    private void setviewlisten() {
        addyhkssqTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addyhkssqTetit.getText().toString().equals("请选择银行卡类别")) {
                    setBankType bankType = (setBankType) getActivity();
                    bankType.setbank(id, name);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "请选择银行卡类别", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setrecycData() {
        addyhkssqTetit.setText("请选择银行卡类别");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        addyhkssqadapter = new addyhkssqadapter(list, getActivity());
        addyhkssqadapter.setSSQidInterface(this);
        addyhkssqRecyc.setLayoutManager(layoutManager);
        addyhkssqRecyc.setAdapter(addyhkssqadapter);

        getSSQ("", "0");

    }

    private void getSSQ(String id, final String type) {

        serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("bank_id", "");
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(getActivity()));
        Call<ResponseBody> call = serivce.getBankistType(retrofit_Single.getInstence().getOpenid(getActivity()), maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response == null)
                        return;
                    String Result = response.body().string();

                    JSONObject jsonObject = new JSONObject(Result);
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("finance_bank_type_list");
                    list.removeAll(list);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        list.add(new addyhkdata(jsonObject1.getString("key"), jsonObject1.getString("text"), type));
                    }
                    addyhkssqRecyc.smoothScrollToPosition(0);
                    addyhkssqadapter.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - 100, 600);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }

    public interface setBankType {
        void setbank(String ids, String ssqstr);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getSSQid(String id, String ssqStr, String type) {
        this.id = id;
        this.name = ssqStr;
        addyhkssqTetit.setText(ssqStr);
    }
}
