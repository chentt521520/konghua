package com.shijiucheng.konghua.renzheng.data;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import butterknife.OnClick;
import butterknife.Unbinder;

public class bdmapGpsfra extends DialogFragment {
    View view;
    @BindView(R.id.gps_teok)
    TextView gpsTeok;
    @BindView(R.id.gps_tequxiao)
    TextView gpsTequxiao;
    Unbinder unbinder;
    @BindView(R.id.gps_tit)
    TextView gpsTit;
    @BindView(R.id.gps_tets)
    TextView gpsTets;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(false);
        view = super.onCreateView(inflater, container, savedInstanceState);

        if (view == null)
            view = inflater.inflate(R.layout.bdmapgpsfra, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - 60, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.gps_teok, R.id.gps_tequxiao})
    public void onViewClicked(View view) {
        openorfulueGps opf;
        switch (view.getId()) {
            case R.id.gps_teok:
                opf = (openorfulueGps) getActivity();
                opf.opengps();
                break;
            case R.id.gps_tequxiao:
                opf = (openorfulueGps) getActivity();
                opf.fuluegps();
                break;
        }
    }

    public interface openorfulueGps {
        void opengps();

        void fuluegps();
    }
}
