package com.diandi.klob.sdk.ui.ktitle;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-20  .
 * *********    Time : 21:51 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class KTitleTabItem {

    public Class<?> mKFragment;

    public KTitleFragment getFragment() {
        return mFragment;
    }

    public void setFragment(KTitleFragment fragment) {
        mFragment = fragment;
    }

    public int mTabImgId;
    public String mTabText;
    public KTitleFragment mFragment;

    public KTitleTabItem(KTitleFragment fragment, int tabImgId, String tabText) {
        mTabImgId = tabImgId;
        mTabText = tabText;
        mFragment = fragment;
    }

    public KTitleTabItem(Class<?> KFragment, int tabImgId, String tabText) {
        mKFragment = KFragment;
        mTabImgId = tabImgId;
        mTabText = tabText;
    }


    public Class<?> getKFragment() {
        return mKFragment;
    }

    public void setKFragment(Class<?> KFragment) {
        mKFragment = KFragment;
    }

    public int getTabImgId() {
        return mTabImgId;
    }

    public void setTabImgId(int tabImgId) {
        mTabImgId = tabImgId;
    }

    public String getTabText() {
        return mTabText;
    }

    public void setTabText(String tabText) {
        mTabText = tabText;
    }
}
