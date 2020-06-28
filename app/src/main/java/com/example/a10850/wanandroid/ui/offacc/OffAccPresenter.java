package com.example.a10850.wanandroid.ui.offacc;

import com.example.a10850.wanandroid.entity.ProjectBean;
import com.example.common.base.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/***
 * 创建时间：2020/2/29 15:11
 * 创建人：10850
 * 功能描述：
 */
public class OffAccPresenter extends BasePresenter<OffAccContract.Model, OffAccContract.View>
        implements OffAccContract.Presenter {

    @Override
    public void getOffAccData() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().handleOffAccData()
                    .subscribe(new Observer<ProjectBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ProjectBean dataBean) {
                            getView().requestSuccess(dataBean);
                            getView().dismissLoading();
                        }

                        @Override
                        public void onError(Throwable e) {
                            getView().registerFailure("请求失败!");
                            getView().dismissLoading();
                        }

                        @Override
                        public void onComplete() {
                            getView().dismissLoading();
                        }
                    });
        }
    }

    @Override
    protected OffAccContract.Model createModule() {
        return new OffAccModel();
    }

    @Override
    public void start() {

    }
}
