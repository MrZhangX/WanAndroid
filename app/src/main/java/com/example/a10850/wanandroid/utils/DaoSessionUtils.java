package com.example.a10850.wanandroid.utils;

import android.util.Log;

import com.example.a10850.wanandroid.app.MyApplication;
import com.example.a10850.wanandroid.greenDao.db.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/***
 * 创建时间：2020/2/29 10:36
 * 创建人：10850
 * 功能描述：
 */
public class DaoSessionUtils {
    static DaoSession daoSession;

    public static DaoSession getDaoInstance() {

        if (daoSession == null) {
            daoSession = MyApplication.getDaoSession();
        }
        //清空所有数据表的缓存数据
        //daoSession.clear();
        return daoSession;
    }


    /**
     * insert() 插入数据
     */
    public static void insertDbBean(Object bean) {
        try {
            getDaoInstance().insert(bean);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("zxd", "插入本地数据失败：" + e.getMessage());
        }

    }


    /**
     * insertOrReplace()数据存在则替换，数据不存在则插入
     */
    public static void insertOrReplaceDbBean(Object bean) {
        try {
            getDaoInstance().insertOrReplace(bean);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("zxd", "插入或替换本地数据失败：" + e.getMessage());
        }

    }


    /**
     * delete()删除单个数据
     */
    public static void deleteDbBean(Object bean) {
        try {
            getDaoInstance().delete(bean);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("zxd", "删除本地数据失败：" + e.getMessage());
        }

    }


    /**
     * deleteAll()删除所有数据
     */
    public static void deleteAllDbBean(Object bean) {
        try {
            getDaoInstance().deleteAll(bean.getClass());

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("zxd", "删除本地所有数据失败：" + e.getMessage());
        }

    }


    /**
     * update()修改本地数据
     */
    public static void updateDbBean(Object bean) {
        try {
            getDaoInstance().update(bean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("zxd", "修改本地所有数据失败：" + e.getMessage());
        }

    }

    /**
     * loadAll()查询本地所有数据
     */
    public static List<? extends Object> queryAll(Object bean) {
        List<Object> beanList = null;
        try {
            beanList = (List<Object>) getDaoInstance().loadAll(bean.getClass());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("zxd", "查询本地所有数据失败：" + e.getMessage());
        }

        return beanList;
    }


    /**
     * 根据条件查询本地所有数据
     * 调用时传值方法whereConditions
     * List<WhereCondition> whereConditions = new ArrayList<>();
     * whereConditions.add(StudentDao.Properties.Name.eq("小明"));
     * whereConditions.add(StudentDao.Properties.Age.eq(22));
     */
    public static List<? extends Object> queryConditionAll(Object bean, List<WhereCondition> whereConditions) throws ClassCastException {
        List<Object> beanList = null;
        try {

            QueryBuilder queryBuilder = getDaoInstance().queryBuilder(bean.getClass());
            //把条件循环加入
            if (null != whereConditions) {
                for (WhereCondition whereCondition : whereConditions) {
                    queryBuilder.where(whereCondition);
                }

            }
            beanList = queryBuilder.build().list();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("zxd", "按条件查询本地数据失败：" + e.getMessage());
        }

        return beanList;
    }


    /**
     * 根据原始 SQL 数据查询
     * 手输写 SQL 语句sqlConditions
     */
    public static List<? extends Object> querySqlAll(Object bean, String sqlConditions) throws ClassCastException {
        List<Object> beanList = null;
        try {
            //查询条件
            WhereCondition.StringCondition stringCondition = new WhereCondition.StringCondition(sqlConditions);
            //查询QueryBuilder
            QueryBuilder<Object> queryBuilder = (QueryBuilder<Object>) getDaoInstance().queryBuilder(bean.getClass());
            //添加查询条件
            queryBuilder.where(stringCondition);

            beanList = queryBuilder.build().list();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("zxd", "sql按条件查询本地数据失败：" + e.getMessage());
        }

        return beanList;
    }
}
