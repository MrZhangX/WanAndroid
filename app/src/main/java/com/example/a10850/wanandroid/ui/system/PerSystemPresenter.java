package com.example.a10850.wanandroid.ui.system;

import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.common.base.BasePresenter;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/***
 * 创建时间：2020/2/12 19:23
 * 创建人：10850
 * 功能描述：
 */
public class PerSystemPresenter extends BasePresenter<PerSystemContract.Model, PerSystemContract.View>
        implements PerSystemContract.Presenter {

    @Override
    public void getPerSystemData() {
        if (isViewAttached()) {
            getView().showLoading();
            Map<String, String> params = getView().getParams();
            getModule().getPerSystemData(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("cid")))
                    .subscribe(new Observer<ContentListBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ContentListBean contentListBean) {
                            getView().requestSuccess(contentListBean);
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
    protected PerSystemContract.Model createModule() {
        return new PerSystemModel();
    }

    @Override
    public void start() {

    }
}
