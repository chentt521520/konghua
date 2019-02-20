package com.shijiucheng.konghua.main.order.popwindow_;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.PopWindow_;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * created 2018/8/2 0002 16:20
 * author ldl
 * 拒绝接单与申请退单
 */
public class orderjujue_popwindow {
    Context context;
    View view;
    PopWindow_ popWindow_;

    jujueada ada;
    List<jujuedata> list = new ArrayList<>();

    public orderjujue_popwindow(Context context) {
        this.context = context;
    }

    //type=1拒绝接单2申请退单
    public void jujue(View view1, int type) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_ = dm.widthPixels;
        view = LayoutInflater.from(context).inflate(R.layout.jujue, null);
        popWindow_ = new PopWindow_(context, view, view1, (int) (w_ * 600 / 750.0), ViewGroup.LayoutParams.WRAP_CONTENT, (int) (w_ * 420 / 750.0));
        TextView te_tit = view.findViewById(R.id.jujue_miaos);
        TextView te_ok = view.findViewById(R.id.jujue_jdteok);
        TextView te_miaos = view.findViewById(R.id.jujue_tebc);
        TextView te_quxiao = view.findViewById(R.id.jujue_jdtequxiao);
        ListView listView = view.findViewById(R.id.jujue_yuany1);
        EditText editText = view.findViewById(R.id.jujue_miaos1);
        final LinearLayout lin_jjms = view.findViewById(R.id.jujue_linms);

        list.removeAll(list);
//        if (type == 1) {
//            te_tit.setText("拒绝接单");
//            te_miaos.setVisibility(View.GONE);
//            list.add(new jujuedata("无法备齐货物", "1"));
//            list.add(new jujuedata("不是有效的订单", "0"));
//            list.add(new jujuedata("经销商主动要求", "0"));
//            list.add(new jujuedata("其他原因", "0"));
//        } else {
//            te_tit.setText("申请退单");
//            list.add(new jujuedata("无法备齐货物", "1"));
//            list.add(new jujuedata("不是有效的订单", "0"));
//            list.add(new jujuedata("经销商主动要求", "0"));
//            list.add(new jujuedata("其他原因", "0"));
//        }


        ada = new jujueada(list, context);
        listView.setAdapter(ada);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        list.get(i).setT_("1");
                    } else {
                        list.get(i).setT_("0");
                    }
                }
                ada.notifyDataSetChanged();
                if (list.get(position).getTxt().equals("其他原因")) {
                    lin_jjms.setVisibility(View.VISIBLE);
                } else
                    lin_jjms.setVisibility(View.GONE);
            }
        });

        te_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow_.disssmiss_window();
            }
        });
        te_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paramsDataBean bean = new paramsDataBean();
                bean.setMsg(configParams.orderRefresh);
                HashMap<String, String> maps = new HashMap<>();
                bean.setT(maps);
                EventBus.getDefault().post(bean);
            }
        });

    }
}
