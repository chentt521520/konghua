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

import com.shijiucheng.konghua.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class jiedan_frag extends DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.tetxt)
    TextView jiedanMsg;
    @BindView(R.id.jiedan_teok)
    TextView jiedanTeok;
    @BindView(R.id.jiedan_tequxiao)
    TextView jiedanTequxiao;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.jiedan_frag, container, false);
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
                jiedan jiedan = (jiedan_frag.jiedan) getActivity();
                jiedan.jiedan(true);
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
        Bundle bundle = getArguments();
        String notice = "您接收该笔订单后不可撤单，确认继续？";
        if (bundle != null) {
            bundle.getString("tag");
            notice = bundle.getString("notice");
        }
        jiedanMsg.setText(notice);
    }

    public interface jiedan {
        void jiedan(boolean t);
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
