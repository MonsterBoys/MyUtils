package com.lock.lifesensexu.myapputils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * Created by lifesensexu on 16/9/6.
 */
public class RoundedBitmapCenterDisplayer implements BitmapDisplayer {

    protected final int cornerRadius;
    protected final int margin;

    public RoundedBitmapCenterDisplayer(int cornerRadiusPixels) {
        this(cornerRadiusPixels, 0);
    }

    public RoundedBitmapCenterDisplayer(int cornerRadiusPixels, int marginPixels) {
        this.cornerRadius = cornerRadiusPixels;
        this.margin = marginPixels;
    }

    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom, String memoryCacheKey) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }

        imageAware.setImageDrawable(new RoundedDrawable(bitmap, cornerRadius, margin));
    }


    public Bitmap getDisplayerBitmap(Bitmap bitmap) {
        return bitmap;
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {

    }

    public static class RoundedDrawable extends Drawable {

        protected final float cornerRadius;
        protected final int margin;

        protected final RectF mRect = new RectF(),
                mBitmapRect;
        protected final BitmapShader bitmapShader;
        protected final Paint paint;

        public RoundedDrawable(Bitmap bitmap, int cornerRadius, int margin) {
            this.cornerRadius = cornerRadius;
            this.margin = margin;

            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapRect = new RectF (margin, margin, bitmap.getWidth() - margin, bitmap.getHeight() - margin);

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(bitmapShader);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mRect.set(margin, margin, bounds.width() - margin, bounds.height() - margin);

            int dwidth = (int)( mBitmapRect.right - mBitmapRect.left);
            int dheight = (int)( mBitmapRect.bottom - mBitmapRect.top);

            int vwidth = (int)( mRect.right - mRect.left);
            int vheight = (int)( mRect.bottom - mRect.top);

            // Resize the original bitmap to fit the new bound
            Matrix shaderMatrix = new Matrix();
            shaderMatrix.setTranslate(Math.round((vwidth - dwidth) * 0.5f),
                    Math.round((vheight - dheight) * 0.5f));
//			shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.CENTER);

            bitmapShader.setLocalMatrix(shaderMatrix);

        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }
    }
}