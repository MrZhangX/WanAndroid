package com.example.common.app;

import android.app.Application;

/***
 * 创建时间：2020/2/2 19:28
 * 创建人：10850
 * 功能描述：
 */
public class MyApp extends Application {

    //单例模式
    private static MyApp myApp;

    /**
     * 单例模式中获取唯一的MyApp实例
     *
     * @return
     */
    public static MyApp getInstance() {
        if (null == myApp) {
            myApp = new MyApp();
        }
        return myApp;
    }
}
