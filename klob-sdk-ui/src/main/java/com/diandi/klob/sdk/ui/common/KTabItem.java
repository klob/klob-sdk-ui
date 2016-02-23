package com.diandi.klob.sdk.ui.common;


public class KTabItem {

    public Class<?> mKFragment;

    public KFragment getFragment() {
        return mFragment;
    }

    public void setFragment(KFragment fragment) {
        mFragment = fragment;
    }

    public int mTabImgId;
    public String mTabText;
    public KFragment mFragment;

    public KTabItem( KFragment fragment,int tabImgId, String tabText) {
        mTabImgId = tabImgId;
        mTabText = tabText;
        mFragment = fragment;
    }

    public KTabItem(Class<?> KFragment, int tabImgId, String tabText) {
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
