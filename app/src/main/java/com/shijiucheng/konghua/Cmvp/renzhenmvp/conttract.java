package com.shijiucheng.konghua.Cmvp.renzhenmvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.Json_.Json_Author;

import org.json.JSONObject;

import java.util.HashMap;

public class conttract {
    //v
    public interface IrezhenView {
        void showload();

        void closeload();

        void showtoaste(String msg);

        void toMainact();

        void getAuthorData(JSONObject jsonObject, Json_Author json_author);

        void shouwCannotToAuth(JSONObject jsonObject);
    }

    //M
    public interface IrenzhenModle {
        void applayRZ(Context context, String cook, HashMap<String, String> map, BaseCallbackListener<BaseResult> resultBaseCallbackListener);

        void getAuthorData(Context context,String cook, BaseCallbackListener<BaseResult> callbackListener);
    }

    //P
    public interface irezhenPresent extends BasePresenter {
        void applayRZ(Context context,String cook, HashMap<String, String> map);

        void getAuthorData(Context context,String cook);
    }

}
