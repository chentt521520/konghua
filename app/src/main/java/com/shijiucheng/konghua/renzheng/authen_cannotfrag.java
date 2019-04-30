package com.shijiucheng.konghua.renzheng;

import android.content.Intent;
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

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.HomePage.OrderTwoPage;
import com.shijiucheng.konghua.main.HomePage.OrderTwoPage1;
import com.shijiucheng.konghua.main.per.payandget.payAndGet;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class authen_cannotfrag extends DialogFragment {
    View view;
    int wxx = 0;

    Unbinder unbinder;
    @BindView(R.id.rzcannot_te1)
    TextView rzcannotTe1;
    @BindView(R.id.rzcannot_te2)
    TextView rzcannotTe2;
    @BindView(R.id.rzcannot_te3)
    TextView rzcannotTe3;
    @BindView(R.id.rzcannot_te4)
    TextView rzcannotTe4;
    @BindView(R.id.rzcannot_te5)
    TextView rzcannotTe5;
    @BindView(R.id.rzcannot_te6)
    TextView rzcannotTe6;
    @BindView(R.id.rzcannot_te7)
    TextView rzcannotTe7;
    @BindView(R.id.rzcannot_te8)
    TextView rzcannotTe8;
    @BindView(R.id.rzcannot_teok)
    TextView rzcannotTeok;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        view = inflater.inflate(R.layout.authen_cannotfrag, container, false);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);
        setdata();
        setviewlisten();

        return view;
    }

    private void setviewlisten() {

    }

    private void setdata() {
        Bundle bundle = getArguments();
        try {
            JSONObject jso = new JSONObject(bundle.getString("object"));

            if (!jso.getString("non_receive_count").equals("0")) {
                rzcannotTe1.setVisibility(View.VISIBLE);
                rzcannotTe1.setText("需处理 " + jso.getString("non_receive_count") + " 个待接收订单");
            } else rzcannotTe1.setVisibility(View.GONE);

            if (!jso.getString("amount_add_count").equals("0")) {
                rzcannotTe2.setVisibility(View.VISIBLE);
                rzcannotTe2.setText("需处理 " + jso.getString("amount_add_count") + " 个待涨价订单");
            } else rzcannotTe2.setVisibility(View.GONE);

            if (!jso.getString("receive_non_delivery_count").equals("0")) {
                rzcannotTe3.setVisibility(View.VISIBLE);
                rzcannotTe3.setText("需处理 " + jso.getString("receive_non_delivery_count") + " 个待配送订单");
            } else rzcannotTe3.setVisibility(View.GONE);

            if (!jso.getString("cancel_count").equals("0")) {
                rzcannotTe4.setVisibility(View.VISIBLE);
                rzcannotTe4.setText("需处理 " + jso.getString("cancel_count") + " 个主动申请退单订单");
            } else rzcannotTe4.setVisibility(View.GONE);

            if (!jso.getString("admin_cancel_count").equals("0")) {
                rzcannotTe5.setVisibility(View.VISIBLE);
                rzcannotTe5.setText("需处理 " + jso.getString("admin_cancel_count") + " 个下单方申请退单订单");
            } else rzcannotTe5.setVisibility(View.GONE);

            if (!jso.getString("delivering_count").equals("0")) {
                rzcannotTe6.setVisibility(View.VISIBLE);
                rzcannotTe6.setText("需处理 " + jso.getString("delivering_count") + " 个待签收订单");
            } else rzcannotTe6.setVisibility(View.GONE);

            if (!jso.getString("sign_count").equals("0")) {
                rzcannotTe7.setVisibility(View.VISIBLE);
                rzcannotTe7.setText("需处理 " + jso.getString("sign_count") + " 个待结算订单");
            } else rzcannotTe7.setVisibility(View.GONE);

            if (!jso.getString("freeze_count").equals("0")) {
                rzcannotTe8.setVisibility(View.VISIBLE);
                rzcannotTe8.setText("需处理 " + jso.getString("freeze_count") + " 个冻结交易流水");
            } else rzcannotTe8.setVisibility(View.GONE);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.rzcannot_te1, R.id.rzcannot_te2, R.id.rzcannot_te3, R.id.rzcannot_te4, R.id.rzcannot_te5, R.id.rzcannot_te6, R.id.rzcannot_te7, R.id.rzcannot_te8, R.id.rzcannot_teok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rzcannot_te1:
                geotoOrder(0, "待接单", "non_receive");
                dismiss();
                break;
            case R.id.rzcannot_te2:
                geotoOrder(0, "请求涨价", "amount_add");
                dismiss();
                break;
            case R.id.rzcannot_te3:
                geotoOrder(0, "待配送", "receive_non_delivery");
                dismiss();
                break;
            case R.id.rzcannot_te4:
                geotoOrder(1, "申请退单", "cancel");
                dismiss();
                break;
            case R.id.rzcannot_te5:
                geotoOrder(1, "申请退单", "cancel");
                dismiss();
                break;
            case R.id.rzcannot_te6:
                geotoOrder(0, "待签收", "delivering");
                dismiss();
                break;
            case R.id.rzcannot_te7:
                geotoOrder(0, "待结算", "sign");
                dismiss();
                break;
            case R.id.rzcannot_te8:
                startActivity(new Intent(getActivity(), payAndGet.class));
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                dismiss();
                break;
            case R.id.rzcannot_teok:
                dismiss();
                break;
        }
    }

    private void geotoOrder(int type, String tit, String status) {//区分是申请退款还是其他

        Bundle bundle = new Bundle();
        bundle.putString("tit", tit);
        bundle.putString("status", status);
        Intent intent;
        if (type == 0){
            bundle.putString("keywords", "");
            bundle.putString("receiver", "");
            bundle.putString("receiver_tel", "");
            bundle.putString("delivery_start_date", "");
            bundle.putString("delivery_end_date", "");
            intent = new Intent(getActivity(), OrderTwoPage.class);}
        else
            intent = new Intent(getActivity(), OrderTwoPage1.class);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }


    public interface bkdetelfrag {
        void bkdetelfrag(boolean t);
    }


    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - 200, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setdata();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
