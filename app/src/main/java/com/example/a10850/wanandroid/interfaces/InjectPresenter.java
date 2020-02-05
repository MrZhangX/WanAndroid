package com.example.a10850.wanandroid.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 创建时间：2019/12/4 14:33
 * 创建人：10850
 * 功能描述：使用 @Target 指定它的注解类型为变量，指定它被保留在运行时期。
 * 我们在 View 层需要引用的 Presenter 实现类变量头上加个 @InjectPresenter 注解就可以了。
 * 当然，你也可以添加多个不同 Presenter，这就解决了我们的 View 与 Presenter 一对多的问题，
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectPresenter {
}


