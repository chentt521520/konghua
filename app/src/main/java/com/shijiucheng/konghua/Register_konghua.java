package com.shijiucheng.konghua;

import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.registermvp.RegisterContact;
import com.shijiucheng.konghua.Cmvp.registermvp.RegisterPresenter;
import com.shijiucheng.konghua.main.widget.CityPickerView;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.main.widget.MyGridView;
import com.shijiucheng.konghua.main.Utils.StringUtils;
import com.shijiucheng.konghua.main.adapter.GridCityAdapter;
import com.shijiucheng.konghua.main.entity.CityInfo;
import com.shijiucheng.konghua.main.widget.NoticeDialog;
import com.shijiucheng.konghua.main.widget.DialogOnClick;

import java.util.List;

import Retrofit2.retrofit_Single;
import butterknife.BindView;

public class Register_konghua extends com.shijiucheng.konghua.Cmvp.BaseActivity_konghua implements RegisterContact.IRegisterView {

    @BindView(R.id.dianzu_dh)
    DaoHang_top dh;
    @BindView(R.id.ui_register_shop_name)
    EditText shopNameEd;
    @BindView(R.id.ui_register_login_account)
    EditText loginAccountEd;
    @BindView(R.id.ui_register_login_password)
    EditText loginPasswordEd;
    @BindView(R.id.ui_register_confirm_password)
    EditText confirmPasswordEd;
    @BindView(R.id.ui_register_shop_owner_name)
    EditText ownerNameEd;
    @BindView(R.id.ui_register_shop_owner_phone)
    EditText ownerPhoneEd;
    @BindView(R.id.ui_register_shop_owner_qq)
    EditText ownerQQEd;
    @BindView(R.id.ui_register_invite_code)
    EditText inviteCodeEd;
    @BindView(R.id.ui_register_district)
    TextView districtTv;
    @BindView(R.id.ui_register_address)
    EditText addressEd;
    @BindView(R.id.ui_register_send_district_view)
    MyGridView sendDistrictView;
    @BindView(R.id.ui_register_btn)
    TextView btnRegister;
    private List<CityInfo> cityList;//省市区
    private List<CityInfo> districtList;//省市区
    private String provonceId, cityId, districtId;

    private RegisterContact.IRegisterPresent present = new RegisterPresenter(this);
    private CityPickerView pickerView;
    private GridCityAdapter adapter;
    private NoticeDialog dialog;

    @Override
    protected void AddView() {
        dh.settext_("注册");
        adapter = new GridCityAdapter(this, districtList);
        sendDistrictView.setAdapter(adapter);
    }

    @Override
    protected void SetViewListen() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        districtTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fastClick()) {
                    showInput(inviteCodeEd, false);
                    getAddress(0, "0");
                }

            }
        });
    }

    public void showInput(EditText et, boolean flag) {
        InputMethodManager im = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
        if (flag) {
            im.showSoftInput(et, 0);
        } else {
            //上下两种都可以 im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            im.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }

    private void createCityDialog() {

        pickerView = new CityPickerView.Builder(Register_konghua.this)
                .setList(cityList)
                .setListener(new CityPickerView.OnCitySelectListener() {
                    @Override
                    public void onConfirmListener(int pos, String id, String addressStr) {
                        if (pos == 1) {
                            provonceId = id;
                            getAddress(1, id);
                        } else if (pos == 2) {
                            cityId = id;
                            getAddress(2, id);
                        } else if (pos == 3) {
                            districtId = id;
                            districtTv.setText(addressStr);
                        }
                    }
                })
                .build();

        pickerView.showPopWin(Register_konghua.this);
    }


    private void getAddress(int pos, String id) {
        present.getDistrict(Register_konghua.this, retrofit_Single.getInstence().getOpenid(Register_konghua.this), pos, id);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected BasePresenter bindPresent() {
        return null;
    }

    private void register() {
        String shopName = shopNameEd.getText().toString();
        if (TextUtils.isEmpty(shopName)) {
            toast("店铺名称不能为空");
            return;
        }
        String account = loginAccountEd.getText().toString();
        if (TextUtils.isEmpty(account)) {
            toast("登录账号不能为空");
            return;
        }
//        else {
//            if (account.contains("@")) {//邮箱
//                if (!StringUtils.isEmail(account)) {
//                    toast("邮箱格式有误");
//                    return;
//                }
//            } else {//手机号
//                if (!StringUtils.isPhoneNumber(account)) {
//                    toast("手机号格式有误");
//                    return;
//                }
//            }
//        }
        String pwd = loginPasswordEd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            toast("密码不能为空");
            return;
        } else {
            if (!verifyPwd(pwd)) {
                return;
            }
        }

        String pwd2 = confirmPasswordEd.getText().toString();
        if (TextUtils.isEmpty(pwd2)) {
            toast("密码不能为空");
            return;
        } else if (!TextUtils.equals(pwd, pwd2)) {
            toast("两次密码不一致");
            return;
        }
        String ownerName = ownerNameEd.getText().toString();
        if (TextUtils.isEmpty(ownerName)) {
            toast("店主姓名不能为空");
            return;
        }
        String ownerPhone = ownerPhoneEd.getText().toString();
        if (TextUtils.isEmpty(ownerPhone)) {
            toast("手机号不能为空");
            return;
        } else if (!StringUtils.isPhoneNumber(ownerPhone)) {
            toast("手机号格式有误");
            return;
        }
        String ownerQQ = ownerQQEd.getText().toString();
        if (TextUtils.isEmpty(ownerQQ)) {
            toast("QQ号不能为空");
            return;
        } else if (!StringUtils.isQQ(ownerQQ)) {
            toast("QQ号格式不正确");
        }
        String inviteCode = inviteCodeEd.getText().toString();
        if (TextUtils.isEmpty(inviteCode)) {
            toast("邀请码不能为空");
            return;
        }
        String district = districtTv.getText().toString();
        if (TextUtils.isEmpty(district)) {
            toast("请选择所在区域");
            return;
        }
        if (TextUtils.isEmpty(provonceId) || TextUtils.isEmpty(cityId) || TextUtils.isEmpty(districtId)) {
            toast("请选择所在区域");
            return;
        }
        String address = addressEd.getText().toString();
        if (TextUtils.isEmpty(address)) {
            toast("详细地址不能为空");
            return;
        }
        if (TextUtils.isEmpty(getIds())) {
            toast("请选择配送区域");
            return;
        }

        present.startRegister(Register_konghua.this, shopName, account, pwd, ownerName, ownerPhone, ownerQQ, inviteCode, provonceId, cityId, districtId, address, getIds());
    }

    private String getIds() {
        StringBuilder deliveryIds = new StringBuilder();
        for (CityInfo info : districtList) {
            if (info.isCheck()) {
                deliveryIds.append(info.getCityId()).append(",");
            }
        }
        if (TextUtils.isEmpty(deliveryIds)) {
            return "";
        } else {
            return deliveryIds.substring(0, deliveryIds.length() - 1);
        }
    }

    private boolean verifyPwd(String pwd) {
        if (pwd.length() < 6 || pwd.length() > 20) {
            toast("密码长度应在6-20位之间");
            return false;
        } else if (!StringUtils.isLetterDigit(pwd)) {
            toast("密码应由字母和数字组合而成");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void complete(String json) {
        dialog = new NoticeDialog.Builder(Register_konghua.this)
                .setDialogOnClick(new DialogOnClick() {
                    @Override
                    public void onConfirm() {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setMsg("注册成功！\n 您可以去登录啦")
                .setBtn("去登录")
                .create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void getAddress(int pos, List<CityInfo> cityList) {
        this.cityList = cityList;
        if (pos == 0) {
            createCityDialog();
        } else {
            if (pos == 2) {
                this.districtList = cityList;
                adapter.refresh(cityList);
            }
            pickerView.reFreshData(pos, cityList);
        }
    }

}
