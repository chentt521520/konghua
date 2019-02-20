package com.shijiucheng.konghua.main.HomePage.frag;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.HomePage.Orderdatels;
import com.shijiucheng.konghua.main.order.popwindow_.jujueada;
import com.shijiucheng.konghua.main.order.popwindow_.jujuedata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class jujue_frag extends DialogFragment {
    View view;
    int wxx = 0;

    String order_id = "";
    @BindView(R.id.jujue_teqb)
    TextView jujueTeqb;
    @BindView(R.id.jujue_tebc)
    TextView jujueTebc;
    @BindView(R.id.jujue_yuany)
    TextView jujueYuany;
    @BindView(R.id.jujue_yuany1)
    ListView jujueYuany1;
    @BindView(R.id.jujue_miaos)
    TextView jujueMiaos;
    @BindView(R.id.jujue_miaos1)
    EditText jujueMiaos1;
    @BindView(R.id.jujue_linms)
    LinearLayout jujueLinms;
    @BindView(R.id.jujue_jdteok)
    TextView jujueJdteok;
    @BindView(R.id.jujue_jdtequxiao)
    TextView jujueJdtequxiao;
    Unbinder unbinder;

    jujueada ada;
    List<jujuedata> list = new ArrayList<>();
    int pos_cho = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        view = inflater.inflate(R.layout.jujue, container, false);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        unbinder = ButterKnife.bind(this, view);
        setdata();
        setviewlisten();

        return view;
    }

    private void setviewlisten() {
        jujueYuany1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos_cho = position;
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        list.get(i).setT_("1");
                    } else {
                        list.get(i).setT_("0");
                    }
                }
                ada.notifyDataSetChanged();
                if (list.get(position).getKye().equals("4")) {
                    jujueLinms.setVisibility(View.VISIBLE);
                } else
                    jujueLinms.setVisibility(View.GONE);
            }
        });
        jujueJdteok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos_cho == -1) {
                    Toast.makeText(getActivity(), "请选择拒绝原因", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pos_cho == list.size() - 1) {

                    if (TextUtils.isEmpty(jujueMiaos1.getText().toString())) {
                        Toast.makeText(getActivity(), "请输入拒绝原因", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    jujue jujue = (jujue_frag.jujue) getActivity();
                    if (pos_cho == list.size() - 1)
                        jujue.jujue(list.get(pos_cho).getKye(), jujueMiaos1.getText().toString());
                    else
                        jujue.jujue(list.get(pos_cho).getKye(), "");
                    dismiss();
                    return;
                }
                jujue jujue = (jujue_frag.jujue) getActivity();
                if (pos_cho == list.size() - 1)
                    jujue.jujue(list.get(pos_cho).getKye(), jujueMiaos1.getText().toString());
                else
                    jujue.jujue(list.get(pos_cho).getKye(), "");
                dismiss();
            }
        });
        jujueJdtequxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setdata() {

        Bundle bundle = getArguments();
        order_id = bundle.getString("id");
        ada = new jujueada(list, getActivity());
        jujueYuany1.setAdapter(ada);
        jujueyy();
    }

    public interface jujue {
        void jujue(String yy_type, String txt);
    }


    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - 200, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }

    public void jujueyy() {
        Retro_Intf serivce = retrofit_Single.getInstence().getserivce(2);
        HashMap<String, String> map = new HashMap<>();
        map.putAll(retrofit_Single.getInstence().retro_postParameter());
        map.put("order_id", order_id);
        Call<ResponseBody> call = serivce.jujueyy(retrofit_Single.getInstence().getOpenid(getActivity()), map);
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

                            JSONArray jsonObject1 = jsonObject.getJSONObject("data").getJSONArray("order_refuse_reason");
                            list.removeAll(list);
                            for (int i = 0; i < jsonObject1.length(); i++) {
                                JSONObject jso = jsonObject1.getJSONObject(i);
                                if (pos_cho != -1) {
                                    if (i == pos_cho)
                                        list.add(new jujuedata(jso.getString("text"), "1", jso.getString("key")));
                                    else
                                        list.add(new jujuedata(jso.getString("text"), "0", jso.getString("key")));
                                } else

                                    list.add(new jujuedata(jso.getString("text"), "0", jso.getString("key")));
                            }
                            ada.notifyDataSetChanged();


                        } else
                            Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
