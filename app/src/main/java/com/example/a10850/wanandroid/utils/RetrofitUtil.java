package com.example.a10850.wanandroid.utils;

import com.example.a10850.wanandroid.constant.UrlString;
import com.example.a10850.wanandroid.interfaces.ApiService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * 创建时间：2020/2/2 21:52
 * 创建人：10850
 * 功能描述：单例模式：https://www.runoob.com/design-pattern/singleton-pattern.html
 * 返回retrofit的服务
 */
public class RetrofitUtil {

    private volatile static ApiService apiService;

    public static ApiService getInstance() {
        if (apiService == null) {
            synchronized (ApiService.class) {
                if (apiService == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(UrlString.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    apiService = retrofit.create(ApiService.class);
                }
            }
        }
        return apiService;
    }


}
