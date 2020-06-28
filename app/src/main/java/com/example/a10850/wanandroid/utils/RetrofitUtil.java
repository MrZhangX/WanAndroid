package com.example.a10850.wanandroid.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.example.a10850.wanandroid.app.MyApplication;
import com.example.a10850.wanandroid.constant.UrlString;
import com.example.a10850.wanandroid.interfaces.ApiService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.addInterceptor(new ReadCookieInterceptor());
                    builder.addInterceptor(new WriteCookieInterceptor());

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(builder.build())
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

    /***
     * 似乎是超时了，导致cookie失效了，需要重新获取
     */
    public static class ReadCookieInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            Request.Builder builder = chain.request().newBuilder();

            long expire = (long) SPUtils.get(
                    MyApplication.mApp, "cookie_expire", 0L);

            if (expire > System.currentTimeMillis()) {
                String cookies = (String) SPUtils.get(
                        MyApplication.mApp, "cookie", "");
                for (String cookie : cookies.split(";")) {
                    builder.addHeader("Cookie", cookie);
                }
                return chain.proceed(builder.build());
            }

            return response;
        }
    }

    public static class WriteCookieInterceptor implements Interceptor {

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            if (!SPUtils.contains(MyApplication.mApp, "cookie")) {
                List<String> headers = response.headers("Set-Cookie");
                Log.i("zxd", "intercept: " + response.headers().toString());
                if (!headers.isEmpty()) {
                    final StringBuilder sb = new StringBuilder();
                    Stream<String> stream = headers.stream();
                    stream.forEach(new Consumer<String>() {
                        @Override
                        public void accept(String s) {
                            sb.append(s);
                        }
                    });
                    SPUtils.put(MyApplication.mApp, "cookie", sb.toString());
                    SPUtils.put(MyApplication.mApp, "cookie_expire", System.currentTimeMillis() + 30 * 24 * 3600 * 1000L);
                }
            }
            return response;
        }
    }


}
