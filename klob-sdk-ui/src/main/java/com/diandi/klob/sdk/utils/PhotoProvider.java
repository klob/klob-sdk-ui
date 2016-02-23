package com.diandi.klob.sdk.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.diandi.klob.sdk.R;
import com.diandi.klob.sdk.concurrent.SimpleExecutor;
import com.diandi.klob.sdk.concurrent.SimpleTask;
import com.diandi.klob.sdk.photo.BitmapDecoder;
import com.diandi.klob.sdk.photo.BitmapUtil;
import com.diandi.klob.sdk.util.CacheUtils;
import com.diandi.klob.sdk.util.FileUtils;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.util.photo.ScreenUtils;
import com.diandi.klob.sdk.widget.ActionSheet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-28  .
 * *********    Time : 16:07 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class PhotoProvider implements ActionSheet.ActionSheetListener {
    public static String FILE_PREFIX = "icon";
    public static int REQUESTCODE_CAMERA = 1;
    public static int REQUESTCODE_LOCATION = 2;
    public static int REQUESTCODE_CROP = 3;
    public static int REQUESTCODE_LOCATION_KIKAT = 4;
    private static String TAG = "PhotoProvider";
    private CameraCallBack mCallBack;
    private Activity mContext;
    private String mCacheDirectory = "";
    private String dateTime;
    private int degree = 0;
    private List<String> mUrlList;
    private boolean isFragment = false;
    private Fragment mFragment;

    private PhotoProvider(FragmentActivity context, CameraCallBack callBack, boolean isFragment) {
        mContext = context;
        mCallBack = callBack;
        this.isFragment = isFragment;
        mCacheDirectory = CacheUtils.getCacheDirectory(mContext, true, FILE_PREFIX) + "";
        mUrlList = new ArrayList<>();
    }

    public PhotoProvider(FragmentActivity context, CameraCallBack callBack) {
        this(context, callBack, false);
    }

    public PhotoProvider(Fragment fragment, CameraCallBack callBack) {
        this(fragment.getActivity(), callBack, true);
        mFragment = fragment;
    }


    public static String saveToSdCard(Context context, Bitmap bitmap) {
        Date date = new Date(System.currentTimeMillis());
        String dateTime = date.getTime() + "";
        File file = new File(CacheUtils.getCacheDirectory(context, true, "icon") + dateTime + ".png");
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        L.i("uri ", file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public List<String> getUrlList() {
        return mUrlList;
    }

    public void clear() {
        mUrlList.clear();
    }

    public void startCamera() {
        Date date = new Date(System.currentTimeMillis());
        dateTime = date.getTime() + "";
        File f = new File(CacheUtils.getCacheDirectory(mContext, true, FILE_PREFIX) + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        L.v("uri", uri + "");

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mContext.startActivityForResult(camera, 1);
    }

    public void startLocation() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mContext.startActivityForResult(intent, REQUESTCODE_LOCATION_KIKAT);
        } else {
            mContext.startActivityForResult(intent, REQUESTCODE_LOCATION);
        }

    }

    public void startCrop() {
        String path = getCameraResultPath();
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            Uri uri = Uri.fromFile(file);
            startCrop(uri);
        }
    }

    public void startCrop(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url = getPath(mContext, uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        } else {
            intent.setDataAndType(uri, "image/*");
        }
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        // intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        mContext.startActivityForResult(intent, REQUESTCODE_CROP);
    }

    public String getCameraResultPath() {
        String path = mCacheDirectory + dateTime;
        return path;
    }

    public void showPhotoSelector() {
        showActionSheet();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUESTCODE_CAMERA) {
                if (mCallBack != null) {
                    degree = BitmapUtil.readPictureDegree(getCameraResultPath());
                    if (degree != 0) {
                        SimpleExecutor.getInstance().execute(new RotateImageTask());
                    } else {
                        mCallBack.cameraFinish(getCameraResultPath());
                    }

                }


            } else if (requestCode == REQUESTCODE_LOCATION || requestCode == REQUESTCODE_LOCATION_KIKAT) {
                if (data == null) {
                    return;
                }
                if (mCallBack != null) {
                    mCallBack.locationFinish(data.getData());
                }
            } else if (requestCode == REQUESTCODE_CROP) {
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        String iconUrl = PhotoProvider.saveToSdCard(mContext, bitmap);
                        mUrlList.add(iconUrl);
                        L.d(TAG,iconUrl);
                        if (mCallBack != null) {
                            mCallBack.cropFinish(bitmap, iconUrl);
                        }

                    }
                }
            }
        }
    }

    public void startCrop(Uri uri, Bundle data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url = getPath(mContext, uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        } else {
            intent.setDataAndType(uri, "image/*");
        }
        L.v(TAG);
        intent.putExtras(data);
        mContext.startActivityForResult(intent, REQUESTCODE_CROP);
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    public void showActionSheet() {
        mContext.setTheme(R.style.ActionSheetStyleIOS7);
        FragmentManager fragmentManager;
        if (!isFragment) {
            fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
            ActionSheet.createBuilder(mContext, fragmentManager)
                    .setCancelButtonTitle("取消")
                    .setOtherButtonTitles("拍照", "从相册选择")
                    .setCancelableOnTouchOutside(true)
                    .setListener(this).show();
        } else {
            fragmentManager = (mFragment.getChildFragmentManager());
            ActionSheet.createBuilder(mContext, fragmentManager)
                    .setCancelButtonTitle("取消")
                    .setOtherButtonTitles("拍照", "从相册选择")
                    .setCancelableOnTouchOutside(true)
                    .setListener(this).show();
        }
    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        switch (index) {
            case 0:
                startCamera();
                break;
            case 1:
                startLocation();
                break;
            default:
        }
    }

    public interface CameraCallBack {
        void cameraFinish(String filePath);

        void locationFinish(Uri uri);

        void cropFinish(Bitmap bitmap, String cropPath);
    }

    private class RotateImageTask extends SimpleTask {
        @Override
        public void doInBackground() {
            try {
                Bitmap bitmap = BitmapDecoder.decodeSampledBitmapFromFile(getCameraResultPath(), ScreenUtils.getScreenHeight(mContext), ScreenUtils.getScreenHeight(mContext));
                bitmap = BitmapUtil.rotateBitmap(bitmap, degree);

                ByteArrayOutputStream outArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outArray);

                FileUtils.writeFile(new File(getCameraResultPath()), outArray.toByteArray());
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish(boolean canceled) {
            if (mCallBack != null) {
                mCallBack.cameraFinish(getCameraResultPath());
            }

        }
    }


}
