package com.diandi.klob.sdk.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diandi.klob.sdk.R;
import com.diandi.klob.sdk.app.IKFragment;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.util.NetworkUtil;
import com.diandi.klob.sdk.widget.ActionCompat;
import com.diandi.klob.sdk.widget.OverridePendingUtil;
import com.diandi.klob.sdk.widget.TopBar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-05-15  .
 * *********    Time : 12:56 .
 * *********    Project name : diandi .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class KFragment extends Fragment implements IKFragment, ActionCompat.ActionSheetListener {
    private static Handler sHandler = new Handler();
    public final String TAG = ((Object) this).getClass().getSimpleName();
    public Context mContext;
    protected TopBar mTopBar;
    protected LayoutInflater mInflater;
    protected Toast mToast;
    protected Bundle data;
    private boolean isShown;
    /*    public static KFragment newInstance(Bundle data) {
            KFragment fragment = new KFragment() {
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
            fragment.setArguments(data);
            return fragment;
        }*/
    private int uid = -1;


    public KFragment() {


    }

    public static void runOnUiThread(Runnable action) {
        sHandler.post(action);
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public KActivity getContext() {
        if (getActivity() != null) {
            return (KActivity) getActivity();
        } else {
            return (KActivity) mContext;
        }
    }

    public abstract void initViews();

    public abstract void bindEvent();

    public abstract void init();

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.v(TAG);
        mInflater = LayoutInflater.from(getActivity());
        mContext = getActivity();
        if (data != null) {
            onEnter(data);
        } else {
            onEnter(getArguments());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.v(TAG);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.v(TAG);
        initViews();
        init();
        bindEvent();
    }

    @Override
    public void onStart() {
        L.v(TAG);
        super.onStart();
    }


    @Override
    public void onStop() {
        L.v(TAG);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        L.v(TAG);
        super.onDestroyView();
    }

    /**
     * onCreateView();
     * onDestroy();
     */

    @Override
    public void onDestroy() {
        L.v(TAG);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        L.v(TAG);
        super.onDetach();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        L.v(TAG);
        super.onViewCreated(view, savedInstanceState);
    }


    /**
     * don't call getContext() or getActivity ()
     */
    @Override
    public void onEnter(Bundle data) {
        this.data = data;
    }

    @Override
    public void onLeave() {
    }

    @Override
    public void onShown(boolean isShown) {
        this.isShown = isShown;
    }


    protected void hideBar() {
        mTopBar = (TopBar) findViewById(R.id.common_top_bar);
        mTopBar.hide();
    }

    protected void showBar() {
        mTopBar = (TopBar) findViewById(R.id.common_top_bar);
        mTopBar.show();
    }

    public TopBar getTopBar() {
        return mTopBar;
    }

    public TopBar getShowBar() {
        if (mTopBar != null && mTopBar.getVisibility() == View.VISIBLE) {
            return mTopBar;
        } else if (getContext().getBar() != null && getContext().getBar().getVisibility() == View.VISIBLE) {
            return getContext().getBar();
        } else {
            return null;
        }
    }

    public void ShowToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mToast == null && getActivity() != null) {
                            mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
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
                    if (mToast == null && getActivity() != null) {
                        mToast = Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG);
                    } else {
                        mToast.setText(resId);
                    }
                    mToast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }

    public void initTopBarForOnlyTitle(String titleName) {
        mTopBar = (TopBar) findViewById(R.id.common_top_bar);
        mTopBar.init(TopBar.HeaderStyle.DEFAULT_TITLE);
        mTopBar.setDefaultTitle(titleName);
    }

    public void initTopBarForBoth(String titleName, int rightDrawableId, TopBar.onRightImageButtonClickListener listener) {
        mTopBar = (TopBar) findViewById(R.id.common_top_bar);
        mTopBar.init(TopBar.HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
        mTopBar.setTitleAndLeftImageButton(titleName,
                R.drawable.top_bar_back_btn,
                new OnLeftButtonClickListener());
        mTopBar.setTitleAndRightImageButton(titleName, rightDrawableId,
                listener);
    }

    public void initTopBarForLeft(String titleName) {
        mTopBar = (TopBar) findViewById(R.id.common_top_bar);
        mTopBar.init(TopBar.HeaderStyle.TITLE_LIFT_IMAGE_BUTTON);
        mTopBar.setTitleAndLeftImageButton(titleName, R.drawable.top_bar_back_btn, new OnLeftButtonClickListener());
    }


    public void initTopBarForRight(String titleName, int rightDrawableId, TopBar.onRightImageButtonClickListener listener) {
        mTopBar = (TopBar) findViewById(R.id.common_top_bar);
        mTopBar.init(TopBar.HeaderStyle.TITLE_RIGHT_IMAGE_BUTTON);
        mTopBar.setTitleAndRightImageButton(titleName, rightDrawableId, listener);
    }

    public void startAnimActivity(Intent intent) {
        getActivity().startActivity(intent);
        OverridePendingUtil.in(getActivity());
    }

    public void startAnimActivity(Class<?> cla, String objtag, Serializable obj) {
        Intent intent = new Intent(mContext, cla);
        intent.putExtra(objtag, obj);
        this.startActivity(intent);
        in();
    }

    public void startAnimActivity(Class<?> cla, String objtag, String obj) {
        Intent intent = new Intent(mContext, cla);
        intent.putExtra(objtag, obj);
        this.startActivity(intent);
        in();
    }

    public void startAnimActivity(Intent intent, Bundle data) {
        intent.putExtras(data);
        getActivity().startActivity(intent);
        OverridePendingUtil.in(getActivity());
    }

    public void startAnimActivity(Class<?> cls, Bundle data) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(data);
        getActivity().startActivity(intent);
        OverridePendingUtil.in(getActivity());
    }

    public void startAnimActivity(Class<?> cla) {
        getActivity().startActivity(new Intent(getActivity(), cla));
        OverridePendingUtil.in(getActivity());
    }


    public boolean netIsntWork() {
        if (!NetworkUtil.isAvailable(getActivity())) {
            ShowToast(R.string.network_tips);
            return true;
        } else return false;
    }


    public void in() {
        OverridePendingUtil.in(getActivity());
    }

    public boolean onBackPressed() {
      return false;
    }



    public void createMenu(int menuId) {
        ActionCompat actionCompat = (getContext()).getSheetCompat();
        if (actionCompat == null) {
            getContext().setTheme(R.style.ActionSheetStyleIOS7);
        }
        MenuBuilder builder = new MenuBuilder(mContext);
        getContext().getMenuInflater().inflate(menuId, builder);
        ActionCompat.Builder actionBuilder = ActionCompat.createBuilder(getContext(), getContext().getSupportFragmentManager());
        ArrayList<MenuItemImpl> menuOptions = builder.getVisibleItems();
        int size = menuOptions.size();
        String[] items = new String[size];
        int[] ids = new int[size];
        for (int i = 0; i < size; i++) {
            items[i] = menuOptions.get(i).toString();
            ids[i] = menuOptions.get(i).getItemId();
        }
        actionBuilder.setCancelButtonTitle("取消")
                .setOtherButtonTitles(ids, items)
                .setCancelableOnTouchOutside(true)
                .setListener(this);
        ((getContext())).setSheetCompat(actionBuilder.show());
    }

    @Override
    public void onDismiss(ActionCompat actionSheet, boolean isCancel) {

    }

    @Override
    public void onOptionsItemSelected(ActionCompat actionSheet, int id) {

    }

    public class OnLeftButtonClickListener implements
            TopBar.onLeftImageButtonClickListener {
        @Override
        public void onClick() {
            getActivity().finish();
            OverridePendingUtil.out(getActivity());
        }
    }
}