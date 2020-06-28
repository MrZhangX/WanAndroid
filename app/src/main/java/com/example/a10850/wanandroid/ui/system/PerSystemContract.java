package com.example.a10850.wanandroid.ui.system;

import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.common.base.IBaseModel;
import com.example.common.base.IBaseView;

import java.util.Map;

import io.reactivex.Observable;

/***
 * 创建时间：2020/2/12 18:07
 * 创建人：10850
 * 功能描述：
 */
public interface PerSystemContract {

    interface Model extends IBaseModel {
        Observable<ContentListBean> getPerSystemData(int page, int cid);
    }

    interface View extends IBaseView {
        Map<String,String> getParams();

        void requestSuccess(ContentListBean contentListBean);

        void registerFailure(String msg);
    }

    interface Presenter {
        /**
         * 注册
         */
        void getPerSystemData();
    }
}
