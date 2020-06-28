package com.example.a10850.wanandroid.ui.offacc.list;

import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.common.base.IBaseModel;
import com.example.common.base.IBaseView;

import io.reactivex.Observable;

/***
 * 创建时间：2020/2/29 14:20
 * 创建人：10850
 * 功能描述：尝试把刷新和下拉加载放到契约接口里
 * ****重要的尝试****
 * 两个字，看起来真“舒服”
 */
public interface OffAccListContract {

    interface Model extends IBaseModel {
        //获取数据
        Observable<ContentListBean> handleOffAccListData(int id, int page);
    }

    interface View extends IBaseView {

        void requestSuccess(ContentListBean dataBean);

        void requestFailure(String msg);

        void refreshList(ContentListBean dataBean);

        void loadMoreList(ContentListBean dataBean);
    }

    interface Presenter {
        //获取数据
        void getOffAccData(int id, int page);

        void refresh(int id);

        void loadMore(int id, int page);
    }
}
