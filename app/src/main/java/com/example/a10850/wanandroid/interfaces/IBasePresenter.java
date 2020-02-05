package com.example.a10850.wanandroid.interfaces;

/***
 * 创建时间：2019/12/2 15:36
 * 创建人：10850
 * 功能描述：
 */
public interface IBasePresenter {
//public interface IBasePresenter<V extends IBaseView> {

    //    void attachView(V view);
    void attachView(IBaseView view);

    void detachView();

    boolean isViewAttached();

//    V getView();

    void loadData();
}
