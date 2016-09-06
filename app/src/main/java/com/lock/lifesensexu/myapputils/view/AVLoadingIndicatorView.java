package com.lock.lifesensexu.myapputils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lock.lifesensexu.myapputils.R;
import com.lock.lifesensexu.myapputils.UiUtils;

/**
 * Created by luoxf on 2016/1/14.
 */
public class AVLoadingIndicatorView extends View {
    private Context mContext;
    //Sizes (with defaults in DP)
    public static final int DEFAULT_SIZE = 26;

    int mIndicatorColor;

    Paint mDeepPaint, mLightPaint;

    BaseIndicatorController mIndicatorController;

    private boolean mHasAnimation;


    public AVLoadingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            applyAnimation();
        } else {
            cancelAnimation();
        }

    }

    private void init(AttributeSet attrs) {
        mIndicatorColor = mContext.getResources().getColor(R.color.main_pull_deep);
        mDeepPaint = new Paint();
        mDeepPaint.setColor(mIndicatorColor);
        mDeepPaint.setStyle(Paint.Style.FILL);
        mDeepPaint.setAntiAlias(true);

        mLightPaint = new Paint();
        mLightPaint.setColor(mContext.getResources().getColor(R.color.main_pull_light));
        mLightPaint.setStyle(Paint.Style.FILL);
        mLightPaint.setAntiAlias(true);
        applyIndicator();
    }

    private void applyIndicator() {
        mIndicatorController = new BallPulseIndicator();
        mIndicatorController.setTarget(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(UiUtils.dipToPx(mContext, DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(UiUtils.dipToPx(mContext, DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mHasAnimation) {
            mHasAnimation = true;
//            applyAnimation();
        }
    }

    void drawIndicator(Canvas canvas) {
        mIndicatorController.draw(canvas, mDeepPaint, mLightPaint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIndicatorController.destroyAnimation();
    }


    void applyAnimation() {
        if (mIndicatorController != null) {
            mIndicatorController.createAnimation();
        }
    }

    void cancelAnimation() {
        if (mIndicatorController != null) {
            mIndicatorController.destroyAnimation();
        }
    }

}
