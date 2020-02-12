package com.example.a10850.wanandroid.ui.login;

import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;
import com.example.common.base.ResponseCallback;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/***
 * 创建时间：2020/2/10 17:59
 * 创建人：10850
 * 功能描述：当打印的error为空的时候，是线程问题
 */
public class LoginModel implements LoginContract.Model {

    @Override
    public void login(PersonBean user, final ResponseCallback callback) {
        if (user == null)
            callback.onError();
        RetrofitUtil.getInstance()
                .postLogin(user.getData().getUsername(), user.getData().getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PersonBean personBean) {
                        callback.onSuccess(personBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError();
                    }

                    @Override
                    public void onComplete() {
                        callback.onComplete();
                    }
                });
    }
}
