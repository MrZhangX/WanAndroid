package com.example.a10850.wanandroid.ui.user;

import android.util.Log;

import com.example.a10850.wanandroid.entity.CollectBean;
import com.example.a10850.wanandroid.entity.CollectWebBean;
import com.example.a10850.wanandroid.entity.SquareShareListBean;
import com.example.a10850.wanandroid.entity.UsedWebBean;
import com.example.common.base.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/***
 * 创建时间：2020/3/10 9:22
 * 创建人：10850
 * 功能描述：
 * 1.问题：cookie超时导致需要用到的地方报错
 *
 */
public class UserCenterPresenter extends BasePresenter<UserCenterContract.Model, UserCenterContract.View>
        implements UserCenterContract.Presenter {
    @Override
    public void getShareArtData(int page) {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getShareArt(page).subscribe(new Observer<SquareShareListBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(SquareShareListBean bean) {
                    getView().dismissLoading();
                    getView().requestSuccessShare(bean);
                }

                @Override
                public void onError(Throwable e) {
                    getView().dismissLoading();
                    getView().requestFailure(e.getMessage());
                }

                @Override
                public void onComplete() {
                    getView().dismissLoading();
                }
            });
        }
    }

    @Override
    public void refreshArtData(int page) {
        getShareArts(page);
    }

    @Override
    public void loadMoreArtData(int page) {
        getShareArts(page);
    }

    private void getShareArts(int page) {
        if (isViewAttached())
            getModule().getShareArt(page).subscribe(new Observer<SquareShareListBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(SquareShareListBean bean) {
                    getView().requestSuccessShare(bean);
                }

                @Override
                public void onError(Throwable e) {
                    getView().requestFailure(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
    }

    //收藏文章开始
    @Override
    public void getCollectArt(int page) {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getCollectArt(page).subscribe(new Observer<CollectBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(CollectBean collectBean) {
                    getView().dismissLoading();
                    getView().requestSuccess(collectBean);
                }

                @Override
                public void onError(Throwable e) {
                    getView().dismissLoading();
                    getView().requestFailure(e.getMessage());
                }

                @Override
                public void onComplete() {
                    getView().dismissLoading();
                }
            });
        }
    }

    @Override
    public void refreshCollectArt(int page) {
        getCollectData(page);
    }

    @Override
    public void loadMoreCollectArt(int page) {
        getCollectData(page);
    }

    private void getCollectData(int page) {
        if (isViewAttached())
            getModule().getCollectArt(page).subscribe(new Observer<CollectBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(CollectBean collectBean) {
                    getView().requestSuccess(collectBean);
                }

                @Override
                public void onError(Throwable e) {
                    getView().requestFailure(e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
    }
    //收藏文章结束

    @Override
    public void getCollectWeb() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getCollectWeb().subscribe(new Observer<UsedWebBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(UsedWebBean webBean) {
                    Log.i("zxd", "onNext: ");
                    getView().dismissLoading();
                    getView().requestSuccessShare(webBean);
                }

                @Override
                public void onError(Throwable e) {
                    Log.i("zxd", "onError: ");
                    getView().dismissLoading();
                    getView().requestFailure(e.getMessage());
                }

                @Override
                public void onComplete() {
                    Log.i("zxd", "onComplete: ");
                    getView().dismissLoading();
                }
            });
        }
    }

    @Override
    public void editWeb(int id, String name, String link) {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().editWeb(id, name, link).subscribe(new Observer<CollectWebBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(CollectWebBean collectWebBean) {
                    if (collectWebBean.getErrorCode() == 0) {
                        getModule().getCollectWeb().subscribe(new Observer<UsedWebBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(UsedWebBean webBean) {
                                getView().dismissLoading();
                                getView().requestSuccessShare(webBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().dismissLoading();
                                getView().requestFailure(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                getView().dismissLoading();
                            }
                        });
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    @Override
    public void delWeb(int id) {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().delWeb(id).subscribe(new Observer<CollectWebBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(CollectWebBean collectWebBean) {
                    if (collectWebBean.getErrorCode() == 0) {
                        getModule().getCollectWeb().subscribe(new Observer<UsedWebBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(UsedWebBean webBean) {
                                getView().dismissLoading();
                                getView().requestSuccessShare(webBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().dismissLoading();
                                getView().requestFailure(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                getView().dismissLoading();
                            }
                        });
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    @Override
    protected UserCenterContract.Model createModule() {
        return new UserCenterModel();
    }

    @Override
    public void start() {

    }
}
