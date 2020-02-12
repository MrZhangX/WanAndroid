package com.example.a10850.wanandroid.ui.login;

import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.common.base.IBaseModel;
import com.example.common.base.IBaseView;
import com.example.common.base.ResponseCallback;

/***
 * 创建时间：2020/2/10 17:57
 * 创建人：10850
 * 功能描述：
 */
public interface LoginContract {

    interface Model extends IBaseModel {
        /**
         * 登录
         *
         * @param user     用户信息
         * @param callback 回调
         */
        void login(PersonBean user, ResponseCallback callback);
    }

    interface View extends IBaseView {
        /**
         * 返回用户信息
         */
        PersonBean getUserInfo();

        /**
         * 登录成功
         */
        void loginSuccess(PersonBean user);

        void loginFailure(String msg);

    }

    interface Presenter {
        /**
         * 登录
         */
        void login();
    }
}
