package com.shijiucheng.konghua.com.shijiucheng.konghua.app.lunbo;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.shijiucheng.konghua.R;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param context
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		imageView.setScaleType(ScaleType.FIT_XY);
		Glide.with(context.getApplicationContext()).load(url).into(imageView);
		return imageView;
	}
}
