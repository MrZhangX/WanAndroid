package com.example.a10850.wanandroid.interfaces;

import android.content.Context;

/***
 * 创建时间：2019/12/2 15:36
 * 创建人：10850
 * 功能描述：model和view的接口可以放在契约接口
 *
 * https://blog.csdn.net/lmj623565791/article/details/46596109
 * 对于View的接口，去观察功能上的操作，然后考虑：
 * 1.该操作需要什么？（getUserName, getPassword）
 * 2.该操作的结果，对应的反馈？(toMainActivity, showFailedError)
 * 3.该操作过程中对应的友好的交互？(showLoading, hideLoading)
 */
//public interface IBaseView<T> {
public interface IBaseView {
    /***
     * 成功时调用
     * @param data 结果
     */
    void onSuccess(Object data);

    /***
     * 失败时调用
     */
    void onFailure();

    /**
     * 显示正在加载view
     */
    void showLoading();

    /**
     * 关闭正在加载view
     */
    void hideLoading();

    /**
     * 显示提示
     *
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示请求错误提示
     */
    void showErr();

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    Context getContext();
}
