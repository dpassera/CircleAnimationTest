package com.smashingboxes.circleanimationtest.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by dantepassera on 4/30/15.
 */
public class ViewUtil {

    private static boolean DO_SUBPIXEL_RENDERING = true;

    public static void setTypeFace(Typeface typeFace, TextView... views){
        for(TextView view: views){
            view.setTypeface(typeFace);
            if(DO_SUBPIXEL_RENDERING){
                System.out.println("do subpixel rendering");
                view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
        }
    }

}
