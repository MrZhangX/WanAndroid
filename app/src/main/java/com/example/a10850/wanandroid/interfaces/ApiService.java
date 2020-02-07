package com.example.a10850.wanandroid.interfaces;

import com.example.a10850.wanandroid.entity.BannerBean;
import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.a10850.wanandroid.entity.ContentZDBean;
import com.example.a10850.wanandroid.entity.NavBean;
import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.a10850.wanandroid.entity.ProjectBean;
import com.example.a10850.wanandroid.entity.ProjectListBean;
import com.example.a10850.wanandroid.entity.SystemTreeBean;
import com.example.a10850.wanandroid.entity.UserBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/***
 * 创建时间：2019/12/6 10:30
 * 创建人：10850
 * 功能描述：
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("user/login")
    Observable<UserBean> getLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Observable<UserBean> getRegister(@FieldMap() Map<String, String> registerMap);

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> onLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/login")
    Observable<PersonBean> onLogin1(@Field("username") String username, @Field("password") String password);

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

}
