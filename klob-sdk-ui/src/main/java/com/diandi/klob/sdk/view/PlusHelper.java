package com.diandi.klob.sdk.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.diandi.klob.sdk.util.L;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-10-09  .
 * *********    Time : 21:15 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class PlusHelper {
    String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private View mTarget;
    private View mPlusView;
    private Animation mAnimation;

    public PlusHelper(View view, Context context) {
        mContext = context;
        mPlusView = view;
        init();
    }

    public PlusHelper(View view, Context context, View target) {
        mContext = context;
        mTarget = target;
        mPlusView = view;
        init();
    }

    protected void init() {
        if (mTarget != null) {
            applyTo(mTarget);
        }
    }

    protected void applyTo(View target) {
        ViewGroup.LayoutParams lp = target.getLayoutParams();

        ViewGroup parent = (ViewGroup) target.getParent();
        FrameLayout container = new FrameLayout(mContext);

        int index = parent.indexOfChild(target);

     /*   if (target.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams relp = (RelativeLayout.LayoutParams) target.getLayoutParams();
            e(" index   " + relp.getRules()[RelativeLayout.ALIGN_TOP] + index + "  " + lp.toString() + "   " + lp.width + "     " + lp.height);
        }*/


        parent.removeView(target);
        parent.addView(container, index, lp);
        container.addView(target);


        FrameLayout.LayoutParams textLp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        container.addView(mPlusView, textLp);

        parent.invalidate();


    }


}
