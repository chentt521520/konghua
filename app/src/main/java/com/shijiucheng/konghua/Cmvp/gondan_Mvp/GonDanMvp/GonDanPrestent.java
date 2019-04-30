package com.shijiucheng.konghua.Cmvp.gondan_Mvp.GonDanMvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.per.payandget.gongdan.mygondandatabean;
import com.shijiucheng.konghua.main.per.payandget.gongdan.mygondandatabean1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GonDanPrestent implements Contact.GonDanIPrestent {
    Contact.GonDanIModle iModle;
    Contact.GonDanIView iView;

    public GonDanPrestent(Contact.GonDanIView iView) {
        this.iView = iView;
        iModle = new GonDanModleIml();
    }


    @Override
    public void applayMsg(Context context, String cook, String cate1, String cate2, String work_order_images, String work_order_content) {
        iModle.applayMsg(context,cook, cate1, cate2, work_order_images, work_order_content, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                iView.shouwload();
            }

            @Override
            public void onNext(BaseResult result) {

                try {
                    JSONObject jsonObject = new JSONObject(result.getData());
                    if (jsonObject.getString("status").equals("1")) {
                        iView.closeload_();
                        iView.showMsg("问题提交成功");
                        iView.finishApplay();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable errorMsg) {
                iView.closeload_();
                iView.showMsg(errorMsg.getMessage());
            }
        });
    }

    @Override
    public void getQustion(Context context,String cook) {
        iModle.getQustion(context,cook, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                iView.shouwload();
            }

            @Override
            public void onNext(BaseResult result) {
                try {
                    JSONObject jsonObject = new JSONObject(result.getData());
                    List<mygondandatabean1> list1 = new ArrayList<>();
                    List<mygondandatabean> list = new ArrayList<>();
                    if (jsonObject.getString("status").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("work_order_category1_list");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jso = jsonArray.getJSONObject(i);
                            list.add(new mygondandatabean(jso.getString("key"), jso.getString("text")));
                            JSONArray jsonArray1 = jso.getJSONArray("category2");
                            List<mygondandatabean> list2 = new ArrayList<>();
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                list2.add(new mygondandatabean(jsonObject1.getString("key"), jsonObject1.getString("text")));
                            }
                            list1.add(new mygondandatabean1(list2));
                        }
                        iView.showQustion(list, list1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                iView.closeload_();
            }

            @Override
            public void onError(Throwable errorMsg) {

                iView.showMsg(errorMsg.getMessage());
                iView.closeload_();

            }
        });
    }

    @Override
    public void upLoadimg(Context context,String cook, String urlEnc) {
        iModle.upLoadimg(context,cook, urlEnc, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                iView.shouwload();
            }

            @Override
            public void onNext(BaseResult result) {
                iView.closeload_();
                try {
                    JSONObject jso = new JSONObject(result.getData());
                    if (jso.getString("status").equals("1")) {
                        iView.showUrl(jso.getJSONObject("data").getString("url"));
                    } else
                        iView.showMsg("上传失败");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable errorMsg) {
                iView.showMsg(errorMsg.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        iView = null;
    }
}
