package com.lock.lifesensexu.myapputils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by lifesensexu on 16/9/6.
 */
public class ImageUtils {
    public static DisplayImageOptions
            circleoptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.ARGB_8888)   //设置图片的解码类型
            .displayer(new CircleBitmapDisplayer())  // 设置成圆形图片  new Displayer(0) 圆形
            .build();

    public static void disableCircleImage(String url, ImageView img, int drawable, SimpleImageLoadingListener loadingListener) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable) // 设置图片下载期间显示的图片
                .showImageOnFail(drawable)    // 设置图片加载或解码过程中发生错误显示的图片
                .showImageForEmptyUri(drawable)  // 设置图片Uri为空或是错误的时候显示的图片
                .cacheOnDisk(true)        // 设置下载的图片是否缓存在SD卡中
                .cacheInMemory(true)  // 设置下载的图片是否缓存在内存中
                .displayer(new CircleBitmapDisplayer())
                .build();
        if (StringUtils.isEmptyOrNull(url) && url.equals("(null)") && url.equals("null")) {
            ImageLoader.getInstance().displayImage(url, img, options, loadingListener);
        } else {
            ImageLoader.getInstance().displayImage("drawable://" + drawable, img, circleoptions, loadingListener);
        }
    }
    public static void disableCircleImage(String url, ImageView img, int defDrawable, int roundedSize) {
        // 设置值
        DisplayImageOptions
                options = new DisplayImageOptions.Builder()
                .showImageOnLoading(defDrawable)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(defDrawable)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(defDrawable)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(roundedSize))  // 设置成圆角图片
                .build();                                   // 创建配置过得DisplayImageOption对象

        if (!StringUtils.isEmptyOrNull(url) && !url.equals("(null)") && !url.equals("null")) {
            ImageLoader.getInstance().displayImage(url, img, options);
        } else {
            ImageLoader.getInstance().displayImage("drawable://" + defDrawable,
                    img, options);
        }
    }


    /**
     * 显示原图
     *
     * @param url
     * @param img
     * @param drawable
     * @param loadingListener
     */
    public static void disableImage(String url, ImageView img, int drawable, SimpleImageLoadingListener loadingListener) {
        // 设置值
        DisplayImageOptions
                options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(drawable)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(drawable)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .build();                                   // 创建配置过得DisplayImageOption对象

        if (!StringUtils.isEmptyOrNull(url) && !url.equals("(null)") && !url.equals("null")) {
            ImageLoader.getInstance().displayImage(url, img, options, loadingListener);
        } else {
            ImageLoader.getInstance().displayImage("drawable://" + drawable,
                    img, circleoptions, loadingListener);
        }
    }
    /**
     * 加载圆角图片  没有默认的图片
     *
     * @param url
     * @param img
     */
    public static void disableImage(final String url, final ImageView img) {
        DisplayImageOptions
                options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)   //设置图片的解码类型
                .displayer(new RoundedBitmapDisplayer(2)) // 设置成圆角图片  new Displayer(0) 圆形
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoader.getInstance().displayImage(url, img, options);
    }
    /**
     * 加载矩形角度2的图片  没有默认的图片
     *
     * @param url
     * @param img
     */
    public static void disableRectangleImage(final String url, final ImageView img) {
        DisplayImageOptions
                options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)   //设置图片的解码类型
                .displayer(new RoundedBitmapCenterDisplayer( 2)) // 2dp圆角居中
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoader.getInstance().displayImage(url, img, options);
    }

}
