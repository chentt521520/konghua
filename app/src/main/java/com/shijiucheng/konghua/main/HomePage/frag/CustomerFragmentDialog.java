package com.shijiucheng.konghua.main.HomePage.frag;

import android.graphics.Color;
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
import android.widget.TextView;

import com.shijiucheng.konghua.R;

import org.xutils.common.util.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CustomerFragmentDialog extends DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.title_divider)
    View title_divider;
    @BindView(R.id.tetit)
    TextView title;
    @BindView(R.id.tetxt)
    TextView msg;
    @BindView(R.id.jiedan_teok)
    TextView confirm;
    @BindView(R.id.jiedan_tequxiao)
    TextView cancel;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.dialog_fragment, container, false);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);

        setdata();
        setviewlisten();

        return view;
    }

    private void setviewlisten() {
        confirm.setOnClickListener(v -> {
            callback.onConfirmCallback(v);
            dismiss();
        });
        cancel.setOnClickListener(v -> dismiss());

    }

    private void setdata() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title_text = bundle.getString("title");
            String msg_text = bundle.getString("msg");
            String confirm_text = bundle.getString("confirm");
            String cancel_text = bundle.getString("cancel");

            if (TextUtils.isEmpty(title_text)) {
                title.setVisibility(View.GONE);
                title_divider.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
                title_divider.setVisibility(View.VISIBLE);
                title.setText(title_text);
            }
            msg.setText(msg_text);
            confirm.setText(confirm_text);
            cancel.setText(cancel_text);

        }
    }

    OnClickCallback callback;

    public interface OnClickCallback {
        void onConfirmCallback(View view);
    }

    public void setOnClickCallback(OnClickCallback callback) {
        this.callback = callback;
    }


    @Override
    public void onStart() {
        super.onStart();
        int width = DensityUtil.getScreenWidth() / 3;
        getDialog().getWindow().setLayout(width * 2, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
