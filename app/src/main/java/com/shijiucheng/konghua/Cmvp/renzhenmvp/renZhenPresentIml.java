package com.shijiucheng.konghua.Cmvp.renzhenmvp;

import android.content.Context;

import com.google.gson.Gson;
import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.Json_.Json_Author;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class renZhenPresentIml implements conttract.irezhenPresent {
    conttract.IrezhenView irezhenView;
    conttract.IrenzhenModle irenzhenModle;

    public renZhenPresentIml(conttract.IrezhenView irezhenView) {
        this.irezhenView = irezhenView;
        irenzhenModle = new renZhenModleIml();
    }


    @Override
    public void applayRZ(Context context, String cook, HashMap<String, String> map) {
        irenzhenModle.applayRZ(context,cook, map, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                irezhenView.showload();
            }

            @Override
            public void onNext(BaseResult result) {
                irezhenView.closeload();
                try {
                    JSONObject jsonObject = new JSONObject(result.getData());
                    if (result.getCode().equals("1")) {
                        irezhenView.showtoaste("提交成功,请等待管理员审核");
                        irezhenView.toMainact();
                    } else {
                        if (jsonObject.getString("msg").equals("not_to_do")) {
                            irezhenView.shouwCannotToAuth(jsonObject.getJSONObject("data").getJSONObject("items"));
                        } else
                            irezhenView.showtoaste(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable errorMsg) {
                irezhenView.showtoaste("请完成全部内容填写");
            }
        });
    }

    @Override
    public void getAuthorData(Context context,String cook) {
        irenzhenModle.getAuthorData(context,cook, new BaseCallbackListener<BaseResult>() {
            @Override
            public void onStart() {
                irezhenView.showload();
            }

            @Override
            public void onNext(BaseResult result) {
                irezhenView.closeload();
                try {

                    JSONObject jsonObject = new JSONObject(result.getData());
                    JSONObject jsa = jsonObject.getJSONObject("data").getJSONObject("store_info");
                    Gson gson = new Gson();
                    Json_Author json_author = gson.fromJson(jsa.toString(), Json_Author.class);
                    irezhenView.getAuthorData(new JSONObject(result.getData()), json_author);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable errorMsg) {
                irezhenView.closeload();
                irezhenView.showtoaste(errorMsg.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        irezhenView = null;
    }
}
