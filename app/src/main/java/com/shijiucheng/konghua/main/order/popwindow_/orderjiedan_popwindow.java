package com.shijiucheng.konghua.main.order.popwindow_;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.PopWindow_;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * 接单弹窗
 * created 2018/7/25 0025 14:26
 * author ldl
 */

public class orderjiedan_popwindow {
    PopWindow_ popWindow_;
    Context context;
    View view;

    public orderjiedan_popwindow(Context context) {
        this.context = context;
    }

    /**
     * @param view1
     * @param view2
     * @param type  1是结算2是接单
     */
    public void jiedan(final TextView view1, final TextView view2, final int type) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_ = dm.widthPixels;
        view = LayoutInflater.from(context).inflate(R.layout.jiedan, null);
        popWindow_ = new PopWindow_(context, view, view1, (int) (w_ * 500 / 750.0), (int) (w_ * 300 / 750.0), false);
        TextView te_jd = view.findViewById(R.id.order_jdteok);
        TextView te_quxiao = view.findViewById(R.id.order_jdtequxiao);
        TextView te_cont = view.findViewById(R.id.jiedan_texx);

        if (type == 1) {
            te_cont.setText("同一个订单一天只能发起一次结算提醒，确认继续？");
            te_jd.setText("确认提醒");
        }

        te_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow_.disssmiss_window();
            }
        });
        te_jd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 2) {
                    paramsDataBean bean = new paramsDataBean();
                    bean.setMsg(configParams.orderRefresh);
                    HashMap<String, String> maps = new HashMap<>();
                    bean.setT(maps);
                    EventBus.getDefault().post(bean);
                    view1.setText("开始配送");
                    view2.setText("申请退单");
                    popWindow_.disssmiss_window();
                } else {

                }
            }
        });

    }
}
