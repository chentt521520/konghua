package com.shijiucheng.konghua;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class gotoAuthen extends DialogFragment {
    View view;
    @BindView(R.id.goto_testatus)
    TextView gotoTestatus;
    @BindView(R.id.goto_telogin)
    TextView gotoTelogin;
    @BindView(R.id.goto_teauthen)
    TextView gotoTeauthen;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setGravity(Gravity.CENTER);
            getDialog().setCanceledOnTouchOutside(false);
            view = inflater.inflate(R.layout.gotoauthen, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        //0未认证，1认证中，2认证失败
        if (bundle.getString("status", "0").equals("1")) {
            gotoTestatus.setText(bundle.getString("statusstr", "0"));
            gotoTeauthen.setText("去查看");
        } else if (bundle.getString("status", "0").equals("2")) {
            gotoTestatus.setText(bundle.getString("statusstr", "0"));
            gotoTeauthen.setText("去修改");
        }

        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent arg2) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    gotoautheninterface gotoautheninterface = (gotoAuthen.gotoautheninterface) getActivity();
                    gotoautheninterface.tofinishapp();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_MENU) {
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - 200, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.goto_telogin, R.id.goto_teauthen})
    public void onViewClicked(View view) {
        gotoautheninterface gotoau = (gotoautheninterface) getActivity();
        switch (view.getId()) {
            case R.id.goto_telogin:
                gotoau.tologin();
//                dismiss();
                break;
            case R.id.goto_teauthen:
                gotoau.toauthen();
//                dismiss();
                break;
        }
    }


    public interface gotoautheninterface {
        void toauthen();

        void tologin();

        void tofinishapp();

    }
}
