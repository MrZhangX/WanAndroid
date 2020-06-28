package com.example.a10850.wanandroid.ui.offacc;

import com.example.a10850.wanandroid.entity.ProjectBean;
import com.example.common.base.IBaseModel;
import com.example.common.base.IBaseView;

import io.reactivex.Observable;

/***
 * 创建时间：2020/2/29 14:20
 * 创建人：10850
 * 功能描述：
 */
public interface OffAccContract {

    interface Model extends IBaseModel {
        Observable<ProjectBean> handleOffAccData();
    }

    interface View extends IBaseView {

        void requestSuccess(ProjectBean dataBean);

        void registerFailure(String msg);
    }

    interface Presenter {

        void getOffAccData();
    }
}
