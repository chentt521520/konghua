package com.shijiucheng.konghua.main.News_;

import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shijiucheng.konghua.R;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.BaseActivity_konghua;
import com.shijiucheng.konghua.com.shijiucheng.konghua.app.DaoHang_top;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Retrofit2.Retro_Intf;
import Retrofit2.retrofit_Single;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newsDetials extends BaseActivity_konghua {
    @BindView(R.id.newdet_dh)
    DaoHang_top dh;
    Retro_Intf service;
    @BindView(R.id.newsdet_web)
    WebView wb;
    int type = 0;


    @Override
    protected void AddView() {
        dh.settext_(getIntent().getStringExtra("tit"));
        service = retrofit_Single.getInstence().getserivce(2);
        if (getIntent().getStringExtra("tit").equals("站内信")) {
            type = 0;
            getdetils(0, getIntent().getStringExtra("id"));
        } else {
            type = 1;
            getdetils(1, getIntent().getStringExtra("id"));
        }
    }

    @Override
    protected void SetViewListen() {
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
                // TODO Auto-generated method stub
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                // view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_news_detials;
    }

    private void getdetils(final int type, String id) {
        HashMap<String, String> maps = new HashMap<>();
        if (type == 0) {
            maps.put("m_id", id);
        } else {
            maps.put("n_id", id);
        }
        maps.putAll(retrofit_Single.getInstence().retro_postParameter());
        Call<ResponseBody> call;
        if (type == 0) {
            call = service.getNeswDetils(retrofit_Single.getInstence().getOpenid(this), maps);
        } else call = service.getNoticeDetils(retrofit_Single.getInstence().getOpenid(this), maps);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    JSONObject jso = new JSONObject(response.body().string());
                    if (jso.getString("status").equals("1")) {
                        if (type == 0) {
                            setviewweb(jso.getJSONObject("data").getString("message_detail_url"));
                        } else {
                            setviewweb(jso.getJSONObject("data").getString("notice_detail_url"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                toaste_ut(newsDetials.this, t.getMessage().toString());
            }
        });
    }

    private void setviewweb(String url) {
        wb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        WebSettings settings = wb.getSettings();
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }// 解决高版本图片显示问题
        wb.getSettings().setDomStorageEnabled(true);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setBlockNetworkImage(false);
        wb.setVerticalScrollBarEnabled(false);
        wb.setHorizontalScrollBarEnabled(false);
        wb.getSettings().setLoadWithOverviewMode(true);
        HashMap<String, String> map = new HashMap<>();
        map.put("cook", "PHPSESSID=864895027854338");
        wb.loadUrl(url);
        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });
    }

}
