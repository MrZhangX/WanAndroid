package com.example.a10850.wanandroid.ui.UsedWeb;

import com.example.a10850.wanandroid.entity.UsedWebBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;
import com.example.common.base.ResponseCallback;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/***
 * 创建时间：2020/2/11 21:00
 * 创建人：10850
 * 功能描述：
 */
public class UsedWebModel implements UsedWebContract.Model {

    @Override
    public void getUsedWeb(final ResponseCallback callback) {
        RetrofitUtil.getInstance().getUsedWeb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UsedWebBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UsedWebBean usedWebBean) {
                        callback.onSuccess(usedWebBean);
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
