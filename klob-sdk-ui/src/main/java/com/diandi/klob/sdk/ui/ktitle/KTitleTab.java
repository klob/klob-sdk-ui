package com.diandi.klob.sdk.ui.ktitle;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.diandi.klob.sdk.R;
import com.diandi.klob.sdk.ui.common.IndexViewPager;
import com.diandi.klob.sdk.ui.common.KFragment;
import com.diandi.klob.sdk.ui.common.KTabItem;

import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-20  .
 * *********    Time : 21:51 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class KTitleTab {
    private final static String TAG = "KTab";

    private KFragment mCurrentFragment;
    private KFragment mCacheFrgment = new KFragment() {
        @Override
        public void initViews() {

        }

        @Override
        public void bindEvent() {

        }

        @Override
        public void init() {

        }
    };
    private List<KTabItem> mTabItems;
    private List<Button> mBtns;
    private KFragment[] mKFragments = {mCacheFrgment, mCacheFrgment, mCacheFrgment, mCacheFrgment};
    private int mTabId;
    private int mContainerId;
    private Context mContext;
    private LinearLayout mTabLayout;
    private FragmentManager mFragmentManager;
    private int mCurrentTabIndex = 0;
    private OnTabSelectedListener mOnTabSelectedListener;

    public KTitleTab(Context context, int tabId, int containerId) {
        mTabId = tabId;
        mContext = context;
        mContainerId = containerId;
        init();
    }

    private IndexViewPager mViewPager;
    private TabPageAdapter mAdapter;

    private void init() {
        mFragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        mTabItems = new ArrayList<>();
        mBtns = new ArrayList<>();


        mTabLayout = (LinearLayout) ((Activity) mContext).findViewById(mTabId);
        mViewPager = (IndexViewPager) ((Activity) mContext).findViewById(mContainerId);
        mViewPager.setOffscreenPageLimit(3);

        Onclick onclick = new Onclick();
        for (int i = 0; i < mBtns.size(); i++) {
            mBtns.get(i).setTag(i + "");
            mBtns.get(i).setOnClickListener(onclick);
        }
    }

    public KTitleTab addTab(KTabItem item) {
        int tabIndex = mTabItems.size();
        mBtns.get(tabIndex).setText(item.getTabText());
        mBtns.get(tabIndex).setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().getDrawable(item.getTabImgId()), null, null);
        mBtns.get(tabIndex).setVisibility(View.VISIBLE);
        // L.e(TAG, mBtns.get(mTabItems.size()).getVisibility());
        mTabItems.add(item);
        KFragment fragment;

        try {
            //  mTabItems.get(mCurrentTabIndex).getKFragment() instanceof HasViews
            fragment = (KFragment) mTabItems.get(tabIndex).getKFragment().newInstance();
            fragment.setUid(tabIndex);
            mKFragments[tabIndex] = fragment;
            mKFragments[tabIndex].onShown(true);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    @Deprecated
    public KTitleTab addFrament(KFragment fragment, int id, int imgid, String title) {
        mKFragments[id] = fragment;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(mContainerId, fragment);
        fragment.setUid(id);

        KTabItem item = new KTabItem(fragment.getClass(), imgid, title);
        mBtns.get(mTabItems.size()).setText(item.getTabText());
        mBtns.get(mTabItems.size()).setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().getDrawable(item.getTabImgId()), null, null);
        mBtns.get(mTabItems.size()).setVisibility(View.VISIBLE);
        mTabItems.add(item);


        transaction.commit();
        return this;
    }

    public KTitleTab addFragment(KTabItem item) {
        mKFragments[mTabItems.size()] = item.getFragment();
        // FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // transaction.add(mContainerId, item.getFragment());
        item.getFragment().setUid(mTabItems.size());

        // KTabItem item2 = new KTabItem(item.getClass(), imgid, title);
        item.setKFragment(item.getFragment().getClass());
        mBtns.get(mTabItems.size()).setText(item.getTabText());
        mBtns.get(mTabItems.size()).setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().getDrawable(item.getTabImgId()), null, null);
        mBtns.get(mTabItems.size()).setVisibility(View.VISIBLE);
        mTabItems.add(item);

        //  transaction.commit();
        return this;
    }

    public void build() {
        mAdapter = new TabPageAdapter(mFragmentManager);
        mViewPager.setAdapter(mAdapter);
        // mViewPager.setPagingEnabled(false);
        mViewPager.addOnPageChangeListener(new TabPageListener());
        mViewPager.setCurrentItem(0);
        selectBtn(0);
        Onclick onclick = new Onclick();
        for (int i = 0; i < mBtns.size(); i++) {
            mBtns.get(i).setTag(i + "");
            mBtns.get(i).setOnClickListener(onclick);
        }

    }



    public int getCurrentTabIndex() {
        return mCurrentTabIndex;
    }

    public LinearLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * Returns the tab at the specified index.
     */
    public KTabItem getTabAt(int index) {
        return mTabItems.get(index);
    }

    public KFragment getCurrentFragment() {
        return mCurrentFragment;
    }


    public void setTabHeight(int tabHeight) {
        mTabLayout.setMinimumHeight(dpToPx(tabHeight));
    }

    public void setTabTextColor(int color) {
        for (Button button : mBtns) {
            button.setTextColor(color);
        }
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mOnTabSelectedListener = onTabSelectedListener;
    }


    private int dpToPx(int dps) {
        return Math.round(mContext.getResources().getDisplayMetrics().density * dps);
    }

    public KFragment selectTab(int currentTabIndex) {
        KFragment fragment = null;

        mCurrentTabIndex = currentTabIndex;
        mCurrentFragment = mKFragments[currentTabIndex];

        if (mKFragments[currentTabIndex].getUid() != -1) {
            mKFragments[currentTabIndex].onShown(true);
            fragment = mKFragments[currentTabIndex];
        }
        return fragment;

    }

    private void selectBtn(int currentTabIndex) {

        for (int i = 0; i < mTabItems.size(); i++) {
            if (i != currentTabIndex) {
                mBtns.get(i).setSelected(false);
                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabUnselected(mTabItems.get(i));
                }
            }
        }
        mBtns.get(currentTabIndex).setSelected(true);
        if (mOnTabSelectedListener != null) {

            mOnTabSelectedListener.onTabSelected(mTabItems.get(currentTabIndex));
        }
    }



    private void hideFragment(FragmentTransaction fragmentTransaction) {
        for (int i = 0; i < mTabItems.size(); i++) {
            mKFragments[i].onShown(false);
            fragmentTransaction.hide(mKFragments[i]);
        }
    }

    private class TabPageListener implements ViewPager.OnPageChangeListener {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selectBtn(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class TabPageAdapter extends FragmentPagerAdapter {

        public TabPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mKFragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            return selectTab(position);
        }

    }

    private class Onclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int i = Integer.parseInt((String) v.getTag());
            mViewPager.setCurrentItem(i, false);
            selectTab(i);
        }
    }

    /**
     * Callback interface invoked when a tab's selection state changes.
     */
    public interface OnTabSelectedListener {

        /**
         * Called when a tab enters the selected state.
         *
         * @param tab The tab that was selected
         */
        public void onTabSelected(KTabItem tab);

        /**
         * Called when a tab exits the selected state.
         *
         * @param tab The tab that was unselected
         */
        public void onTabUnselected(KTabItem tab);

    }

}
