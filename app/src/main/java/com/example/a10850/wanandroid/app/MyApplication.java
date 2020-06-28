package com.example.a10850.wanandroid.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.dao.MySqliteOpenHelper;
import com.example.a10850.wanandroid.greenDao.db.DaoMaster;
import com.example.a10850.wanandroid.greenDao.db.DaoSession;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


/***
 * 创建时间：2020/2/4 10:16
 * 创建人：10850
 * 功能描述：
 */
public class MyApplication extends Application {

    //双重效验锁实现单例
    //private static volatile MyApplication mInstance;
    private static MyApplication mInstance;
    SharedPreferences mSharedPreferences;

    public static synchronized MyApplication getInstance() {
       /* if (mInstance == null) {
            synchronized (MyApplication.class) {
                if (mInstance == null) {
                    mInstance = new MyApplication();
                }
            }
        }*/
        return mInstance;
    }

    public static MyApplication mApp;


    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        mContext = this;
        initGreenDao();

        mInstance = this;
        mSharedPreferences = MyApplication.getInstance().getSharedPreferences("mao_wanandroid_sharepreference", Context.MODE_PRIVATE);

        mApp = this;
    }

    private Context mContext;


    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private static DaoSession daoSession;

    private void initGreenDao() {
        // 初始化//如果你想查看日志信息，请将 DEBUG 设置为 true
        /*if (BuildConfig.DEBUG){
            MigrationHelper.DEBUG = true;
        }else {
            MigrationHelper.DEBUG = false;
        }*/

        //数据库名字
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(mContext, "greenDaoTest.db", null);

        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);

        daoSession = daoMaster.newSession();


    }

    /**
     * 提供一个全局的会话
     *
     * @return
     */
    public static DaoSession getDaoSession() {
        return daoSession;
    }


}
