package com.diandi.klob.sdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-10-09  .
 * *********    Time : 14:11 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class PlusTextView extends TextView {

    private PlusHelper mPlusHelper;

    public PlusTextView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public PlusTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlusTextView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, null);
    }

    /**
     * Constructor -
     * <p>
     * create a new PlusTextView instance attached to a target {@link View}.
     *
     * @param context context for this view.
     * @param target  the View to attach the badge to.
     */
    public PlusTextView(Context context, View target) {
        this(context, null, 0, target);
    }


    public PlusTextView(Context context, AttributeSet attrs, int defStyle, View target) {
        super(context, attrs, defStyle);
        mPlusHelper = new PlusHelper(this, context, target);

    }


}
