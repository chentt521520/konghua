package com.shijiucheng.konghua.Cmvp.registermvp;

import android.content.Context;

import com.shijiucheng.konghua.Cmvp.BaseCallbackListener;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.main.entity.CityInfo;

import java.util.List;

public class RegisterContact {
    //M
    public interface IRegisterModel {
        /**
         * store_name	string	是	花店名
         * store_account	string	是	登录账号
         * store_pwd	string	是	登录密码
         * address	string	是	街道地址
         * store_master_uname	string	是	店主姓名
         * store_master_tel	string	是	店主手机
         * store_master_qq	string	是	店主QQ
         * join_code	string	是	邀请码（后台管理员给予）
         * province_id	string	是	省份id
         * city_id	string	是	城市id
         * district_id	string	是	区域id
         * delivery_district_ids
         */
        void register(Context context, String shopName, String loginAccount, String loginPassword, String ownerName, String ownerPhone, String ownerQQ, String inviteCode, String provinceId, String cityId, String districtId, String address, String deliveryIds, BaseCallbackListener<BaseResult> callbackListener);

        /**
         * @param parent_id 上节区域id，获取一级区域就传0
         */
        void getDistrict(Context context, String cook, int pos, String parent_id, BaseCallbackListener<BaseResult> callbackListener);
    }

    //V
    public interface IRegisterView {
        void showLoading();

        void closeLoading();

        void complete(String json);

        void toast(String msg);

        void getAddress(int pos, List<CityInfo> cityList);
    }

    //P
    public interface IRegisterPresent extends BasePresenter {
        void startRegister(Context context, String shopName, String loginAccount, String loginPassword, String ownerName, String ownerPhone, String ownerQQ, String inviteCode, String provinceId, String cityId, String districtId, String address, String deliveryIds);

        void getDistrict(Context context, String cook, int pos, String parent_id);
    }

}
