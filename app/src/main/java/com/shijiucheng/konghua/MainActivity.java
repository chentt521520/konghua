package com.shijiucheng.konghua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Button;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created 2018/9/13 0013 17:14
 * author ldl
 * 接收消息处理页面
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
        Bundle xgnotification = this.getIntent().getExtras();
        button2.setText(xgnotification.getString("content"));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        startActivity(new Intent(MainActivity.this, com.shijiucheng.konghua.main.MainActivity.class));
        return super.onKeyDown(keyCode, event);

    }
}
