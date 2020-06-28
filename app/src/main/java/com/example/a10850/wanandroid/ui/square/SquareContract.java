package com.example.a10850.wanandroid.ui.square;


import com.example.common.base.IBaseModel;
import com.example.common.base.IBaseView;

import okhttp3.ResponseBody;
import retrofit2.Call;

/***
 * 创建时间：2020/3/1 11:29
 * 创建人：10850
 * 功能描述：
 */
public interface SquareContract {

    interface Model extends IBaseModel {

        Call<ResponseBody> shareArticle(String title, String link);
    }

    interface View extends IBaseView {

        void requestSuccess(Object o);

        void requestFailure(String msg);
    }

    interface Presenter {
        //分享文章
        void shareArticle(String title, String link);
    }
}
