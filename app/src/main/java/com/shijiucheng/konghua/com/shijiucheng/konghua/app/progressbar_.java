package com.shijiucheng.konghua.com.shijiucheng.konghua.app;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.shijiucheng.konghua.R;

public class progressbar_ extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.progressbar_, null);
        setCancelable(true);
        return view;
    }


    @Override


    public void onStart() {

        super.onStart();

        Window window = getDialog().getWindow();

        WindowManager.LayoutParams windowParams = window.getAttributes();

        windowParams.dimAmount = 0.0f;

        window.setAttributes(windowParams);

    }


    @Override
    public void show(FragmentManager manager, String tag) {
        //解决反复调用show方法抛出异常
        try {
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (IllegalStateException ignore) {
        }
    }


}
