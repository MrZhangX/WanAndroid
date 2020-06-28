package com.example.a10850.wanandroid.interfaces;

import com.example.a10850.wanandroid.entity.BannerBean;
import com.example.a10850.wanandroid.entity.CoinBean;
import com.example.a10850.wanandroid.entity.CollectBean;
import com.example.a10850.wanandroid.entity.CollectWebBean;
import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.a10850.wanandroid.entity.ContentZDBean;
import com.example.a10850.wanandroid.entity.NavBean;
import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.a10850.wanandroid.entity.ProjectBean;
import com.example.a10850.wanandroid.entity.ProjectListBean;
import com.example.a10850.wanandroid.entity.SearchHotBean;
import com.example.a10850.wanandroid.entity.SquareShareListBean;
import com.example.a10850.wanandroid.entity.SystemTreeBean;
import com.example.a10850.wanandroid.entity.UsedWebBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/***
 * 创建时间：2019/12/6 10:30
 * 创建人：10850
 * 功能描述：
 */
public interface ApiService {

    //开始
    @FormUrlEncoded
    @POST("user/login")
    Observable<PersonBean> postLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Observable<PersonBean> postRegister(@FieldMap() Map<String, String> registerMap);

    @GET("banner/json")
    Call<BannerBean> getBannerData();

    @GET("article/list/{page}/json")
    Call<ContentListBean> getContentData(@Path("page") int page);

    @GET("article/top/json")
    Call<ContentZDBean> getContentZDData();

    //体系tree
    @GET("tree/json")
    Call<SystemTreeBean> getSystemTree();

    //体系查询
    @GET("article/list/{page}/json")
    Observable<ContentListBean> getSystemSearch(@Query("author") String author);

    //项目
    @GET("project/tree/json")
    Call<ProjectBean> getProject();

    //项目
    @GET("project/list/{page}/json")
    Call<ProjectListBean> getProjectList(@Path("page") int page, @Query("cid") int cid);

    //最新项目
    @GET("article/listproject/{page}/json")
    Call<ProjectListBean> getProjectNewList(@Path("page") int page);

    //导航
    @GET("navi/json")
    Call<NavBean> getNav();

    //常用网站
    @GET("friend/json")
    Observable<UsedWebBean> getUsedWeb();

    //
    @GET("article/list/{page}/json")
    Observable<ContentListBean> getSystemList(@Path("page") int page, @Query("cid") int cid);

    //搜索热词
    @GET("hotkey/json")
    Observable<SearchHotBean> getHotSearch();

    //搜索列表
    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @POST("article/query/{page}/json")
    Observable<ContentListBean> getSearchList(@Path("page") int page, @Field("k") String k);

    //公众号列表
    @GET("wxarticle/chapters/json")
    Observable<ProjectBean> getOffAcc();

    //公众号列表，从1开始
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<ContentListBean> getOffAccList(@Path("id") int id, @Path("page") int page);

    //公众号下的搜索
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<ContentListBean> getOffAccSearchList(@Path("id") int id, @Path("page") int page, @Query("k") String k);

    //广场列表 https://wanandroid.com/user_article/list/0/json
    @GET("user_article/list/{page}/json")
    Observable<ContentListBean> getSquareList(@Path("page") int page);

    //分享人对应列表数据（别人的分享） https://wanandroid.com/user/2/articles/1
    @GET("user/{user}/articles/{page}/json")
    Observable<ContentListBean> getOtherSquareList(@Path("page") int page, @Path("user") int user);

    //用于测试保存会话的
    @GET("lg/coin/userinfo/json")
    Call<CoinBean> getJf();

    //积分
    @GET("lg/coin/userinfo/json")
    Observable<CoinBean> getCoin();

    //问答
    @GET("wenda/list/{page}/json")
    Observable<ContentListBean> getQuestion(@Path("page") int page);

    //分享文章
    @FormUrlEncoded
    @POST("lg/user_article/add/json")
    Call<ResponseBody> shareArticle(@Field("title") String title, @Field("link") String link);

    //分享人 分享文章列表 https://www.wanandroid.com/user/2/share_articles/页码/json
    @GET("user/2/share_articles/{page}/json")
    Observable<SquareShareListBean> otherShareArticle(@Path("page") int page);

    //自己的分享的文章列表 https://wanandroid.com/user/lg/private_articles/1/json
    @GET("user/lg/private_articles/{page}/json")
    Observable<SquareShareListBean> myShareArticle(@Path("page") int page);

    //收藏列表
    @GET("lg/collect/list/{page}/json")
    Observable<CollectBean> getCollectList(@Path("page") int page);

    //收藏网站列表
    @GET("lg/collect/usertools/json")
    Observable<UsedWebBean> getCollectWebList();

    //编辑收藏网站https://www.wanandroid.com/lg/collect/updatetool/json
    @FormUrlEncoded
    @POST("lg/collect/updatetool/json")
    Observable<CollectWebBean> updateCw(@Field("id") int id, @Field("name") String name, @Field("link") String link);

    @FormUrlEncoded
    @POST("lg/collect/deletetool/json")
    Observable<CollectWebBean> delCw(@Field("id") int id);

}
