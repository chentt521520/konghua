package com.shijiucheng.konghua.renzheng;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.DPAddressMvp.Contact;
import com.shijiucheng.konghua.Cmvp.DPAddressMvp.DDAddressPrestenIml;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.PopWindow_;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.QNumberPicker;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.renzheng.data.MyRecyclerAdapter;
import com.shijiucheng.konghua.renzheng.data.Recyc_data;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.x;

import java.util.ArrayList;
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

    View view_window;
    PopWindow_ window_;

    List<Recyc_data> list = new ArrayList<>();
    MyRecyclerAdapter ada;

    QNumberPicker qnp1, qnp2, qnp3;
    TextView te_dzok, te_dzqx;
    int position_ssq = 0;//0表示省1表示市2表示区

    Contact.IdpAddressPrestent prestent = new DDAddressPrestenIml(this);
    List<String> shen, shi, qu;//省市区
    List<String> shen1, shi1, qu1;//省市区id


    @Override
    protected void AddView() {
        x.view().inject(this);

        EventBus.getDefault().register(this);

        dh.settext_("填写店铺位置信息");

        dh.setFocusable(true);
        dh.setFocusableInTouchMode(true);

        ed_xx1.setText(authen_RZ.jsonAuthor.getAddress());
        if (authen_RZ.jsonAuthor.getLongitude().length() >= 1)
            te_jwd1.setText(authen_RZ.jsonAuthor.getLongitude() + "," + authen_RZ.jsonAuthor.getLatitude());
        if (authen_RZ.jsonAuthor.getCity_id_text().length() >= 1)
            te_ssq1.setText(authen_RZ.jsonAuthor.getProvince_id_text() + "-" + authen_RZ.jsonAuthor.getCity_id_text() + "-" + authen_RZ.jsonAuthor.getDistrict_id_text());


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

        setViewHw_Lin(te_ok, w_ - (int) (w_ * 100 / 750.0), (int) (w_ * 100 / 750.0), (int) (w_ * 50 / 750.0), (int) (w_ * 40 / 750.0), (int) (w_ * 50 / 750.0), (int) (w_ * 14 / 750.0));


        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ada = new MyRecyclerAdapter(list, DPAddress.this);
        recyclerView.setAdapter(ada);

        view_window = LayoutInflater.from(DPAddress.this).inflate(R.layout.dizhi_popwindow, null);

        prestent.getSSQ(retrofit_Single.getInstence().getOpenid(DPAddress.this), 3, authen_RZ.jsonAuthor.getCity_id());


    }

    @Override
    protected void SetViewListen() {
        te_ssq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("sdfsdf111");
                if (fastClick()) {
                    window_ = new PopWindow_(DPAddress.this, view_window, view, w_, (int) (w_ * 400 / 750.0), true);
                    getshen(view);
                }

            }
        });
        te_setjwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("jd", authen_RZ.jsonAuthor.getLongitude().length() == 0 ? "0" : authen_RZ.jsonAuthor.getLatitude());
                i.putExtra("wd", authen_RZ.jsonAuthor.getLatitude().length() == 0 ? "0" : authen_RZ.jsonAuthor.getLongitude());
                i.setClass(DPAddress.this, BDMap.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        te_jwd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("jd", authen_RZ.jsonAuthor.getLongitude().length() == 0 ? "0" : authen_RZ.jsonAuthor.getLatitude());
                i.putExtra("wd", authen_RZ.jsonAuthor.getLatitude().length() == 0 ? "0" : authen_RZ.jsonAuthor.getLongitude());
                i.setClass(DPAddress.this, BDMap.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        te_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (te_ssq1.getText().equals("请选择地址信息!")) {
                    toaste_ut(DPAddress.this, "请选择地址信息");
                    return;
                }
                if (TextUtils.isEmpty(ed_xx1.getText().toString())) {
                    toaste_ut(DPAddress.this, "请填写详细地址信息");
                    return;
                }
                if (te_jwd1.getText().equals("请设置经纬度信息!")) {
                    toaste_ut(DPAddress.this, "请设置店铺的经纬度");
                    return;
                }
                String ids = "";
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isIsselect())
                        ids += qu1.get(i) + ",";
                }
                ids = ids.substring(0, ids.length() - 1);
                authen_RZ.jsonAuthor.setDelivery_district_ids(ids);

                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.dprzStep3);
                EventBus.getDefault().post(databean);
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_dpaddress;
    }

    @Override
    protected BasePresenter bindPresent() {
        return prestent;
    }

    private void set_npView() {
        qnp1 = view_window.findViewById(R.id.adddzpic1);
        qnp2 = view_window.findViewById(R.id.adddzpic2);
        qnp3 = view_window.findViewById(R.id.adddzpic3);
        te_dzok = view_window.findViewById(R.id.adddz_teok);
        te_dzqx = view_window.findViewById(R.id.adddz_tequxiao);

        String[] data = new String[shen.size()];
        for (int i = 0; i < shen.size(); i++) {
            data[i] = shen.get(i);
        }

        qnp1.setDisplayedValues(data);
        setNumberPickerDividerColor(qnp1);
        qnp1.setMinValue(0);
        qnp1.setMaxValue(data.length - 1);
        qnp1.setValue(0);

        te_dzqx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window_.disssmiss_window();
                position_ssq = 0;
            }
        });
        te_dzok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position_ssq == 0) {
                    position_ssq = 1;
                    getshi();
                } else if (position_ssq == 1) {
                    position_ssq = 2;
                    getqu();
                } else if (position_ssq == 2) {

                    te_ssq1.setText(shen.get(qnp1.getValue()) + "-" + shi.get(qnp2.getValue()) + "-" + qu.get(qnp3.getValue()));
                    authen_RZ.jsonAuthor.setProvince_id(shen1.get(qnp1.getValue()) + "");
                    authen_RZ.jsonAuthor.setCity_id(shi1.get(qnp2.getValue()) + "");
                    authen_RZ.jsonAuthor.setCity_id(qu1.get(qnp3.getValue()) + "");

                    list.removeAll(list);
                    for (int i = 0; i < qu.size(); i++) {
                        if (qu.get(i).equals(qu.get(qnp3.getValue())))
                            list.add(new Recyc_data(true, qu.get(i), qu1.get(i)));
                        else list.add(new Recyc_data(false, qu.get(i), qu1.get(i)));
                    }
                    ada.notifyDataSetChanged();

                    window_.disssmiss_window();
                    position_ssq = 0;


                }
            }
        });
    }


    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        java.lang.reflect.Field[] pickerFields = NumberPicker.class
                .getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    // 设置分割线的颜色值 透明
                    pf.set(picker,
                            new ColorDrawable(Color.parseColor("#0894ec")));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
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


    private void getshen(View view) {
        if (shen != null) {
            if (shen.size() >= 1) {
                set_npView();
            }
        } else
            prestent.getSSQ(retrofit_Single.getInstence().getOpenid(DPAddress.this), 0, "0");
    }

    private void getshi() {
        if (shi != null) {
            if (shi.size() >= 1) {
                String[] data = new String[shi.size()];
                for (int i = 0; i < shi.size(); i++) {
                    data[i] = shi.get(i);
                }
                qnp2.setDisplayedValues(data);
                setNumberPickerDividerColor(qnp2);
                qnp2.setMinValue(0);
                qnp2.setMaxValue(data.length - 1);
                qnp2.setValue(0);
                qnp1.setVisibility(View.GONE);
                qnp2.setVisibility(View.VISIBLE);
            }
        } else {
            prestent.getSSQ(retrofit_Single.getInstence().getOpenid(DPAddress.this), 1, shen1.get(qnp1.getValue()));
        }
    }

    private void getqu() {

        if (qu != null) {
            if (qu.size() >= 1) {
                String[] data = new String[qu.size()];
                for (int i = 0; i < qu.size(); i++) {
                    data[i] = qu.get(i);
                }
                qnp3.setDisplayedValues(data);
                setNumberPickerDividerColor(qnp3);
                qnp3.setMinValue(0);
                qnp3.setMaxValue(data.length - 1);
                qnp3.setValue(0);
                qnp2.setVisibility(View.GONE);
                qnp3.setVisibility(View.VISIBLE);
            }
        } else {
            prestent.getSSQ(retrofit_Single.getInstence().getOpenid(DPAddress.this), 2, shi1.get(qnp2.getValue()));
        }
    }

    private void getqu1() {
        qu = null;
        qu1 = null;
        if (qu != null) {
            if (qu.size() >= 1) {
                String[] data = new String[qu.size()];
                for (int i = 0; i < qu.size(); i++) {
                    data[i] = qu.get(i);
                }
                qnp3.setDisplayedValues(data);
                setNumberPickerDividerColor(qnp3);
                qnp3.setMinValue(0);
                qnp3.setMaxValue(data.length - 1);
                qnp3.setValue(0);
                qnp2.setVisibility(View.GONE);
                qnp3.setVisibility(View.VISIBLE);
            }
        } else {
            prestent.getSSQ(retrofit_Single.getInstence().getOpenid(DPAddress.this), 3, shi1.get(qnp2.getValue()));
        }
    }

    @Override
    public void getAddress(int pos, List ssq, List ssqid) {
        if (pos == 0) {
            shen = ssq;
            shen1 = ssqid;
            getshen(te_ssq1);
        } else if (pos == 1) {
            shi = ssq;
            shi1 = ssqid;
            getshi();
        } else if (pos == 2) {
            qu = ssq;
            qu1 = ssqid;
            getqu();
        } else if (pos == 3) {
            qu = ssq;
            qu1 = ssqid;
            list.removeAll(list);
            for (int i = 0; i < qu.size(); i++) {
                if (i == 0)
                    list.add(new Recyc_data(true, qu.get(i), qu1.get(i)));
                else list.add(new Recyc_data(false, qu.get(i), qu1.get(i)));
            }
            ada.notifyDataSetChanged();
            pos = 0;
        }
    }
}
