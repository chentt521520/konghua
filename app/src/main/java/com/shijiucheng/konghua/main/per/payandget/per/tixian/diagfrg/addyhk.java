package com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.Pd_yhk;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.per.mima.PayPwd;
import com.shijiucheng.konghua.main.per.mima.ValidatePwd;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addyhk extends BaseActivity_konghua implements addyhkssq.setSSq, addyhkType.setBankType, ValidatePwd.yanzhenpwd, PayPwd.getpwddata {
    @BindView(R.id.addyhk_tit)
    DaoHang_top addyhkTit;
    @BindView(R.id.addyhk_edname)
    EditText addyhkEdname;
    @BindView(R.id.addyhk_edyhk)
    EditText addyhkEdyhk;
    @BindView(R.id.addyhk_edyhkname)
    TextView addyhkEdyhkname;//银行卡类别
    @BindView(R.id.addyhk_spindizhi)
    TextView addyhkSpindizhi;
    @BindView(R.id.addyhk_edkhh)
    EditText addyhkEdkhh;
    Unbinder unbinder;

    String ssq_str = "";
    @BindView(R.id.addyhk_teok)
    TextView addyhkTeok;

    String type = "0";//0是刷新申请提现数据，1是还要设置银行卡选择的 2是编辑银行卡
    String yhktypeid = "";

    Retro_Intf serivce;

    String parent_id = "0";
    int type_ssq = 0;
    List<String> data_list = new ArrayList<>(), data_list1, data_list2;

    Retro_Intf serivcce = retrofit_Single.getInstence().getserivce(2);

    addyhkssq addyhkssq;
    addyhkType addyhkType;

    PayPwd pwd;
    ValidatePwd yanzhenpwd;


    public void setviewdat() {
        addyhkTit.settext_("添加银行卡");
        type = getIntent().getStringExtra("type");
        if (type.equals("3")) {
            addyhkTeok.setText("添加银行卡");
        }
        addyhkssq = new addyhkssq();
        addyhkType = new addyhkType();

        pwd = new PayPwd();
        yanzhenpwd = new ValidatePwd();

        addyhkSpindizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addyhkssq.isAdded()) {
                    addyhkssq.show(getFragmentManager(), "ssq");
                }
            }
        });
        addyhkEdyhkname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!addyhkType.isAdded()) {
                    addyhkType.show(getFragmentManager(), "yhk");
                }
            }
        });


        addyhkEdyhk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pd_yhk pd_yhk = new Pd_yhk();

                if (addyhkEdyhk.getText().toString().length() == 6) {
                    String nameOfBank = pd_yhk.getNameOfBank(addyhkEdyhk.getText().toString());
                    addyhkEdkhh.setText(nameOfBank);
                } else if (addyhkEdyhk.getText().toString().length() == 8) {
                    String nameOfBank = pd_yhk.getNameOfBank(addyhkEdyhk.getText().toString());
                    addyhkEdkhh.setText(nameOfBank);
                } else if (addyhkEdyhk.getText().toString().length() > 15) {
                    String nameOfBank = pd_yhk.getDetailNameOfBank(addyhkEdyhk.getText().toString());
                    addyhkEdkhh.setText(nameOfBank);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        addyhkTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(addyhkEdname.getText().toString())) {
                    toaste_ut(addyhk.this, "请输入持卡人姓名");
                    return;
                }
                if (addyhkEdyhkname.getText().toString().equals("请选择开户类型")) {
                    toaste_ut(addyhk.this, "请输入开户行");
                    return;
                }
                if (TextUtils.isEmpty(addyhkEdyhk.getText().toString())) {
                    toaste_ut(addyhk.this, "请输入正确的银行卡号");
                    return;
                }
                if (TextUtils.isEmpty(addyhkSpindizhi.getText().toString())) {
                    toaste_ut(addyhk.this, "请选择开户行地址");
                    return;
                }
                if (TextUtils.isEmpty(addyhkEdkhh.getText().toString())) {
                    toaste_ut(addyhk.this, "请选择开户支行");
                    return;
                }
                AddYhk();
            }
        });
    }

    private void AddYhk() {
        HashMap<String, String> map = new HashMap<>();
        String[] ssq = ssq_str.split(",");
        map.put("bank_id", "");
        map.put("bank_type", yhktypeid);
        map.put("province_id", ssq[0]);
        map.put("city_id", ssq[1]);
        map.put("district_id", ssq[2]);
        map.put("bank_name", addyhkEdkhh.getText().toString());
        map.put("bank_no", addyhkEdyhk.getText().toString());
        map.put("bank_username", addyhkEdname.getText().toString());
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        Call<ResponseBody> call = serivcce.AddYhk(retrofit_Single.getInstence().getOpenid(addyhk.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getString("status").equals("1")) {
                            toaste_ut(addyhk.this, "添加银行卡成功");
                            if (!type.equals("3")) {
                                paramsDataBean dataBean = new paramsDataBean();
                                dataBean.setMsg(configParams.addyhktosqtx);
                                Map<String, Object> maps = new HashMap<>();
                                maps.put("type", type);
                                dataBean.setT(maps);
                                EventBus.getDefault().post(dataBean);
                            } else {
                                paramsDataBean dataBean = new paramsDataBean();
                                dataBean.setMsg(configParams.perdizhirefr);
                                Map<String, Object> maps = new HashMap<>();
                                maps.put("type", type);
                                dataBean.setT(maps);
                                EventBus.getDefault().post(dataBean);
                            }
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        } else {
                            String msg = jsonObject.getString("msg");
                            if (msg.equals("not_validate_pay_pwd"))
                                yanzhenpwd.show(getFragmentManager(), "czmm");
                            else toaste_ut(addyhk.this, jsonObject.getString("msg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    protected void AddView() {
        setviewdat();
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.addyhk;
    }


    @Override
    public void setSSq(String ids, String ssqstr) {
        this.ssq_str = ids;
        addyhkSpindizhi.setText(ssqstr);
    }

    @Override
    public void setbank(String ids, String ssqstr) {
        yhktypeid = ids;
        addyhkEdyhkname.setText(ssqstr);
    }

    @Override
    public void showpaypwd() {
        if (!pwd.isAdded())
            pwd.show(getSupportFragmentManager(), "pwd");
    }

    @Override
    public void getYanZhenResult(Object object) {
        AddYhk();
    }

    @Override
    public void getpwddatax() {

    }
}
