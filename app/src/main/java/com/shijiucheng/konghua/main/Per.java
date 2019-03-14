package com.shijiucheng.konghua.main;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.Banben_;
import com.shijiucheng.konghua.Cmvp.BaseFragment_konghua;
import com.shijiucheng.konghua.Cmvp.BasePresenter;
import com.shijiucheng.konghua.Cmvp.BaseResult;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.authen_RZ;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.IsLoginOrAuthor;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.per.payandget.gongdan.gondanlist;
import com.shijiucheng.konghua.main.per_.Setings;
import com.shijiucheng.konghua.main.per_.bank.banklist_guanli;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Per extends BaseFragment_konghua implements IsLoginOrAuthor.refresh, authen_RZ.refresh, Banben_.fuluebanben {
    View view;

    @BindView(R.id.per_name)
    TextView te_name;
    @BindView(R.id.per_imhead)
    ImageView perImhead;
    @BindView(R.id.per_lin1)
    LinearLayout perLin1;
    @BindView(R.id.per_lin2)
    LinearLayout perLin2;
    @BindView(R.id.per_lin3)
    LinearLayout perLin3;
    @BindView(R.id.per_lin4)
    LinearLayout perLin4;
    @BindView(R.id.per_lin5)
    LinearLayout perLin5;

    @BindView(R.id.sper_quit)
    TextView te_quit;

    @BindView(R.id.per_tebbh)
    TextView te_bbh;

    Banben_ banben_ = new Banben_();

    @Override
    protected void AddView() {
        te_bbh.setText("V" + getVersion());
        EventBus.getDefault().register(this);
        IsLoginOrAuthor.getInstence().setfr(this);
        authen_RZ.setrefrxx(this);
//        Glide.with(this).load(R.mipmap.appicon).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(perImhead);

        getuserinfo();

    }

    //获取用户信息
    private void getuserinfo() {
        if (getSharePre("name", getActivity()).equals("0")) {
            te_name.setText("请登录");
            te_quit.setVisibility(View.GONE);

            if (!getSharePre("name", getActivity()).equals("0")) {
                IsLoginOrAuthor.getInstence().login(getActivity(), retrofit_Single.getInstence().getOpenid(getActivity()), getSharePre("name", getActivity()), getSharePre("pwd", getActivity()));
            } else {
                IsLoginOrAuthor.getInstence().goToLogin(getActivity());
            }
        } else {
            Glide.with(this).load(getSharePre("user_pic", getActivity())).into(perImhead);
            te_name.setText(getSharePre("user_name", getActivity()));
            te_quit.setVisibility(View.VISIBLE);
        }

    }


    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.per;
    }

    /**
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.perRrefresh)) {
                return;
            }
        }
    }


    @Override
    protected BasePresenter bindPresent() {
        return null;
    }


    @OnClick({R.id.per_lin1, R.id.per_lin2, R.id.per_lin3, R.id.per_lin4, R.id.per_lin5, R.id.per_lin6, R.id.sper_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.per_lin1:
                setact(authen_RZ.class);
                break;
            case R.id.per_lin2:
                setact(Setings.class);
                break;
            case R.id.per_lin3:
                setact(gondanlist.class);
                break;
            case R.id.per_lin4:
                setact(banklist_guanli.class);
                break;
            case R.id.per_lin5:

                if (!jdt.isAdded()) {
                    jdt.show(getActivity().getFragmentManager(), "jdt");
                }
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Glide.get(getActivity().getApplication()).clearDiskCache();
                    }
                }).start();
                new Handler().postDelayed(new Runnable() {


                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "清除缓存成功",
                                Toast.LENGTH_SHORT).show();
                        if (jdt.isAdded()) {
                            jdt.dismiss();
                        }
                    }
                }, 2000);
                break;
            case R.id.per_lin6:
                getappvis();
                break;

            case R.id.sper_quit:
                getquit();
                break;
        }
    }

    private void setact(Class classx) {
        if (!getSharePre("name", getActivity()).equals("0"))
            startActivityByIntent(getActivity(), classx, false);
        else {

            if (!getSharePre("name", getActivity()).equals("0")) {

                IsLoginOrAuthor.getInstence().login(getActivity(), retrofit_Single.getInstence().getOpenid(getActivity()), getSharePre("name", getActivity()), getSharePre("pwd", getActivity()));
            } else {
                IsLoginOrAuthor.getInstence().goToLogin(getActivity());
            }
        }
    }

    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    getActivity().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    public int getVersion1() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    getActivity().getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public void refresh() {
        getuserinfo();
    }

    @Override
    public void refreshrz(Context context) {

    }

    public void getappvis() {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call = serivce.appverison(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strx = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(strx);
                        if (jso.getString("status").equals("1")) {
                            JSONObject data = jso.getJSONObject("data");
                            JSONObject jso_ = data
                                    .getJSONObject("android_new_version_info");
                            String str = jso_.getString("content").replace("br",
                                    "\n");
                            str = str.replace("nbsp", "  ");
                            String type = data.getString("type");// 1:不用强制更新，2：需要强制更新

                            String android_url = data.getString("android_url");
                            if (getVersion1() >= data.getInt("versionCode")) {
                                toaste_ut(getActivity(), "当前版本是最新版本");
                            } else {
                                if (!banben_.isAdded()) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("txt", str);
                                    bundle.putString("type", type);
                                    bundle.putString("url", android_url);
                                    banben_.setArguments(bundle);
                                    banben_.show(getActivity().getFragmentManager(), "bb");
                                }
                            }
                        } else
                            toaste_ut(getActivity(), jso.getString("msg"));
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

    Retro_Intf retro_intf;

    /**
     * 退出登录从设置页面迁移到个人中心首页
     */
    private void getquit() {
        retro_intf = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> maps = new HashMap<>();
        maps.putAll(retrofit_Single.getInstence().retro_postParameter());//公共参数
        Call<ResponseBody> getdata = retro_intf.quitLoigin(retrofit_Single.getInstence().getOpenid(getActivity()), maps);
        getdata.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(
                    Call<ResponseBody> call, Response<ResponseBody> response) {
                String Result = null;
                if (response.body() == null)
                    return;
                try {
                    Result = response.body().string();
                    try {
                        if (Result != null && Result.startsWith("\ufeff")) {
                            Result = Result.substring(1);
                        }
                        JSONObject jsonObject = new JSONObject(Result);
                        BaseResult result = new BaseResult();

                        if (jsonObject.getString("status").equals("1")) {
                            toaste_ut(getActivity(), jsonObject.getString("msg"));
                            sharePre("name", "0", getActivity());
                            sharePre("pwd", "0", getActivity());

                            IsLoginOrAuthor.getInstence().goToLogin(getActivity());
                        } else {
                            if (jsonObject.getString("msg").contains("未登录")) {
                                IsLoginOrAuthor.getInstence().goToLogin(getActivity());
                                toaste_ut(getActivity(), jsonObject.getString("msg"));
                            } else toaste_ut(getActivity(), jsonObject.getString("msg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println(e.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    @Override
    public void fuluebanben() {

    }
}
