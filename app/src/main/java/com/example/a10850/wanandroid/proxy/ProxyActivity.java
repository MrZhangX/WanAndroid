package com.example.a10850.wanandroid.proxy;

import com.example.a10850.wanandroid.interfaces.IBaseView;

/***
 * 创建时间：2019/12/5 10:34
 * 创建人：10850
 * 功能描述：
 */
public class ProxyActivity<V extends IBaseView> extends ProxyImpl {

    public ProxyActivity(IBaseView view) {
        super(view);
    }
}
