package com.example.a10850.wanandroid.ui.offacc;

import com.example.a10850.wanandroid.entity.ProjectBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/***
 * 创建时间：2020/2/29 15:11
 * 创建人：10850
 * 功能描述：
 */
public class OffAccModel implements OffAccContract.Model {
    @Override
    public Observable<ProjectBean> handleOffAccData() {
        return RetrofitUtil.getInstance().getOffAcc()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
