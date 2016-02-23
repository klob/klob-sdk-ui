package com.diandi.klob.sdk.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diandi.klob.sdk.R;


/**
 * Created by chaochen on 14-11-10.
 */
public final class CustomDialog {

    private static void dialogTitleLineColor(Context context, Dialog dialog, int color) {
        String dividers[] = {
                "android:id/alertTitle", "android:id/titleDividerTop", "android:id/titleDivider"
        };
        for (int i = 0; i < dividers.length; ++i) {
            int divierId = context.getResources().getIdentifier(dividers[i], null, null);
            View divider = dialog.findViewById(divierId);
            if (i == 0) {
                ((TextView) divider).setTextColor(color);
                continue;
            }
            if (divider != null) {
                divider.setBackgroundColor(color);
            }
        }

      /*  int textViewId = context.getResources().getIdentifier("android:id/alertTitle", null, null);
        TextView tv = (TextView) dialog.findViewById(textViewId);
        tv.setTextColor(context.getResources().getColor(R.color.blue));*/
    }

    public static void dialogTitleLineColor(Context context, Dialog dialog) {
        if (dialog != null) {
            dialogTitleLineColor(context, dialog, context.getResources().getColor(R.color.blue));
        }
    }

    public static void dialogWithEditext(final Context context, String title, final EditListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater li = LayoutInflater.from(context);
        View v1 = li.inflate(R.layout.dialog_input, null);
        final EditText input = (EditText) v1.findViewById(R.id.value);
        builder.setTitle(title)
                .setView(v1).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPath = input.getText().toString().trim();
                if (newPath.equals("")) {
                    Toast.makeText(context, "内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listener != null) {
                    listener.onText(newPath);
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.show();
        listener.onShow(dialog);
        dialogTitleLineColor(context, dialog);
    }

    public static void dialogWithNumEditext(final Context context, String title, final EditListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater li = LayoutInflater.from(context);
        View v1 = li.inflate(R.layout.dialog_input_number, null);
        final EditText input = (EditText) v1.findViewById(R.id.value);
        builder.setTitle(title)
                .setView(v1).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPath = input.getText().toString().trim();
                if (newPath.equals("")) {
                    Toast.makeText(context, "内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (listener != null) {
                    listener.onText(newPath);
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.show();
        listener.onShow(dialog);
        dialogTitleLineColor(context, dialog);
    }

    public interface EditListener {
        void onShow(AlertDialog dialog);

        void onText(String text);
    }
}
