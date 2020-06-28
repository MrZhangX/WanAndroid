package com.example.a10850.wanandroid.ui.offacc.list;

import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/***
 * 创建时间：2020/2/29 20:57
 * 创建人：10850
 * 功能描述：
 */
public class OffAccListModel implements OffAccListContract.Model {

    @Override
    public Observable<ContentListBean> handleOffAccListData(int id, int page) {
        return RetrofitUtil.getInstance().getOffAccList(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
