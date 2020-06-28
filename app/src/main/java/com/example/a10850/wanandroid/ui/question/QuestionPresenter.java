package com.example.a10850.wanandroid.ui.question;

import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.common.base.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/***
 * 创建时间：2020/3/7 13:38
 * 创建人：10850
 * 功能描述：
 */
public class QuestionPresenter extends BasePresenter<QuestionContract.Model, QuestionContract.View>
        implements QuestionContract.Presenter {
    @Override
    public void getQuestionData(int page) {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().requestData(page).subscribe(new Observer() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Object o) {
                    getView().dismissLoading();
                    ContentListBean bean = (ContentListBean) o;
                    getView().registerSuccess(bean.getData().getDatas());
                    getView().getCurPage(bean.getData().getCurPage() + 1);
                }

                @Override
                public void onError(Throwable e) {
                    getView().dismissLoading();
                    getView().registerFailure(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    @Override
    public void refreshData(int page) {
        getData(page);
    }


    @Override
    public void loadMoreData(int page) {
        getData(page);
    }

    private void getData(final int page) {
        if (isViewAttached()) {
            getModule().requestData(page).subscribe(new Observer() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Object o) {
                    ContentListBean bean = (ContentListBean) o;
                    getView().registerSuccess(bean.getData().getDatas());
                    if (page <= bean.getData().getPageCount())
                        getView().getCurPage(bean.getData().getCurPage() + 1);
                }

                @Override
                public void onError(Throwable e) {
                    getView().registerFailure(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    @Override
    protected QuestionContract.Model createModule() {
        return new QuestionModel();
    }

    @Override
    public void start() {

    }
}
