package com.example.a10850.wanandroid.ui.system;

import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/***
 * 创建时间：2020/2/12 19:09
 * 创建人：10850
 * 功能描述：
 */
public class PerSystemModel implements PerSystemContract.Model {
    @Override
    public Observable<ContentListBean> getPerSystemData(int page, int cid) {
        Observable<ContentListBean> contentListBeanObservable = RetrofitUtil.getInstance().getSystemList(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return contentListBeanObservable;
    }
}
