package com.diandi.klob.sdk.db;

import android.content.Context;

import com.diandi.klob.sdk.util.L;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Project name : Diandi1.18 .
 * *********    Version : 1.0
 * *********    Copyright @ 2014, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 基础本地DB操作类
 */
public abstract class BaseDao<T> {
    public static Class sOrmLiteSqliteOpenHelper;
    public final String TAG = getClass().getName();
    public Dao<T, Integer> mDao = null;
    protected OrmLiteSqliteOpenHelper mDataHelper;
    protected Context mContext;

    public BaseDao() {

    }

    /**
     * public CrashDao(Context context) {
     * super(context);
     * }
     * <p/>
     * public static CrashDao getInstance(Context context) {
     * if (mInstance == null) {
     * synchronized (CrashDao.class) {
     * if (mInstance == null) {
     * mInstance = new CrashDao(context);
     * }
     * }
     * }
     * return mInstance;
     * }
     *
     * @Override public Dao<LocalCrash, Integer> getDao() {
     * if (mDao == null) {
     * try {
     * mDao = getHelper().getDao(LocalCrash.class);
     * } catch (SQLException e) {
     * e.printStackTrace();
     * }
     * }
     * return mDao;
     * }
     * }
     */
    protected BaseDao(Context context) {
        this.mContext = context;
        getHelper();
        try {
            getDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public OrmLiteSqliteOpenHelper getHelper() {
        if (mDataHelper == null) {
            mDataHelper = OpenHelperManager.getHelper(mContext, sOrmLiteSqliteOpenHelper);
        }
        return mDataHelper;
    }

    protected abstract Dao<T, Integer> getDao() throws SQLException;

    public void create(T t) {
        try {
            mDao.createOrUpdate(t);
        } catch (SQLException e) {
            L.e(TAG, "创建失败t   " + e);
            e.printStackTrace();
        }
    }

    public void update(T t) {
        try {
            mDao.update(t);
        } catch (SQLException e) {
            L.e(TAG, "更新失败");
            e.printStackTrace();
        }
    }

    public void creates(List<T> ts) {
        try {
            for (T t : ts)
                mDao.createOrUpdate(t);
        } catch (SQLException e) {
            //         Log.e(TAG, "创建失败ts   " + e);
            e.printStackTrace();
        }
    }

    public void delete(T t) {
        try {
            mDao.delete(t);
        } catch (SQLException e) {
            L.e(TAG, "删除失败");
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try {
            L.e(TAG, mDao.deleteById(id));
        } catch (SQLException e) {
            L.e(TAG, "删除失败");
            e.printStackTrace();
        }
    }

    public List<T> queryAll() {
        List<T> list = new ArrayList<T>();
        try {
            list = mDao.queryForAll();
        } catch (SQLException e) {
            L.e(TAG, "查询失败");
            e.printStackTrace();
        }
        return list;
    }

    public T queryTById(int id) {
        T t = null;
        try {
            t = mDao.queryForId(id);
        } catch (SQLException e) {
            L.e(TAG, "查询失败");
            e.printStackTrace();
        }
        return t;
    }

    public T queryByParam(String idName, String idValue) {
        List<T> lst = query(idName, idValue);
        if (null != lst && !lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    public T queryByParams(String[] attributeNames, String[] attributeValues) {
        try {
            List<T> lst = query(attributeNames, attributeValues);
            if (null != lst && !lst.isEmpty()) {
                return lst.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> query(PreparedQuery<T> preparedQuery) {
        try {
            Dao<T, Integer> dao = getDao();
            return dao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> query(String attributeName, String attributeValue) {
        try {
            QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
            queryBuilder.orderBy("_id", false).where().eq(attributeName, attributeValue);
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            return query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<T> queryAllInOrder() {
        try {
            QueryBuilder<T, Integer> queryBuilder = mDao.queryBuilder().orderBy("_id", false);
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            return query(preparedQuery);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> queryAllInOrder(int count) {
        if (count < 0)
            throw new IndexOutOfBoundsException("count must be more than 0 ");
        try {
            QueryBuilder<T, Integer> queryBuilder = mDao.queryBuilder().orderBy("_id", false);
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            List<T> list = query(preparedQuery);

            int size = list.size();
            if (size <= count)
                return list;
            if (size > count)
                return list.subList(0, count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> query(String[] attributeNames, String[] attributeValues) {
        try {
            if (attributeNames.length != attributeValues.length) {
                L.e(TAG, "params size is not equal");
            }
            QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
            queryBuilder.orderBy("_id", false);
            Where<T, Integer> wheres = queryBuilder.where();
            if (attributeNames.length == 2) {
                wheres.eq(attributeNames[0], attributeValues[0]).and().eq(attributeNames[1], attributeValues[1]);
            } else {
                for (int i = 0; i < attributeNames.length; i++) {
                    if (i != 0)
                        wheres = wheres.and().eq(attributeNames[i], attributeValues[i]);
                }
            }
            PreparedQuery<T> preparedQuery = queryBuilder.prepare();
            return query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAll() {
        for (T t : queryAll()) {
            delete(t);
        }
    }


}
