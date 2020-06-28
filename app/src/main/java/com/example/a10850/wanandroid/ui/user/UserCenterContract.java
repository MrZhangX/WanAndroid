package com.example.a10850.wanandroid.ui.user;

import com.example.a10850.wanandroid.entity.CollectBean;
import com.example.a10850.wanandroid.entity.CollectWebBean;
import com.example.a10850.wanandroid.entity.SquareShareListBean;
import com.example.a10850.wanandroid.entity.UsedWebBean;
import com.example.common.base.IBaseModel;
import com.example.common.base.IBaseView;

import io.reactivex.Observable;

/***
 * 创建时间：2020/3/10 9:07
 * 创建人：10850
 * 功能描述：
 */
public interface UserCenterContract {

    interface Model extends IBaseModel {
        Observable<SquareShareListBean> getShareArt(int page);

        Observable<CollectBean> getCollectArt(int page);

        Observable<UsedWebBean> getCollectWeb();

        Observable<CollectWebBean> editWeb(int id, String name, String link);

        Observable<CollectWebBean> delWeb(int id);
    }

    interface View extends IBaseView {
        void requestSuccess(CollectBean bean);

        void requestFailure(String msg);

        void requestSuccessShare(Object o);
    }

    interface Presenter {
        void getShareArtData(int page);

        void refreshArtData(int page);

        void loadMoreArtData(int page);

        //
        void getCollectArt(int page);

        void refreshCollectArt(int page);

        void loadMoreCollectArt(int page);

        //
        //
        void getCollectWeb();

        void editWeb(int id, String name, String link);

        void delWeb(int id);
    }
}
