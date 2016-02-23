package com.diandi.klob.sdk.ui;

import android.content.res.ColorStateList;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-06  .
 * *********    Time : 11:17 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class ColorSet {
    public static ColorStateList createColorStateList(int normal, int pressed, int focused) {
        int[] colors = new int[]{pressed, focused, pressed, normal, normal, pressed};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_selected};
        states[3] = new int[]{android.R.attr.state_enabled};
        states[4] = new int[]{android.R.attr.state_focused};
        states[5] = new int[]{android.R.attr.state_window_focused};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

}
