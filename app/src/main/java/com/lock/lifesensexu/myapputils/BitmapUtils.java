package com.lock.lifesensexu.myapputils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by lifesensexu on 16/9/6.
 */
public class BitmapUtils {
    public static final int DEFAULT_MINI_RADIUS = 100;

    /**
     * 将图片截小成 {@link BitmapUtils#DEFAULT_MINI_RADIUS}*{@link BitmapUtils#DEFAULT_MINI_RADIUS}，因为如果图片过大时候会容易导致OOM
     *
     * @param bitmap 原图
     * @return 返回压缩后的图片
     */
    public static Bitmap small(Bitmap bitmap) {
        return small(bitmap, DEFAULT_MINI_RADIUS);
    }

    /**
     * @author xyc
     * @ClassName small
     * @Description 截成固定size
     */
    public static Bitmap small(Bitmap bitmap, int size) {
        if (size > DEFAULT_MINI_RADIUS) {
            size = DEFAULT_MINI_RADIUS;
        }
        return Bitmap.createScaledBitmap(bitmap, size, size, true);   //Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 柔化效果(高斯模糊)(优化后比上面快三倍)
     *
     * @param bmp
     * @return
     */
    public static Bitmap blurImageAmeliorate(Bitmap bmp) {
        long start = System.currentTimeMillis();
        // 高斯矩阵
        int[] gauss = new int[]{1, 2, 2, 3, 4, 3, 3, 4, 3};

        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int delta = 40; // 值越小图片会越亮，越大则越暗

        int idx = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * gauss[idx]);
                        newG = newG + (int) (pixG * gauss[idx]);
                        newB = newB + (int) (pixB * gauss[idx]);
                        idx++;
                    }
                }

                newR /= delta;
                newG /= delta;
                newB /= delta;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        long end = System.currentTimeMillis();
        Log.d("may", "used time=" + (end - start));
        return bitmap;
    }

    /**
     * 快速的生成有高斯模糊效果的图片同时设置到指定的view。
     *
     * @param bkg  原图
     * @param view 效果图要配置到的view
     */
    public static void blur(Bitmap bkg, ImageView view) {
        bkg = small(bkg);
        bkg = FastBlur.doBlur(bkg, (int) 2, false);
        view.setImageBitmap(bkg);
    }

    /**
     * 生成指定大小的高斯模糊效果图,最大不超过 10*10的比例的缩略图
     *
     * @param bkg
     * @param radius
     * @return
     */
    public static Bitmap blur(Bitmap bkg, int radius) {
        bkg = small(bkg);
        bkg = FastBlur.doBlur(bkg, radius, false);
        return bkg;
    }

    /**
     * 压缩成只有1*1的
     *
     * @param bitmap
     * @return
     */
    public static Bitmap smallest(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, 1, 1, true);
    }

    /**
     * 对一些特大的图片，请用这个，会卷积成接近一种颜色的效果图，应为解决的是1*1像素大小的
     *
     * @param bkg  原图
     * @param view 效果图要绑定的view
     */
    public static void smallBlur(Bitmap bkg, ImageView view) {

        bkg = smallest(bkg);
        bkg = FastBlur.doBlur(bkg, 16, false);
        view.setImageBitmap(bkg);
    }

    /**
     * 保存位图到本地
     */
    public static void saveBitmap2File(Bitmap bitmap, String filePath) {
        if (bitmap != null) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(filePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                StreamUtil.close(null, fos);
                bitmap.recycle();
            }
        }
    }
}
