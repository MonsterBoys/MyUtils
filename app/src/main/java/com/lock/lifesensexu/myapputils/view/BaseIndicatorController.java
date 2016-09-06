package com.lock.lifesensexu.myapputils.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.lang.ref.SoftReference;

/**
 * Created by luoxf on 2016/1/14.
 */
public abstract class BaseIndicatorController {

    private SoftReference<View> mTarget;


    public void setTarget(View target) {
        this.mTarget = new SoftReference<View>(target);
    }

    public View getTarget() {
        if (mTarget != null) {


            return mTarget.get();
        }
        return null;
    }


    public int getWidth() {
        if (getTarget() != null) {
            return getTarget().getWidth();
        }
        return 0;
    }

    public int getHeight() {
        if (getTarget() != null) {
            return getTarget().getHeight();
        }
        return 0;
    }

    public void postInvalidate() {
        if (getTarget() != null) {
            getTarget().postInvalidate();
        }
    }

    /**
     * draw indicator what ever
     * you want to draw
     *
     * @param canvas
     */
    public abstract void draw(Canvas canvas, Paint deepPaint, Paint lightPaint);

    /**
     * create animation or animations
     * ,and add to your indicator.
     */
    public abstract void createAnimation();

    public abstract void destroyAnimation();


}

