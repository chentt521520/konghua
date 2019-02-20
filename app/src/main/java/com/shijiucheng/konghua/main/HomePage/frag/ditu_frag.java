package com.shijiucheng.konghua.main.HomePage.frag;

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

public class ditu_frag extends DialogFragment {
    View view;
    int wxx = 0;
    Unbinder unbinder;
    @BindView(R.id.xzfs_tebd)
    TextView xzfsTebd;
    @BindView(R.id.xzfs_tetx)
    TextView xzfsTetx;
    @BindView(R.id.xzfs_teqx)
    TextView xzfsTeqx;
    mappos mappos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        view = inflater.inflate(R.layout.ditu_frag, container, false);
        mappos = (ditu_frag.mappos) getActivity();
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);
        setdata();
        setviewlisten();

        return view;
    }

    private void setviewlisten() {
        xzfsTebd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mappos.mappos(0);
                dismiss();
            }
        });
        xzfsTetx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mappos.mappos(1);
                dismiss();
            }
        });
        xzfsTeqx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void setdata() {

    }

    public interface mappos {
        void mappos(int pos);
    }


    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
