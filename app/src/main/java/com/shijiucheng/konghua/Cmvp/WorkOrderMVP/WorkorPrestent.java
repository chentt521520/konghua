package com.shijiucheng.konghua.Cmvp.WorkOrderMVP;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada.gdlistdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WorkorPrestent implements WorkorContact.Prestent {
    WorkorContact.Modle modle;
    WorkorContact.View view;

    public WorkorPrestent(WorkorContact.View view) {
        this.view = view;
        modle = new WorkorModle();
    }

    @Override
    public void getList(Context context, String keyword, String cook, final int type, int page) {
        modle.getList(context,keyword, cook, type, page, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                view.showloading();
            }

            @Override
            public void onNext(BaseResult result) {
                try {
                    JSONObject jso = new JSONObject(result.getData());
                    view.closeload();
                    if (jso.getString("status").equals("1")) {
                        JSONArray jsa = jso.getJSONObject("data").getJSONArray("rows");
                        List<gdlistdata> list = new ArrayList<>();
                        if (jsa.length() >= 1) {
                            for (int i = 0; i < jsa.length(); i++) {
                                JSONObject jsoList = jsa.getJSONObject(i);
                                list.add(new gdlistdata(jsoList.getString("id"), jsoList.getString("add_time_text"), jsoList.getString("work_order_content"), jsoList.getString("work_order_status_text"), jsoList.getString("cate1_text") + ">" + jsoList.getString("cate2_text"), jsoList.getString("work_order_status")));
                            }
                        }
                        view.showList(type, list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable errorMsg) {
                view.showMsg(errorMsg.toString());
            }
        });
    }

    @Override
    public void onDestroy() {
        if (view != null)
            view = null;
    }
}
