package com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.configParams;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.paramsDataBean;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class evaluatequestion extends BaseActivity_konghua {

    @BindView(R.id.evalue_dh)
    DaoHang_top evalueDh;
    @BindView(R.id.pjgd_edtxt)
    EditText pjgdEdtxt;
    @BindView(R.id.pjgd_tenum)
    TextView pjgdTenum;
    @BindView(R.id.pjgd_teok)
    TextView pjgdTeok;

    @BindView(R.id.pinjia_kfxin1)
    ImageView im_fuwuxin1;
    @BindView(R.id.pinjia_kfxin2)
    ImageView im_fuwuxin2;
    @BindView(R.id.pinjia_kfxin3)
    ImageView im_fuwuxin3;
    @BindView(R.id.pinjia_kfxin4)
    ImageView im_fuwuxin4;
    @BindView(R.id.pinjia_kfxin5)
    ImageView im_fuwuxin5;


    String id = "";
    int select = 5;

    @Override
    protected void AddView() {
        id = getIntent().getStringExtra("gdid");
        evalueDh.settext_("评价问题");
        selector_fuwu(5);

    }

    @Override
    protected void SetViewListen() {
        pjgdEdtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pjgdTenum.setText(pjgdEdtxt.getText().toString().length() + "/1000");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setonc(im_fuwuxin1, 1);
        setonc(im_fuwuxin2, 2);
        setonc(im_fuwuxin3, 3);
        setonc(im_fuwuxin4, 4);
        setonc(im_fuwuxin5, 5);
    }

    public void setonc(View view, int pos) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector_fuwu(pos);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_evaluatequestion;
    }

    @OnClick(R.id.pjgd_teok)
    public void onViewClicked() {
        if (TextUtils.isEmpty(pjgdEdtxt.getText().toString())) {
            Toast.makeText(evaluatequestion.this, "请输入评价内容", Toast.LENGTH_SHORT).show();
        } else {
            pinjia(select + "", pjgdEdtxt.getText().toString());
        }
    }

    Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);

    public void pinjia(String evaluate_star, String evaluate_content) {
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(this));
        map.put("id", id);
        map.put("evaluate_star", evaluate_star);
        map.put("evaluate_content", evaluate_content);
        Call<ResponseBody> call = serivce.WOPinjia(retrofit_Single.getInstence().getOpenid(evaluatequestion.this), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        if (jso.getString("status").equals("1")) {

                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.wodetalisrefresh11);
                            int pos = getIntent().getIntExtra("pos", 0);
                            databean.setT(pos);
                            EventBus.getDefault().post(databean);

                            databean.setMsg(configParams.wodetalisrefresh2);
                            databean.setT(getIntent().getIntExtra("pos", 0));
                            EventBus.getDefault().post(databean);
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        } else
                            Toast.makeText(evaluatequestion.this, jso.getString("msg"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t.getMessage().toString());
            }
        });
    }


    private void selector_fuwu(int id) {
        switch (id) {
            case 1:
                im_fuwuxin1.setSelected(true);
                im_fuwuxin2.setSelected(false);
                im_fuwuxin3.setSelected(false);
                im_fuwuxin4.setSelected(false);
                im_fuwuxin5.setSelected(false);
                select = 1;
                break;
            case 2:
                im_fuwuxin1.setSelected(true);
                im_fuwuxin2.setSelected(true);
                im_fuwuxin3.setSelected(false);
                im_fuwuxin4.setSelected(false);
                im_fuwuxin5.setSelected(false);
                select = 2;
                break;
            case 3:
                im_fuwuxin1.setSelected(true);
                im_fuwuxin2.setSelected(true);
                im_fuwuxin3.setSelected(true);
                im_fuwuxin4.setSelected(false);
                im_fuwuxin5.setSelected(false);
                select = 3;
                break;
            case 4:
                im_fuwuxin1.setSelected(true);
                im_fuwuxin2.setSelected(true);
                im_fuwuxin3.setSelected(true);
                im_fuwuxin4.setSelected(true);
                im_fuwuxin5.setSelected(false);
                select = 4;
                break;
            case 5:
                im_fuwuxin1.setSelected(true);
                im_fuwuxin2.setSelected(true);
                im_fuwuxin3.setSelected(true);
                im_fuwuxin4.setSelected(true);
                im_fuwuxin5.setSelected(true);
                select = 5;
                break;
            default:
                break;
        }
    }


}
