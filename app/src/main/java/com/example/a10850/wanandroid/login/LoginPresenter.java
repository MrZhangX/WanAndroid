package com.example.a10850.wanandroid.login;


import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.a10850.wanandroid.entity.UserBean;
import com.example.a10850.wanandroid.interfaces.ILoginRegisiterModel;
import com.example.a10850.wanandroid.interfaces.OnLoadCallBack;

/***
 * 创建时间：2019/12/6 10:17
 * 创建人：10850
 * 功能描述：
 */
public class LoginPresenter {

    private ILoginRegisiterModel mUserModel;  //Model接口
    private LoginView mUserView;    //View接口

    public LoginPresenter(LoginView userView) {
        mUserModel = new LoginModel();
        this.mUserView = userView;
    }

    public void registerPerson(UserBean userBean) {
        mUserModel.onRegister(userBean, new OnLoadCallBack() {
            @Override
            public void onSuccess(Object data) {
                mUserView.onRegisterSucceed();
            }

            @Override
            public void onFailure() {
                mUserView.onRegisterFaild();
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
//        mUserModel.onRegister(userBean, callBack);
        //根据Model中的结果调用不同的方法进行UI展示
//        if (isRegister) {
//            mPersonView.onRegisterSucceed();
//        } else {
//            mPersonView.onRegisterFaild();
//        }
    }

    public void loginPerson(UserBean userBean) {
        mUserModel.onLogin(userBean, new OnLoadCallBack() {
            @Override
            public void onSuccess(Object data) {
                PersonBean bean = (PersonBean) data;
                if (bean.getData() != null)
                    mUserView.onLoginSucceed();
                else
                    mUserView.onLoginFaild();
            }

            @Override
            public void onFailure() {
                mUserView.onLoginFaild();
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
//        mUserModel.onLogin(userBean, callBack);
//        boolean isLogin = mPersonModel.onLogin(name, pwd);
//        //根据Model中的结果调用不同的方法进行UI展示
//        if (isLogin) {
//            mPersonView.onLoginSucceed();
//        } else {
//            mPersonView.onLoginFaild();
//        }
    }
}



