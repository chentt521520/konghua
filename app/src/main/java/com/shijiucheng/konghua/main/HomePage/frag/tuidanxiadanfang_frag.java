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

public class tuidanxiadanfang_frag extends DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.jiedan_teok)
    TextView jiedanTeok;
    @BindView(R.id.jiedan_tequxiao)
    TextView jiedanTequxiao;
    Unbinder unbinder;

    @BindView(R.id.tetit)
    TextView te_tit;
    @BindView(R.id.tetxt)
    TextView te_txt;


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
        te_tit.setText("下单方申请退单");
        te_txt.setText("您确认同意退单吗，确认继续？");
        jiedanTeok.setText("同意退单");
        setdata();
        setviewlisten();

        return view;
    }

    private void setviewlisten() {
        jiedanTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuidanxdf jiedan = (tuidanxdf) getActivity();
                jiedan.tuidanxdf(true);
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

    public interface tuidanxdf {
        void tuidanxdf(boolean t);
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
