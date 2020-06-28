package com.example.a10850.wanandroid.ui.question;

import com.example.a10850.wanandroid.entity.ContentBean;
import com.example.common.base.IBaseModel;
import com.example.common.base.IBaseView;

import java.util.List;

import io.reactivex.Observable;

/***
 * 创建时间：2020/3/7 13:25
 * 创建人：10850
 * 功能描述：
 */
public interface QuestionContract {

    interface Model extends IBaseModel {

        Observable requestData(int page);
    }

    interface View extends IBaseView {

        void registerSuccess(List<ContentBean> list);

        void registerFailure(String msg);

        void getRefreshData(List<ContentBean> list);

        void getLoadMoreData(List<ContentBean> list);

        void getCurPage(int page);
    }

    interface Presenter {

        void getQuestionData(int page);

        void refreshData(int page);

        void loadMoreData(int page);
    }

}
