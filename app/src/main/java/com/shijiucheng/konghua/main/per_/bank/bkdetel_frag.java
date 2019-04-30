package com.shijiucheng.konghua.main.per_.bank;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class bkdetel_frag extends DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.jiedan_teok)
    TextView jiedanTeok;
    @BindView(R.id.jiedan_tequxiao)
    TextView jiedanTequxiao;

    @BindView(R.id.jiedan_tets)
    TextView te_ts;
    Unbinder unbinder;

    @BindView(R.id.jiedan_tit)
    TextView te_tit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        view = inflater.inflate(R.layout.jiesuan_frag, container, false);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);
        te_ts.setText("您确认要删除该银行卡吗？");
        jiedanTeok.setText("确认");
        jiedanTequxiao.setText("取消");
        te_tit.setText("温馨提示");
        setdata();
        setviewlisten();

        return view;
    }

    private void setviewlisten() {
        jiedanTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bkdetelfrag jiedan = (bkdetelfrag) getActivity();
                jiedan.bkdetelfrag(true);
                dismiss();
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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
