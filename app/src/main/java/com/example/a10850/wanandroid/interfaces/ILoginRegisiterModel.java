package com.example.a10850.wanandroid.interfaces;

import com.example.a10850.wanandroid.entity.UserBean;

/***
 * 创建时间：2019/12/6 11:19
 * 创建人：10850
 * 功能描述：登录、注册，不包含加载数据
 */
public interface ILoginRegisiterModel {
    //登录
    void onLogin(UserBean userBean,OnLoadCallBack callBack);

    //注册
    void onRegister(UserBean userBean,OnLoadCallBack callBack);

    //忘记密码->重新保存
    void onSaveUser(UserBean userBean);
}
