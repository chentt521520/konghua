package com.shijiucheng.konghua.Cmvp.NewsMvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.News_.znxada_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsPrestent implements contact.IPrestent {
    contact.IModle modle;
    contact.IView view;

    public NewsPrestent(contact.IView view) {
        this.view = view;
        modle = new NewsModle();
    }

    @Override
    public void getNews(Context context, String cook, int page, final int type, String is_read) {
        modle.getNews(context,cook, page, type, is_read, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                view.showloading();
            }

            @Override
            public void onNext(BaseResult result) {
                List<znxada_data> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(result.getData());
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray jsoList = jsonObject.getJSONObject("data").getJSONArray("rows");
                        if (jsoList.length() >= 1) {
                            for (int i = 0; i < jsoList.length(); i++) {
                                JSONObject jso = jsoList.getJSONObject(i);
                                if (type == 1)
                                    list.add(new znxada_data(jso.getString("is_read"), "公告信息", jso.getString("add_time_text"), jso.getString("message_title"), jso.getString("m_id"), "0", jso.getString("message_content")));
                                else if (type == 2) {
                                    if (jso.getString("is_read").equals("0")) {
                                        list.add(new znxada_data(jso.getString("is_read"), "公告信息", jso.getString("add_time_text"), jso.getString("message_title"), jso.getString("m_id"), "0", jso.getString("message_content")));
                                    }
                                } else if (type == 3) {
                                    if (jso.getString("is_read").equals("1")) {
                                        list.add(new znxada_data(jso.getString("is_read"), "公告信息", jso.getString("add_time_text"), jso.getString("message_title"), jso.getString("m_id"), "0", jso.getString("message_content")));
                                    }
                                }
                            }
                        }
                    }
                    view.showNews(type, list);
                    view.closeloading();
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
    public void getNotice(Context context,String cook, int page, final int type, String is_read) {
        modle.getNotice(context,cook, page, type, is_read, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                view.showloading();
            }

            @Override
            public void onNext(BaseResult result) {
                List<znxada_data> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(result.getData());
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray jsoList = jsonObject.getJSONObject("data").getJSONArray("rows");
                        if (jsoList.length() >= 1) {
                            for (int i = 0; i < jsoList.length(); i++) {
                                JSONObject jso = jsoList.getJSONObject(i);
                                if (type == 4)
                                    list.add(new znxada_data(jso.getString("notice_type"), "公告信息", jso.getString("notice_pub_time_text"), jso.getString("notice_title"), jso.getString("n_id"), "1", ""));
                                else if (type == 5) {
                                    if (jso.getString("notice_type").equals("0")) {
                                        list.add(new znxada_data(jso.getString("notice_type"), "公告信息", jso.getString("notice_pub_time_text"), jso.getString("notice_title"), jso.getString("n_id"), "1", ""));
                                    }
                                } else if (type == 6) {
                                    if (jso.getString("notice_type").equals("1")) {
                                        list.add(new znxada_data(jso.getString("notice_type"), "公告信息", jso.getString("notice_pub_time_text"), jso.getString("notice_title"), jso.getString("n_id"), "1", ""));
                                    }
                                }
                            }
                        }
                    }
                    view.showNews(type, list);
                    view.closeloading();
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
        view = null;
    }
}
