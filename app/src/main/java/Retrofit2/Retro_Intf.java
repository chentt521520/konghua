package Retrofit2;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface Retro_Intf {
    //@FieldMap用于post    @Query用于get请求
    @POST("/ApiApp-Index-login_do.html")
    @FormUrlEncoded
    Call<ResponseBody> getLogin(@Header("Cookie") String cook, @FieldMap Map<String, String> maps);

    @GET("/ApiApp-Index-index.html")
    Call<ResponseBody> homePage(@Header("Cookie") String cook, @QueryMap Map<String, String> maps);

    @GET("/ApiApp-Index-logout.html")
    Call<ResponseBody> quitLoigin(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //上传图片
    @POST("/ApiApp-Store-upload_image_do.html")
    @FormUrlEncoded
    Call<ResponseBody> uploadPic(@Header("Cookie") String cook, @FieldMap Map<String, String> Map);

    @GET("/ApiApp-Store-get_authentication_info.html")
    Call<ResponseBody> getAuthorData(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    @GET("/ApiApp-Index-get_region_list.html")
    Call<ResponseBody> getAddress(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    @POST("/ApiApp-Store-authentication_do.html")
    @FormUrlEncoded
    Call<ResponseBody> AutherCommit(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //站内信
    @GET("/ApiApp-Message-get_message_station_list.html")
    Call<ResponseBody> getNews(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //公告
    @GET("/ApiApp-Message-get_message_notice_list.html")
    Call<ResponseBody> getNotice(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //公告站内信详情
    @GET("/ApiApp-Message-message_notice_detail.html")
    Call<ResponseBody> getNoticeDetils(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //公告站内信详情
    @GET("/ApiApp-Message-message_station_detail.html")
    Call<ResponseBody> getNeswDetils(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //工单列表
    @GET("/ApiApp-WorkOrder-get_work_order_list.html")
    Call<ResponseBody> getWorkOrderList(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    @GET("/ApiApp-WorkOrder-work_order_detail.html")
    Call<ResponseBody> getWorkOrderDetils(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    @GET("/ApiApp-WorkOrder-close_do.html")
    Call<ResponseBody> closeWorkorder(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //上传工单图片
    @POST("/ApiApp-WorkOrder-work_order_upload_image_do.html")
    @FormUrlEncoded
    Call<ResponseBody> WOuploadimg(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    @Multipart
    @POST("/ApiApp-WorkOrder-work_order_upload_image_do.html")
    Call<ResponseBody> uploadFile(@Header("Cookie") String cook,
                                  @Part() RequestBody file);


    //回复工单
    @POST("/ApiApp-WorkOrder-work_order_reply_do.html")
    @FormUrlEncoded
    Call<ResponseBody> WOupload(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //评价工单
    @POST("/ApiApp-WorkOrder-work_order_evaluate_do.html")
    @FormUrlEncoded
    Call<ResponseBody> WOPinjia(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    @GET("/ApiApp-WorkOrder-work_order_add.html")
    Call<ResponseBody> getWoQuestion(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //提交工单
    @POST("/ApiApp-WorkOrder-work_order_add_do.html")
    @FormUrlEncoded
    Call<ResponseBody> WOuploadwork(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //收支明细
    @GET("/ApiApp-Finance-get_finance_transaction_list.html")
    Call<ResponseBody> getPGList(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //提现
    @GET("/ApiApp-Finance-finance_withdraw.html")
    Call<ResponseBody> getNumTX(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //提现
    @GET("/ApiApp-Finance-get_finance_withdraw_list.html")
    Call<ResponseBody> getListTX(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //提现验证码
    @GET("/ApiApp-Account-send_sms_code.html")
    Call<ResponseBody> getCode(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //银行卡列表
    @GET("/ApiApp-Finance-get_finance_bank_list.html")
    Call<ResponseBody> getBankist(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //编辑和添加银行卡
    @GET("/ApiApp-Finance-finance_add_bank.html")
    Call<ResponseBody> getBankistType(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    @POST("/ApiApp-Finance-finance_del_bank_do.html")
    @FormUrlEncoded
    Call<ResponseBody> bkdetel(@Header("Cookie") String cook, @FieldMap Map<String, String> map);


    @POST("/api_mobile/address.php")
    @FormUrlEncoded
    Call<ResponseBody> getSSQ(@Header("Cookie") String cook, @FieldMap Map<String, String> map);


    @POST("/ApiApp-Finance-finance_add_bank_do.html")
    @FormUrlEncoded
    Call<ResponseBody> AddYhk(@Header("Cookie") String cook, @FieldMap Map<String, String> map);


    @POST("/ApiApp-Finance-finance_set_default_bank_info_do.html")
    @FormUrlEncoded
    Call<ResponseBody> setMoRen(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //申请提现
    @POST("/ApiApp-Finance-finance_withdraw_do.html")
    @FormUrlEncoded
    Call<ResponseBody> shenqingtixian(@Header("Cookie") String cook, @FieldMap Map<String, String> map);


    //设置支付密码

    @POST("/ApiApp-Account-modify_pay_pwd_do.html")
    @FormUrlEncoded
    Call<ResponseBody> setpwd(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //验证支付密码
    @POST("/ApiApp-Account-validate_pay_pwd_do.html")
    @FormUrlEncoded
    Call<ResponseBody> valitpwd(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    ///ApiApp-Order-get_order_list.html  订单列表
    @GET("/ApiApp-Order-get_order_list.html")
    Call<ResponseBody> getOrderNumList(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //  订单详情
    @GET("/ApiApp-Order-get_order_detail.html")
    Call<ResponseBody> getOrderDetail(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //提醒下单方结算
    @POST("/ApiApp-Order-order_balance_remind_do.html")
    @FormUrlEncoded
    Call<ResponseBody> jiesuan(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //接单
    @POST("/ApiApp-Order-order_receive_do.html")
    @FormUrlEncoded
    Call<ResponseBody> jiedan(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //涨价
    @POST("/ApiApp-Order-order_amount_add_do.html")
    @FormUrlEncoded
    Call<ResponseBody> zhangjia(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //  拒绝原因
    @GET("/ApiApp-Order-order_refuse.html")
    Call<ResponseBody> jujueyy(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //拒绝
    @POST("/ApiApp-Order-order_refuse_do.html")
    @FormUrlEncoded
    Call<ResponseBody> jujue(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //上传包装图
    @POST("/ApiApp-Order-upload_store_pack_image_do.html")
    @FormUrlEncoded
    Call<ResponseBody> uploadpeisongtp(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //配送联系人
    @GET("/ApiApp-Order-order_delivery.html")
    Call<ResponseBody> peisonglxr(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //开始配送
    @POST("/ApiApp-Order-order_delivery_do.html")
    @FormUrlEncoded
    Call<ResponseBody> kaishipeisong(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //申请退单页面
    @GET("/ApiApp-Order-order_cancel.html")
    Call<ResponseBody> tuidanym(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //退单
    @POST("/ApiApp-Order-order_cancel_do.html")
    @FormUrlEncoded
    Call<ResponseBody> tuidan(@Header("Cookie") String cook, @FieldMap Map<String, String> map);


    //上传签收图片
    @POST("/ApiApp-Order-upload_order_sign_image_do.html")
    @FormUrlEncoded
    Call<ResponseBody> qianshoupic(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //确认签收
    @POST("/ApiApp-Order-order_sign_do.html")
    @FormUrlEncoded
    Call<ResponseBody> qianshou(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //退单(下单方)
    @POST("/ApiApp-Order-order_agree_cancel_do.html")
    @FormUrlEncoded
    Call<ResponseBody> tuidanxdf(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //设置页面
    @GET("/ApiApp-Account-account_security.html")
    Call<ResponseBody> setinginter(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //修改登录密码
    @POST("/ApiApp-Account-modify_login_pwd_do.html")
    @FormUrlEncoded
    Call<ResponseBody> xiugaimm(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //身份验证短信验证码
    @POST("/ApiApp-Account-send_sms_code.html")
    @FormUrlEncoded
    Call<ResponseBody> sfyz_getcode(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //身份验证短信
    @POST("/ApiApp-Account-validate_sms_code.html")
    @FormUrlEncoded
    Call<ResponseBody> sfyz(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //业务新手机短信验证码
    @POST("/ApiApp-Account-send_service_mobile_code.html")
    @FormUrlEncoded
    Call<ResponseBody> yewu_getcode(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //跟换业务手机
    @POST("/ApiApp-Account-modify_service_mobile_do.html")
    @FormUrlEncoded
    Call<ResponseBody> yewu_pho(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //安全新手机短信验证码
    @POST("/ApiApp-Account-send_safe_mobile_code.html")
    @FormUrlEncoded
    Call<ResponseBody> safe_getcode(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //跟换安全手机
    @POST("/ApiApp-Account-modify_safe_mobile_do.html")
    @FormUrlEncoded
    Call<ResponseBody> safe_pho(@Header("Cookie") String cook, @FieldMap Map<String, String> map);

    //版本更新获取
    @GET("/ApiApp-Public-app_version.html")
    Call<ResponseBody> appverison(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //魅族测试
    @POST("/ups/api/server/push/pushTask/cancel")
    @FormUrlEncoded
    Call<ResponseBody> meizu(@FieldMap Map<String, String> map);

    //  新首页
    @GET("/ApiApp-Index-index.html")
    Call<ResponseBody> getHomePageData(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //站内信
    @GET("/ApiApp-Message-message_station_detail.html")
    Call<ResponseBody> news(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //同意部分结算
    @GET("/ApiApp-Order-order_agree_balance_communicate_do.html")
    Call<ResponseBody> tyjs(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

    //拒绝部分结算
    @GET("/ApiApp-Order-order_refuse_balance_communicate_do.html")
    Call<ResponseBody> jujuejs(@Header("Cookie") String cook, @QueryMap Map<String, String> map);

}