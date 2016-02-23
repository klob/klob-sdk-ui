package com.diandi.klob.sdk.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diandi.klob.sdk.R;
import com.diandi.klob.sdk.ui.ColorSet;
import com.diandi.klob.sdk.util.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KTab {
    private final static String TAG = "KTab";

    private KFragment mCurrentFragment;
    private List<KTabItem> mTabItems;
    private List<Button> mBtns;
    private KFragment[] mKFragments;
    private int mTabId;
    private int mContainerId;
    private int mTabCount;
    private Context mContext;
    private LinearLayout mTabLayout;
    private FragmentManager mFragmentManager;
    private int mCurrentTabIndex = -1;
    private OnTabSelectedListener mOnTabSelectedListener;
    private IndexViewPager mViewPager;

    public KTab(KActivity context, int tabId, int containerId, int count) {
        mTabId = tabId;
        mContext = context;
        mContainerId = containerId;
        mTabCount = count;
        init();
    }

    public void setButtonColor(int color) {
        for (Button button : mBtns) {
            button.setTextColor(color);
        }
    }

    public void setButtonColor(ColorStateList color) {
        for (Button button : mBtns) {
            button.setTextColor(color);
        }
    }

    private void init() {
        mFragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        mTabLayout = (LinearLayout) ((Activity) mContext).findViewById(mTabId);
        mViewPager = (IndexViewPager) ((Activity) mContext).findViewById(mContainerId);


        mTabItems = new ArrayList<>();
        mBtns = new ArrayList<>();

        initButton(mTabCount);

    }

    public void initButton(int count) {
        mViewPager.setOffscreenPageLimit(count - 1);
        KFragment mCacheFragment = new KFragment() {
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
        Onclick onclick = new Onclick();
        mKFragments = new KFragment[count];
        KActivity activity = (KActivity) mContext;

        for (int i = 0; i < count; i++) {
            View view = activity.getLayoutInflater().inflate(R.layout.item_tab_btn, null);
            Button button1 = (Button) view.findViewById(R.id.tab);
            mBtns.add(button1);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            lp.gravity = Gravity.CENTER;
            mTabLayout.addView(view, lp);

            mKFragments[i] = mCacheFragment;

            mBtns.get(i).setTag(i + "");
            mBtns.get(i).setOnClickListener(onclick);
        }


    }

    public KTab addTab(KTabItem item) {
        mCurrentTabIndex++;
        mBtns.get(mCurrentTabIndex).setText(item.getTabText());
        mBtns.get(mCurrentTabIndex).setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().getDrawable(item.getTabImgId()), null, null);
        mBtns.get(mCurrentTabIndex).setVisibility(View.VISIBLE);
        mTabItems.add(item);
        KFragment fragment;

        try {
            fragment = (KFragment) mTabItems.get(mCurrentTabIndex).getKFragment().newInstance();
            fragment.setUid(mCurrentTabIndex);
            mKFragments[mCurrentTabIndex] = fragment;
            mKFragments[mCurrentTabIndex].onShown(true);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }


    public KTab addFragment(KTabItem item) {
        mCurrentTabIndex++;
        mKFragments[mCurrentTabIndex] = item.getFragment();
        item.getFragment().setUid(mCurrentTabIndex);
        item.setKFragment(item.getFragment().getClass());
        mBtns.get(mCurrentTabIndex).setText(item.getTabText());
        mBtns.get(mCurrentTabIndex).setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().getDrawable(item.getTabImgId()), null, null);
        mBtns.get(mCurrentTabIndex).setVisibility(View.VISIBLE);
        mTabItems.add(item);

        return this;
    }

    public void build() {
        //   L.e(TAG,mCurrentTabIndex);
        if (mCurrentTabIndex + 1 != mTabCount) {
            throw new IllegalStateException("You must fill up the KTab with " + mTabCount + " KTabItem before ");
        }
        TabPageAdapter adapter = new TabPageAdapter(mFragmentManager);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabPageListener());
        // mViewPager.setPagingEnabled(false);
        mViewPager.setCurrentItem(0);
        mCurrentTabIndex = 0;
        selectTab(0);
        selectBtn(0);

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
        return mKFragments[mCurrentTabIndex];
    }

    public KFragment getFragment(int index) {
        return mKFragments[index];
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
            mOnTabSelectedListener.onTabSelected(mTabItems.get(currentTabIndex),currentTabIndex);
        }
    }


    private void hideFragment(FragmentTransaction fragmentTransaction) {
        for (int i = 0; i < mTabItems.size(); i++) {
            mKFragments[i].onShown(false);
            fragmentTransaction.hide(mKFragments[i]);
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
         void onTabSelected(KTabItem tab,int index);

        /**
         * Called when a tab exits the selected state.
         *
         * @param tab The tab that was unselected
         */
         void onTabUnselected(KTabItem tab);

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

}
