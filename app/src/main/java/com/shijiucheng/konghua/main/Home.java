package com.shijiucheng.konghua.main;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.HomePageMvp.HPContact;
import com.shijiucheng.konghua.Cmvp.HomePageMvp.HPPrestentIml;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.IsLoginOrAuthor;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.per.payandget.payAndGet;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.sqtixian;
import com.shijiucheng.konghua.main.per.payandget.per.tixian.tiXianlishi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends com.shijiucheng.konghua.Cmvp.BaseFragment_konghua implements HPContact.HPView, IsLoginOrAuthor.refresh {
    //主页改成财务页面（申请提现）


    @BindView(R.id.txian_tenum1)
    TextView txianTenum1;
    @BindView(R.id.txian_tetx)
    TextView txianTetx;
    @BindView(R.id.txian_tetxls)
    TextView txianTetxls;
    @BindView(R.id.txian_tesm)
    TextView txianTesm;
    @BindView(R.id.txian_tesm1)
    TextView txianTesm1;
    HPPrestentIml hpPrestentIml;

    @BindView(R.id.txian_teAllNum)
    TextView txianallnum;
    @BindView(R.id.txian_teyitx)
    TextView txianTeytx;

    @BindView(R.id.txian_teszmx)
    TextView txianTeszmx;

    @BindView(R.id.hpNew_temoney1)
    TextView te_ketixian;

    Retro_Intf serivce;

    @Override
    protected void AddView() {
        IsLoginOrAuthor.setfr(this);
        hpPrestentIml = new HPPrestentIml(this);
        hpPrestentIml.getData(retrofit_Single.getInstence().getOpenid(getActivity()));
        serivce = retrofit_Single.getInstence().getserivce(2);

        EventBus.getDefault().register(this);
        getNumTX();
    }

    @Override
    protected void SetViewListen() {
        txianTetx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByIntent(getActivity(), sqtixian.class, false);
            }
        });
        txianTetxls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByIntent(getActivity(), tiXianlishi.class, false);
            }
        });
        txianTeszmx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), payAndGet.class));
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ti_xian;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected BasePresenter bindPresent() {
        return hpPrestentIml;
    }

    @Override
    public void showLoading() {
        if (!jdt.isAdded()) {
            jdt.show(getActivity().getFragmentManager(), "homepage");
        }
    }

    @Override
    public void closeLoading() {
        if (jdt.isAdded()) {
            jdt.dismiss();
        }
    }

    @Override
    public void showMsg(String msg) {
        toaste_ut(getActivity(), msg);
    }

    @Override
    public void toLogin() {
        System.out.println(getSharePre("name", getActivity()) + "  " + getSharePre("pwd", getActivity()));
        if (!getSharePre("name", getActivity()).equals("0")) {

            IsLoginOrAuthor.getInstence().login(getActivity(), retrofit_Single.getInstence().getOpenid(getActivity()), getSharePre("name", getActivity()), getSharePre("pwd", getActivity()));
        } else {
            IsLoginOrAuthor.getInstence().goToLogin(getActivity());
        }
    }

    @Override
    public void toRenZhen() {
        IsLoginOrAuthor.getInstence().goToAuthor(getActivity());
    }

    @Override
    public void refresh() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hpPrestentIml.getData(retrofit_Single.getInstence().getOpenid(getActivity()));
            }
        }, 10);

    }

    public void getNumTX() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        retrofit2.Call<ResponseBody> call = serivce.getNumTX(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            txianTenum1.setText(" ￥" + jsonObject.getJSONObject("data").getString("balance_amount"));
                            txianallnum.setText(" 总收入：￥" + jsonObject.getJSONObject("data").getString("income_amount"));
                            txianTeytx.setText(" ￥" + jsonObject.getJSONObject("data").getString("withdraw_amount"));
                            te_ketixian.setText(jsonObject.getJSONObject("data").getString("balance_amount"));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(paramsDataBean dataBean) {
        if (dataBean.getMsg().equals(configParams.sycw)) {
            getNumTX();
        }

    }
}
