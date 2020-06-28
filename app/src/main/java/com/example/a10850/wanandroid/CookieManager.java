package com.example.a10850.wanandroid;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @author zxd
 * @Description cookie 管理类 继承
 * @Time 2020/03/14
 */
public class CookieManager implements CookieJar {


    private static volatile CookieManager cookieManager;

    private final PersistentCookieStore persistentCookieStore;

    //双重效验锁实现单例
    public static CookieManager getInstance(){
        if(cookieManager  == null){
            synchronized (CookieManager.class){
                if(cookieManager  == null){
                    cookieManager=new CookieManager();
                }
            }
        }
        return cookieManager;
    }

    public CookieManager(){
        persistentCookieStore=new PersistentCookieStore();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        persistentCookieStore.add(url,cookies);
    }

    @NonNull
    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        List<Cookie> cookies = persistentCookieStore.get(url);
        Log.e("zxd","cookies size: "+cookies.size()+" cookies values :"+cookies.toString());
        return persistentCookieStore.get(url);
    }


    public void clearAllCookie(){
        persistentCookieStore.removeAll();
    }
}

