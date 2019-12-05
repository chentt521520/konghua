package com.shijiucheng.konghua.main.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.shijiucheng.konghua.R;

public class NoticeDialog extends Dialog {

    private Context context;
    private TextView message;
    private TextView btnLogin;
    private String msg;
    private String btnMsg;
    private DialogOnClick dialogOnClick;    //对话框操作监听

    public NoticeDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.dialog);

        message = findViewById(R.id.dialog_message);
        btnLogin = findViewById(R.id.dialog_login_btn);

        btnLogin.setOnClickListener(v -> dialogOnClick.onConfirm());
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    public void setDialogOnClick(DialogOnClick dialogOnClick) {
        this.dialogOnClick = dialogOnClick;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setBtnMsg(String btnMsg) {
        this.btnMsg = btnMsg;
    }

    public static class Builder {

        private NoticeDialog mDialog;

        public Builder(Context context) {
            mDialog = new NoticeDialog(context);
        }

        public Builder setContext(Context context) {
            mDialog.context = context;
            return this;
        }

        public Builder setDialogOnClick(DialogOnClick dialogOnClick) {
            mDialog.setDialogOnClick(dialogOnClick);
            return this;
        }

        public Builder setMsg(String msg) {
            mDialog.setMsg(msg);
            return this;
        }

        public Builder setBtn(String btn) {
            mDialog.setBtnMsg(btn);
            return this;
        }

        /**
         * ͨ 通过Builder类设置完属性后构造对话框的方法
         */
        public NoticeDialog create() {
            return mDialog;
        }
    }

    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(final NoticeDialog mDialog) {
        if (!TextUtils.isEmpty(msg)){
            mDialog.message.setText(msg);
        }
        if (!TextUtils.isEmpty(btnMsg)){
            mDialog.btnLogin.setText(btnMsg);
        }
    }
}
