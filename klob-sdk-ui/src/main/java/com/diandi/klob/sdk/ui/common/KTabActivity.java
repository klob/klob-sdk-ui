package com.diandi.klob.sdk.ui.common;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.diandi.klob.sdk.R;

/**
 * Created by Administrator on 2015/6/6 0006.
 */
public class KTabActivity extends KActivity  {
    public KTab mTabManager;
    public RelativeLayout mTabLayout;

    public RelativeLayout getTabLayout() {
        return mTabLayout;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow(R.layout.activity_k_tab);
        mTabLayout=(RelativeLayout)findViewById(R.id.tab_layout);
        mTabManager = new KTab(this, R.id.btom_tab_layout, R.id.fragment_container,4);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void init() {

    }

    @Override
    public void bindEvent() {

    }
}
