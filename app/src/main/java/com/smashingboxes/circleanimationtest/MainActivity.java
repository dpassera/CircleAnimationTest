package com.smashingboxes.circleanimationtest;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
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
    private static final int MSG_BEAT = 0;

    private static final float MAX_PEAK = 10f;
    // one minute in ms
    private static final int MINUTE = 60000;
    private static final int PEAKS_PER_BEAT = 5;

    private int BPM = 60; // 60 | 100 | 200
    private ArrayList<Integer> mCircleIds = new ArrayList<>();
    private int mBeatCount = 0;
    private int mRWhich = 1;
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
        initCirclesContainer();
        initText();
        startLoop(); // move to onCreate?
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

    private void initCirclesContainer() {
        mCirclesContainer = (RelativeLayout) findViewById(R.id.expand_circles_container);
        AlphaAnimation aAnim = new AlphaAnimation(1f, 0.3f);
        aAnim.setDuration(100);
        aAnim.setFillAfter(true);
        aAnim.start();
    }

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
               handler.sendEmptyMessage(MSG_BEAT);
            }
        };

        mTimer.scheduleAtFixedRate(mTimerTask, 0, MINUTE / BPM / PEAKS_PER_BEAT);
    }

    private void animateBeat(int which) {
        // create view
        View view = new View(this);
        view.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));

        mCirclesContainer.addView(view);

        // add drawable to view background
        int color = getResources().getColor(R.color.red50);
        switch(which) {
            case 3:
                if(mRWhich == 1) {
                    color = getResources().getColor(R.color.r_1);
                    mRWhich = 2;
                } else {
                    color = getResources().getColor(R.color.r_2);
                    mRWhich = 1;
                }
                break;
        }
        view.setBackground(new RingDrawable(this, color));

        RingDrawable rd = (RingDrawable)view.getBackground();
        rd.setStroke(0f);

        // animate
        if(which == 3) {
            // R | fixed inner-radius + variable stroke
            rd.animateStroke(480f, 1000);
        } else {
            // variable inner-radius + fixed stroke
        }
    }

    public class BeatHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            animateBeat(mBeatCount);
            mBeatCount ++;
            if(mBeatCount == PEAKS_PER_BEAT) {
                mBeatCount = 0;
            }
        }
    }
}

