package com.diandi.klob.sdk.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diandi.klob.sdk.R;
import com.diandi.klob.sdk.util.L;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-13  .
 * *********    Time : 19:08 .
 * *********    Project name : diandi .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  ? 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class DialogUtils {

    public static SweetAlertDialog showSelectDialog(Context context, String title, String content, String confirmText, String cancelText, final ConfirmListener confirmListener, final CancelListener cancelListener) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(confirmText)
                .setCancelText(cancelText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                        if (confirmListener != null) {
                            confirmListener.onClick();
                        }

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                        if (cancelListener != null) {
                            cancelListener.onClick();
                        }

                    }
                });
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static SweetAlertDialog showSelectDialog(Context context, String title, String content, final ConfirmListener confirmListener) {
        return showSelectDialog(context, title, content, "是", "否", confirmListener, null);
    }

    public static SweetAlertDialog showSelectDialog(Context context, String title, String content, final ConfirmListener confirmListener,CancelListener cancelListener) {
        return showSelectDialog(context, title, content, "是", "否", confirmListener, cancelListener);
    }

    public static SweetAlertDialog showSelectDialog(Context context, String title, final ConfirmListener confirmListener) {
        return showSelectDialog(context, title, "", "是", "否", confirmListener, null);
    }

    public static SweetAlertDialog showSelectDialog(Context context, int titleId, int contentId, int confirmTextId, int cancelTextId, final ConfirmListener confirmListener, final CancelListener cancelListener) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(context.getString(titleId))
                .setContentText(context.getString(contentId))
                .setConfirmText(context.getString(confirmTextId))
                .setCancelText(context.getString(cancelTextId))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                        if (confirmListener != null) {
                            confirmListener.onClick();
                        }

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.cancel();
                        if (cancelListener != null) {
                            cancelListener.onClick();
                        }

                    }
                });
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static SweetAlertDialog showSelectDialog(Context context, int titleId, int contentId, final ConfirmListener confirmListener, final CancelListener cancelListener) {
        return showSelectDialog(context, titleId, contentId, R.string.yes, R.string.no, confirmListener, cancelListener);
    }

    public interface ConfirmListener {
        void onClick();
    }

    public interface CancelListener {
        void onClick();
    }

    private static final String TAG = DialogUtils.class.getSimpleName();

    private static Animation loadingLogoAnimation;
    private static Animation loadingRoundAnimation;

    /**
     * 初始化进度条dialog
     *
     * @param activity
     * @return
     */
    public static LoadingPopupWindow initProgressDialog(Activity activity, PopupWindow.OnDismissListener onDismissListener) {
        if (activity == null || activity.isFinishing()) {
            return null;
        }

        // 获得背景（6个图片形成的动画）
        //AnimationDrawable animDance = (AnimationDrawable) imgDance.getBackground();

        //final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        final LoadingPopupWindow popupWindow = new LoadingPopupWindow(activity);
        ColorDrawable cd = new ColorDrawable(-0000);
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(onDismissListener);

        popupWindow.setFocusable(true);
        //animDance.start();
        return popupWindow;
    }

    /**
     * 显示进度条对话框
     *
     * @param activity
     * @param popupWindow
     * @param title
     */
    public static void showProgressDialog(Activity activity, LoadingPopupWindow popupWindow, String title) {
        if ((activity == null || activity.isFinishing()) || (popupWindow == null)) {
            return;
        }

        final LoadingPopupWindow tmpPopupWindow = popupWindow;
        View popupView = popupWindow.getContentView();
        if (popupView != null) {
            TextView tvTitlename = (TextView) popupView.findViewById(R.id.tv_titlename);
            if (tvTitlename != null && !title.isEmpty()) {
                tvTitlename.setText(title);
            }
        }

        if (popupWindow != null && !popupWindow.isShowing()) {
            final View rootView1 = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView1.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        tmpPopupWindow.showAtLocation(rootView1, Gravity.CENTER, 0, 0);
                        tmpPopupWindow.startAnimation();
                    } catch (Exception e) {
                        L.errorLog(e);
                    }
                }
            });

        }
    }

    /**
     * 隐藏对话框
     *
     * @param popupWindow
     */
    public static void hideDialog(final PopupWindow popupWindow) {
        if (popupWindow != null) {
            popupWindow.getContentView().post(new Runnable() {
                @Override
                public void run() {
                    if (popupWindow != null && popupWindow.isShowing())
                        try {
                            popupWindow.dismiss();
                        } catch (Exception e) {
                        }
                }
            });
        }
    }

    /**
     * 立即关闭对话框， 在对话框是用来确认是否关闭某个Activity的时候上面的方法有概率会报错
     *
     * @param popupWindow
     */
    public static void hideDialogNow(PopupWindow popupWindow) {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public static class LoadingPopupWindow extends PopupWindow {

        ImageView loadingLogo;
        ImageView loadingRound;

        public LoadingPopupWindow(Activity activity) {
            super(activity.getLayoutInflater().inflate(R.layout.common_loading, null), RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, false);
            this.loadingLogo = (ImageView) getContentView().findViewById(R.id.loading_logo);
            this.loadingRound = (ImageView) getContentView().findViewById(R.id.loading_round);

            if (loadingLogoAnimation == null) {
                loadingLogoAnimation = AnimationUtils.loadAnimation(activity, R.anim.loading_alpha);
            }
            if (loadingRoundAnimation == null) {
                loadingRoundAnimation = AnimationUtils.loadAnimation(activity, R.anim.loading_rotate);
            }
        }

        public void startAnimation() {
            loadingRoundAnimation.setStartTime(500L);//不然会跳帧
            loadingRound.setAnimation(loadingRoundAnimation);
            loadingLogo.startAnimation(loadingLogoAnimation);
        }
    }










}
