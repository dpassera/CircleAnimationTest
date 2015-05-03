package com.smashingboxes.circleanimationtest.drawable;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;

import com.smashingboxes.circleanimationtest.R;

/**
 * Created by dantepassera on 5/1/15.
 */
public class RingDrawable extends ShapeDrawable {

    public static final String PROP_STROKE_WIDTH = "stroke";

    private static final String LOG_TAG = "RingDrawable";

    private static final float DEFAULT_INITIAL_IRAD = 180f;
    private static final float DEFAULT_INITIAL_STROKE_WIDTH = 0f;
    private static final float DEFAULT_CY = 690f;
    private static final boolean DEFAULT_IS_FIXED_IRAD = true;

    private float mIRad;

//    private Context mContext;

    public RingDrawable(Context context, int color) {
        this(context, DEFAULT_IS_FIXED_IRAD, DEFAULT_INITIAL_IRAD, DEFAULT_INITIAL_STROKE_WIDTH, color);
    }

    public RingDrawable(Context context, boolean fixedIRad, float iRad, float strokeW, int color) {
//        mContext = context;
        mIRad = iRad;

        setShape(getRing(fixedIRad, strokeW));
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setColor(color);
    }

    public void animateStroke(float width, long dur) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat(PROP_STROKE_WIDTH, 1f, width);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, holder1);
        animator.setDuration(dur);
        animator.start();
    }

    public void animateRadius(float radius, long dur) {

    }

    private Shape getRing(boolean fixedIRad, final float strokeW) {
        return new Shape() {

            @Override
            public void draw(Canvas canvas, Paint paint) {
               canvas.drawCircle(canvas.getWidth()/2, DEFAULT_CY, mIRad, paint);
            }
        };
    }

    public void setStroke(float strokeW) {
        getPaint().setStrokeWidth(strokeW);
        mIRad = DEFAULT_INITIAL_IRAD + strokeW/2;
        invalidateSelf();
    }

    public float getStroke() {
        return getPaint().getStrokeWidth();
    }

}
