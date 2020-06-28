package com.example.a10850.wanandroid.ui.offacc.list;

import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.common.base.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/***
 * 创建时间：2020/2/29 21:07
 * 创建人：10850
 * 功能描述：
 */
public class OffAccListPresenter extends BasePresenter<OffAccListContract.Model, OffAccListContract.View>
        implements OffAccListContract.Presenter {
    @Override
    public void getOffAccData(int id, int page) {
        getData(id, page);
    }

    @Override
    public void refresh(int id) {
        getDataWithoutLoading(id, 1);
    }

    @Override
    public void loadMore(int id, int page) {
        getDataWithoutLoading(id, page);
    }

    private void getData(int id, int page) {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().handleOffAccListData(id, page)
                    .subscribe(new Observer<ContentListBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ContentListBean dataBean) {
                            getView().requestSuccess(dataBean);
                            getView().dismissLoading();
                        }

                        @Override
                        public void onError(Throwable e) {
                            getView().requestFailure("请求失败!");
                            getView().dismissLoading();
                        }

                        @Override
                        public void onComplete() {
                            getView().dismissLoading();
                        }
                    });
        }
    }

    private void getDataWithoutLoading(int id, int page) {
        if (isViewAttached()) {
            getModule().handleOffAccListData(id, page)
                    .subscribe(new Observer<ContentListBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ContentListBean dataBean) {
                            getView().requestSuccess(dataBean);
                        }

                        @Override
                        public void onError(Throwable e) {
                            getView().requestFailure("请求失败!");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    protected OffAccListContract.Model createModule() {
        return new OffAccListModel();
    }

    @Override
    public void start() {

    }
}
