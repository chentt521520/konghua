package com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;
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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class huifugd extends DialogFragment {
    @BindView(R.id.hfgd_edtxt)
    EditText hfgdEdtxt;
    @BindView(R.id.hfgd_tenum)
    TextView hfgdTenum;
    @BindView(R.id.fhgd_imtp1)
    ImageView fhgdImtp1;
    @BindView(R.id.fhgd_imtp2)
    ImageView fhgdImtp2;
    @BindView(R.id.fhgd_imtp3)
    ImageView fhgdImtp3;
    @BindView(R.id.fhgd_lintp)
    LinearLayout fhgdLintp;
    @BindView(R.id.fhgd_teok)
    TextView fhgdTeok;
    Unbinder unbinder;
    String type = "0";//0不掉返回即可1调用
    String woId = "0";

    int pos = 0;
    huifugdre huifugd;
    String[] urls = {"0", "0", "0"};

    Retro_Intf serivce;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.huifugd, container, false);
        unbinder = ButterKnife.bind(this, view);
        serivce = retrofit_Single.getInstence().getserivce(2);
        setview();
        setviewlisten();
        return view;
    }

    private void setviewlisten() {
        hfgdEdtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hfgdTenum.setText(hfgdEdtxt.getText().toString().length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fhgdImtp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = 0;
                huifugd.huifugd();

            }
        });
        fhgdImtp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = 1;
                huifugd.huifugd();

            }
        });
        fhgdImtp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = 2;
                huifugd.huifugd();

            }
        });
        fhgdTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(hfgdEdtxt.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    if (!urls[0].equals("0") || (!urls[0].equals("1")) || (!urls[2].equals("0"))) {
                        String uus = "";
                        for (int i = 0; i < 3; i++) {
                            if (!urls[i].equals("0"))
                                uus+= urls[i] + ",";
                        }
                        uus = uus.substring(0, uus.length() - 1);
                        System.out.println(uus);
                        uploadReplay(woId, uus, hfgdEdtxt.getText().toString());
                    } else
                        uploadReplay(woId, "", hfgdEdtxt.getText().toString());
                }
            }
        });
    }


    private void setview() {
        type = getArguments().getString("type");
        woId = getArguments().getString("gdid");
        huifugd = (huifugdre) getActivity();
    }

    public interface huifugdre {
        void huifugd();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showimgs(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (pos == 0) {
                urls[0] = url;
                Glide.with(getActivity()).load(url).into(fhgdImtp1);
            } else if (pos == 1) {
                urls[1] = url;
                Glide.with(getActivity()).load(url).into(fhgdImtp2);
            } else if (pos == 2) {
                urls[2] = url;
                Glide.with(getActivity()).load(url).into(fhgdImtp3);
            }

        }
    }

    private void uploadReplay(String id, String urls, String str) {
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(getActivity()));
        map.put("id", id);
        map.put("reply_images", urls);
        map.put("reply_content", str);
        Call<ResponseBody> call = serivce.WOupload(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        if (jso.getString("status").equals("1")) {
                            Toast.makeText(getActivity(), "回复成功，请等待管理员回复", Toast.LENGTH_SHORT).show();
                            if (type.equals("1")) {
                                paramsDataBean databean = new paramsDataBean();
                                databean.setMsg(configParams.wodetalisrefresh);
                                EventBus.getDefault().post(databean);
                                dismiss();
                            } else
                                dismiss();
                        } else
                            Toast.makeText(getActivity(), jso.getString("msg"), Toast.LENGTH_SHORT).show();
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
}
