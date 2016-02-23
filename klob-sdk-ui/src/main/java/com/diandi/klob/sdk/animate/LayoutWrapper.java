package com.diandi.klob.sdk.animate;

import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;

import com.diandi.klob.sdk.animate.scroll.ObservableScrollView;
import com.diandi.klob.sdk.animate.scroll.ObservableScrollViewListener;



/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-09  .
 * *********    Time : 20:27 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public class LayoutWrapper {
    private final static String TAG = "LayoutWrapper";
    private static final int TRANSLATE_DURATION_MILLIS = 200;
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    protected AbsListView mListView;
    private ViewGroup mLayout;
    private boolean mVisible;
    private ObservableScrollView mScrollView;
    private FabOnScrollListeners mOnScrollListener;
    private FabScroolLister mScroolLister;

    private LayoutWrapper() {
    }

    public LayoutWrapper(ViewGroup mLayout) {
        init();
        this.mLayout = mLayout;
    }

    public void init() {
        mVisible = true;
    }

    public ViewGroup getLayout() {
        return mLayout;
    }

    private int getMarginBottom() {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = mLayout.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }

    public void show() {
        show(true);
    }

    public void hide() {
        hide(true);
    }

    public void show(boolean animate) {
        toggle(true, animate, false);
    }

    public void hide(boolean animate) {
        toggle(false, animate, false);
    }

    private void toggle(final boolean visible, final boolean animate, boolean force) {
        if (mVisible != visible || force) {
            mVisible = visible;
            int height = mLayout.getHeight();
            if (height == 0 && !force) {
                ViewTreeObserver vto = mLayout.getViewTreeObserver();
                if (vto.isAlive()) {
                    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentVto = mLayout.getViewTreeObserver();
                            if (currentVto.isAlive()) {
                                currentVto.removeOnPreDrawListener(this);
                            }
                            toggle(visible, animate, true);
                            return true;
                        }
                    });
                    return;
                }
            }
            int translationY = visible ? 0 : height + getMarginBottom();
            if (animate) {
                mLayout.animate().setInterpolator(mInterpolator)
                        .setDuration(TRANSLATE_DURATION_MILLIS)
                        .translationY(translationY);
            } else {
                mLayout.setTranslationY(translationY);
            }
        }
    }

    public void attachToListView(AbsListView listView) {
        attachToListView(listView, new FabOnScrollListeners());
    }

    public void attachToListView(AbsListView listView, FabOnScrollListeners onScrollListener) {
        mListView = listView;
        mOnScrollListener = onScrollListener;
        onScrollListener.setLayout(this);
        onScrollListener.setListView(listView);
        //   mListView.setOnScrollListener(onScrollListener);
    }

    public void attachToSrollView(ObservableScrollView scrollView) {
        attachToSrollView(scrollView, new FabScroolLister());
    }

    public void attachToSrollView(ObservableScrollView scrollView, FabScroolLister fabScroolListeer) {
        mScrollView = scrollView;
        mScroolLister = fabScroolListeer;
        mScroolLister.setLayout(this);
        mScrollView.setScrollViewListener(fabScroolListeer);
    }


    public class FabOnScrollListeners extends ScrollDirectionDetector {
        private LayoutWrapper mDecorator;

        public FabOnScrollListeners() {
            setScrollDirectionListener(new ScrollDirectionListener() {
                @Override
                public void onScrollDown() {
                    mDecorator.show();
                }

                @Override
                public void onScrollUp() {
                    mDecorator.hide();
                }
            });
        }

        public void setLayout(LayoutWrapper decorator) {
            mDecorator = decorator;
        }
    }

    public class FabScroolLister implements ObservableScrollViewListener {
        private LayoutWrapper mDecorator;

        public void setLayout(LayoutWrapper decorator) {
            mDecorator = decorator;
        }

        @Override
        public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
            if (y - oldy > 20)
                mDecorator.hide();
            if (oldy - y > 20)
                mDecorator.show();
        }
    }

}
