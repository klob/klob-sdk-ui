package com.diandi.klob.sdk.animate.scroll;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-09  .
 * *********    Time : 20:27 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {

    private ObservableScrollViewListener scrollViewListener = null;
    private float prevoiusX;
    //按下时y坐标
    private float prevoiusY;
    // 判断是否横向滑动
    private int isHor = 0;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        isHor = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevoiusY = ev.getY();
                prevoiusX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //判断是横向操作还是垂直操作，哪个先移动50像素，就判断为哪种操作
                if (ev.getX() - prevoiusX > 50 && isHor == 0) {
                    isHor = 1;
                }
                if (ev.getY() - prevoiusY > 50 && isHor == 0) {
                    isHor = 2;
                }
                // 如果为横向操作就将点击事件往后传给scrollview
                if (isHor == 1) {
                    return false;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public void setScrollViewListener(ObservableScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


}
