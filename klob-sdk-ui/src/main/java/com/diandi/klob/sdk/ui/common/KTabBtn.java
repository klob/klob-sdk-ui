package com.diandi.klob.sdk.ui.common;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/6/6 0006.
 */
public class KTabBtn extends Button {
    private Context mContext;
    private int mImgId;
    private String mText;

    public KTabBtn(Context context) {
        super(context);
        mContext = context;
    }

    public KTabBtn(Context context, int imgId, String text) {
        this(context);
        mImgId = imgId;
        mText = text;
    }

    public KTabBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KTabBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public KTabBtn(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init() {

    }
}
