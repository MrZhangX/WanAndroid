package com.example.a10850.wanandroid.login;

import android.util.Log;

import com.example.a10850.wanandroid.constant.UrlString;
import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.a10850.wanandroid.entity.UserBean;
import com.example.a10850.wanandroid.interfaces.ApiService;
import com.example.a10850.wanandroid.interfaces.ILoginRegisiterModel;
import com.example.a10850.wanandroid.interfaces.OnLoadCallBack;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * 创建时间：2019/12/6 10:19
 * 创建人：10850
 * 功能描述：
 */
public class LoginModel implements ILoginRegisiterModel {

    @Override
    public void onLogin(UserBean userBean, final OnLoadCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlString.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<PersonBean> login = apiService.postLogin(userBean.getUser(), userBean.getPassword());
        login.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PersonBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PersonBean personBean) {
                callBack.onSuccess(personBean);
            }

            @Override
            public void onError(Throwable e) {
                callBack.onFailure();
            }

            @Override
            public void onComplete() {

            }
        });
//        final Observable<UserBean> login = apiService.getLogin(userBean.getUser(), userBean.getPassword());
//        login.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserBean>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(UserBean userBean) {
//                callBack.onSuccess(userBean);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("zxd", "onLogin: " + e.getMessage());
//                callBack.onFailure();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }

    @Override
    public void onRegister(UserBean userBean, final OnLoadCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlString.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Map registerMap = new HashMap();
        registerMap.put("username", userBean.getUser());
        registerMap.put("password", userBean.getPassword());
        registerMap.put("repassword", userBean.getRepassword());
        Observable<UserBean> register = apiService.getRegister(registerMap);
        register.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserBean userBean) {
                callBack.onSuccess(userBean);
            }

            @Override
            public void onError(Throwable e) {
                callBack.onFailure();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onSaveUser(UserBean userBean) {

    }
}
