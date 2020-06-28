package com.example.a10850.wanandroid.ui.square;

import com.example.a10850.wanandroid.utils.RetrofitUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;

/***
 * 创建时间：2020/3/1 14:41
 * 创建人：10850
 * 功能描述：
 */
public class SquareModel implements SquareContract.Model {

    @Override
    public Call<ResponseBody> shareArticle(String title, String link) {
        return RetrofitUtil.getInstance().shareArticle(title, link);
    }
}
