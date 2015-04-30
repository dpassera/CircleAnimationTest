package com.smashingboxes.circleanimationtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    private static final String LOG_TAG = "MainActivity";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCircles();
        startLoop();
        addTapListener();

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void initCircles() {
        mCircleIds.add(R.id.circle_p);
        mCircleIds.add(R.id.circle_q);
        mCircleIds.add(R.id.circle_r);
        mCircleIds.add(R.id.circle_s);
        mCircleIds.add(R.id.circle_t);

        for(int i=0; i< mCircleIds.size(); i++) {
            findViewById(mCircleIds.get(i)).setVisibility(View.INVISIBLE);
        }
    }

    private void startLoop() {
        final Handler handler = new BeatHandler();

        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(BeatHandler.BEAT);
            }
        };

        mTimer.scheduleAtFixedRate(mTimerTask, 0, MINUTE/BPM/PEAKS_PER_BEAT);
    }

    private void addTapListener() {
        RelativeLayout view = (RelativeLayout) findViewById(R.id.main_view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scaleCircle(findViewById(R.id.circle_p), 0f, 1f, 1000);
                //TODO: step through BPMs
            }
        });
    }

    private void scaleCircle(View circle, float startScale, float endScale, long dur, boolean doScaleDown) {
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

    public class BeatHandler extends Handler
    {
        public static final int BEAT = 1;
        private static final long dur = 500;

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case BEAT:
                    float endScale = PEAKS[mBeatCount]/MAX_PEAK;
                    boolean doScaleDown = false;
                    if(mBeatCount == 2) {
                        doScaleDown = true;
                    }
                    scaleCircle(findViewById(mCircleIds.get(mBeatCount)),
                            0, endScale, dur, doScaleDown);
                    mBeatCount ++;
                    if(mBeatCount == PEAKS_PER_BEAT) {
                        mBeatCount = 0;
                    }
                    break;
            }
        }
    }
}

