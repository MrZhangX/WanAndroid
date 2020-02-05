package com.example.a10850.wanandroid.proxy;


import com.example.a10850.wanandroid.base.BasePresenter;
import com.example.a10850.wanandroid.interfaces.IBaseView;
import com.example.a10850.wanandroid.interfaces.InjectPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/***
 * 创建时间：2019/12/5 10:30
 * 创建人：10850
 * 功能描述：
 */
public class ProxyImpl implements IProxy {

    private IBaseView mView;
    private List<BasePresenter> mInjectPresenters;

    public ProxyImpl(IBaseView view) {
        this.mView = view;
        mInjectPresenters = new ArrayList<>();
    }

    @Override
    public void bindPresenter() {
        //获取变量上面的注解类型
        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取变量上面的注解类型
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                try {
                    Class<? extends BasePresenter> type = (Class<? extends BasePresenter>) field.getType();
                    BasePresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.attachView(mView);
                    field.setAccessible(true);
                    field.set(mView, mInjectPresenter);
                    mInjectPresenters.add(mInjectPresenter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    throw new RuntimeException("SubClass must extends Class:BasePresenter");
                }
            }
        }
    }

    @Override
    public void unbindPresenter() {
        /**
         * 解绑，避免内存泄漏
         */
        for (BasePresenter presenter : mInjectPresenters) {
            presenter.detachView();
        }
        mInjectPresenters.clear();
        mInjectPresenters = null;

    }
}
