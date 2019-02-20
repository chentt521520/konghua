package com.shijiucheng.konghua.main.per.payandget.gongdan;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class gondansctupian extends DialogFragment {
    @BindView(R.id.gdsctp_teth)
    TextView gdsctpTeth;
    @BindView(R.id.gdsctp_tesc)
    TextView gdsctpTesc;
    @BindView(R.id.gdsctp_teqx)
    TextView gdsctpTeqx;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);

        View view = inflater.inflate(R.layout.gondansctupian, container, false);
        unbinder = ButterKnife.bind(this, view);

        setviewlisten();
        return view;
    }

    private void setviewlisten() {
        gdsctpTeth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caozuo caozuo = (gondansctupian.caozuo) getActivity();
                caozuo.caozuotp(0);
                getDialog().dismiss();
            }
        });
        gdsctpTesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caozuo caozuo = (gondansctupian.caozuo) getActivity();
                caozuo.caozuotp(1);
                getDialog().dismiss();
            }
        });
    }

    public interface caozuo {
        void caozuotp(int i);//0替换，1删除
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        getDialog().getWindow().setLayout((int) (w * 500 / 750.0), ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
