package com.example.a10850.wanandroid.interfaces;

/***
 * 创建时间：2019/12/2 15:35
 * 创建人：10850
 * 功能描述：model和view的接口可以放在契约接口
 */
public interface IBaseModel<T> {
    //加载数据
    void loadData(T data, OnLoadCallBack callBack);

}
