package com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class tixianfs extends DialogFragment {

    View view;
    @BindView(R.id.sqtx_tezfb)
    TextView sqtxTezfb;
    @BindView(R.id.sqtx_teyhk)
    TextView sqtxTeyhk;
    Unbinder unbinder;

    public interface gettxfs {
        void getfs(String txt);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            view = inflater.inflate(R.layout.tixianfs, container, false);

        }
        unbinder = ButterKnife.bind(this, view);
        setviewlisten();
        return view;

    }

    public void setviewlisten() {
        sqtxTezfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettxfs fs = (gettxfs) getActivity();
                fs.getfs("支付宝提现");
            }
        });
        sqtxTeyhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettxfs fs = (gettxfs) getActivity();
                fs.getfs("银行卡提现");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
