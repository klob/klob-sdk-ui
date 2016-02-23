package com.diandi.klob.sdk.widget.klist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.diandi.klob.sdk.common.ListViewTool;
import com.diandi.klob.sdk.widget.OverridePendingUtil;

import java.util.List;

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
public abstract class KListAdapter<E> extends BaseAdapter {

    public String TAG = getClass().getSimpleName();
    public List<E> mDataList;
    public Context mContext;
    public LayoutInflater mInflater;

    protected Toast mToast;
    protected FootUpdate.LoadMore mLoadMore;
    protected ListViewTool mListViewTool = new ListViewTool(true);

    public KListAdapter(Context context) {
        this(context, null, null);
    }

    public KListAdapter(Context context, List<E> list) {
        this(context, list, null);
    }

    public KListAdapter(Context context, List<E> list, FootUpdate.LoadMore loadMore) {
        this.mContext = context;
        this.mDataList = list;
        this.mLoadMore = loadMore;
        mInflater = LayoutInflater.from(context);
    }

    protected KListAdapter() {
    }

    public List<E> getList() {
        return mDataList;
    }

    public void setList(List<E> list) {
        this.mDataList = list;
        notifyDataSetChanged();
    }

    public void add(E e) {
        this.mDataList.add(e);
        notifyDataSetChanged();
    }

    public void addAll(List<E> list) {
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(E t) {
        this.mDataList.remove(t);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.mDataList.clear();
        notifyDataSetChanged();
    }
    @Override
    public E getItem(int position) {
        return mDataList.get(position);
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = bindView(position, convertView, parent);
        if (position == mDataList.size() - 1) {
            if (mLoadMore != null) {
                mLoadMore.onLoadMore();
            }
        }
        return convertView;
    }

    public abstract View bindView(int position, View convertView, ViewGroup parent);

    public void ShowToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            ((Activity) mContext).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(mContext, text,
                                Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });

        }
    }


    public void startAnimActivity(Class<?> cla) {
        mContext.startActivity(new Intent(mContext, cla));
        if (mContext instanceof Activity) {
            OverridePendingUtil.in((Activity) mContext);
        }
    }

    public void startAnimActivity(Intent intent) {
        mContext.startActivity(intent);
        if (mContext instanceof Activity) {
            OverridePendingUtil.in((Activity) mContext);
        }
    }


}
