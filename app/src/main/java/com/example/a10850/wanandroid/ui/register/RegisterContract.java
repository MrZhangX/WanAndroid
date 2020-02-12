package com.example.a10850.wanandroid.ui.register;

import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.common.base.IBaseModel;
import com.example.common.base.IBaseView;
import com.example.common.base.ResponseCallback;

import java.util.Map;

/***
 * 创建时间：2020/2/10 20:47
 * 创建人：10850
 * 功能描述：
 */
public interface RegisterContract {

    interface Model extends IBaseModel {
        /**
         * 注册
         *
         * @param user     用户信息
         * @param callback 回调
         */
        void register(Map<String, String> user, ResponseCallback callback);
    }

    interface View extends IBaseView {
        /**
         * 返回用户信息
         */
        Map<String, String> getUserInfo();

        /**
         * 注册成功
         */
        void registerSuccess(PersonBean user);

        void registerFailure(String msg);

    }

    interface Presenter {
        /**
         * 注册
         */
        void register();
    }
}
