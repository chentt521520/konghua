package com.shijiucheng.konghua.main.HomePage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
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
import com.shijiucheng.konghua.main.HomePage.frag.jiesuan_fragagreerefuse;
import com.shijiucheng.konghua.main.HomePage.frag.jujue_frag;
import com.shijiucheng.konghua.main.HomePage.frag.riliTime_frag;
import com.shijiucheng.konghua.main.HomePage.frag.tuidan_frag;
import com.shijiucheng.konghua.main.HomePage.frag.tuidanxiadanfang_frag;
import com.shijiucheng.konghua.main.HomePage.frag.zhangjia_frag;
import com.shijiucheng.konghua.main.HomePage.viewPagerUtils.CopyButtonLibrary;
import com.shijiucheng.konghua.main.order.Order_detalis;
import com.shijiucheng.konghua.main.per.mima.PayPwd;
import com.shijiucheng.konghua.main.per.mima.ValidatePwd;
import com.shijiucheng.konghua.main.per.payandget.gongdan.check_pic;

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

public class Orderdatels extends BaseActivity_konghua implements jiedan_frag.jiedan, zhangjia_frag.zhangjia,
        jujue_frag.jujue, tuidan_frag.tuidan, ValidatePwd.yanzhenpwd, riliTime_frag.setdatetime, TakePhoto.TakeResultListener,
        InvokeListener, jiesuan_frag.jieshuan, tuidanxiadanfang_frag.tuidanxdf, ditu_frag.mappos, jiesuan_fragagreerefuse.jieshuanbf {

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
    @BindView(R.id.orderddatails_tebrqs1)
    TextView tebr;
    @BindView(R.id.orderddatails_tetrqs1)
    TextView tetr;


    @BindView(R.id.orderddatails_imqstp1)
    ImageView orderddatailsImqstp1;
    @BindView(R.id.orderddatails_imqstp2)
    ImageView orderddatailsImqstp2;
    @BindView(R.id.orderddatails_imqstp3)
    ImageView orderddatailsImqstp3;
    @BindView(R.id.orderddatails_fd1)
    LinearLayout lin_tp1;
    @BindView(R.id.orderddatails_fd2)
    LinearLayout lin_tp2;
    @BindView(R.id.orderddatails_fd3)
    LinearLayout lin_tp3;


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

    //配送布局
    @BindView(R.id.orderddatails_imbz1)
    ImageView bz1;
    @BindView(R.id.orderddatails_imbz2)
    ImageView bz2;
    @BindView(R.id.orderddatails_imbz3)
    ImageView bz3;
    @BindView(R.id.orderddatails_bz1)
    LinearLayout lin_bz1;
    @BindView(R.id.orderddatails_bz2)
    LinearLayout lin_bz2;
    @BindView(R.id.orderddatails_bz3)
    LinearLayout lin_bz3;
    @BindView(R.id.orderddatails_bz)
    LinearLayout lin_bza;
    @BindView(R.id.v_kb)
    View v_kb;

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
    riliTime_frag riliTime_frag;
    jiesuan_frag jiesuan_frag;
    tuidanxiadanfang_frag xdf_tuidan;

    PayPwd pwd;
    ValidatePwd yanzhenpwd;
    String yy_typex = "", txtx = "";//用在申请退单的参数
    String orderstatus = "0";

    String[] dataurl = {"", "", ""};//签收信息


    @BindView(R.id.orderddatails_texdfname)
    TextView orderddatailsTexdfname;
    @BindView(R.id.orderddatails_texdfqq)
    TextView orderddatailsTexdfqq;
    @BindView(R.id.orderddatails_imxdfqq)
    ImageView orderddatailsImxdfqq;
    @BindView(R.id.orderddatails_texdfpho)
    TextView orderddatailsTexdfpho;
    @BindView(R.id.orderddatails_imxdfpho)
    ImageView orderddatailsImxdfpho;
    @BindView(R.id.orderddatails_linxdf)
    LinearLayout orderddatailsLinxdf;
    @BindView(R.id.orderddatails_henxdf)
    View orderddatailsHenxdf;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    int ix = 1;
    String order_sign_personnel_type = "1", order_sign_time = "";
    String is_order_balance_remind = "0";

    @BindView(R.id.orderdet_nodata)
    View v_nodata;

    @BindView(R.id.orderddatails_tepsfz)
    TextView te_fzps;
    @BindView(R.id.orderddatails_thkfz)
    TextView te_fzhk;

    @BindView(R.id.orderddatails_tejsts)
    TextView te_jsts;//提醒7日自动结算

    String ssq = "";//百度地图用到的省市区
    double[] jwd = {0, 0, 0, 0};
    ditu_frag ditu_frag;

    String QQ = "", phoxdf = "", jiesuannum = "";
    GeoCoder mSearch = GeoCoder.newInstance();

    jiesuan_fragagreerefuse js_bufen = new jiesuan_fragagreerefuse();

    CopyButtonLibrary copyButtonLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void AddView() {
        copyButtonLibrary = new CopyButtonLibrary(Orderdatels.this);
        mSearch.setOnGetGeoCodeResultListener(listener);
        serivce = retrofit_Single.getInstence().getserivce(2);
        orderddatailsDh.settext_("订单详情");
        id = getIntent().getStringExtra("id");
        jiedan_frag = new jiedan_frag();
        zhangjia = new zhangjia_frag();
        jujue_frag = new jujue_frag();
        EventBus.getDefault().register(this);
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

        orderddatailsRecyc1.setHasFixedSize(true);
        orderddatailsRecyc1.setNestedScrollingEnabled(false);
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

        orderddatailsTexdfqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkApkExist(Orderdatels.this, "com.tencent.mobileqq")) {

                    if (!TextUtils.isEmpty(QQ)) {
                        String urlQQ = "mqqwpa://im/chat?chat_type=wpa&uin=" + QQ + "&version=1";
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                } else {
                    toaste_ut(Orderdatels.this, "未检测到QQ");
                }
            }
        });
        orderddatailsImxdfqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkApkExist(Orderdatels.this, "com.tencent.mobileqq")) {

                    if (!TextUtils.isEmpty(QQ)) {
                        String urlQQ = "mqqwpa://im/chat?chat_type=wpa&uin=" + QQ + "&version=1";
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                } else {
                    toaste_ut(Orderdatels.this, "未检测到QQ");
                }
            }
        });

        orderddatailsImxdfpho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(phoxdf)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                            + phoxdf));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });
        orderddatailsTexdfpho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(phoxdf)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                            + phoxdf));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });

        te_fzps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int statu = Integer.valueOf(orderstatus).byteValue();//包扎图

                String xx = "";
                if (statu >= 10)
                    xx = orderddatailsTetime.getText().toString() + "\n配送地址：" +
                            orderddatailsTedizhi.getText().toString() + "\n收货人：" + orderddatailsTeshren.getText().toString() + "  " + orderddatailsTepho.getText().toString();
                else xx = orderddatailsTetime.getText().toString() + "\n配送地址：" +
                        orderddatailsTedizhi.getText().toString();
                copyButtonLibrary.init(xx);
            }
        });
        te_fzhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(orderddatailsTeheka.getText().toString()))
                    copyButtonLibrary.init("贺卡:" + orderddatailsTeheka.getText().toString());
                else toaste_ut(Orderdatels.this, "贺卡内容为空");
            }
        });
    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    private void getorder() {
        jdt.show(getFragmentManager(), "jdt");
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
                        if (jsonObject.getString("status").equals("1")) {
                            JSONObject jso = jsonObject.getJSONObject("data");
                            order_info(jso.getJSONObject("order_info"));
                            caozuolishi(jso.getJSONArray("order_follow_list"));
                        } else {
                            v_nodata.setVisibility(View.VISIBLE);
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
            listczls.add(new ord_detadataczls(jso.getString("follow_id"), jso.getString("follow_content_text") + " \n" + jso.getString("add_time_text")));
        }
        adaczls.notifyDataSetChanged();
    }

    private void order_info(JSONObject jso) {
        try {
            if (orderddatailsTebh == null)
                return;
            orderddatailsTebh.setText("    订单号：" + jso.getString("order_sn"));

            orderddatailsTetime.setText("配送时间：" + jso.getString("delivery_time_text"));

            orderddatailsTespprice1.setText("结算金额：" + jso.getString("order_balance_amount") + "元");
            jiesuannum = jso.getString("order_balance_amount");

            orderstatus = jso.getString("order_status");
            orderddatailsTezt.setText(jso.getString("order_status_text"));
            if (orderstatus.equals("40") || orderstatus.equals("31")) {
                orderddatailsTespprice1.setVisibility(View.VISIBLE);
            } else orderddatailsTespprice1.setVisibility(View.GONE);

            if (orderstatus.equals("30")) {
                te_jsts.setVisibility(View.VISIBLE);
                te_jsts.setText("如7天内未收到下单方主动确认结算，系统将会在 " + jso.getString("auto_balance_time_text") + " 后自动为您结算。");
            } else
                te_jsts.setVisibility(View.GONE);

            if (orderstatus.equals("3")) {
                orderddatailsTespprice.setText("订单金额：" + jso.getString("order_amount") + "元" + "        期望金额 :" + jso.getString("order_amount_add") + "元");
            } else orderddatailsTespprice.setText("订单金额：" + jso.getString("order_amount") + "元");
            price_order = jso.getString("order_amount");

            orderddatailsTedizhi.setText(jso.getString("receiver_province_text") + jso.getString("receiver_city_text") + jso.getString("receiver_district_text") + " " + jso.getString("receiver_address"));
            mSearch.geocode(new GeoCodeOption()
                    .city(jso.getString("receiver_city_text"))
                    .address(jso.getString("receiver_address")));
            ssq = jso.getString("receiver_city_text") + jso.getString("receiver_district_text");


            if (jso.getString("order_status").equals("1") || jso.getString("order_status").equals("2") || jso.getString("order_status").equals("3")) {
                orderddatailsTeshren.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                orderddatailsTepho.setVisibility(View.GONE);
                orderddatailsImpho.setVisibility(View.GONE);
                orderddatailsTeshren.setText(jso.getString("receiver"));

            } else {
                orderddatailsTeshren.getPaint().setFlags(0);
                orderddatailsTeshren.invalidate();
                orderddatailsTepho.setVisibility(View.VISIBLE);
                orderddatailsImpho.setVisibility(View.VISIBLE);
                orderddatailsTeshren.setText(jso.getString("receiver"));
                orderddatailsTepho.setText(jso.getString("receiver_tel"));
            }
            pho = jso.getString("receiver_tel");
            orderddatailsTeheka.setText(jso.getString("card_message"));

            if (!TextUtils.isEmpty(jso.getString("card_message"))) {
                te_fzhk.setVisibility(View.VISIBLE);
            } else te_fzhk.setVisibility(View.GONE);

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

            if (!TextUtils.isEmpty(jso.getString("order_balance_communicate_content")))
                orderddatailsTekkyy.setText("部分结算说明：" + jso.getString("order_balance_communicate_content"));
            else orderddatailsTekkyy.setVisibility(View.GONE);

            if (orderstatus.equals("40") || orderstatus.equals("30")) {
                v_hen1.setVisibility(View.VISIBLE);
                v_hen2.setVisibility(View.VISIBLE);
                orderddatailsLinddxx.setVisibility(View.VISIBLE);
                orderddatailsLinqs.setVisibility(View.VISIBLE);


                orderddatailsTejdsj.setText("下单时间：" + jso.getString("add_time_text"));

                orderddatailsTeqianshousj.setText("签收时间：  " + jso.getString("order_sign_time"));

                String qstype = jso.getString("order_sign_personnel_type");
                if (qstype.equals("1")) {
                    tebr.setVisibility(View.VISIBLE);
                    tetr.setVisibility(View.GONE);
                } else {
                    tetr.setVisibility(View.VISIBLE);
                    tebr.setVisibility(View.GONE);
                }
                String url = jso.getString("order_sign_images");
                if (url.contains(",")) {
                    String[] urls = url.split(",");
                    if (urls.length == 2) {
                        lin_tp3.setVisibility(View.GONE);
                        Glide.with(this).load(urls[0]).into(orderddatailsImqstp1);
                        Glide.with(this).load(urls[1]).into(orderddatailsImqstp2);
                    } else if (urls.length == 3) {
                        Glide.with(this).load(urls[0]).into(orderddatailsImqstp1);
                        Glide.with(this).load(urls[1]).into(orderddatailsImqstp2);
                        Glide.with(this).load(urls[2]).into(orderddatailsImqstp3);
                    }
                } else {
                    lin_tp2.setVisibility(View.GONE);
                    lin_tp3.setVisibility(View.GONE);
                    Glide.with(this).load(url).into(orderddatailsImqstp1);
                }

                lin_tp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setClass(Orderdatels.this, check_pic.class);
                        i.putExtra("pos", 0 + "");
                        i.putExtra("urls", url);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                });
                orderddatailsImqstp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setClass(Orderdatels.this, check_pic.class);
                        i.putExtra("pos", 0 + "");
                        i.putExtra("urls", url);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                });

            } else if (orderstatus.equals("20")) {
                v_hen1.setVisibility(View.VISIBLE);
                v_hen2.setVisibility(View.VISIBLE);
                orderddatailsLinddxx.setVisibility(View.VISIBLE);
                orderddatailsLinqs.setVisibility(View.GONE);
            } else {
                v_hen1.setVisibility(View.VISIBLE);
                v_hen2.setVisibility(View.GONE);
                orderddatailsLinddxx.setVisibility(View.VISIBLE);
                orderddatailsLinqs.setVisibility(View.GONE);
            }

            int statu = Integer.valueOf(orderstatus).byteValue();//包扎图
            if (statu >= 20) {
                lin_bza.setVisibility(View.VISIBLE);
                v_kb.setVisibility(View.VISIBLE);
                String bz = jso.getString("store_pack_images");
                if (bz.contains(",")) {
                    String[] tp = {"", "", ""};
                    for (int i = 0; i < bz.split(",").length; i++) {
                        tp[i] = bz.split(",")[i];
                    }
                    if (!TextUtils.isEmpty(tp[0]))
                        Glide.with(this).load(bz.split(",")[0]).into(bz1);
                    if (!TextUtils.isEmpty(tp[1]))
                        Glide.with(this).load(bz.split(",")[1]).into(bz2);
                    else lin_bz2.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(tp[2]))
                        Glide.with(this).load(bz.split(",")[2]).into(bz3);
                    else lin_bz3.setVisibility(View.GONE);
                } else {
                    Glide.with(this).load(bz).into(bz1);
                    lin_bz2.setVisibility(View.GONE);
                    lin_bz3.setVisibility(View.GONE);
                }

                lin_bz1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setClass(Orderdatels.this, check_pic.class);
                        i.putExtra("pos", 0 + "");
                        i.putExtra("urls", bz);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                });
                lin_bz2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setClass(Orderdatels.this, check_pic.class);
                        i.putExtra("pos", 1 + "");
                        i.putExtra("urls", bz);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                });
                lin_bz3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setClass(Orderdatels.this, check_pic.class);
                        i.putExtra("pos", 2 + "");
                        i.putExtra("urls", bz);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                });

            } else {
                lin_bza.setVisibility(View.GONE);
                v_kb.setVisibility(View.GONE);
            }
            String xdfname = jso.getString("delivery_uname");
            QQ = jso.getString("delivery_qq");
            phoxdf = jso.getString("delivery_tel");

            if (!TextUtils.isEmpty(xdfname))
                if (!TextUtils.isEmpty(QQ))
                    if (!TextUtils.isEmpty(phoxdf)) {
                        orderddatailsLinxdf.setVisibility(View.VISIBLE);
                        orderddatailsHenxdf.setVisibility(View.VISIBLE);
                        orderddatailsTexdfname.setText("下单方姓名:   " + xdfname);
                        orderddatailsTexdfqq.setText(QQ);
                        orderddatailsTexdfpho.setText(phoxdf);

                    } else {
                        orderddatailsLinxdf.setVisibility(View.GONE);
                        orderddatailsHenxdf.setVisibility(View.GONE);
                    }


            status(orderstatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void status(String status) {
        orderstatus = status;
        if (status.equals("1")) {
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
//                    if (!jujue_frag.isAdded()) {
//                        if (!TextUtils.isEmpty(price_order)) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString("id", id);
//                            jujue_frag.setArguments(bundle);
//                            jujue_frag.show(getSupportFragmentManager(), "zj");
//                        }
//                    }
                    if (fastClick()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("type", "jujue");//拒绝jujue，tuidan退单
                        startActivityByIntent(Orderdatels.this, refuseorder.class, bundle);
                    }
                }
            });
        } else if (status.equals("2")) {
            lin_bot.setVisibility(View.GONE);
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
        } else if (status.equals("3")) {
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
                    if (fastClick()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("type", "jujue");//拒绝jujue，tuidan退单
                        startActivityByIntent(Orderdatels.this, refuseorder.class, bundle);
                    }
                }
            });
        } else if (status.equals("10")) {
            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.INVISIBLE);
            tecz2.setVisibility(View.VISIBLE);
            tecz2.setBackgroundResource(R.drawable.biankuangyuanjiao);
            tecz2.setTextColor(getResources().getColor(R.color.zhu));
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
                    if (fastClick()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("type", "tuidan");//拒绝jujue，tuidan退单
                        startActivityByIntent(Orderdatels.this, refuseorder.class, bundle);
                    }

                }
            });
        } else if (status.equals("20")) {
            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.INVISIBLE);
            tecz2.setVisibility(View.INVISIBLE);
            tecz3.setVisibility(View.VISIBLE);
            tecz3.setBackgroundResource(R.drawable.biankuangyuanjiao);
            tecz3.setTextColor(getResources().getColor(R.color.zhu));
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

                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    startActivityByIntent(Orderdatels.this, qianshou.class, bundle);
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
            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.INVISIBLE);
            tecz2.setVisibility(View.INVISIBLE);
            tecz3.setBackgroundResource(R.drawable.biankuangyuanjiao);
            tecz3.setTextColor(getResources().getColor(R.color.zhu));
            tecz3.setVisibility(View.VISIBLE);
            tecz3.setText("申请结算");
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
//                    if (is_order_balance_remind.equals("0")) {
                    if (!jiesuan_frag.isAdded())
                        jiesuan_frag.show(getSupportFragmentManager(), "js");
//                    }
                }
            });
        } else if (status.equals("31")) {
            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.INVISIBLE);
            tecz2.setVisibility(View.VISIBLE);
            tecz2.setBackgroundResource(R.drawable.biankuangyuanjiao);
            tecz2.setTextColor(getResources().getColor(R.color.zhu));
            tecz3.setVisibility(View.VISIBLE);
            tecz2.setText("同意结算");
            tecz3.setText("拒绝结算");

            tecz1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putString("orderid", id);
                    b.putString("tit", "同意结算");
                    b.putString("txt", "确认同意部分结算金额:" + jiesuannum + "吗，确认继续？");
                    b.putString("type", "0");
                    if (!js_bufen.isAdded()) {
                        js_bufen.setArguments(b);
                        js_bufen.show(getSupportFragmentManager(), "ty");
                    }
                }
            });
            tecz3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fastClick()) {
                        Bundle b = new Bundle();
                        b.putString("orderid", id);
                        b.putString("tit", "拒绝结算");
                        b.putString("txt", "确认拒绝部分结算金额:" + jiesuannum + "吗,拒绝后订单将回到待结算状态，确认继续？");
                        b.putString("type", "1");
                        if (!js_bufen.isAdded()) {
                            js_bufen.setArguments(b);
                            js_bufen.show(getSupportFragmentManager(), "jjue");
                        }


                    }

                }
            });

        } else if (status.equals("40")) {
            lin_bot.setVisibility(View.GONE);


        } else if (status.equals("11")) {
            lin_bot.setVisibility(View.GONE);
        } else if (status.equals("12")) {
            lin_bot.setVisibility(View.VISIBLE);
            tecz1.setVisibility(View.INVISIBLE);
            tecz2.setVisibility(View.INVISIBLE);
            tecz3.setVisibility(View.VISIBLE);
            tecz3.setBackgroundResource(R.drawable.biankuangyuanjiao);
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
                return;
            }

            if (data.getMsg().equals(configParams.orderqianshou)) {
                getorder();
                return;
            }
        }
    }

    public void peisong1(String store_pack_images, String delivery_emp_uname, String delivery_emp_tel) {


        jdt.show(getFragmentManager(), "jdt");
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
//                            toaste_ut(Orderdatels.this, jsonObject.getString("msg"));
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
                        } else
                            toaste_ut(Orderdatels.this, jso.getString("msg"));
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
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
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
                            Toast.makeText(Orderdatels.this, "操作成功", Toast.LENGTH_SHORT).show();
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
    public void jieshuan_bufen() {
        getorder();
    }
}
