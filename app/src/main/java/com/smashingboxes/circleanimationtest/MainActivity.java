package com.smashingboxes.circleanimationtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
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
        addTapListener();
    }

    private void addTapListener() {
        RelativeLayout view = (RelativeLayout) findViewById(R.id.main_view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleCircle(findViewById(R.id.circle_p), 0f, 1f, 1000);
            }
        });
    }

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
}
