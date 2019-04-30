package com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;

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

public class pinjiagd extends DialogFragment {
    @BindView(R.id.pjgd_rtbar)
    RatingBar pjgdRtbar;
    @BindView(R.id.pjgd_edtxt)
    EditText pjgdEdtxt;
    @BindView(R.id.pjgd_tenum)
    TextView pjgdTenum;
    @BindView(R.id.pjgd_teok)
    TextView pjgdTeok;
    @BindView(R.id.pjgd_teno)
    TextView pjgdTeno;
    Unbinder unbinder;

    String itid = "";
    int pos = 0;
    Retro_Intf serivce;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.pinjiagd, container, false);
        unbinder = ButterKnife.bind(this, view);
        itid = getArguments().getString("gdid");
        pos = getArguments().getInt("pos");
        serivce = retrofit_Single.getInstence().getserivce(2);
        setviewlisten();
        setview();
        return view;
    }

    private void setview() {
        itid = getArguments().getString("gdid");
    }

    private void setviewlisten() {
        pjgdRtbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float barnum = pjgdRtbar.getRating();
                if (barnum >= 3) {
                    pjgdRtbar.setSelected(true);
                } else
                    pjgdRtbar.setSelected(false);
            }
        });
        pjgdEdtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pjgdTenum.setText(pjgdEdtxt.getText().toString().length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pjgdTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rtbar = pjgdRtbar.getNumStars() + "";
                if (TextUtils.isEmpty(pjgdEdtxt.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入评价内容", Toast.LENGTH_SHORT).show();
                } else {
                    pinjia(rtbar, pjgdEdtxt.getText().toString());
                }
            }
        });
        pjgdTeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void pinjia(String evaluate_star, String evaluate_content) {
        Map<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter(getActivity()));
        map.put("id", itid);
        map.put("evaluate_star", evaluate_star);
        map.put("evaluate_content", evaluate_content);
        Call<ResponseBody> call = serivce.WOPinjia(retrofit_Single.getInstence().getOpenid(getActivity()), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    try {
                        JSONObject jso = new JSONObject(str);
                        if (jso.getString("status").equals("1")) {
                            getRefershwo getRefershwo = (pinjiagd.getRefershwo) getActivity();
                            getRefershwo.refreshpj(pos);
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
                System.out.println(t.getMessage().toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        getDialog().getWindow().setLayout((int) (w * 600 / 750.0), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public interface getRefershwo {
        void refreshpj(int position);
    }

}
