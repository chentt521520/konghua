package com.shijiucheng.konghua.main.HomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shijiucheng.konghua.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ordertwoAdapter extends RecyclerView.Adapter<ordertwoAdapter.viewholder> {

//    //订单未被接 初始状态
//	'ORDER_STATUS_NON_RECEIVE'=>1,
//            //订单被花店拒接
//            'ORDER_STATUS_REFUSE'=>2,
//            //订单请求涨价
//            'ORDER_STATUS_AMOUNT_ADD'=>3,
//            //花店接收未配送
//            'ORDER_STATUS_RECEIVE_NON_DELIVERY'=>10,
//            //花店配送中 待签收
//            'ORDER_STATUS_DELIVERING'=>20,
//            //订单已签收 待结算
//            'ORDER_STATUS_SIGN'=>30,
//            //订单已结算
//            'ORDER_STATUS_BALANCE'=>40,
//            //花店接单后申请退单
//            'ORDER_STATUS_CANCEL'=>11,
//            //管理员指定花店后主动申请退单
//            'ORDER_STATUS_CANCEL_ADMIN'=>12,


    Context context;
    List<ordertwoData> list;
    tixijieshuan txjs;

    public void settxjs(tixijieshuan txjs) {
        this.txjs = txjs;
    }

    public ordertwoAdapter(Context context,
                           List<ordertwoData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ordertwoadapter, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {
        final ordertwoData data = list.get(position);
        holder.ordertwoitTebh.setText("    订单编号 :  " + data.getDdh());

        holder.ordertwoitTetime.setText("    配送时间 :  " + data.getTime());
        holder.ordertwoitTetime1.setText(data.getTime1());
        holder.ordertwoitTeprice.setText(data.getPrice() + "元");
        holder.ordertwoitTedizhi.setText(data.getAddress() + " " + data.getAddress1());

        holder.te_djd.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (data.getStatus().equals("1")||data.getStatus().equals("2")||data.getStatus().equals("3")) {
            holder.lin_djdf.setVisibility(View.GONE);
            holder.lin_djd.setVisibility(View.VISIBLE);
        } else {
            holder.lin_djdf.setVisibility(View.VISIBLE);
            holder.lin_djd.setVisibility(View.GONE);
        }

        if (position == list.size() - 1) {
            holder.v_kb.setVisibility(View.GONE);
        } else holder.v_kb.setVisibility(View.VISIBLE);
        holder.ordertwoitTeshren.setText("收货人信息 :   " + data.getShrname());
        holder.ordertwoitTepho.setText(data.getShrpho());

        holder.ordertwoitTezt.setText(data.getStatustxt());
//        if (data.getStatus().equals("1") || data.getStatus().equals("2") || data.getStatus().equals("3") || data.getStatus().equals("10"))
//            holder.ordertwoitTezt.setText("待接单");
//        else if (data.getStatus().equals("2"))
//            holder.ordertwoitTezt.setText("拒绝接单");
//        else if (data.getStatus().equals("3"))
//            holder.ordertwoitTezt.setText("请求涨价");
//        else if (data.getStatus().equals("10"))
//            holder.ordertwoitTezt.setText("待配送");
//        else if (data.getStatus().equals("20"))
//            holder.ordertwoitTezt.setText("待签收");
//        else if (data.getStatus().equals("30"))
//            holder.ordertwoitTezt.setText("待结算");
//        else if (data.getStatus().equals("40"))
//            holder.ordertwoitTezt.setText("已完成");
//        else if (data.getStatus().equals("11"))
//            holder.ordertwoitTezt.setText("花店申请退单");
//        else if (data.getStatus().equals("12"))
//            holder.ordertwoitTezt.setText("下单方申请退单");

        if (data.getStatus().equals("30")) {
//            holder.tejs.setVisibility(View.VISIBLE);
            holder.tejs.setVisibility(View.GONE);
            if (data.getIstixin().equals("0")) {
                holder.tejs.setBackgroundColor(context.getResources().getColor(R.color.zhu));
            } else {
                holder.tejs.setBackgroundColor(context.getResources().getColor(R.color.hei777));
            }
        } else {
            holder.tejs.setVisibility(View.GONE);
        }

        holder.ordertwoitImpho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                        + data.getShrpho()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(context, Orderdatels.class);
                i.putExtra("id", data.getId());
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        holder.tejs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getStatus().equals("30")) {
                    if (data.getIstixin().equals("0")) {
                        txjs.tixijieshuan(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.ordertwoit_tebh)
        TextView ordertwoitTebh;
        @BindView(R.id.ordertwoit_tezt)
        TextView ordertwoitTezt;
        @BindView(R.id.ordertwoit_texq)
        TextView ordertwoitTexq;
        @BindView(R.id.ordertwoit_tetime)
        TextView ordertwoitTetime;
        @BindView(R.id.ordertwoit_tetime1)
        TextView ordertwoitTetime1;
        @BindView(R.id.ordertwoit_teprice)
        TextView ordertwoitTeprice;
        @BindView(R.id.ordertwoit_tedizhi)
        TextView ordertwoitTedizhi;
        @BindView(R.id.ordertwoit_teshren)
        TextView ordertwoitTeshren;
        @BindView(R.id.ordertwoit_tepho)
        TextView ordertwoitTepho;
        @BindView(R.id.ordertwoit_impho)
        ImageView ordertwoitImpho;
        @BindView(R.id.ordertwoit_lin)
        LinearLayout linearLayout;

        @BindView(R.id.ordertwoit_tetxjs)
        TextView tejs;

        @BindView(R.id.ordertwoit_djdf)
        LinearLayout lin_djdf;
        @BindView(R.id.ordertwoit_djd)
        LinearLayout lin_djd;
        @BindView(R.id.ordertwoit_tedjd)
        TextView te_djd;

        @BindView(R.id.ordertwoit_viwkb)
        View v_kb;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface tixijieshuan {
        void tixijieshuan(int pos);
    }

}
