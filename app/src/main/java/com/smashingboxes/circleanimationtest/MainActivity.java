package com.smashingboxes.circleanimationtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smashingboxes.circleanimationtest.drawable.RingDrawable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * THIS CODE IS FOR QUICK EXPERIMENTATION!
 * BEST PRACTICES AND CLARITY ARE OUT THE WINDOW!
 * ANYTHING GOES!
 */

public class MainActivity extends Activity {

    private static final String LOG_TAG = "MainActivity";
    private static final String ANIM_TYPE = "B";

    private static final float MAX_PEAK = 10f;
    // one minute in ms
    private static final int MINUTE = 60000;
    private static final int PEAKS_PER_BEAT = 5;

    private int BPM = 60; // 60 | 100 | 200
    private ArrayList<Integer> mCircleIds = new ArrayList<>();
    private int mBeatCount = 0;
    // dummy peak amplitudes
    private float[] PEAKS = {0.125f*MAX_PEAK, -0.125f*MAX_PEAK, 1*MAX_PEAK, -0.25f*MAX_PEAK, 0.1875f*MAX_PEAK};
    private Timer mTimer;
    private TimerTask mTimerTask;

    private RelativeLayout mCirclesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setImmersiveMode();
        if(ANIM_TYPE == "A") {
//            initCircles();
        } else if(ANIM_TYPE == "B") {
            mCirclesContainer = (RelativeLayout) findViewById(R.id.expand_circles_container);
        }
        initText();
//        startLoop(); // move to onCreate?
        expandCircle(3);
    }

    private void setImmersiveMode() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    /*private void initCircles() {
        mCircleIds.add(R.id.circle_p);
        mCircleIds.add(R.id.circle_q);
        mCircleIds.add(R.id.circle_r);
        mCircleIds.add(R.id.circle_s);
        mCircleIds.add(R.id.circle_t);

        for(int i=0; i< mCircleIds.size(); i++) {
            findViewById(mCircleIds.get(i)).setVisibility(View.INVISIBLE);
        }
    }*/

    private void initText() {
        CircleTestApplication app = (CircleTestApplication) getApplication();
        final Typeface tfRobotoBk = app.getCustomTypeface(app.TF_ROBOTO_BK);
        TextView tBPM = (TextView) findViewById(R.id.t_bpm);

        tBPM.setTypeface(tfRobotoBk);
    }

    private void startLoop() {
        final Handler handler = new BeatHandler();

        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                switch (ANIM_TYPE) {
                    case "A":
                        handler.sendEmptyMessage(BeatHandler.A);
                        break;
                    case "B":
                        handler.sendEmptyMessage(BeatHandler.B);
                }
            }
        };

        mTimer.scheduleAtFixedRate(mTimerTask, 0, MINUTE / BPM / PEAKS_PER_BEAT);
    }

    private void pulseCircle(View circle, float startScale, float endScale, long dur, boolean doScaleDown) {
        AnimationSet set = new AnimationSet(true);

        ScaleAnimation scaleUp = new ScaleAnimation(
                startScale, endScale,
                startScale, endScale,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        if(doScaleDown) {
            scaleUp.setDuration((long)(dur*0.75));
        } else {
            scaleUp.setDuration(dur);
        }
        set.addAnimation(scaleUp);

        if(doScaleDown) {
            ScaleAnimation scaleDown = new ScaleAnimation(
                    endScale, startScale,
                    endScale, startScale,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            scaleDown.setDuration((long)(dur*0.25));
            scaleDown.setStartOffset((long)(dur*0.75));
            set.addAnimation(scaleDown);
        }

        set.setFillBefore(true);
        set.setFillEnabled(true);
        set.setFillAfter(true);
        set.setInterpolator(new AccelerateDecelerateInterpolator());

        circle.setVisibility(View.VISIBLE);
        circle.startAnimation(set);
    }

    private void expandCircle(int which) {
        // create view
        View view = new View(this);
        view.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));

        mCirclesContainer.addView(view);

        // add drawable to view background
        view.setBackground(new RingDrawable(this));

        RingDrawable rd = (RingDrawable)view.getBackground();
        rd.setStrokeWidth(120f);

        if(which == 3) {
            // fixed inner-radius
        } else {
            // fixed outer-radius
        }
    }

    public class BeatHandler extends Handler
    {
        public static final int A = 0;
        public static final int B = 1;
        private static final long dur = 500;

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case A:
                    float endScale = PEAKS[mBeatCount]/MAX_PEAK;
                    boolean doScaleDown = false;
                    if(mBeatCount == 2) {
                        doScaleDown = true;
                    }
                    pulseCircle(findViewById(mCircleIds.get(mBeatCount)),
                            0, endScale, dur, doScaleDown);
                    mBeatCount ++;
                    if(mBeatCount == PEAKS_PER_BEAT) {
                        mBeatCount = 0;
                    }
                    break;
                case B:
                    expandCircle(mBeatCount);
                    mBeatCount ++;
                    if(mBeatCount == PEAKS_PER_BEAT) {
                        mBeatCount = 0;
                    }
                    break;
            }
        }
    }
}

