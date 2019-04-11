package com.shijiucheng.konghua.main.HomePage;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;
import com.shijiucheng.konghua.main.HomePage.frag.ditu_frag;
import com.shijiucheng.konghua.main.HomePage.frag.jiedan_frag;
import com.shijiucheng.konghua.main.HomePage.frag.jiesuan_frag;
import com.shijiucheng.konghua.main.HomePage.frag.jujue_frag;
import com.shijiucheng.konghua.main.HomePage.frag.noorder;
import com.shijiucheng.konghua.main.HomePage.frag.riliTime_frag;
import com.shijiucheng.konghua.main.HomePage.frag.tuidan_frag;
import com.shijiucheng.konghua.main.HomePage.frag.tuidanxiadanfang_frag;
import com.shijiucheng.konghua.main.HomePage.frag.zhangjia_frag;
import com.shijiucheng.konghua.main.per.mima.PayPwd;
import com.shijiucheng.konghua.main.per.mima.ValidatePwd;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class Orderdatels extends BaseActivity_konghua implements jiedan_frag.jiedan, zhangjia_frag.zhangjia, jujue_frag.jujue, tuidan_frag.tuidan, ValidatePwd.yanzhenpwd, riliTime_frag.setdatetime, TakePhoto.TakeResultListener, InvokeListener, jiesuan_frag.jieshuan, tuidanxiadanfang_frag.tuidanxdf, ditu_frag.mappos, noorder.queren {

    @BindView(R.id.orderddatails_dh)
    DaoHang_top orderddatailsDh;
    @BindView(R.id.orderddatails_tebh)
    TextView orderddatailsTebh;
    @BindView(R.id.orderddatails_tezt)
    TextView orderddatailsTezt;
    @BindView(R.id.orderddatails_tejdsj)
    TextView orderddatailsTejdsj;
    @BindView(R.id.orderddatails_tespprice)
    TextView orderddatailsTespprice;
    @BindView(R.id.orderddatails_tespprice1)
    TextView orderddatailsTespprice1;
    @BindView(R.id.orderddatails_tekkyy)
    TextView orderddatailsTekkyy;
    @BindView(R.id.orderddatails_linddxx)
    LinearLayout orderddatailsLinddxx;
    @BindView(R.id.orderddatails_tetime)
    TextView orderddatailsTetime;
    @BindView(R.id.orderddatails_tetime1)
    TextView orderddatailsTetime1;
    @BindView(R.id.orderddatails_tedizhi)
    TextView orderddatailsTedizhi;
    @BindView(R.id.orderddatails_teshren)
    TextView orderddatailsTeshren;
    @BindView(R.id.orderddatails_tepho)
    TextView orderddatailsTepho;
    @BindView(R.id.orderddatails_impho)
    ImageView orderddatailsImpho;
    @BindView(R.id.orderddatails_recyc)
    RecyclerView orderddatailsRecyc;
    @BindView(R.id.orderddatails_teheka)
    TextView orderddatailsTeheka;
    @BindView(R.id.orderddatails_tebeizu)
    TextView orderddatailsTebeizu;
    @BindView(R.id.orderddatails_teqianshousj)
    TextView orderddatailsTeqianshousj;
    @BindView(R.id.orderddatails_teqsr)
    TextView orderddatailsTeqsr;
    @BindView(R.id.orderddatails_tebrqs)
    ImageView orderddatailsTebrqs;
    @BindView(R.id.orderddatails_tetrqs)
    ImageView orderddatailsTetrqs;
    @BindView(R.id.orderddatails_imqstp1)
    ImageView orderddatailsImqstp1;
    @BindView(R.id.orderddatails_imqstp2)
    ImageView orderddatailsImqstp2;
    @BindView(R.id.orderddatails_imqstp3)
    ImageView orderddatailsImqstp3;
    @BindView(R.id.orderddatails_teqsts)
    TextView orderddatailsTeqsts;
    @BindView(R.id.orderddatails_linqs)
    LinearLayout orderddatailsLinqs;
    @BindView(R.id.orderddatails_recyc1)
    RecyclerView orderddatailsRecyc1;


    @BindView(R.id.orderddatails_tejuli)
    TextView orderddatailsTejuli;
    @BindView(R.id.orderddatails_teditu)
    TextView orderddatailsTeditu;

    @BindView(R.id.orderddatails_linbot)
    LinearLayout lin_bot;
    @BindView(R.id.orderddatails_tecz1)
    TextView tecz1;
    @BindView(R.id.orderddatails_tecz2)
    TextView tecz2;
    @BindView(R.id.orderddatails_tecz3)
    TextView tecz3;


    orderdetailsadaptersp adasp;
    List<ord_detadata> listsp = new ArrayList<>();
    orderdetailsadaptercaozuolis adaczls;
    List<ord_detadataczls> listczls = new ArrayList<>();
    @BindView(R.id.orderddatails_teprice1)
    TextView orderddatailsTeprice1;

    @BindView(R.id.orderddatails_hen1)
    View v_hen1;
    @BindView(R.id.orderddatails_hen2)
    View v_hen2;

    @BindView(R.id.orderddatails_scroll)
    ScrollView scrollView;

    Retro_Intf serivce;
    String id = "", pho = "", price_order = "";

    jiedan_frag jiedan_frag;
    zhangjia_frag zhangjia;
    jujue_frag jujue_frag;
    tuidan_frag tuidan_frag;
    riliTime_frag riliTime_frag;
    jiesuan_frag jiesuan_frag;
    tuidanxiadanfang_frag xdf_tuidan;

    PayPwd pwd;
    ValidatePwd yanzhenpwd;
    String yy_typex = "", txtx = "";//用在申请退单的参数
    String orderstatus = "";

    String[] dataurl = {"", "", ""};//签收信息
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    int ix = 1;
    String order_sign_personnel_type = "1", order_sign_time = "";
    String is_order_balance_remind = "0";

    noorder noorder_ = new noorder();

    String ssq = "";//百度地图用到的省市区
    double[] jwd = {0, 0, 0, 0};
    ditu_frag ditu_frag;

    GeoCoder mSearch = GeoCoder.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void AddView() {
        mSearch.setOnGetGeoCodeResultListener(listener);
        serivce = retrofit_Single.getInstence().getserivce(2);
        orderddatailsDh.settext_("订单详情");
        id = getIntent().getStringExtra("id");
        System.out.println(id + "  xxxx");
        jiedan_frag = new jiedan_frag();
        zhangjia = new zhangjia_frag();
        jujue_frag = new jujue_frag();
        EventBus.getDefault().register(this);
        tuidan_frag = new tuidan_frag();
        pwd = new PayPwd();
        yanzhenpwd = new ValidatePwd();
        riliTime_frag = new riliTime_frag();
        jiesuan_frag = new jiesuan_frag();
        xdf_tuidan = new tuidanxiadanfang_frag();
        ditu_frag = new ditu_frag();


        LinearLayoutManager manager = new LinearLayoutManager(this);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        manager1.setOrientation(OrientationHelper.VERTICAL);
        adasp = new orderdetailsadaptersp(this, listsp);
        adaczls = new orderdetailsadaptercaozuolis(this, listczls);
        orderddatailsRecyc.setLayoutManager(manager);
        orderddatailsRecyc.setAdapter(adasp);

        orderddatailsRecyc1.setLayoutManager(manager1);
        orderddatailsRecyc1.setAdapter(adaczls);
        getorder();
    }


    @Override
    protected void SetViewListen() {
        orderddatailsImpho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(pho)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                            + pho));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });
        orderddatailsTepho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(pho)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                            + pho));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });

    }

    private void getorder() {
        jdt.show(getFragmentManager(), "jdt");
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("order_id", id);
        Call<ResponseBody> call = serivce.getOrderDetail(retrofit_Single.getInstence().getOpenid(this), map);
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
                        System.out.println(jsonObject);
                        if (jsonObject.getString("status").equals("1")) {
                            JSONObject jso = jsonObject.getJSONObject("data");
                            order_info(jso.getJSONObject("order_info"));
                            caozuolishi(jso.getJSONArray("order_follow_list"));
                        } else {
                            if (!noorder_.isAdded()) {
                                noorder_.show(getSupportFragmentManager(), "nooo");
                            }
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
        jdt.dismiss();
    }

    private void caozuolishi(JSONArray jsonArray) throws JSONException {
        listczls.removeAll(listczls);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jso = jsonArray.getJSONObject(i);
            listczls.add(new ord_detadataczls(jso.getString("follow_id"), jso.getString("add_time_text") + " " + jso.getString("follow_content_text")));
        }
        adaczls.notifyDataSetChanged();
    }

    private void order_info(JSONObject jso) {
        try {
            orderddatailsTebh.setText("订单编码：" + jso.getString("order_sn"));

            orderddatailsTetime.setText(jso.getString("delivery_time_text"));
            orderddatailsTespprice1.setText("结算金额：￥" + jso.getString("order_balance_amount"));
            orderddatailsTespprice.setText("转单金额：￥" + jso.getString("order_amount"));
            price_order = jso.getString("order_amount");

            orderddatailsTedizhi.setText(jso.getString("receiver_province_text") + jso.getString("receiver_city_text") + jso.getString("receiver_district_text") + " " + jso.getString("receiver_address"));
            mSearch.geocode(new GeoCodeOption()
                    .city(jso.getString("receiver_city_text"))
                    .address(jso.getString("receiver_address")));
            ssq = jso.getString("receiver_city_text") + jso.getString("receiver_district_text");

            orderddatailsTeshren.setText("收货人：" + jso.getString("receiver"));
            orderddatailsTepho.setText(jso.getString("receiver_tel"));
            pho = jso.getString("receiver_tel");
            orderddatailsTeheka.setText(jso.getString("card_message"));
            orderddatailsTebeizu.setText(jso.getString("order_remark"));
            orderddatailsTeprice1.setText("￥" + jso.getString("order_amount"));
            is_order_balance_remind = jso.getString("is_order_balance_remind");

            JSONArray jsa = jso.getJSONArray("goods_list");
            listsp.removeAll(listsp);
            for (int i = 0; i < jsa.length(); i++) {
                JSONObject jsoit = jsa.getJSONObject(i);
                listsp.add(new ord_detadata(jsoit.getString("goods_id"), jsoit.getString("goods_thumb"), jsoit.getString("goods_material"), jsoit.getString("goods_pack"), "x" + jsoit.getString("goods_number"), jsoit.getString("goods_img")));
            }
            adasp.notifyDataSetChanged();
            String orderstatus = jso.getString("order_status");
            if (orderstatus.equals("40") || orderstatus.equals("30")) {
                v_hen1.setVisibility(View.VISIBLE);
                v_hen2.setVisibility(View.VISIBLE);
                orderddatailsLinddxx.setVisibility(View.VISIBLE);
                orderddatailsLinqs.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(jso.getString("order_balance_communicate_content")))
                    orderddatailsTekkyy.setText(jso.getString("order_balance_communicate_content"));
                else orderddatailsTekkyy.setVisibility(View.GONE);
                orderddatailsTejdsj.setText("下单时间：" + jso.getString("add_time_text"));

                orderddatailsTeqianshousj.setText("签收时间：" + jso.getString("order_sign_time"));

                String qstype = jso.getString("order_sign_personnel_type");
                if (qstype.equals("1")) {
                    orderddatailsTebrqs.setImageResource(R.mipmap.xzhong);
                    orderddatailsTetrqs.setImageResource(R.mipmap.xzhong1);
                } else {
                    orderddatailsTebrqs.setImageResource(R.mipmap.xzhong1);
                    orderddatailsTetrqs.setImageResource(R.mipmap.xzhong);
                }
                String url = jso.getString("order_sign_images");
                if (url.contains(",")) {
                    String[] urls = url.split(",");
                    if (urls.length == 2) {
                        Glide.with(this).load(urls[0]).into(orderddatailsImqstp1);
                        Glide.with(this).load(urls[1]).into(orderddatailsImqstp2);
                    } else if (urls.length == 3) {
                        Glide.with(this).load(urls[0]).into(orderddatailsImqstp1);
                        Glide.with(this).load(urls[1]).into(orderddatailsImqstp2);
                        Glide.with(this).load(urls[2]).into(orderddatailsImqstp3);
                    }
                } else
                    Glide.with(this).load(url).into(orderddatailsImqstp1);

            } else if (orderstatus.equals("20")) {
                v_hen1.setVisibility(View.GONE);
                v_hen2.setVisibility(View.VISIBLE);
                orderddatailsLinddxx.setVisibility(View.GONE);
                orderddatailsLinqs.setVisibility(View.VISIBLE);
            } else {
                v_hen1.setVisibility(View.GONE);
                v_hen2.setVisibility(View.GONE);
                orderddatailsLinddxx.setVisibility(View.GONE);
                orderddatailsLinqs.setVisibility(View.GONE);
            }
            status(orderstatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void status(String status) {
        orderstatus = status;
        if (status.equals("1")) {
            orderddatailsTezt.setText("待接单");
            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.VISIBLE);
            tecz2.setVisibility(View.VISIBLE);
            tecz3.setVisibility(View.VISIBLE);
            tecz1.setText("立即接单");
            tecz3.setText("拒绝接单");
            tecz2.setText("请求涨价");

            tecz1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!jiedan_frag.isAdded())
                        jiedan_frag.show(getSupportFragmentManager(), "jd");
                }
            });
            tecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!zhangjia.isAdded()) {
                        if (!TextUtils.isEmpty(price_order)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("price", price_order);
                            zhangjia.setArguments(bundle);
                            zhangjia.show(getSupportFragmentManager(), "zj");
                        }
                    }
                }
            });
            tecz3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!jujue_frag.isAdded()) {
                        if (!TextUtils.isEmpty(price_order)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", id);
                            jujue_frag.setArguments(bundle);
                            jujue_frag.show(getSupportFragmentManager(), "zj");
                        }
                    }
                }
            });
        } else if (status.equals("2")) {
            orderddatailsTezt.setText("拒绝接单");
            lin_bot.setVisibility(View.GONE);
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
        } else if (status.equals("3")) {
            orderddatailsTezt.setText("请求涨价");
            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.VISIBLE);
            tecz2.setVisibility(View.VISIBLE);
            tecz3.setVisibility(View.VISIBLE);
            tecz1.setText("立即接单");
            tecz3.setText("拒绝接单");
            tecz2.setText("请求涨价");

            tecz1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!jiedan_frag.isAdded())
                        jiedan_frag.show(getSupportFragmentManager(), "jd");
                }
            });
            tecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!zhangjia.isAdded()) {
                        if (!TextUtils.isEmpty(price_order)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("price", price_order);
                            zhangjia.setArguments(bundle);
                            zhangjia.show(getSupportFragmentManager(), "zj");
                        }
                    }
                }
            });
            tecz3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!jujue_frag.isAdded()) {
                        if (!TextUtils.isEmpty(price_order)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", id);
                            jujue_frag.setArguments(bundle);
                            jujue_frag.show(getSupportFragmentManager(), "zj");
                        }
                    }
                }
            });
        } else if (status.equals("10")) {

            orderddatailsTezt.setText("待配送");

            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.INVISIBLE);
            tecz2.setVisibility(View.VISIBLE);
            tecz3.setVisibility(View.VISIBLE);
            tecz2.setText("开始配送");
            tecz3.setText("申请退单");

            tecz1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Orderdatels.this, order_PeiSong.class);
                    i.putExtra("id", id);
                    startActivity(i);
                }
            });
            tecz3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!tuidan_frag.isAdded()) {
                        if (!TextUtils.isEmpty(price_order)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", id);
                            tuidan_frag.setArguments(bundle);
                            tuidan_frag.show(getSupportFragmentManager(), "zj");
                        }
                    }

                }
            });
        } else if (status.equals("20")) {
            orderddatailsTezt.setText("待签收");
            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.INVISIBLE);
            tecz2.setVisibility(View.INVISIBLE);
            tecz3.setVisibility(View.VISIBLE);
            tecz3.setText("确认签收");

            tecz1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tecz3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(order_sign_time)) {
                        toaste_ut(Orderdatels.this, "请选择签收时间");
                        if (orderstatus.equals("20"))
                            if (!riliTime_frag.isAdded()) {
                                riliTime_frag.show(getSupportFragmentManager(), "rit");
                            }
                        return;
                    }
                    if (TextUtils.isEmpty(dataurl[0]) || TextUtils.isEmpty(dataurl[1]) || TextUtils.isEmpty(dataurl[2])) {
                        scrollView.smoothScrollBy(0, orderddatailsTeqianshousj.getTop());
                        toaste_ut(Orderdatels.this, "请上传3张配送图片");
                        return;
                    }
                    qianshoup(dataurl[0] + "," + dataurl[1] + "," + dataurl[2]);
                }
            });
            orderddatailsTeqianshousj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderstatus.equals("20"))
                        if (!riliTime_frag.isAdded()) {
                            riliTime_frag.show(getSupportFragmentManager(), "rit");
                        }
                }
            });
            orderddatailsTebrqs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderstatus.equals("20")) {
                        orderddatailsTebrqs.setImageResource(R.mipmap.xzhong);
                        orderddatailsTetrqs.setImageResource(R.mipmap.xzhong1);
                        order_sign_personnel_type = "1";
                    }
                }
            });
            orderddatailsTetrqs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderstatus.equals("20")) {
                        orderddatailsTetrqs.setImageResource(R.mipmap.xzhong);
                        orderddatailsTebrqs.setImageResource(R.mipmap.xzhong1);
                        order_sign_personnel_type = "2";
                    }
                }
            });

            orderddatailsImqstp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderstatus.equals("20")) {
                        if (fastClick()) {
                            ix = 1;
                            startToPhoto();
                        }
                    }
                }
            });
            orderddatailsImqstp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderstatus.equals("20")) {
                        if (fastClick()) {
                            ix = 2;
                            startToPhoto();
                        }
                    }
                }
            });
            orderddatailsImqstp3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderstatus.equals("20")) {
                        if (fastClick()) {
                            ix = 3;
                            startToPhoto();
                        }
                    }
                }
            });

        } else if (status.equals("30")) {
            orderddatailsTezt.setText("待结算");
            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.INVISIBLE);
            tecz2.setVisibility(View.INVISIBLE);
            tecz3.setVisibility(View.VISIBLE);
            tecz3.setText("申请结算");

            if (is_order_balance_remind.equals("1"))
                tecz3.setBackgroundColor(getResources().getColor(R.color.hei777));

            tecz1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tecz3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is_order_balance_remind.equals("0")) {
                        if (!jiesuan_frag.isAdded())
                            jiesuan_frag.show(getSupportFragmentManager(), "js");
                    }
                }
            });
        } else if (status.equals("40")) {
            orderddatailsTezt.setText("已完成");
            lin_bot.setVisibility(View.GONE);


        } else if (status.equals("11")) {
            orderddatailsTezt.setText("花店申请退单");
            lin_bot.setVisibility(View.GONE);
        } else if (status.equals("12")) {
            orderddatailsTezt.setText("下单方申请退单");
            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.INVISIBLE);
            tecz2.setVisibility(View.INVISIBLE);
            tecz3.setVisibility(View.VISIBLE);
            tecz3.setText("确认退单");

            tecz1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            tecz3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!xdf_tuidan.isAdded())
                        xdf_tuidan.show(getSupportFragmentManager(), "xdf");
                }
            });
        }

    }


    @Override
    protected int getLayout() {
        return R.layout.activity_orderdatels;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.orderSYrefr1);
        EventBus.getDefault().post(databean);
        mSearch.destroy();
    }

    @Override
    public void jiedan(boolean t) {//立即接单
        jiedan1();
    }

    private void jiedan1() {
        jdt.show(getFragmentManager(), "jdt");
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("order_id", id);
        Call<ResponseBody> call = serivce.jiedan(retrofit_Single.getInstence().getOpenid(this), map);
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
                            jdt.dismiss();
                            toaste_ut(Orderdatels.this, "操作成功");
                            getorder();
                        } else {
                            jdt.dismiss();
                            toaste_ut(Orderdatels.this, jsonObject.getString("msg"));
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
                jdt.dismiss();
            }
        });

    }

    @Override
    public void zhangjia(String price) {//涨价
        zhangjia1(price);
    }

    public void zhangjia1(String price) {
        jdt.show(getFragmentManager(), "jdt");
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("order_id", id);
        map.put("order_amount_add", price);
        Call<ResponseBody> call = serivce.zhangjia(retrofit_Single.getInstence().getOpenid(this), map);
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
                            jdt.dismiss();
                            toaste_ut(Orderdatels.this, "操作成功");
                            getorder();
                        } else {
                            jdt.dismiss();
                            toaste_ut(Orderdatels.this, jsonObject.getString("msg"));
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
                jdt.dismiss();
            }
        });

    }

    @Override
    public void jujue(String yy_type, String txt) {
        jujue1(yy_type, txt);
    }

    public void jujue1(String yy_type, String txt) {
        jdt.show(getFragmentManager(), "jdt");
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("order_id", id);
        map.put("order_refuse_reason", yy_type);
        map.put("order_refuse_reason_content", txt);
        Call<ResponseBody> call = serivce.jujue(retrofit_Single.getInstence().getOpenid(this), map);
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
                            jdt.dismiss();
                            toaste_ut(Orderdatels.this, "操作成功");
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        } else {
                            jdt.dismiss();
                            toaste_ut(Orderdatels.this, jsonObject.getString("msg"));
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
                jdt.dismiss();
            }
        });
    }

    /**
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void peisonmsg(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.orderpeisong)) {
                Bundle bundle = (Bundle) data.getT();
                peisong1(bundle.getString("store_pack_images"), bundle.getString("delivery_emp_uname"), bundle.getString("delivery_emp_tel"));
            }
            return;
        }
    }

    public void peisong1(String store_pack_images, String delivery_emp_uname, String delivery_emp_tel) {
        jdt.show(getFragmentManager(), "jdt");
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("order_id", id);
        map.put("store_pack_images", store_pack_images);
        map.put("delivery_emp_uname", delivery_emp_uname);
        map.put("delivery_emp_tel", delivery_emp_tel);
        Call<ResponseBody> call = serivce.kaishipeisong(retrofit_Single.getInstence().getOpenid(this), map);
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
                            jdt.dismiss();
                            toaste_ut(Orderdatels.this, "操作成功");
                            getorder();
                        } else {
                            jdt.dismiss();
                            toaste_ut(Orderdatels.this, jsonObject.getString("msg"));
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
                jdt.dismiss();
            }
        });
    }

    @Override
    public void ttuidan(String yy_type, String txt) {
        tuidan(yy_type, txt);
    }

    public void tuidan(String yy_type, String txt) {
        jdt.show(getFragmentManager(), "jdt");
        yy_typex = yy_type;
        txtx = txt;
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("order_id", id);
        map.put("order_cancel_reason", yy_type);
        map.put("order_cancel_reason_content", txt);
        Call<ResponseBody> call = serivce.tuidan(retrofit_Single.getInstence().getOpenid(this), map);
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
                            jdt.dismiss();
                            toaste_ut(Orderdatels.this, "操作成功");
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        } else {
                            jdt.dismiss();
                            toaste_ut(Orderdatels.this, jsonObject.getString("msg"));
                            if (jsonObject.getString("msg").equals("not_validate_pay_pwd"))
                                yanzhenpwd.show(getFragmentManager(), "czmm");
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
                jdt.dismiss();
            }
        });
    }


    @Override
    public void showpaypwd() {
        if (!pwd.isAdded())
            pwd.show(getSupportFragmentManager(), "pwd");
    }

    @Override
    public void getYanZhenResult(Object object) {
        tuidan(yy_typex, txtx);
    }

    @Override
    public void setdatetime(String time) {//设置签收时间
        if (orderstatus.equals("20")) {
            orderddatailsTeqianshousj.setText("签收时间:   " + time);
            order_sign_time = time;
        }
    }

    public void startToPhoto() {
        int flag = 2;
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
        Uri uri = Uri.fromFile(file);
        int size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        CropOptions cropOptions = new CropOptions.Builder().setOutputX(size).setOutputX(size).setWithOwnCrop(false).create();
        if (flag == 1) { //相机获取照片并剪裁
//                        takePhoto.onPickFromCaptureWithCrop(uri, cropOptions);
            //相机获取不剪裁
            takePhoto.onPickFromCapture(uri);
        } else if (flag == 2) { //相册获取照片并剪裁
//                takePhoto.onPickFromGalleryWithCrop(uri, cropOptions);
            //相册获取不剪裁
            takePhoto.onPickFromGallery();
        } else if (flag == 3) { //多选，并剪裁
            takePhoto.onPickMultipleWithCrop(9, cropOptions); //多选，不剪裁 // takePhoto.onPickMultiple(9);
        }
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        //设置压缩规则，最大500kb
//        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).create(), true);
        return takePhoto;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void takeSuccess(TResult result) {
        File test = new File(result.getImage().getOriginalPath());
        if (test.exists()) {
            Luban.with(this).load(test)
                    .ignoreBy(100)
                    .setTargetDir("")
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            if (!jdt.isAdded())
                                jdt.show(getFragmentManager(), "jdt");
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onSuccess(File file) {
                            if (jdt.isAdded())
                                jdt.dismiss();
                            String str = "";
                            try {
                                str = encodeBase64File(file);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            uploadpic(ix, str);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (jdt.isAdded())
                                jdt.dismiss();
                        }
                    }).launch();    //启动压缩
        }
    }

    public String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }


    private void uploadpic(final int xx, String url) {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("file_content", url);
        Call<ResponseBody> call = serivce.qianshoupic(retrofit_Single.getInstence().getOpenid(this), map);
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
                            jdt.dismiss();

                            JSONObject jso = jsonObject.getJSONObject("data");
                            if (xx == 1) {
                                Glide.with(Orderdatels.this)
                                        .load(jso.getString("url"))
                                        .into(orderddatailsImqstp1);
                                dataurl[0] = jso.getString("url");
                            } else if (xx == 2) {
                                Glide.with(Orderdatels.this)
                                        .load(jso.getString("url"))
                                        .into(orderddatailsImqstp2);
                                dataurl[1] = jso.getString("url");
                            } else if (xx == 3) {
                                Glide.with(Orderdatels.this)
                                        .load(jso.getString("url"))
                                        .into(orderddatailsImqstp3);
                                dataurl[2] = jso.getString("url");
                            }
                        } else {
                            toaste_ut(Orderdatels.this, jsonObject.getString("msg"));
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

    private void qianshoup(String url) {
        jdt.show(getFragmentManager(), "jdt");
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("order_id", id);
        map.put("order_sign_personnel_type", order_sign_personnel_type);
        map.put("order_sign_images", url);
        map.put("order_sign_time", order_sign_time);
        Call<ResponseBody> call = serivce.qianshou(retrofit_Single.getInstence().getOpenid(this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                jdt.dismiss();
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        if (jsonObject.getString("status").equals("1")) {
                            toaste_ut(Orderdatels.this, "操作成功");
                            scrollView.smoothScrollBy(0, 0);
                            getorder();
                        } else {

                            toaste_ut(Orderdatels.this, jsonObject.getString("msg"));
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
                jdt.dismiss();
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void jieshuan(boolean t) {
        jieshuan(id);
    }

    private void jieshuan(String id) {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", id);
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call = serivce.jiesuan(retrofit_Single.getInstence().getOpenid(Orderdatels.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        jdt.dismiss();
                        if (jso.getString("status").equals("1")) {
                            Toast.makeText(Orderdatels.this, "提醒成功", Toast.LENGTH_SHORT).show();
                        }
                        getorder();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                jdt.dismiss();
            }
        });

    }

    @Override
    public void tuidanxdf(boolean t) {
        tuidanf(id);
    }

    private void tuidanf(String id) {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", id);
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call = serivce.tuidanxdf(retrofit_Single.getInstence().getOpenid(Orderdatels.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null) {
                    return;
                }
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        jdt.dismiss();
                        if (jso.getString("status").equals("1")) {
                            Toast.makeText(Orderdatels.this, "提醒成功", Toast.LENGTH_SHORT).show();
                        }
                        getorder();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                jdt.dismiss();
            }
        });
    }


    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {

        public void onGetGeoCodeResult(GeoCodeResult result) {

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
                orderddatailsTejuli.setText("配送距离：未获取到收货人地址");
                orderddatailsTejuli.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                orderddatailsTeditu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                return;
            }

            double t1 = Double.valueOf(result.getLocation().latitude).doubleValue();
            double t11 = Double.valueOf(result.getLocation().longitude).doubleValue();
            double t2 = Double.valueOf(getSharePre("latitude", Orderdatels.this)).doubleValue();
            double t21 = Double.valueOf(getSharePre("longitude", Orderdatels.this)).doubleValue();

            DistanceOfTwoPoints(t1, t11, t2, t21);
        }

        @Override

        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
            }
        }
    };

    private final double EARTH_RADIUS = 6378.137;

    public double DistanceOfTwoPoints(final double lat1, final double lng1,
                                      final double lat2, final double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        orderddatailsTejuli.setText("配送距离：" + s + "公里");

        jwd[0] = lat1;
        jwd[1] = lng1;
        jwd[2] = lat2;
        jwd[3] = lng2;

        orderddatailsTejuli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ditu_frag.isAdded()) {
                    ditu_frag.show(getSupportFragmentManager(), "jwd");
                }
            }
        });
        orderddatailsTeditu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ditu_frag.isAdded()) {
                    ditu_frag.show(getSupportFragmentManager(), "jwd");
                }
            }
        });

        return s;
    }

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }


    private void openBaiduMap(double slat, double slon, String sname, double dlat, double dlon, String dname, String city) {
        if (OpenLocalMapUtil.isBaiduMapInstalled()) {
            try {
                String uri = OpenLocalMapUtil.getBaiduMapUri(String.valueOf(slat), String.valueOf(slon), sname,
                        String.valueOf(dlat), String.valueOf(dlon), dname, city, "com.shijiucheng.konghua");
                Intent intent = Intent.parseUri(uri, 0);
                startActivity(intent); //启动调用

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            toaste_ut(Orderdatels.this, "未找到百度地图应用");
        }
    }

    private void goToTencentMap(double mLat, double mLng, double dLat, double dLng) {
        if (!isInstalled("com.tencent.map")) {
            toaste_ut(Orderdatels.this, "请先安装腾讯地图客户端");
            return;
        }
        LatLng startPoint = BD2GCJ(new LatLng(dLat, dLng));//坐标转换
        LatLng endPoint = BD2GCJ(new LatLng(mLat, mLng));//坐标转换
        StringBuffer stringBuffer = new StringBuffer("qqmap://map/routeplan?type=drive")
                .append("&fromcoord=").append(startPoint.latitude).append(",").append(startPoint.longitude).append("&from=" + "起点")
                .append("&tocoord=").append(endPoint.latitude).append(",").append(endPoint.longitude).append("&to=" + ssq);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        startActivity(intent);
    }

    public static LatLng BD2GCJ(LatLng bd) {
        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double lng = z * Math.cos(theta);//lng
        double lat = z * Math.sin(theta);//lat
        return new LatLng(lat, lng);
    }

    private boolean isInstalled(String packageName) {
        PackageManager manager = getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }


    @Override
    public void mappos(int pos) {
        if (pos == 0) {
            openBaiduMap(jwd[2], jwd[3], "起点", jwd[0], jwd[1], "终点", ssq);
        } else {
            goToTencentMap(jwd[0], jwd[1], jwd[2], jwd[3]);
        }
    }

    @Override
    public void queren() {
        finish();
        overridePendingTransition(R.anim.push_right_out,
                R.anim.push_right_in);
    }


}
