package com.shijiucheng.konghua.main.order.popwindow_;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.PopWindow_;
import com.shijiucheng.konghua.main.Json_.ordershaixuanview.utils1.utils.MonthDateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created 2018/8/2 0002 17:31
 * author ldl
 * q签收弹窗
 */
public class queRenQianShou_popwindow {
    Context context;
    View view;
    PopWindow_ popWindow_;
    String textInfo = "";

    Unbinder unbinder;
    @BindView(R.id.qianshou_imclose)
    ImageView im_close;
    @BindView(R.id.checkBox1)
    CheckBox checkBox1;
    @BindView(R.id.checkBox2)
    CheckBox checkBox2;

    @BindView(R.id.qianshou_terqq)
    TextView te_date;

    @BindView(R.id.qianshou_linrq)
    LinearLayout lin_date;
    MonthDateView monthdataview;

    public queRenQianShou_popwindow(Context context) {
        this.context = context;
    }

    public void show_pop(View view1) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_ = dm.widthPixels;
        view = LayoutInflater.from(context).inflate(R.layout.querenqianshou, null);
        unbinder = ButterKnife.bind(this, view);

        popWindow_ = new PopWindow_(context, view, view1, (int) (w_ * 600 / 750.0), ViewGroup.LayoutParams.WRAP_CONTENT, (int) (w_ * 420 / 750.0));


        ImageView iv_left = view.findViewById(R.id.iv_left);
        ImageView iv_right = view.findViewById(R.id.iv_right);
        TextView tv_date = view.findViewById(R.id.date_text);
        monthdataview = view.findViewById(R.id.rili_date);

        List<Integer> list = new ArrayList<Integer>();
        list.add(20160810);
        list.add(20160812);
        list.add(20160815);
        list.add(20160816);
        monthdataview.setTextView(tv_date);
        monthdataview.setDaysHasThingList(list);
        iv_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthdataview.onLeftClick();
            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthdataview.onRightClick();
            }
        });

        te_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lin_date.isShown()) {
                    lin_date.setVisibility(View.GONE);
                } else
                    lin_date.setVisibility(View.VISIBLE);
            }
        });

        im_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                unbinder.unbind();
                popWindow_.disssmiss_window();
            }
        });

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox1.isChecked())
                    checkBox1.setChecked(false);
                else checkBox1.setChecked(true);
                checkBox2.setChecked(false);
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox2.isChecked())
                    checkBox2.setChecked(false);
                else checkBox2.setChecked(true);
                checkBox1.setChecked(false);
            }
        });



    }

}
