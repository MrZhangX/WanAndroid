package com.example.a10850.wanandroid.ui.register;

import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;
import com.example.common.base.ResponseCallback;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/***
 * 创建时间：2020/2/10 20:56
 * 创建人：10850
 * 功能描述：
 */
public class RegisterModel implements RegisterContract.Model {


    @Override
    public void register(Map<String, String> user, final ResponseCallback callback) {
        if (user == null)
            callback.onError();
        RetrofitUtil.getInstance().postRegister(user)
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
