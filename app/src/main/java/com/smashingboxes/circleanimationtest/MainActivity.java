package com.smashingboxes.circleanimationtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        addTapListener();
    }

    private void addTapListener() {
        RelativeLayout view = (RelativeLayout) findViewById(R.id.main_view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleCircleX();
            }
        });
    };

    private void scaleCircle(View circle, float startScale, float endScale, long dur) {
        ScaleAnimation scale = new ScaleAnimation(
                startScale, endScale,
                startScale, endScale,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scale.setDuration(dur);

        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        alpha.setDuration(dur/2);
        alpha.setStartOffset(dur/2);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        set.setFillBefore(true);
        set.setFillAfter(true);
        set.setFillEnabled(true);
        set.setInterpolator(new AccelerateDecelerateInterpolator());

        circle.startAnimation(set);
    }

    private void scaleCircleX() {
        View circle = findViewById(R.id.circle_p);

        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleUp = new ScaleAnimation(
                0f, 1,
                0f, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleUp.setDuration(2000);

        ScaleAnimation scaleDown = new ScaleAnimation(
                1, 0,
                1, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleDown.setDuration(2000);

        AlphaAnimation alphaOut = new AlphaAnimation(1, 0);
        alphaOut.setDuration(900);
        alphaOut.setStartOffset(1000);

        animationSet.addAnimation(scaleUp);
        animationSet.addAnimation(alphaOut);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setFillEnabled(true);
//        animationSet.setRepeatCount(Animation.INFINITE);
//        animationSet.setRepeatMode(Animation.RESTART);
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());

        circle.startAnimation(animationSet);
    }
}
