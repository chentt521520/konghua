package com.shijiucheng.konghua.com.shijiucheng.konghua.app;

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

public class internate_if extends DialogFragment {

    View view;
    @BindView(R.id.internate_gx)
    TextView internate_;
    Unbinder unbinder;


    public interface internetagain {
        void internetagain();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        if (view == null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            view = inflater.inflate(R.layout.internate_if, container, false);
        }
        unbinder = ButterKnife.bind(this, view);

        setviewlisten();
        return view;

    }

    public void setviewlisten() {
        internate_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetagain internetagain = (internate_if.internetagain) getActivity();
                internetagain.internetagain();
            }
        });
    }

    @Override
    public void onDestroyView() {
        getActivity().finish();
        super.onDestroyView();
        unbinder.unbind();
    }
}
