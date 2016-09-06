package com.lock.lifesensexu.myapputils.view;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by luoxf on 2016/1/14.
 */
public class BallPulseIndicator extends BaseIndicatorController {

    public static final float SCALE = 1.0f;

    //scale x ,y
    private float[] scaleFloats = new float[]{SCALE,
            SCALE,
            SCALE};


    @Override
    public void draw(Canvas canvas, Paint deepPaint, Paint lightPaint) {
        float circleSpacing = 4;
        float radius = (Math.min(getWidth(), getHeight()) - circleSpacing * 2) / 6;
        float x = getWidth() / 2 - (radius * 2 + circleSpacing);
        float y = getHeight() / 2;
        for (int i = 0; i < 3; i++) {
            canvas.save();
            float translateX = x + (radius * 2) * i + circleSpacing * i;
            canvas.translate(translateX, y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            if (scaleFloats[i] >= 1f) {
                canvas.drawCircle(0, 0, radius, lightPaint);
            } else {
                canvas.drawCircle(0, 0, radius, deepPaint);
            }
            canvas.restore();
        }
    }

    @Override
    public void createAnimation() {
        int[] delays = new int[]{120, 240, 360};
        destroyAnimation();
        scaleAnims = new ValueAnimator[3];
        for (int i = 0; i < 3; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);
            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (Float) animation.getAnimatedValue();
                    postInvalidate();

                }
            });
            scaleAnim.start();
            scaleAnims[i] = scaleAnim;
        }
    }

    private ValueAnimator[] scaleAnims;

    @Override
    public void destroyAnimation() {
        if (scaleAnims != null && scaleAnims.length > 0) {
            for (int i = 0; i < scaleAnims.length; i++) {
                if (scaleAnims[i] != null)
                    scaleAnims[i].cancel();
                scaleAnims[i] = null;

            }
        }
    }

}
