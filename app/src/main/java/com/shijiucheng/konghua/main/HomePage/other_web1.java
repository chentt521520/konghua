package com.shijiucheng.konghua.main.HomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gyf.barlibrary.ImmersionBar;
import com.shijiucheng.konghua.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

public class other_web1 extends Activity {
    @ViewInject(R.id.ddanxq_retop)
    RelativeLayout re_top;
    @ViewInject(R.id.ddanxq_imreturn)
    ImageView im_return;
    @ViewInject(R.id.ddanxq_imcha)
    ImageView im_cha;
    @ViewInject(R.id.ddanxq_web)
    WebView wb;

    @ViewInject(R.id.ddanxq_tetit)
    TextView te_tit;

    @ViewInject(R.id.progressBar1)
    ProgressBar pbar;

    String url = "", titl = "";
    AudioManager mAudioManager;//音量管理
    int num = 0;
    int nummax = 0;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_web1);
        ImmersionBar.with(this).statusBarColor(R.color.zhu).statusBarDarkFont(false, 0.0f).fitsSystemWindows(true).init();

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        num = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        nummax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        x.view().inject(this);
        setviewdata();
        setviewweb();
        setviewhw();
        setviewlisten();
    }

    private void setviewdata() {
        Intent i = getIntent();
        titl = i.getStringExtra("titl");
        te_tit.setText(titl);
        url = i.getStringExtra("url");
    }

    private void setviewlisten() {
        im_return.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (wb.canGoBack()) {
                    wb.goBack();
                } else {
                    finish();
                    overridePendingTransition(R.anim.push_right_out,
                            R.anim.push_right_in);
                }

            }
        });

        im_cha.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                wb.setEnabled(false);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                wb.loadUrl(url);
                return false;
            }
        });
        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if (newProgress == 100) {
                    pbar.setVisibility(View.GONE);// 加载完网页进度条消失
                } else {
                    pbar.setVisibility(View.VISIBLE);// 开始加载网页时显示进度条
                    pbar.setProgress(newProgress);// 设置进度值
                }

            }
        });

    }

    private void setviewhw() {

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        setviewhw_re(re_top, w, (int) (w * 55 / 450.0), 0, 0, 0, 0);
        setviewhw_re(im_return, (int) (w * 49 / 450.0), (int) (w * 25 / 450.0),
                0, (int) (w * 15 / 450.0), 0, 0);
        im_return.setPadding((int) (w * 12 / 450.0), 0, (int) (w * 12 / 450.0),
                0);
        setviewhw_re(im_cha, (int) (w * 49 / 450.0), (int) (w * 25 / 450.0),
                (int) (w * 49 / 450.0), (int) (w * 15 / 450.0), 0, 0);
        im_cha.setPadding((int) (w * 12 / 450.0), 0, (int) (w * 12 / 450.0), 0);
    }

    private void setviewhw_re(View v, int width, int height, int left, int top,
                              int right, int bottom) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,
                height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK && wb.canGoBack()) {
            wb.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (num >= 1)
                num--;
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, num, AudioManager.FLAG_SHOW_UI);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (num < nummax)
                num++;
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, num, AudioManager.FLAG_SHOW_UI);
            return true;
        } else {
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
            return false;
        }
    }

    private void setviewweb() {
        wb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        WebSettings settings = wb.getSettings();
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wb.getSettings().setDomStorageEnabled(true);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setBlockNetworkImage(false);
        wb.setVerticalScrollBarEnabled(false);
        wb.setHorizontalScrollBarEnabled(false);
        wb.getSettings().setLoadWithOverviewMode(true);
        HashMap<String, String> map = new HashMap<>();
        map.put("cook", "PHPSESSID=864895027854338");
        wb.loadUrl(url);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        wb.removeAllViews();
        wb.destroy();
    }
}
