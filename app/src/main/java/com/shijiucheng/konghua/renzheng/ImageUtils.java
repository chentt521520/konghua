package com.shijiucheng.konghua.renzheng;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.shijiucheng.konghua.main.per.payandget.gongdan.check_pic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fuh on 2017/5/4.
 * Email：unableApe@gmail.com
 */

public class ImageUtils {

    /**
     * ImageView设置优化内存使用后的Bitmap
     * 返回一个等同于ImageView宽高的bitmap
     *
     * @param view    ImageView
     * @param imgPath 图像路径
     */
    @SuppressWarnings("deprecation")
    public static void imageViewSetPic(ImageView view, String imgPath) {
        // Get the dimensions of the View
        int targetW = view.getWidth();
        int targetH = view.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, bmOptions);
        view.setImageBitmap(bitmap);
    }

    public static boolean saveImageToGallery(Activity activity, Bitmap bitmap) {
//        String PATH = Environment.getExternalStorageDirectory() + File.separator + "HuaMai";
        File appDir = new File(Environment.getExternalStorageDirectory(), "花脉保存的图片");
        // 首先保存图片
//        File appDir = new File(appDir, "album");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(activity.getContentResolver(), file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            if (Build.VERSION.SDK_INT > 23) { // 判断SDK版本是不是4.4或者高于4.4
                String[] paths = new String[]{file.getAbsolutePath()};
                MediaScannerConnection.scanFile(activity, paths, null, null);
            } else {
                final Intent intent;
                if (file.isDirectory()) {
                    intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
                    intent.setClassName("com.android.providers.media", "com.android.providers.media.MediaScannerReceiver");
                    intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
                } else {
                    intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(file));
                }
                activity.sendBroadcast(intent);
            }
        }
        return true;
    }

}
