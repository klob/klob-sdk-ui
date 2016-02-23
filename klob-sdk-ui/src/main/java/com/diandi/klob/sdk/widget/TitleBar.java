package com.diandi.klob.sdk.widget;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-20  .
 * *********    Time : 18:36 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diandi.klob.sdk.R;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Project name : klob-sdk .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class TitleBar extends RelativeLayout {
    private LayoutInflater mInflater;
    private View mHeader;
    private LinearLayout mLayoutLeftContainer;
    private LinearLayout mLayoutRightContainer;
    private LinearLayout mBackgroundLayout;
    private TextView mHtvSubTitle;

    private LinearLayout mLayoutRightImageButtonLayout;
    private TextView mRightImageButton;
    private OnRightImageButtonClickListener mRightImageButtonClickListener;


    private LinearLayout mLayoutLeftImageButtonLayout;
    private TextView mLeftImageButton;
    private OnLeftImageButtonClickListener mLeftImageButtonClickListener;


    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LinearLayout getBackgroundLayout() {
        return mBackgroundLayout;
    }

    public void setBackground(LinearLayout background) {
        mBackgroundLayout = background;
    }

    public void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mHeader = mInflater.inflate(R.layout.title_bar, null);
        mBackgroundLayout = (LinearLayout) mHeader.findViewById(R.id.header_background);
        addView(mHeader);
        initViews();
    }

    public void initViews() {
        mLayoutLeftContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_container);
        // mLayoutMiddleContainer = (LinearLayout)
        // findViewByHeaderId(R.id.header_layout_middleview_container);中间部分添加搜索或者其他按钮时可打开
        mLayoutRightContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_rightview_container);
        mHtvSubTitle = (TextView) findViewByHeaderId(R.id.header_htv_subtitle);

    }

    public View findViewByHeaderId(int id) {
        return mHeader.findViewById(id);
    }

    public void init(HeaderStyle hStyle) {
        switch (hStyle) {
            case DEFAULT_TITLE:
                defaultTitle();
                break;
            case TITLE_LIFT_IMAGEBUTTON:
                defaultTitle();
                titleLeftImageButton();
                break;
            case TITLE_RIGHT_IMAGEBUTTON:
                defaultTitle();
                titleRightImageButton();
                break;

            case TITLE_DOUBLE_IMAGEBUTTON:
                defaultTitle();
                titleLeftImageButton();
                titleRightImageButton();
                break;
        }
    }

    // 默认文字标题
    private void defaultTitle() {
        mLayoutLeftContainer.removeAllViews();
        mLayoutRightContainer.removeAllViews();
    }

    // 左侧自定义按钮
    private void titleLeftImageButton() {
        View mleftImageButtonView = mInflater.inflate(R.layout.title_bar_left_text, null);
        mLayoutLeftContainer.addView(mleftImageButtonView);
        mLayoutLeftImageButtonLayout = (LinearLayout) mleftImageButtonView.findViewById(R.id.header_layout_imagebuttonlayout);
        mLeftImageButton = (TextView) mleftImageButtonView.findViewById(R.id.header_ib_imagebutton);
        mLayoutLeftImageButtonLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mLeftImageButtonClickListener != null) {
                    mLeftImageButtonClickListener.onClick();
                }
            }
        });
    }

    // 右侧自定义按钮
    private void titleRightImageButton() {
        View mRightImageButtonView = mInflater.inflate(
                R.layout.title_bar_right_text, null);
        mLayoutRightContainer.addView(mRightImageButtonView);
        mLayoutRightImageButtonLayout = (LinearLayout) mRightImageButtonView
                .findViewById(R.id.header_layout_imagebuttonlayout);
        mRightImageButton = (TextView) mRightImageButtonView
                .findViewById(R.id.header_ib_imagebutton);
        mLayoutRightImageButtonLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mRightImageButtonClickListener != null) {
                    mRightImageButtonClickListener.onClick();

                }
            }
        });
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    public TextView getRightImageButton() {
        if (mRightImageButton != null) {
            return mRightImageButton;
        }
        return null;
    }

    public TextView getLeftImageButton() {
        if (mLeftImageButton != null) {
            return mLeftImageButton;
        }
        return null;
    }

    public void setDefaultTitle(CharSequence title) {
        if (title != null) {
            mHtvSubTitle.setText(title);
        } else {
            mHtvSubTitle.setVisibility(View.GONE);
        }
    }

  /*  public void setDefaultTitle(CharSequence title, int imgId) {
        if (title != null) {
            mTitleImg.setBackgroundResource(imgId);
            mTitleImg.setVisibility(VISIBLE);
            mHtvSubTitle.setText(title);
        } else {
            mTitleImg.setVisibility(INVISIBLE);
            mHtvSubTitle.setVisibility(View.GONE);
        }
    }*/

    public void setTitleAndRightButton(CharSequence title, int backid,
                                       OnRightImageButtonClickListener onRightImageButtonClickListener) {
        setDefaultTitle(title);
        mLayoutRightContainer.setVisibility(View.VISIBLE);
        if (mRightImageButton != null && backid > 0) {
            mRightImageButton.setText(backid);
            setOnRightImageButtonClickListener(onRightImageButtonClickListener);
        }
    }

    public void setTitleAndRightImageButton(CharSequence title, int backid, OnRightImageButtonClickListener onRightImageButtonClickListener) {
        setDefaultTitle(title);
        mLayoutRightContainer.setVisibility(View.VISIBLE);
        if (mRightImageButton != null && backid > 0) {
            mRightImageButton.setText(backid);
            setOnRightImageButtonClickListener(onRightImageButtonClickListener);
        }
    }


    public void setTitleAndLeftImageButton(CharSequence title, int id, OnLeftImageButtonClickListener listener) {
        setDefaultTitle(title);
        if (mLeftImageButton != null && id > 0) {
            mLeftImageButton.setText(id);
            setOnLeftImageButtonClickListener(listener);
        }
        mLayoutRightContainer.setVisibility(View.INVISIBLE);
    }

    /*public void setTitleAndLeftImageButton(CharSequence title, int id, int imgId, onLeftImageButtonClickListener listener) {
        setDefaultTitle(title, imgId);
        if (mLeftImageButton != null && id > 0) {
            mLeftImageButton.setImageResource(id);
            setOnLeftImageButtonClickListener(listener);
        }
        mLayoutRightContainer.setVisibility(View.INVISIBLE);
    }
*/

    public void setOnRightImageButtonClickListener(
            OnRightImageButtonClickListener listener) {
        mRightImageButtonClickListener = listener;
    }

    public void setOnLeftImageButtonClickListener(
            OnLeftImageButtonClickListener listener) {
        mLeftImageButtonClickListener = listener;
    }

    public enum HeaderStyle {// 头部整体样式
        DEFAULT_TITLE, TITLE_LIFT_IMAGEBUTTON, TITLE_RIGHT_IMAGEBUTTON, TITLE_DOUBLE_IMAGEBUTTON;
    }

    public interface OnRightImageButtonClickListener {
        void onClick();
    }

    public interface OnLeftImageButtonClickListener {
        void onClick();
    }

}
