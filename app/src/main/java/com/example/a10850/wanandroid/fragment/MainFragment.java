package com.example.a10850.wanandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.activity.WebActivity;
import com.example.a10850.wanandroid.adapter.ContentListAdapter;
import com.example.a10850.wanandroid.constant.UrlString;
import com.example.a10850.wanandroid.entity.BannerBean;
import com.example.a10850.wanandroid.entity.ContentBean;
import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.a10850.wanandroid.entity.ContentZDBean;
import com.example.a10850.wanandroid.interfaces.ApiService;
import com.example.a10850.wanandroid.utils.RetrofitUtil;
import com.example.common.base.LazyLoadFragment;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/***
 * 创建时间：2019/12/10 21:48
 * 创建人：10850
 * 功能描述：首页
 * 问题1：BaseRecyclerViewAdapterHelper中的下拉刷新并不好，建议使用EasyRefreshLayout或者SmartRefreshLayout
 * 上拉加载更多可以自定义，但是既然用了框架，最好也是用框架的下拉和上拉
 *
 * 问题2：轮播图在fragment切换时,轮播图的图片会消失
 * 可能原因1:轮播图的问题，应该不是本身的问题
 * 可能原因2:轮播图与BaseRecyclerViewAdapterHelper不兼容,测试使用iv试试，测试结果也不行
 * mVpMain.setOffscreenPageLimit(mFragments.size());可能是这个的原因。暂时没问题了，需要跟踪
 * 是getLayoutInflater填充的布局，在fragment切换时destroyview，切回又重新创建了VIEW，填充布局没有重新加入
 */
public class MainFragment extends LazyLoadFragment {

    private int total = 0;//总数
    private int currentPage = 0;//当前页

    private static final String TAG = "MainFragment";

    private BGABanner mMainBanner;

    @BindView(R.id.main_rv)
    RecyclerView mMainRv;

    private static String PARAM = "PARAM";

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.main_container)
    RelativeLayout mMainContainer;

    Handler mHandler = new Handler();
    private List<String> mImages;

    //列表内容
    private ContentListAdapter mAdapter;
    private List<ContentBean> mList;
    private View mView;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mImages = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mAdapter = new ContentListAdapter(R.layout.home_contentlist_item, mList);
        mView = getLayoutInflater().inflate(R.layout.main_content_header, mMainContainer, false);
        mMainBanner = (BGABanner) mView;

        mMainRv.setAdapter(mAdapter);
        mAdapter.addHeaderView(mView);
        mMainRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMainBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                Glide.with(getActivity())
                        .load(model)
                        .into(itemView);
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getActivity(), mImages.size() + "-" + mMainBanner.getItemCount(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", mAdapter.getData().get(position).getLink());
                intent.putExtra("title", mAdapter.getData().get(position).getTitle());
                startActivity(intent);
            }
        });

        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mMainRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //
                        RetrofitUtil.getInstance().getBannerData().enqueue(new Callback<BannerBean>() {
                            @Override
                            public void onResponse(Call<BannerBean> call, Response<BannerBean> response) {
                                mImages.clear();
                                List<BannerBean.DataBean> data = response.body().getData();
                                for (int i = 0; i < data.size(); i++)
                                    mImages.add(data.get(i).getImagePath());
                                mMainBanner.setData(Arrays.asList(mImages.get(0), mImages.get(1), mImages.get(2), mImages.get(3)), null);
                            }

                            @Override
                            public void onFailure(Call<BannerBean> call, Throwable t) {

                            }
                        });
                        //加载成功
                        RetrofitUtil.getInstance().getContentZDData().enqueue(new Callback<ContentZDBean>() {
                            @Override
                            public void onResponse(Call<ContentZDBean> call, Response<ContentZDBean> response) {
                                mAdapter.setNewData(response.body().getData());
                            }

                            @Override
                            public void onFailure(Call<ContentZDBean> call, Throwable t) {

                            }
                        });
                        //加载成功
                        RetrofitUtil.getInstance().getContentData(0).enqueue(new Callback<ContentListBean>() {
                            @Override
                            public void onResponse(Call<ContentListBean> call, Response<ContentListBean> response) {
//                                mAdapter.setNewData(response.body().getData().getDatas());
                                mAdapter.addData(response.body().getData().getDatas());
                                currentPage = response.body().getData().getCurPage();
                                mRefreshLayout.finishRefresh(true);
                                Logger.d("getCurPage: " + response.body().getData().getCurPage());
                            }

                            @Override
                            public void onFailure(Call<ContentListBean> call, Throwable t) {
                                mRefreshLayout.finishRefresh(false);
                            }
                        });

                    }
                }, 300);


            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreData();
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /***
     * 上拉加载更多
     */
    private void loadMoreData() {
        mMainRv.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPage >= total) {
                    //数据全部加载完毕
                    //滑动到底部了。显示：   --我也是有底线的--
                    mAdapter.loadMoreEnd();
                } else {
                    //加载成功
                    RetrofitUtil.getInstance().getContentData(currentPage).enqueue(new Callback<ContentListBean>() {
                        @Override
                        public void onResponse(Call<ContentListBean> call, Response<ContentListBean> response) {
                            mAdapter.addData(response.body().getData().getDatas());
                            currentPage = response.body().getData().getCurPage();
                            mRefreshLayout.finishLoadMore(true);
                            Logger.d("getCurPage: " + response.body().getData().getCurPage());
                        }

                        @Override
                        public void onFailure(Call<ContentListBean> call, Throwable t) {
                            mRefreshLayout.finishLoadMore(false);
                        }
                    });
                }
            }
        }, 300);
    }


    public MainFragment newInstance(String str) {
        MainFragment baseFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, str);
        baseFragment.setArguments(bundle);
        return baseFragment;
    }


    @Override
    protected void loadData() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UrlString.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                Call<BannerBean> bannerData = apiService.getBannerData();
                bannerData.enqueue(new Callback<BannerBean>() {
                    @Override
                    public void onResponse(Call<BannerBean> call, Response<BannerBean> response) {
                        List<BannerBean.DataBean> data = response.body().getData();
                        for (int i = 0; i < data.size(); i++)
                            mImages.add(data.get(i).getImagePath());
                        mMainBanner.setData(Arrays.asList(mImages.get(0), mImages.get(1), mImages.get(2), mImages.get(3)), null);
                    }

                    @Override
                    public void onFailure(Call<BannerBean> call, Throwable t) {

                    }
                });

                //******列表置顶******
                Call<ContentZDBean> contentZDData = apiService.getContentZDData();
                contentZDData.enqueue(new Callback<ContentZDBean>() {
                    @Override
                    public void onResponse(Call<ContentZDBean> call, Response<ContentZDBean> response) {
                        List<ContentBean> data = response.body().getData();
                        mList.addAll(data);
                    }

                    @Override
                    public void onFailure(Call<ContentZDBean> call, Throwable t) {

                    }
                });
                //******列表置顶******

                //******列表内容******
                Call<ContentListBean> contentData = apiService.getContentData(currentPage);
                contentData.enqueue(new Callback<ContentListBean>() {
                    @Override
                    public void onResponse(Call<ContentListBean> call, Response<ContentListBean> response) {
                        List<ContentBean> datas = response.body().getData().getDatas();
                        mList.addAll(datas);
                        mAdapter.notifyDataSetChanged();
                        currentPage = response.body().getData().getCurPage();
                        total = response.body().getData().getPageCount();
                    }

                    @Override
                    public void onFailure(Call<ContentListBean> call, Throwable t) {
                        //数据加载失败可以设置空view
//                        mAdapter.setEmptyView();
                    }
                });
                //****结束*****

            }
        });
    }


}