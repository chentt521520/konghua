package com.shijiucheng.konghua.renzheng;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.DPAddressMvp.Contact;
import com.shijiucheng.konghua.Cmvp.DPAddressMvp.DDAddressPrestenIml;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.main.widget.CityPickerView;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.entity.CityInfo;
import com.shijiucheng.konghua.renzheng.data.MyRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.x;

import java.util.List;
import java.util.Map;

import Retrofit2.retrofit_Single;
import butterknife.BindView;

public class DPAddress extends com.shijiucheng.konghua.Cmvp.BaseActivity_konghua implements Contact.IdpAddressView {
    @BindView(R.id.dpdz_dh)
    DaoHang_top dh;
    @BindView(R.id.dpdz_linssq)
    LinearLayout lin_ssq;
    @BindView(R.id.dpdz_tessq)
    TextView te_ssq;
    @BindView(R.id.dpdz_tesq1)
    TextView te_ssq1;

    @BindView(R.id.dpdz_linxx)
    LinearLayout lin_xx;
    @BindView(R.id.dpdz_texx)
    TextView te_xx;
    @BindView(R.id.dpdz_edxx)
    EditText ed_xx1;

    @BindView(R.id.dpdz_linjwd)
    LinearLayout lin_jwd;
    @BindView(R.id.dpdz_tejwd)
    TextView te_jwd;
    @BindView(R.id.dpdz_tejwd1)
    TextView te_jwd1;
    @BindView(R.id.dpdz_tejwd2)
    TextView te_setjwd;

    @BindView(R.id.dpdz_tepsqy)
    TextView te_psqy;
    @BindView(R.id.dpdz_recyc)
    RecyclerView recyclerView;

    @BindView(R.id.dpdz_teok)
    TextView te_ok;

    MyRecyclerAdapter ada;

    Contact.IdpAddressPrestent prestent = new DDAddressPrestenIml(this);
    private CityPickerView pickerView;
    List<CityInfo> cityList;//省市区
    List<CityInfo> districtList;//省市区
    boolean initDistrict = true;
    private String provinceId;
    private String cityId;
    private String districtId;

    @Override
    protected void AddView() {
        x.view().inject(this);

        EventBus.getDefault().register(this);

        dh.settext_("填写店铺位置信息");

        dh.setFocusable(true);
        dh.setFocusableInTouchMode(true);

        if (authen_RZ.jsonAuthor != null) {
            ed_xx1.setText(authen_RZ.jsonAuthor.getAddress());
            if (authen_RZ.jsonAuthor.getLongitude().length() >= 1) {
                te_jwd1.setText(authen_RZ.jsonAuthor.getLongitude() + "," + authen_RZ.jsonAuthor.getLatitude());
            }
            if (!TextUtils.isEmpty(authen_RZ.jsonAuthor.getProvince_id_text())
                    && !TextUtils.isEmpty(authen_RZ.jsonAuthor.getCity_id_text())
                    && !TextUtils.isEmpty(authen_RZ.jsonAuthor.getDistrict_id_text())) {
                te_ssq1.setText(authen_RZ.jsonAuthor.getProvince_id_text() + " " + authen_RZ.jsonAuthor.getCity_id_text() + " " + authen_RZ.jsonAuthor.getDistrict_id_text());
                provinceId = authen_RZ.jsonAuthor.getProvince_id();
                cityId = authen_RZ.jsonAuthor.getCity_id();
                districtId = authen_RZ.jsonAuthor.getDistrict_id();
            }
            //获取配送区域
            if (!TextUtils.isEmpty(authen_RZ.jsonAuthor.getCity_id_text())) {
                getAddress(2, cityId);
            }
        }


        setViewHw_Lin(lin_ssq, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 30 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_ssq, (int) (w_ * 28 / 750.0));
        setTextSize(te_ssq1, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(lin_xx, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 30 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_xx, (int) (w_ * 28 / 750.0));
        setTextSize(ed_xx1, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(lin_jwd, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 30 / 750.0), (int) (w_ * 14 / 750.0), 0);
        setTextSize(te_jwd, (int) (w_ * 28 / 750.0));
        setTextSize(te_jwd1, (int) (w_ * 28 / 750.0));
        setTextSize(te_setjwd, (int) (w_ * 28 / 750.0));

        setViewHw_Lin(te_psqy, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 30 / 750.0), (int) (w_ * 14 / 750.0), 0);

        setViewHw_Lin(te_ok, w_ - (int) (w_ * 28 / 750.0), (int) (w_ * 80 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 0 / 750.0), (int) (w_ * 14 / 750.0), (int) (w_ * 14 / 750.0));


        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ada = new MyRecyclerAdapter(districtList, DPAddress.this);
        recyclerView.setAdapter(ada);

    }

    @Override
    protected void SetViewListen() {
        te_ssq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("sdfsdf111");
                if (fastClick()) {
                    getAddress(0, "0");
                }

            }
        });
        te_setjwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("jd", authen_RZ.jsonAuthor.getLatitude().length() == 0 ? "0" : authen_RZ.jsonAuthor.getLatitude());
                i.putExtra("wd", authen_RZ.jsonAuthor.getLongitude().length() == 0 ? "0" : authen_RZ.jsonAuthor.getLongitude());
                i.putExtra("shi", authen_RZ.jsonAuthor.getCity_id_text());
                i.setClass(DPAddress.this, BDMap.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        te_jwd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("jd", authen_RZ.jsonAuthor.getLatitude().length() == 0 ? "0" : authen_RZ.jsonAuthor.getLatitude());
                i.putExtra("wd", authen_RZ.jsonAuthor.getLongitude().length() == 0 ? "0" : authen_RZ.jsonAuthor.getLongitude());

                System.out.println("jd " + authen_RZ.jsonAuthor.getLatitude() + "  wd   " + authen_RZ.jsonAuthor.getLongitude());

                i.putExtra("shi", authen_RZ.jsonAuthor.getCity_id_text());
                i.setClass(DPAddress.this, BDMap.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        te_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(te_ssq1.getText().toString())) {
                    toaste_ut(DPAddress.this, "请选择地址信息");
                    return;
                }
                String[] citys = te_ssq1.getText().toString().split(" ");
                authen_RZ.jsonAuthor.setProvince_id(provinceId);
                authen_RZ.jsonAuthor.setProvince_id_text(citys[0]);
                authen_RZ.jsonAuthor.setCity_id(cityId);
                authen_RZ.jsonAuthor.setCity_id_text(citys[1]);
                authen_RZ.jsonAuthor.setDistrict_id(districtId);
                authen_RZ.jsonAuthor.setDistrict_id_text(citys[2]);

                if (TextUtils.isEmpty(ed_xx1.getText().toString())) {
                    toaste_ut(DPAddress.this, "请填写详细地址信息");
                    return;
                }
                authen_RZ.jsonAuthor.setAddress(ed_xx1.getText().toString());

                if (TextUtils.isEmpty(te_jwd1.getText().toString()) || te_jwd1.getText().toString().contains("请设置")) {
                    toaste_ut(DPAddress.this, "请设置店铺的经纬度");
                    return;
                }
                if (TextUtils.isEmpty(getIds())) {
                    toaste_ut(DPAddress.this, "请选择配送地址");
                    return;
                }
                authen_RZ.jsonAuthor.setDelivery_district_ids(getIds());

                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.dprzStep3);
                EventBus.getDefault().post(databean);
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });
    }

    private String getIds() {
        StringBuilder deliveryIds = new StringBuilder();
        if (districtList == null || districtList.isEmpty()) {
            return "";
        }
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

    @Override
    protected int getLayout() {
        return R.layout.activity_dpaddress;
    }

    @Override
    protected BasePresenter bindPresent() {
        return prestent;
    }

    @Subscribe
    public void get_address(Map<String, String> maps) {
        if (maps != null)
            if (maps.size() == 1)
                te_jwd1.setText(maps.get("dizhi"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        setContentView(R.layout.view_null);
    }

    @Override
    public void showload() {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "dpa");
    }

    @Override
    public void closeload() {
        if (jdt.isAdded())
            jdt.dismiss();
    }


    @Override
    public void showToast(String msg) {
        toaste_ut(getApplicationContext(), msg);
    }

    private void createCityDialog() {
        initDistrict = false;
        pickerView = new CityPickerView.Builder(DPAddress.this)
                .setList(cityList)
                .setListener(new CityPickerView.OnCitySelectListener() {
                    @Override
                    public void onConfirmListener(int pos, String id, String addressStr) {
                        if (pos == 1) {
                            provinceId = id;
                            getAddress(1, id);
                        } else if (pos == 2) {
                            cityId = id;
                            getAddress(2, id);
                        } else if (pos == 3) {
                            districtId = id;
                            te_ssq1.setText(addressStr);
                            ada.refresh(districtList);
                        }
                    }
                })
                .build();

        pickerView.showPopWin(DPAddress.this);
    }

    @Override
    public void getAddress(int pos, List<CityInfo> ssq) {

        this.cityList = ssq;
        if (pos == 0) {
            createCityDialog();
        } else {
            if (pos == 2) {
                this.districtList = ssq;
                if (initDistrict) {
                    setDistrictView();
                }
            }
            if (pickerView != null)
                pickerView.reFreshData(pos, cityList);
        }
    }


    private void getAddress(int pos, String id) {
        prestent.getSSQ(DPAddress.this, retrofit_Single.getInstence().getOpenid(DPAddress.this), pos, id);
    }

    private void setDistrictView() {
        String delivery_district_ids = authen_RZ.jsonAuthor.getDelivery_district_ids();
        if (districtList == null || districtList.isEmpty()) {
            return;
        }
        String[] ids = delivery_district_ids.split(",");
        for (String id : ids) {
            for (CityInfo info : districtList) {
                if (TextUtils.equals(id, info.getCityId())) {
                    info.setCheek(true);
                }
            }
        }
        ada.refresh(districtList);
    }
}
