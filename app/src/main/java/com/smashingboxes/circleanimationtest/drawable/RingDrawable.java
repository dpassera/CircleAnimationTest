package com.smashingboxes.circleanimationtest.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by dantepassera on 5/1/15.
 */
public class RingDrawable extends ShapeDrawable {

    private static final String PROP_ARC_WIDTH = "stroke";

    private static final float DEFAULT_INITIAL_IRAD = 40f;
    private static final float DEFAULT_INITIAL_ARC_WIDTH = 0f;
    private static final boolean DEFAULT_IS_FIXED_IRAD = true;

    public RingDrawable() {
        this(DEFAULT_IS_FIXED_IRAD, DEFAULT_INITIAL_IRAD, DEFAULT_INITIAL_ARC_WIDTH);
    }

    public RingDrawable(boolean fixedIRad, float iRad, float arcW) {
        setShape(getRing(fixedIRad, iRad, arcW));
        getPaint().setStyle(Paint.Style.STROKE);
    }

    private Shape getRing(boolean fixedIRad, final float iRad, final float arcW) {
        return new Shape() {

            @Override
            public void draw(Canvas canvas, Paint paint) {
               canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, iRad, paint);
            }
        };
    }

    public void setArcWidth(float arcW) {
        getPaint().setStrokeWidth(arcW);
        invalidateSelf();
    }

    public float getArcWidth() {
        return getPaint().getStrokeWidth();
    }

}
