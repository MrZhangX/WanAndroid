package com.example.a10850.wanandroid.ui.UsedWeb;

import com.example.a10850.wanandroid.entity.UsedWebBean;
import com.example.common.base.BasePresenter;
import com.example.common.base.ResponseCallback;

/***
 * 创建时间：2020/2/11 21:03
 * 创建人：10850
 * 功能描述：
 */
public class UsedWebPresenter extends BasePresenter<UsedWebContract.Model, UsedWebContract.View>
        implements UsedWebContract.Presenter {

    @Override
    public void getUsedWebData() {
        getView().showLoading();
        getModule().getUsedWeb(new ResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                getView().requestSuccess((UsedWebBean) data);
                getView().dismissLoading();
            }

            @Override
            public void onFailure() {
                getView().requestFailure("错误！");
                getView().dismissLoading();
            }

            @Override
            public void onError() {
                getView().requestFailure("错误！");
                getView().dismissLoading();
            }

            @Override
            public void onComplete() {
                getView().dismissLoading();
            }
        });
    }

    @Override
    protected UsedWebContract.Model createModule() {
        return new UsedWebModel();
    }

    @Override
    public void start() {

    }
}
