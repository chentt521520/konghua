package com.shijiucheng.konghua.renzheng.data;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

public class SSQDialogFra extends DialogFragment {
    NumberPicker qnp1, qnp2, qnp3;
    TextView te_dzok, te_dzqx;
    int position_ssq = 0;//0表示省1表示市2表示区
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        view = inflater.inflate(R.layout.dizhi_popwindow, container, false);
        set_npView();

        return view;
    }
    private void set_npView() {
        qnp1 = view.findViewById(R.id.adddzpic1);
        qnp2 = view.findViewById(R.id.adddzpic2);
        qnp3 = view.findViewById(R.id.adddzpic3);
        te_dzok = view.findViewById(R.id.adddz_teok);
        te_dzqx = view.findViewById(R.id.adddz_tequxiao);

        String[] data = {"11", "22", "33", "11", "22", "33", "11", "22", "33"};
        qnp1.setDisplayedValues(data);
        setNumberPickerDividerColor(qnp1);
        qnp1.setMinValue(0);
        qnp1.setMaxValue(data.length - 1);
        qnp1.setValue(0);

        te_dzqx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        te_dzok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position_ssq == 0)
                    position_ssq = 1;
                else if (position_ssq == 1)
                    position_ssq = 2;
                else if (position_ssq == 2)
                    position_ssq = 3;
                String[] data1 = {"x11", "x22", "x33", "11", "22", "33", "11", "22", "33"};
                set_npData(position_ssq, data1);
            }
        });
    }

    private void set_npData(int pos, String[] data) {
        switch (pos) {
            case 1:
                qnp2.setDisplayedValues(data);
                setNumberPickerDividerColor(qnp2);
                qnp2.setMinValue(0);
                qnp2.setMaxValue(data.length - 1);
                qnp2.setValue(0);
                qnp1.setVisibility(View.GONE);
                qnp2.setVisibility(View.VISIBLE);

                break;
            case 2:

                break;
            case 3:

                break;
            default:
                break;
        }

    }

    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        java.lang.reflect.Field[] pickerFields = NumberPicker.class
                .getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    // 设置分割线的颜色值 透明
                    pf.set(picker,
                            new ColorDrawable(Color.parseColor("#0894ec")));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        getDialog().getWindow().setLayout(w, ViewGroup.LayoutParams.WRAP_CONTENT);

    }
}
