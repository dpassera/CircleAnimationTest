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

    private static final String PROP_STROKE_WIDTH = "stroke";

    private static final float DEFAULT_INITIAL_IRAD = 180f;
    private static final float DEFAULT_INITIAL_STROKE_WIDTH = 0f;
    private static final boolean DEFAULT_IS_FIXED_IRAD = true;

    private float mIRad;

//    private Context mContext;

    public RingDrawable(Context context) {
        this(context, DEFAULT_IS_FIXED_IRAD, DEFAULT_INITIAL_IRAD, DEFAULT_INITIAL_STROKE_WIDTH);
    }

    public RingDrawable(Context context, boolean fixedIRad, float iRad, float strokeW) {
//        mContext = context;
        mIRad = iRad;

        setShape(getRing(fixedIRad, strokeW));
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setColor(context.getResources().getColor(R.color.red50));
    }

    private Shape getRing(boolean fixedIRad, final float strokeW) {
        return new Shape() {

            @Override
            public void draw(Canvas canvas, Paint paint) {
               canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, mIRad, paint);
            }
        };
    }

    public void setStrokeWidth(float strokeW) {
        getPaint().setStrokeWidth(strokeW);
        mIRad += strokeW/2;
        invalidateSelf();
    }

    public float getStrokeWidth() {
        return getPaint().getStrokeWidth();
    }

}
