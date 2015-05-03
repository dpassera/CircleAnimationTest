package com.smashingboxes.circleanimationtest.drawable;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by dantepassera on 5/1/15.
 */
public class AnimatedRing extends ShapeDrawable {

    public static final String PROP_STROKE_WIDTH = "stroke";

    private static final String LOG_TAG = "AnimatedRing";

    private static final float DEFAULT_INITIAL_IRAD = 180f;
    private static final float DEFAULT_INITIAL_STROKE_WIDTH = 0f;
    private static final float DEFAULT_CY = 690f;
    private static final boolean DEFAULT_IS_FIXED_IRAD = true;

    private float mIRad;
    private int mId;
    private ArrayList<View> mRViewArr = new ArrayList<View>();

    public AnimatedRing(int id, int color, ArrayList<View> rViewArr) {
        this(id, DEFAULT_IS_FIXED_IRAD, DEFAULT_INITIAL_IRAD, DEFAULT_INITIAL_STROKE_WIDTH, color, rViewArr);
    }

    public AnimatedRing(int id, boolean fixedIRad, float iRad, float strokeW, int color, ArrayList<View> rViewArr) {
        mId = id;
        mIRad = iRad;
        mRViewArr = rViewArr;

        setShape(getRing(fixedIRad, strokeW));
        getPaint().setStyle(Paint.Style.STROKE);
//        getPaint().setColor(color);
        getPaint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
    }

    public void animateStroke(float width, long dur) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat(PROP_STROKE_WIDTH, 1f, width);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, holder1);
        animator.setDuration(dur);
        animator.addListener(mAnimatorListener);
        animator.start();

        AlphaAnimation aAnim = new AlphaAnimation(1f, 0f);
        aAnim.setDuration((long)(dur*0.25));
        aAnim.setFillAfter(true);
        aAnim.setStartOffset((long)(dur*0.75));
        ((View)(mRViewArr.get(mId))).startAnimation(aAnim);
    }

    public void animateRadius(float radius, long dur) {

    }

    private Shape getRing(boolean fixedIRad, final float strokeW) {
        return new Shape() {

            @Override
            public void draw(Canvas canvas, Paint paint) {
//                Log.d(LOG_TAG, "! draw : "+mId);
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

    private Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
//            Log.d(LOG_TAG, "! onAnimationEnd : "+mId);
            if(mId > 0) {
                View view = mRViewArr.get(mId-1);
                ((RelativeLayout)view.getParent()).removeView(view);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

}
