package com.diandi.klob.sdk.widget;

import android.app.Activity;

import com.diandi.klob.sdk.R;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * <p/>
 * *********    Version : 1.0
 * *********    Copyright © 2014, klob, All Rights Reserved
 * *******************************************************************************
 */


public class OverridePendingUtil {
    public static void in(final Activity activity) {
        activity.overridePendingTransition(R.anim.fb_slide_in_from_right, R.anim.fb_forward);
    }

    public static void out(final Activity activity) {
        activity.overridePendingTransition(R.anim.fb_back, R.anim.fb_slide_out_from_right);

    }
}
