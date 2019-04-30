package com.shijiucheng.konghua.main.HomePage.frag;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.order.popwindow_.jujuedata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class jiesuan_fragagreerefuse extends DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.jiedan_teok)
    TextView jiedanTeok;
    @BindView(R.id.jiedan_tequxiao)
    TextView jiedanTequxiao;
    Unbinder unbinder;

    String orderid = "", tit = "", txt = "", type = "";
    @BindView(R.id.jiedan_tit)
    TextView jiedanTit;
    @BindView(R.id.jiedan_tets)
    TextView jiedanTets;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.jiesuan_frag, container, false);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);
        setdata();
        setviewlisten();

        return view;
    }

    private void setviewlisten() {
        jiedanTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                js();
            }
        });
        jiedanTequxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void setdata() {
        Bundle bundle = getArguments();
        orderid = bundle.getString("orderid");
        tit = bundle.getString("tit");
        txt = bundle.getString("txt");
        type = bundle.getString("type");//0同意1拒绝

        if (type.equals("0")) {
            jiedanTeok.setText("确认结算");
        } else jiedanTeok.setText("确认拒绝");

        jiedanTit.setText(tit);
        jiedanTets.setText(txt);
    }

    public interface jieshuanbf {
        /**
         * 同意和拒绝部分结算，接口请求成功重新刷新订单详情
         */
        void jieshuan_bufen();
    }

    public void js() {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(getActivity()));
        map.put("order_id", orderid);
        Call<ResponseBody> call;
        if (type.equals("0"))
            call = serivce.tyjs(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        else
            call = serivce.jujuejs(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_SHORT).show();
                            jieshuanbf jsbf = (jieshuanbf) getActivity();
                            jsbf.jieshuan_bufen();
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


    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - 200, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
