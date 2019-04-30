package com.shijiucheng.konghua;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Banben_ extends DialogFragment {
    @BindView(R.id.bbgx_txt)
    TextView bbgxTxt;
    @BindView(R.id.bbgx_gx)
    TextView bbgxGx;
    @BindView(R.id.bbgx_fl)
    TextView bbgxFl;
    Unbinder unbinder;

    boolean tt = false;
    @BindView(R.id.bbgx_progress)
    ProgressBar bbgxProgress;
    @BindView(R.id.bbgx_tejd)
    TextView bbgxTejd;
    @BindView(R.id.bbgx_linjdt)
    LinearLayout bbgxLinjdt;

    String txt = "", type_ = "", url = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.banben_, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        txt = bundle.getString("txt");
        type_ = bundle.getString("type");
        url = bundle.getString("url");
        bbgxTxt.setText(txt);
        setViewListen();
        return view;
    }

    private void setViewListen() {
        bbgxGx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bbgxLinjdt.setVisibility(View.VISIBLE);
                dowload(url);

//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);

            }
        });
        bbgxFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuluebanben fuluebanben = (Banben_.fuluebanben) getActivity();
                fuluebanben.fuluebanben();
                dismiss();
            }
        });
    }

    public void dowload(String url) {
        RequestParams params = new RequestParams(url);
        params.setAutoRename(true);// 断点下载
        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/JuanDie.apk");
        x.http().get(params, new org.xutils.common.Callback.ProgressCallback<File>() {
            @Override
            public void onCancelled(org.xutils.common.Callback.CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                dismiss();
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(File arg0) {
                Toast.makeText(getActivity(), "下载成功" + arg0.getTotalSpace(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//                    Uri apkUri =
//                            FileProvider.getUriForFile(getActivity(), "com.shijiucheng.konghua.fileprovider", arg0);
//                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//                } else {
//                    intent.setDataAndType(Uri.fromFile(new File(Environment
//                                    .getExternalStorageDirectory(), "JuanDie.apk")),
//                            "application/vnd.android.package-archive");
//                }
//                startActivity(intent);

                fuluebanben fl = (fuluebanben) getActivity();
                fl.installapk(arg0);
            }

            @Override
            public void onLoading(long arg0, long arg1, boolean arg2) {
                int size = Integer.valueOf((int) arg0).intValue();
                int pro = Integer.valueOf((int) arg1).intValue();
                bbgxProgress.setMax(size);
                bbgxProgress.setProgress(pro);
                bbgxTejd.setText(pro * 100 / size + "%");
            }

            @Override
            public void onStarted() {
//                pbBar.setProgress(0);
            }

            @Override
            public void onWaiting() {
                // TODO Auto-generated method stub
            }
        });
    }

    public interface fuluebanben {
        void fuluebanben();

        void installapk(File file);
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        getDialog().getWindow().setLayout(w - 100, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        bbgxLinjdt.setVisibility(View.GONE);
        if (type_.equals("2")) {
            bbgxFl.setVisibility(View.GONE);
            bbgxFl.setBackgroundResource(R.drawable.biankuangyuanjiaodialogxia);
        } else bbgxFl.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
