package com.example.a10850.wanandroid.ui.UsedWeb;

import com.example.a10850.wanandroid.entity.UsedWebBean;
import com.example.common.base.IBaseModel;
import com.example.common.base.IBaseView;
import com.example.common.base.ResponseCallback;

/***
 * 创建时间：2020/2/11 20:52
 * 创建人：10850
 * 功能描述：
 */
public interface UsedWebContract {

    interface Model extends IBaseModel {
        void getUsedWeb(ResponseCallback callback);
    }

    interface View extends IBaseView {
        void requestSuccess(UsedWebBean webBean);

        void requestFailure(String msg);
    }

    interface Presenter {
        void getUsedWebData();
    }


}
