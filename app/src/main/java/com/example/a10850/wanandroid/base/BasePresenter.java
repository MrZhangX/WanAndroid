package com.example.a10850.wanandroid.base;

import com.example.a10850.wanandroid.interfaces.IBasePresenter;
import com.example.a10850.wanandroid.interfaces.IBaseView;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/***
 * 创建时间：2019/12/2 17:32
 * 创建人：10850
 * 功能描述：Presenter的基类
 * 强引用可能会造成内存泄漏的问题
 * 如果没有进行detachView的view的解绑，就会不断产生一个被对象占用的引用，无法回收，最后导致内存泄漏
 */
public abstract class BasePresenter<V extends IBaseView, M extends BaseModel> implements IBasePresenter {

    /**
     * 绑定的view
     */
    private SoftReference<IBaseView> mReferenceView;
    //动态代理的view
    private V mProxyView;
    //反射model
    private M mModel;


    /**
     * 绑定view，一般在初始化中调用该方法
     */
    @SuppressWarnings("unchecked")
    public void attachView(IBaseView view) {
        this.mReferenceView = new SoftReference<>(view);

        //加一个动态代理
        //使用动态代理做统一的逻辑判断 aop 思想
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                if (mReferenceView == null || mReferenceView.get() == null) {
                    return null;
                }
                return method.invoke(mReferenceView.get(), objects);
            }
        });


        //通过获得泛型类的父类，拿到泛型的接口类实例，通过反射来实例化 model
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        if (type != null) {
            Type[] types = type.getActualTypeArguments();
            try {
                mModel = (M) ((Class<?>) types[1]).newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    @Override
    public void detachView() {
        mReferenceView.clear();
        this.mReferenceView = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return mReferenceView != null;
    }

    /**
     * 获取连接的view
     */
    public V getView() {
        return mProxyView;
    }

    protected M getModel() {
        return mModel;
    }

}
