package com.example.a10850.wanandroid.ui.login;

import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.common.base.BasePresenter;
import com.example.common.base.ResponseCallback;

/***
 * 创建时间：2020/2/10 18:41
 * 创建人：10850
 * 功能描述：
 */
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login() {
        if (isViewAttached()) {
            getView().showLoading();

            getModule().login(getView().getUserInfo(), new ResponseCallback() {
                @Override
                public void onSuccess(Object data) {
                    PersonBean data1 = (PersonBean) data;
                    getView().loginSuccess(data1);
                    getView().dismissLoading();
                }

                @Override
                public void onFailure() {
                    getView().loginFailure("登录失败!");
                    getView().dismissLoading();
                }

                @Override
                public void onError() {
                    getView().loginFailure("登录失败!");
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
    protected LoginContract.Model createModule() {
        return new LoginModel();
    }

    @Override
    public void start() {

    }
}
