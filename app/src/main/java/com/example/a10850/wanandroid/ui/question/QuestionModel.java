package com.example.a10850.wanandroid.ui.question;


import com.example.a10850.wanandroid.utils.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/***
 * 创建时间：2020/3/7 13:24
 * 创建人：10850
 * 功能描述：
 */
public class QuestionModel implements QuestionContract.Model {

    @Override
    public Observable requestData(int page) {
        return RetrofitUtil.getInstance().getQuestion(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
