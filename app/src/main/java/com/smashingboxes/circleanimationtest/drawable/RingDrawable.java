package com.smashingboxes.circleanimationtest.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

import com.smashingboxes.circleanimationtest.R;

/**
 * Created by dantepassera on 5/1/15.
 */
public class RingDrawable extends ShapeDrawable {

    private static final String PROP_ARC_WIDTH = "stroke";

    private static final float DEFAULT_INITIAL_IRAD = 100f;
    private static final float DEFAULT_INITIAL_STROKE_WIDTH = 0f;
    private static final boolean DEFAULT_IS_FIXED_IRAD = true;

//    private Context mContext;

    public RingDrawable(Context context) {
        this(context, DEFAULT_IS_FIXED_IRAD, DEFAULT_INITIAL_IRAD, DEFAULT_INITIAL_STROKE_WIDTH);
    }

    public RingDrawable(Context context, boolean fixedIRad, float iRad, float strokeW) {
//        mContext = context;

        setShape(getRing(fixedIRad, iRad, strokeW));
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setColor(context.getResources().getColor(R.color.red50));
    }

    private Shape getRing(boolean fixedIRad, final float iRad, final float strokeW) {
        return new Shape() {

            @Override
            public void draw(Canvas canvas, Paint paint) {
               canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, iRad, paint);
            }
        };
    }

    public void setStrokeWidth(float strokeW) {
        getPaint().setStrokeWidth(strokeW);
        invalidateSelf();
    }

    public float getStrokeWidth() {
        return getPaint().getStrokeWidth();
    }

}
