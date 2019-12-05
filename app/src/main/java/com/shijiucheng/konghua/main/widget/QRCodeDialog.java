package com.shijiucheng.konghua.main.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.main.Utils.StringUtils;

public class QRCodeDialog extends Dialog {

    private Context context;
    private onDismissListener dialogOnClick;    //对话框操作监听

    public QRCodeDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.item_pop_save_qrcode);
        RelativeLayout water_mark = findViewById(R.id.water_mark);

        String text1 = "完成绑定后";
        String text2 = "不再出现该提示";
        float w1 = (float) (StringUtils.getTextWidth(context, text2, 12) * Math.sin(45));

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) w1, (int) w1);
        lp.setMargins(0, 0, 0, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        water_mark.setLayoutParams(lp);

        water_mark.setBackground(new WaterMarkBg(context, text1, text2));

        findViewById(R.id.pop_close).setOnClickListener(v -> {
            dialogOnClick.onDismissListener(v);
            dismiss();
        });
        findViewById(R.id.pop_to_wechart).setOnLongClickListener(v -> {
            dialogOnClick.onConfirmListener(v);
            return true;
        });

        //处理返回键
        setOnKeyListener((arg0, arg1, arg2) -> {
            if (arg1 == KeyEvent.KEYCODE_BACK) {
            }
            return true;
        });
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    public void setDialogOnClick(onDismissListener dialogOnClick) {
        this.dialogOnClick = dialogOnClick;
    }

    public static class Builder {

        private QRCodeDialog mDialog;

        public Builder(Context context) {
            mDialog = new QRCodeDialog(context);
        }

        public Builder setContext(Context context) {
            mDialog.context = context;
            return this;
        }

        public Builder setDialogOnClick(onDismissListener dialogOnClick) {
            mDialog.setDialogOnClick(dialogOnClick);
            return this;
        }

        /**
         * ͨ 通过Builder类设置完属性后构造对话框的方法
         */
        public QRCodeDialog create() {
            return mDialog;
        }
    }

    @Override
    public void show() {
        super.show();
    }


    public interface onDismissListener {
        void onDismissListener(View view);

        void onConfirmListener(View view);
    }
}
