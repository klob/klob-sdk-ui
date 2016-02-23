package com.diandi.klob.sdk.ui.ktitle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diandi.klob.sdk.R;
import com.diandi.klob.sdk.app.IKFragment;
import com.diandi.klob.sdk.ui.common.KActivity;
import com.diandi.klob.sdk.util.NetworkUtil;
import com.diandi.klob.sdk.widget.OverridePendingUtil;
import com.diandi.klob.sdk.widget.TitleBar;

import java.io.Serializable;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-20  .
 * *********    Time : 21:49 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class KTitleFragment extends Fragment implements IKFragment {
    private static Handler sHandler = new Handler();
    public final String TAG = ((Object) this).getClass().getSimpleName();
    protected TitleBar mTopBar;
    protected LayoutInflater mInflater;
    protected Toast mToast;
    protected Bundle data;
    private boolean isShown;
    public Context mContext;
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


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public KTitleFragment() {


    }

    public static void runOnUiThread(Runnable action) {
        sHandler.post(action);
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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        init();
        bindEvent();
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * onCreateView();
     * onDestroy();
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    /**
     * dont call getContext() or getActivity ()
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
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.hide();
    }

    protected void showBar() {
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.show();
    }

    public TitleBar getTopBar() {
        return mTopBar;
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
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.init(TitleBar.HeaderStyle.DEFAULT_TITLE);
        mTopBar.setDefaultTitle(titleName);
    }

    public void initTopBarForLeft(String titleName) {
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.init(TitleBar.HeaderStyle.TITLE_LIFT_IMAGEBUTTON);
        mTopBar.setTitleAndLeftImageButton(titleName, R.string.back, new OnLeftButtonClickListener());
    }

    public void initTopBarForRight(String titleName, int rightText, TitleBar.OnRightImageButtonClickListener listener) {
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.init(TitleBar.HeaderStyle.TITLE_RIGHT_IMAGEBUTTON);
        mTopBar.setTitleAndRightImageButton(titleName, rightText, listener);
    }

    public void initTopBarForBoth(String titleName, int rightText, TitleBar.OnRightImageButtonClickListener listener) {
        mTopBar = (TitleBar) findViewById(R.id.common_top_bar);
        mTopBar.init(TitleBar.HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
        mTopBar.setTitleAndLeftImageButton(titleName,
                R.string.back,
                new OnLeftButtonClickListener());
        mTopBar.setTitleAndRightImageButton(titleName, rightText,
                listener);
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
            ShowToast("当前网络不可用,请检查您的网络!");
            return true;
        } else return false;
    }


    public class OnLeftButtonClickListener implements
            TitleBar.OnLeftImageButtonClickListener {
        @Override
        public void onClick() {
            getActivity().finish();
            OverridePendingUtil.out(getActivity());
        }
    }

    public void in() {
        OverridePendingUtil.in(getActivity());
    }
}
