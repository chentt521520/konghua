package com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class guanbigd extends DialogFragment {
    @BindView(R.id.gbgd_teok)
    TextView gbgdTeok;
    @BindView(R.id.gbgd_teno)
    TextView gbgdTeno;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.guanbigd, container, false);
        unbinder = ButterKnife.bind(this, view);
        setviewlisten();
        setview();
        return view;
    }

    private void setview() {
        Bundle bundle = getArguments();
    }

    private void setviewlisten() {
        gbgdTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querenClose querenClose = (querenClose) getActivity();
                querenClose.querenclose();
                dismiss();
            }
        });
        gbgdTeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
        getDialog().getWindow().setLayout((int) (w * 600 / 750.0), -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public interface querenClose {
        void querenclose();
    }
}
