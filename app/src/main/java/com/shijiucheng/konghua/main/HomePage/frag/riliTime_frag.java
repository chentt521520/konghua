package com.shijiucheng.konghua.main.HomePage.frag;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.Json_.ordershaixuanview.utils1.utils.MonthDateView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class riliTime_frag extends DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.rili_teok)
    TextView riliTeok;
    @BindView(R.id.rili_retop)
    RelativeLayout riliRetop;
    @BindView(R.id.rili_date)
    MonthDateView riliDate;
    Unbinder unbinder;
    @BindView(R.id.rili_teddbh)
    TextView riliTeddbh;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.date_text)
    TextView dateText;

    String date_str = "";
    @BindView(R.id.date_operator_ll)
    LinearLayout dateOperatorLl;
    @BindView(R.id.qianshou_edxiaos)
    EditText qianshouEdxiaos;
    @BindView(R.id.qianshou_edfen)
    EditText qianshouEdfen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        view = inflater.inflate(R.layout.rilitime_frag, container, false);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);


        setdata();
        setviewlisten();
        return view;
    }

    private void setviewlisten() {
        ivLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                riliDate.onLeftClick();
            }
        });

        ivRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                riliDate.onRightClick();
            }
        });
        qianshouEdxiaos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(qianshouEdxiaos.getText().toString())) {
                    double txs = Double.valueOf(qianshouEdxiaos.getText().toString()).doubleValue();
                    if (txs >= 24) {
                        qianshouEdxiaos.setText("00");
                        Editable etext = qianshouEdxiaos.getText();
                        Selection.setSelection(etext, etext.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        qianshouEdfen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(qianshouEdfen.getText().toString())) {
                    double txs = Double.valueOf(qianshouEdfen.getText().toString()).doubleValue();
                    if (txs >= 60) {
                        qianshouEdfen.setText("00");
                        Editable etext = qianshouEdfen.getText();
                        Selection.setSelection(etext, etext.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        riliTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mon = (riliDate.getmSelMonth() + 1) + "";
                String day = riliDate.getmSelDay() + "";
                String year = riliDate.getmSelYear() + "";
                if (mon.length() == 1) {
                    mon = "0" + mon;
                }
                if (day.length() == 1) {
                    day = "0" + day;
                }
                String date = year + "-" + mon + "-" + day;

                if (TextUtils.isEmpty(qianshouEdxiaos.getText().toString()) || TextUtils.isEmpty(qianshouEdfen.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入签收时间小时和分", Toast.LENGTH_SHORT).show();
                } else {
                    setdatetime setdatetime = (riliTime_frag.setdatetime) getActivity();
                    setdatetime.setdatetime(date + " " + qianshouEdxiaos.getText().toString() + ":" + qianshouEdfen.getText().toString());
                    dismiss();
                }
            }
        });
    }

    private void setdata() {

        List<Integer> list = new ArrayList<>();
        list.add(20160810);
        list.add(20160812);
        list.add(20160815);
        list.add(20160816);
        riliDate.setTextView(dateText);
        riliDate.setDaysHasThingList(list);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        int minute = calendar.get(Calendar.MINUTE);
        qianshouEdxiaos.setText(hour + "");
        qianshouEdfen.setText(minute + "");

    }

    public interface setdatetime {
        void setdatetime(String time);
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
