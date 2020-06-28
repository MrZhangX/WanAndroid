package com.example.a10850.wanandroid.ui.user;


import com.example.a10850.wanandroid.entity.CollectBean;
import com.example.a10850.wanandroid.entity.CollectWebBean;
import com.example.a10850.wanandroid.entity.SquareShareListBean;
import com.example.a10850.wanandroid.entity.UsedWebBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/***
 * 创建时间：2020/3/10 9:21
 * 创建人：10850
 * 功能描述：
 */
public class UserCenterModel implements UserCenterContract.Model {
    @Override
    public Observable<SquareShareListBean> getShareArt(int page) {
        return RetrofitUtil.getInstance().myShareArticle(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<CollectBean> getCollectArt(int page) {
        return RetrofitUtil.getInstance().getCollectList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<UsedWebBean> getCollectWeb() {
        return RetrofitUtil.getInstance().getCollectWebList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<CollectWebBean> editWeb(int id, String name, String link) {
        return RetrofitUtil.getInstance().updateCw(id, name, link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<CollectWebBean> delWeb(int id) {
        return RetrofitUtil.getInstance().delCw(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
