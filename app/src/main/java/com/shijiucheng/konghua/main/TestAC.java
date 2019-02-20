package com.shijiucheng.konghua.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shijiucheng.konghua.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestAC extends AppCompatActivity {
    @BindView(R.id.teste)
    ImageView testx;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ac);

        ButterKnife.bind(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        final int w_ = dm.widthPixels;

        testx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] position = new int[2];
                testx.getLocationInWindow  (position);//相对于窗口的坐标
                testx.getLocationOnScreen (position);//相对于屏幕的坐标
                Log.d("xxxxxxxxxxx","距离左边屏幕距离:"+position[0]+"距离上边屏幕距离:"+position[1]);
            }
        });
    }


    protected void setViewHw_Lin(View v, int width, int height, int left,
                                 int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }


}
