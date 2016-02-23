package com.diandi.klob.sdk.ui.ktitle;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-20  .
 * *********    Time : 21:48 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.diandi.klob.sdk.R;
import com.diandi.klob.sdk.app.BaseFragmentParam;
import com.diandi.klob.sdk.common.Global;
import com.diandi.klob.sdk.ui.common.KFragment;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.util.NetworkUtil;
import com.diandi.klob.sdk.widget.OverridePendingUtil;
import com.diandi.klob.sdk.widget.TitleBar;
import com.diandi.klob.sdk.widget.TitleBar.HeaderStyle;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.Serializable;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-05-15  .
 * *********    Time : 10:15 .
 * *********    Project name : diandi .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class KTitleActivity extends SwipeBackActivity {
    public final String TAG = getClass().getName();
    protected TitleBar mTopBar;
    protected Context mContext;
    protected Toast mToast;
    protected KFragment mCurrentFragment;
    protected SystemBarTintManager mTintManager;
    private boolean isClearView = false;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && !isClearView) {
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setTintColor(getResources().getColor(R.color.k_theme_color));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected KTitleActivity getActivity() {
        return this;
    }

    @Override
    public void setContentView(int layoutId) {
        super.setContentView(R.layout.activity_k_title);
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        FrameLayout layout = (FrameLayout) findViewById(R.id.activity_base_layout);
        layout.addView(view);
        try {
            getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
        } catch (NoSuchFieldException e) {
            // Ignore since this field won't exist in most versions of Android
        } catch (IllegalAccessException e) {
            L.e(TAG, "Could not access FLAG_NEEDS_MENU_KEY in addLegacyOverflowButton()");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setTintColor(getResources().getColor(R.color.k_theme_color));
        }

    }

    public void setClearContentView(int layoutId) {
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.setContentView(layoutId);
        isClearView = true;
        setSwipeBackEnable(false);

    }

    public void initWindow(int layoutId) {
        super.setContentView(R.layout.activity_k_simple);
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        FrameLayout layout = (FrameLayout) findViewById(R.id.activity_base_layout);
        layout.addView(view);
        try {
            getWindow().addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
        } catch (NoSuchFieldException e) {
            // Ignore since this field won't exist in most versions of Android
        } catch (IllegalAccessException e) {
            L.e(TAG, "Could not access FLAG_NEEDS_MENU_KEY in addLegacyOverflowButton()");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setTintColor(getResources().getColor(R.color.k_theme_color));
        }


    }

    public SystemBarTintManager getTintManager() {
        return mTintManager;
    }

    public abstract void initViews();

    public abstract void init();

    public abstract void bindEvent();

    protected TitleBar getBar() {
        return mTopBar;
    }

    protected void hideBar() {
        ((TitleBar) findViewById(R.id.common_top_bar)).hide();
    }

    public void pushFragmentToStack(KFragment fragment, Bundle data) {
        super.setContentView(R.layout.activity_k);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setTintColor(getResources().getColor(R.color.k_theme_color));
        }
        hideBar();
        BaseFragmentParam param = new BaseFragmentParam();
        param.data = data;
        param.cls = fragment.getClass();
        goToThisFragment(param, fragment);
    }

    public void pushFragmentToStack(KFragment fragment, Bundle data, String title) {
        super.setContentView(R.layout.activity_k);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setTintColor(getResources().getColor(R.color.k_theme_color));
        }
        initTopBarForLeft(title);
        BaseFragmentParam param = new BaseFragmentParam();
        param.data = data;
        param.cls = fragment.getClass();
        goToThisFragment(param, fragment);
    }

    public void pushFragmentToStack(Class<?> cls, Bundle data) {
        super.setContentView(R.layout.activity_k);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setTintColor(getResources().getColor(R.color.k_theme_color));
        }
        hideBar();
        BaseFragmentParam param = new BaseFragmentParam();
        param.cls = cls;
        param.data = data;
        goToThisFragment(param);
    }

    public void pushFragmentToStack(Class<?> cls, Bundle data, String title) {
        super.setContentView(R.layout.activity_k);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setTintColor(getResources().getColor(R.color.k_theme_color));
        }
        initTopBarForLeft(title);
        BaseFragmentParam param = new BaseFragmentParam();
        param.cls = cls;
        param.data = data;
        goToThisFragment(param);
    }

    protected int getFragmentContainerId() {
        return R.id.activity_base_layout;
    }

    private void goToThisFragment(BaseFragmentParam param, KFragment fragment) {
        int containerId = getFragmentContainerId();
        Class<?> cls = param.cls;
        if (cls == null) {
            return;
        }
        String fragmentTag = getFragmentTag(param);
        FragmentManager fm = getSupportFragmentManager();

        if (mCurrentFragment != null && mCurrentFragment != fragment) {
            mCurrentFragment.onLeave();
        }
        fragment.onEnter(param.data);

        FragmentTransaction ft = fm.beginTransaction();
        if (fragment.isAdded()) {
            Log.d("%s has been added", fragmentTag);
            ft.show(fragment);
        } else {
            Log.d("%s is added.", fragmentTag);
            ft.add(containerId, fragment, fragmentTag);
        }
        mCurrentFragment = fragment;
        ft.addToBackStack(fragmentTag);
        ft.commitAllowingStateLoss();
    }

    private void goToThisFragment(BaseFragmentParam param) {
        int containerId = getFragmentContainerId();
        Class<?> cls = param.cls;
        if (cls == null) {
            return;
        }
        try {
            String fragmentTag = getFragmentTag(param);
            FragmentManager fm = getSupportFragmentManager();
            KFragment fragment = (KFragment) fm.findFragmentByTag(fragmentTag);
            if (fragment == null) {
                fragment = (KFragment) cls.newInstance();
            }
            if (mCurrentFragment != null && mCurrentFragment != fragment) {
                mCurrentFragment.onLeave();
            }
            fragment.onEnter(param.data);

            FragmentTransaction ft = fm.beginTransaction();
            if (fragment.isAdded()) {
                Log.d("%s has been added", fragmentTag);
                ft.show(fragment);
            } else {
                Log.d("%s is added.", fragmentTag);
                ft.add(containerId, fragment, fragmentTag);
            }
            mCurrentFragment = fragment;
            ft.addToBackStack(fragmentTag);
            ft.commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  if (mCurrentFragment != null) {
        //      mCurrentFragment.onActivityResult(requestCode, resultCode, data);
        //  }
    }

    protected String getFragmentTag(BaseFragmentParam param) {
        StringBuilder sb = new StringBuilder(param.cls.toString());
        return sb.toString();
    }




    public void ShowToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (mToast == null) {
                            mToast = Toast.makeText(getApplicationContext(), text,
                                    Toast.LENGTH_SHORT);
                        } else {
                            mToast.setText(text);
                        }
                        mToast.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    public void ShowToast(final int resId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mToast == null) {
                        mToast = Toast.makeText(KTitleActivity.this.getApplicationContext(), resId,
                                Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(resId);
                    }
                    mToast.show();
                    Log.d(TAG, getString(resId));
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void initTopBarForOnlyTitle(String titleName) {
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.init(TitleBar.HeaderStyle.DEFAULT_TITLE);
        mTopBar.setDefaultTitle(titleName);
    }

    public void initTopBarForLeft(String titleName) {
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
        mTopBar.setTitleAndLeftImageButton(titleName,
                R.string.back,
                new OnLeftButtonClickListener());
    }

    public void initTopBarForBoth(String titleName, int rightText,
                                  TitleBar.OnRightImageButtonClickListener listener) {
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
        mTopBar.setTitleAndLeftImageButton(titleName,
                R.string.back,
                new OnLeftButtonClickListener());
        mTopBar.setTitleAndRightImageButton(titleName, rightText, listener);
    }



 /*   public void initTopBarForBoth(String titleName, int rightDrawableId,
                                  TitleBar.onRightImageButtonClickListener listener) {
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.init(HeaderStyle.TITLE_DOUBLE_IMAGE_BUTTON);
        mTopBar.setTitleAndLeftImageButton(titleName,
                R.string.back
                , new OnLeftButtonClickListener());
        mTopBar.setTitleAndRightButton(titleName, rightDrawableId,
                listener);
    }*/

 /*   public void initTopBarForBoth(String titleName, int rightDrawableId, int imgId,
                                  TitleBar.onRightImageButtonClickListener listener) {
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.init(HeaderStyle.TITLE_DOUBLE_IMAGE_BUTTON);
        mTopBar.setTitleAndLeftImageButton(titleName,
                R.string.back,
                new OnLeftButtonClickListener());
        mTopBar.setTitleAndRightImageButton(titleName, rightDrawableId, listener);
    }
*/



/*
    public void initTopBarForLeft(String titleName) {
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
        mTopBar.setTitleAndLeftImageButton(titleName, R.string.back,
                new OnLeftButtonClickListener());
    }*/

    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(this, cla));
        OverridePendingUtil.in(KTitleActivity.this);
    }

    public void startAnimActivity(Class<?> cla, Bundle bundle) {
        Intent intent = new Intent(this, cla);
        intent.putExtras(bundle);
        this.startActivity(intent);
        OverridePendingUtil.in(KTitleActivity.this);
    }

    public void startAnimActivity(Class<?> cla, String objtag, Serializable obj) {
        Intent intent = new Intent(this, cla);
        intent.putExtra(objtag, obj);
        this.startActivity(intent);
        OverridePendingUtil.in(KTitleActivity.this);
    }

    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
        OverridePendingUtil.in(KTitleActivity.this);
    }

    public boolean netIsntWork() {
        if (!NetworkUtil.isAvailable(mContext)) {
            ShowToast("当前网络不可用,请检查您的网络!");
            return true;
        } else return false;
    }

    @Override
    public void onBackPressed() {
        finish();
        OverridePendingUtil.out(this);
    }

    public void hideSoftInputView() {
        Global.hideSoftInputView(this);
    }

    public void in() {
        OverridePendingUtil.in(this);
    }

    public void out() {
        OverridePendingUtil.out(this);
    }

    public class OnLeftButtonClickListener implements
            TitleBar.OnLeftImageButtonClickListener {
        @Override
        public void onClick() {
            onBackPressed();
            OverridePendingUtil.out(KTitleActivity.this);
        }
    }
}
