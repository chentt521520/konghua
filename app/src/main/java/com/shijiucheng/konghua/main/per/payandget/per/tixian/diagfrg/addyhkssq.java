package com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg;

import android.app.DialogFragment;
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

public class addyhkssq extends DialogFragment implements addyhkssqadapter.getSSQidx {
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

    Retro_Intf serivce;
    String ShenId = "", ShiId = "", QuId = "";
    String ssqstr = "";
//    int posSSQ = 0;//0表示省1表示市2表示区

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
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
                if (TextUtils.isEmpty(ShenId) || TextUtils.isEmpty(ShiId) || TextUtils.isEmpty(QuId)) {
                    Toast.makeText(getActivity(), "请选择完省市区", Toast.LENGTH_SHORT).show();
                } else {
                    setSSq setSSq = (addyhkssq.setSSq) getActivity();
                    setSSq.setSSq(ShenId + "," + ShiId + "," + QuId, ssqstr);
                    dismiss();
                }
            }
        });
    }

    private void setrecycData() {
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
        maps.put("parent_id", id);
        maps.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call = serivce.getAddress(retrofit_Single.getInstence().getOpenid(getActivity()), maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response == null)
                        return;
                    String Result = response.body().string();

                    JSONObject jsonObject = new JSONObject(Result);
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("region_list");
                    list.removeAll(list);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        list.add(new addyhkdata(jsonObject1.getString("region_id"), jsonObject1.getString("region_name"), type));
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
        ShenId = "";
        ShiId = "";
        ssqstr = "";
    }


    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, 700);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));

    }

    public interface setSSq {
        void setSSq(String ids, String ssqstr);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getSSQid(String id, String ssqStr, String type) {
        if (type.equals("0")) {
            ShenId = id;
            ssqstr = ssqStr + "-";
            addyhkssqTetit.setText(ssqstr);
            ShiId = "";
            getSSQ(id, "1");
        } else if (type.equals("1")) {
            ShiId = id;
            ssqstr += ssqStr + "-";
            addyhkssqTetit.setText(ssqstr);
            QuId = "";
            getSSQ(id, "2");

        } else if (type.equals("2")) {
            if (TextUtils.isEmpty(QuId)) {
                QuId = id;
                ssqstr += ssqStr;
                addyhkssqTetit.setText(ssqstr);
            } else {
                String[] ssq_ = ssqstr.split("-");
                ssqstr = ssq_[0] + "-" + ssq_[1] + "-" + ssqStr;
                QuId = id;
                addyhkssqTetit.setText(ssqstr);
            }
        }
    }
}
