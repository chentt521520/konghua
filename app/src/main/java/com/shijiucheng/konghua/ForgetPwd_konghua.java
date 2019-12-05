package com.shijiucheng.konghua;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua_;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.main.Utils.StringUtils;
import com.shijiucheng.konghua.main.adapter.ShopListAdapter;
import com.shijiucheng.konghua.main.entity.OwnerShop;
import com.shijiucheng.konghua.main.widget.NoticeDialog;
import com.shijiucheng.konghua.main.widget.MyGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPwd_konghua extends BaseActivity_konghua_ {

    //导航栏
    @BindView(R.id.dianzu_dh)
    DaoHang_top dh;
    //banner
    @BindView(R.id.ui_forget_banner)
    ImageView banner;
    //店主电话
    @BindView(R.id.ui_shop_phone)
    EditText shopPhone;
    //验证码
    @BindView(R.id.ui_verify_code)
    EditText verifyCode;
    //获取验证码
    @BindView(R.id.ui_gain_verify_code)
    TextView gainVerifyCode;
    //默认账号View
    @BindView(R.id.ui_default_account_view)
    LinearLayout defaultAccountView;
    //重置账号
    @BindView(R.id.ui_list_view)
    MyGridView list_view;
    //新密码
    @BindView(R.id.ui_new_password)
    EditText newPassword;
    //提交按钮
    @BindView(R.id.ui_commit_btn)
    TextView commitBtn;
    //店铺数据源
    private List<OwnerShop> list;
    //店铺列表适配器
    private ShopListAdapter adapter;
    //倒计时
    private int count = 60;
    //修改成功提示
    private NoticeDialog dialog;
    private boolean isStart = true;

    @Override
    protected void AddView() {
        dh.settext_(getResources().getString(R.string.find_password));
        adapter = new ShopListAdapter(ForgetPwd_konghua.this, list);
        list_view.setAdapter(adapter);
    }

    private void toast(String text) {
        toaste_ut(ForgetPwd_konghua.this, text);
    }

    @Override
    protected void SetViewListen() {
        //获取验证码
        gainVerifyCode.setOnClickListener(v -> {
            String phone = shopPhone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                toast(getResources().getString(R.string.please_input_phone_number));
                return;
            } else if (!StringUtils.isPhoneNumber(phone)) {
                toast(getResources().getString(R.string.phone_number_is_error));
                return;
            }
            gainVerifyCode.setTag("1");
            getShopList(phone);
        });
        commitBtn.setOnClickListener(v -> {
            String phone = shopPhone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                toast(getResources().getString(R.string.please_input_phone_number));
                return;
            } else if (!StringUtils.isPhoneNumber(phone)) {
                toast(getResources().getString(R.string.phone_number_is_error));
                return;
            }
            String code = verifyCode.getText().toString();
            if (TextUtils.isEmpty(code)) {
                toast(getResources().getString(R.string.please_input_verify_code));
                return;
            } else if (gainVerifyCode.getTag() == null) {
                toast(getResources().getString(R.string.please_gain_verify_code));
                return;
            }

            String shopId = adapter.getShopId();
            if (TextUtils.isEmpty(shopId)) {
                toast(getResources().getString(R.string.please_select_store));
                return;
            }

            String newPwd = newPassword.getText().toString();
            if (TextUtils.isEmpty(newPwd)) {
                toast(getResources().getString(R.string.please_input_password));
                return;
            } else if (!StringUtils.isLetterDigit(newPwd)) {
                toast(getResources().getString(R.string.password_must_contain_letter_digital));
                return;
            }
            modifyPwd(phone, code, shopId, newPwd);
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forget_pwd;
    }


    private void getShopList(String ownerPhone) {
        Retro_Intf retro_intf = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("store_master_tel", ownerPhone);
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(ForgetPwd_konghua.this));
        String cookie = retrofit_Single.getInstence().getOpenid(ForgetPwd_konghua.this);
        Call<ResponseBody> call = retro_intf.forget_pwd_sms_code(cookie, maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String Result = response.body().string();
//            String Result = "{\"status\":1,\"msg\":\"\",\"data\":{\"store_list\":[{\"store_id\":\"1237\",\"store_name\":\"\\u5a1f\\u8776\\u9c9c\\u82b1\\u5e97\",\"store_account\":\"jua***ie\",\"province_id_text\":\"\",\"city_id_text\":\"\",\"district_id_text\":\"\",\"add_time_text\":\"1970-01-01 08:00\",\"add_authentication_time_text\":\"1970-01-01 08:00\",\"store_contact_qq\":null,\"store_contact_tel\":null,\"delivery_district_ids_text\":\"\"},{\"store_id\":\"1279\",\"store_name\":\"\\u9c9c\\u82b1\\u5a1f\\u87764\",\"store_account\":\"jua***e4\",\"province_id_text\":\"\",\"city_id_text\":\"\",\"district_id_text\":\"\",\"add_time_text\":\"1970-01-01 08:00\",\"add_authentication_time_text\":\"1970-01-01 08:00\",\"store_contact_qq\":null,\"store_contact_tel\":null,\"delivery_district_ids_text\":\"\"},{\"store_id\":\"1280\",\"store_name\":\"\\u5a1f\\u87765\",\"store_account\":\"jua***e5\",\"province_id_text\":\"\",\"city_id_text\":\"\",\"district_id_text\":\"\",\"add_time_text\":\"1970-01-01 08:00\",\"add_authentication_time_text\":\"1970-01-01 08:00\",\"store_contact_qq\":null,\"store_contact_tel\":null,\"delivery_district_ids_text\":\"\"},{\"store_id\":\"1281\",\"store_name\":\"\\u5a1f\\u87766\",\"store_account\":\"jua***e6\",\"province_id_text\":\"\",\"city_id_text\":\"\",\"district_id_text\":\"\",\"add_time_text\":\"1970-01-01 08:00\",\"add_authentication_time_text\":\"1970-01-01 08:00\",\"store_contact_qq\":null,\"store_contact_tel\":null,\"delivery_district_ids_text\":\"\"},{\"store_id\":\"1287\",\"store_name\":\"\\u592a\\u5b50\\u82b1\\u5e97\",\"store_account\":\"183****4852\",\"province_id_text\":\"\",\"city_id_text\":\"\",\"district_id_text\":\"\",\"add_time_text\":\"1970-01-01 08:00\",\"add_authentication_time_text\":\"1970-01-01 08:00\",\"store_contact_qq\":null,\"store_contact_tel\":null,\"delivery_district_ids_text\":\"\"},{\"store_id\":\"1289\",\"store_name\":\"\\u592a\\u5b50\\u82b1\\u5e972\",\"store_account\":\"tai***i2\",\"province_id_text\":\"\",\"city_id_text\":\"\",\"district_id_text\":\"\",\"add_time_text\":\"1970-01-01 08:00\",\"add_authentication_time_text\":\"1970-01-01 08:00\",\"store_contact_qq\":null,\"store_contact_tel\":null,\"delivery_district_ids_text\":\"\"},{\"store_id\":\"1291\",\"store_name\":\"\\u54c0\\u4f24\\u9c9c\\u82b1\\u5e97\",\"store_account\":\"133***@qq.com\",\"province_id_text\":\"\",\"city_id_text\":\"\",\"district_id_text\":\"\",\"add_time_text\":\"1970-01-01 08:00\",\"add_authentication_time_text\":\"1970-01-01 08:00\",\"store_contact_qq\":null,\"store_contact_tel\":null,\"delivery_district_ids_text\":\"\"},{\"store_id\":\"1292\",\"store_name\":\"\\u5395\\u6240\\u6821\\u82b1\\u7684\",\"store_account\":\"133***73\",\"province_id_text\":\"\",\"city_id_text\":\"\",\"district_id_text\":\"\",\"add_time_text\":\"1970-01-01 08:00\",\"add_authentication_time_text\":\"1970-01-01 08:00\",\"store_contact_qq\":null,\"store_contact_tel\":null,\"delivery_district_ids_text\":\"\"},{\"store_id\":\"1293\",\"store_name\":\"\\u54ce\\u5440\",\"store_account\":\"aiy***ya\",\"province_id_text\":\"\",\"city_id_text\":\"\",\"district_id_text\":\"\",\"add_time_text\":\"1970-01-01 08:00\",\"add_authentication_time_text\":\"1970-01-01 08:00\",\"store_contact_qq\":null,\"store_contact_tel\":null,\"delivery_district_ids_text\":\"\"}]},\"PHPSESSID\":\"b671ce6d1e2f70820c7340391c1f32a2\"}";
                    JSONObject jsonObject = new JSONObject(Result);
                    BaseResult result = new BaseResult();
                    result.setCode(jsonObject.getString("status"));
                    result.setData(jsonObject.getString("data"));
                    result.setMsg(jsonObject.getString("msg"));

                    if (TextUtils.equals(result.getCode(), "1")) {
                        JSONObject data = new JSONObject(result.getData());

                        Gson gson = new Gson();
                        list = gson.fromJson(data.getString("store_list"), new TypeToken<List<OwnerShop>>() {
                        }.getType());

                        if (StringUtils.listIsEmpty(list)) {
                            defaultAccountView.setVisibility(View.GONE);
                        } else {
                            defaultAccountView.setVisibility(View.VISIBLE);
                        }
                        adapter.refresh(list);

                        count();//倒计时

                    } else {
                        toast(TextUtils.isEmpty(result.getMsg()) ? "获取失败" : result.getMsg());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }


    /**
     * 提交新密码
     *
     * @param ownerPhone string	是	店主手机
     * @param verifyCode string	是	短信验证码
     * @param shopId     string	是	店铺id
     * @param password   string	是	设置的新密码
     */
    private void modifyPwd(String ownerPhone, String verifyCode, String shopId, String password) {
        Retro_Intf retro_intf = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.put("store_master_tel", ownerPhone);
        maps.put("sms_code", verifyCode);
        maps.put("store_id", shopId);
        maps.put("store_pwd", password);
        maps.putAll(retrofit_Single.getInstence().retro_postParameter(ForgetPwd_konghua.this));
        String cookie = retrofit_Single.getInstence().getOpenid(ForgetPwd_konghua.this);
        Call<ResponseBody> call = retro_intf.forget_pwd_commit(cookie, maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String Result = response.body().string();
                    JSONObject jsonObject = new JSONObject(Result);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");
                    if (TextUtils.equals(status, "1")) {
                        showDialog();
                    } else {
                        toast(TextUtils.isEmpty(msg) ? "获取失败" : msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new NoticeDialog.Builder(ForgetPwd_konghua.this)
                    .setDialogOnClick(() -> {
                        dialog.dismiss();
                        finish();
                    })
                    .setMsg("密码重置成功")
                    .setBtn("去登录")
                    .create();
        }
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }


    private void count() {
        new Thread(() -> {
            for (int i = 60; i >= 0; i--) {
                if (isStart) {
                    try {
                        Thread.sleep(1000);
                        count = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (gainVerifyCode != null) {
                                    if (count == 0) {
                                        gainVerifyCode.setText("获取验证码");
                                        gainVerifyCode.setEnabled(true);
                                    } else {
                                        gainVerifyCode.setText(count - 1 + "");
                                        gainVerifyCode.setEnabled(false);
                                    }
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }
            }
        }).start();
    }

    @Override
    public void onPause() {
        super.onPause();
        isStart = false;
    }
}
