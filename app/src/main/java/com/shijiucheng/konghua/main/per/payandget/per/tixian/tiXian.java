package com.shijiucheng.konghua.main.per.payandget.per.tixian;

import android.view.View;
import android.widget.TextView;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;

import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tiXian extends BaseActivity_konghua {


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

    Retro_Intf serivce;

    @Override
    protected void AddView() {
        serivce = retrofit_Single.getInstence().getserivce(2);
        getNumTX();
    }

    @Override
    protected void SetViewListen() {
        txianTetx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByIntent(tiXian.this, sqtixian.class);
            }
        });
        txianTetxls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByIntent(tiXian.this, tiXianlishi.class);
            }
        });


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ti_xian;
    }

    public void getNumTX() {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        retrofit2.Call<ResponseBody> call = serivce.getNumTX(retrofit_Single.getInstence().getOpenid(this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
