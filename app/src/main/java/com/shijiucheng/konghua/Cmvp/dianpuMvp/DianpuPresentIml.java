package com.shijiucheng.konghua.Cmvp.dianpuMvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;

import org.json.JSONException;
import org.json.JSONObject;

public class DianpuPresentIml implements Contact.IdianPuPresent {
    Contact.IdianPuView idianPuView;
    Contact.IdianPuModle idianPuModle;

    public DianpuPresentIml(Contact.IdianPuView idianPuView) {
        this.idianPuView = idianPuView;
        idianPuModle = new DianpuModleIml();
    }


    @Override
    public void uploadPic(Context context,final int pos, String cook, final String filestr) {
        idianPuModle.uploadPic(context,pos,cook, filestr, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                idianPuView.shouload();
            }

            @Override
            public void onNext(BaseResult result) {
                idianPuView.closeload();
                try {
                    JSONObject jsonObject = new JSONObject(result.getData());
                    if (result.getCode().equals("1")) {
                        JSONObject jso = jsonObject.getJSONObject("data");
                        idianPuView.showPic(pos,jso.getString("url"));
                    } else
                        idianPuView.showToast("上传失败，请稍后重试");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable errorMsg) {
                idianPuView.closeload();
            }
        });
    }


    @Override
    public void onDestroy() {
        idianPuView = null;
    }
}
