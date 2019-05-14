package com.shijiucheng.konghua.main.HomePage.frag;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.Json_.ordershaixuanview.utils1.utils.MonthDateView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class rili_frag extends android.support.v4.app.DialogFragment {
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

    int type = 0;//表示开始日期
    String date_str = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        view = inflater.inflate(R.layout.rili_view, container, false);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);

        Bundle data = getArguments();
        type = data.getInt("type");
        date_str = data.getString("time");

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

        riliTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
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
                    if (TextUtils.isEmpty(date_str)) {
                        paramsDataBean databean = new paramsDataBean();
                        databean.setMsg(configParams.orderSYsetdate1);
                        Bundle bundle = new Bundle();
                        bundle.putString("time", date);
                        databean.setT(bundle);
                        EventBus.getDefault().post(databean);
                        dismiss();
                    } else {
                        if (isDate2Bigger(date, date_str)) {
                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.orderSYsetdate1);
                            Bundle bundle = new Bundle();
                            bundle.putString("time", date);
                            databean.setT(bundle);
                            EventBus.getDefault().post(databean);
                            dismiss();
                        } else {
                            Toast.makeText(getActivity(), "开始日期大于或等于结束日期,请重新选择", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                } else {
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
                    if (TextUtils.isEmpty(date_str)) {
                        paramsDataBean databean = new paramsDataBean();
                        databean.setMsg(configParams.orderSYsetdate2);
                        Bundle bundle = new Bundle();
                        bundle.putString("time", date);
                        databean.setT(bundle);
                        EventBus.getDefault().post(databean);
                        dismiss();
                    } else {
                        if (!isDate2Bigger(date, date_str)) {
                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.orderSYsetdate2);
                            Bundle bundle = new Bundle();
                            bundle.putString("time", date);
                            databean.setT(bundle);
                            EventBus.getDefault().post(databean);
                            dismiss();
                        } else {
                            Toast.makeText(getActivity(), "结束日期大于或等于开始日期,请重新选择", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
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

    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
