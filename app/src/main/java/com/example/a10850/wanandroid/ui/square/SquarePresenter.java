package com.example.a10850.wanandroid.ui.square;

import com.example.common.base.BasePresenter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * 创建时间：2020/3/9 19:31
 * 创建人：10850
 * 功能描述：
 */
public class SquarePresenter extends BasePresenter<SquareContract.Model, SquareContract.View>
        implements SquareContract.Presenter {
    @Override
    public void shareArticle(String title, String link) {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().shareArticle(title, link).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    getView().dismissLoading();
                    try {
                        getView().requestSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    getView().requestFailure(t.getMessage());
                }
            });
        }
    }

    @Override
    protected SquareContract.Model createModule() {
        return new SquareModel();
    }

    @Override
    public void start() {

    }
}
