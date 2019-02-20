package com.shijiucheng.konghua.main.HomePage.frag;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class zhangjia_frag extends DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.zhangjia_jine)
    TextView zhangjiaJine;
    @BindView(R.id.zhangjia_edit)
    EditText zhangjiaEdit;
    @BindView(R.id.zhangjia_teok)
    TextView zhangjiaTeok;
    @BindView(R.id.zhangjia_tequxiao)
    TextView zhangjiaTequxiao;
    Unbinder unbinder;

    String price_mq = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        view = inflater.inflate(R.layout.zhangjia_frag, container, false);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);
        setdata();
        setviewlisten();

        return view;
    }

    private void setviewlisten() {
        zhangjiaTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(zhangjiaEdit.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入期望的订单金额", Toast.LENGTH_SHORT).show();
                } else {
                    double price1 = Double.valueOf(price_mq).doubleValue();
                    double price2 = Double.valueOf(zhangjiaEdit.getText().toString()).doubleValue();
                    if (price1 > price2) {
                        Toast.makeText(getActivity(), "期望的订单金额不要小于目前订单金额", Toast.LENGTH_SHORT).show();
                    } else {
                        zhangjia zhangjia = (zhangjia_frag.zhangjia) getActivity();
                        zhangjia.zhangjia(zhangjiaEdit.getText().toString());
                        dismiss();
                    }

                }
            }
        });
        zhangjiaTequxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void setdata() {

        Bundle bundle = getArguments();
        price_mq = bundle.getString("price");
        zhangjiaJine.setText("目前订单金额：" + bundle.getString("price"));
    }

    public interface zhangjia {
        void zhangjia(String price);
    }


    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - 200, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
