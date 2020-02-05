package com.example.a10850.wanandroid.login;

/***
 * 创建时间：2019/12/8 11:12
 * 创建人：10850
 * 功能描述：
 */
public interface LoginView {
    void onRegisterSucceed();  //注册成功

    void onRegisterFaild();    //注册失败

    void onLoginSucceed();     //登录成功

    void onLoginFaild();       //登录失败
}
