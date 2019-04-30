package com.shijiucheng.konghua.Cmvp.dianZhuMvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

import org.json.JSONException;
import org.json.JSONObject;

public class DianZuPresentIml implements Contact.IDianZuPresent {
    Contact.IDianZuView iDianZuView;
    Contact.IDianZuModle iDianZuModle;

    public DianZuPresentIml(Contact.IDianZuView iDianZuView) {
        this.iDianZuView = iDianZuView;
        iDianZuModle = new DianZuModleIml();
    }


    @Override
    public void saveData( final String name, final String pho, final String qq, final String idcar, final String pic1, final String pic2) {
        iDianZuModle.saveData(name, pho, qq, idcar, pic1, pic2, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                iDianZuView.showload();
            }

            @Override
            public void onNext(BaseResult result) {

                iDianZuView.closeload();

                iDianZuView.showtoast("保存成功");
                iDianZuView.gotosave();
            }

            @Override
            public void onError(Throwable errorMsg) {

            }
        });
    }

    @Override
    public void uploadPic(Context context,final String key, String cook, final String urlStr) {
        iDianZuModle.uploadPic(context,key, cook, urlStr, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                iDianZuView.showload();
            }

            @Override
            public void onNext(BaseResult result) {
                iDianZuView.closeload();
                try {
                    JSONObject jsonObject = new JSONObject(result.getData());
                    if (result.getCode().equals("1")) {
                        JSONObject jso = jsonObject.getJSONObject("data");
                        iDianZuView.savePicurl(jso.getString("url"), key);
                    } else
                        iDianZuView.showtoast("上传失败，请稍后重试");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable errorMsg) {
                iDianZuView.closeload();
            }
        });
    }

    @Override
    public void onDestroy() {
        iDianZuView = null;
    }
}
