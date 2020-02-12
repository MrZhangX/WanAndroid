package com.example.a10850.wanandroid.ui.register;

import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.common.base.BasePresenter;
import com.example.common.base.ResponseCallback;

/***
 * 创建时间：2020/2/10 20:57
 * 创建人：10850
 * 功能描述：
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View>
        implements RegisterContract.Presenter {
    @Override
    public void register() {
        if (isViewAttached()) {
            getView().showLoading();

            getModule().register(getView().getUserInfo(), new ResponseCallback() {
                @Override
                public void onSuccess(Object data) {
                    PersonBean data1 = (PersonBean) data;
                    getView().registerSuccess(data1);
                    getView().dismissLoading();
                }

                @Override
                public void onFailure() {
                    getView().registerFailure("注册失败!");
                    getView().dismissLoading();
                }

                @Override
                public void onError() {
                    getView().registerFailure("注册失败!");
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
    protected RegisterContract.Model createModule() {
        return new RegisterModel();
    }

    @Override
    public void start() {

    }
}
