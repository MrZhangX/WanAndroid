package com.example.a10850.wanandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.adapter.ProjectListAdapter;
import com.example.a10850.wanandroid.entity.ProjectListBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;
import com.example.common.base.LazyLoadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * 创建时间：2019/12/10 21:53
 * 创建人：10850
 * 功能描述：问题1：page值的问题导致切换后的界面有的不显示
 */
public class PerProjectFragment extends LazyLoadFragment {

    private static String PARAM = "PARAM";
    private static String TYPE = "TYPE";

    @BindView(R.id.project_rv)
    RecyclerView mProjectRv;
    @BindView(R.id.project_refresh)
    SmartRefreshLayout mProjectRefresh;

    private int value;
    private String type;
    private int page = 1;

    private List<ProjectListBean.DataBean.DatasBean> mData;
    private ProjectListAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        value = arguments.getInt(PARAM, 0);
        type = arguments.getString(TYPE);
//        Logger.i("id:" + arguments.getInt(PARAM, 0) + "-" + arguments.getString("name") +
//                "-" + arguments.getInt("loc"));


    }

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_per_project;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mAdapter = new ProjectListAdapter(R.layout.project_list_item, mData, getActivity(), type);
        mProjectRv.setAdapter(mAdapter);
        mProjectRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mProjectRefresh.setEnableRefresh(true);
        mProjectRefresh.setEnableLoadMore(true);

        mProjectRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mProjectRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if ("NORMAL".equals(type))
                            RetrofitUtil.getInstance().getProjectList(page, value).enqueue(new Callback<ProjectListBean>() {
                                @Override
                                public void onResponse(Call<ProjectListBean> call, Response<ProjectListBean> response) {
                                    ProjectListBean.DataBean data = response.body().getData();
                                    mAdapter.addData(data.getDatas());
                                    page = data.getCurPage() + 1;
                                    mProjectRefresh.finishLoadMore(true);
                                }

                                @Override
                                public void onFailure(Call<ProjectListBean> call, Throwable t) {

                                }
                            });
                        else
                            RetrofitUtil.getInstance().getProjectNewList(page).enqueue(new Callback<ProjectListBean>() {
                                @Override
                                public void onResponse(Call<ProjectListBean> call, Response<ProjectListBean> response) {
                                    ProjectListBean.DataBean data = response.body().getData();
                                    mAdapter.addData(data.getDatas());
                                    page = data.getCurPage();
                                    mProjectRefresh.finishLoadMore(true);
                                }

                                @Override
                                public void onFailure(Call<ProjectListBean> call, Throwable t) {

                                }
                            });

                    }
                }, 300);
            }
        });


        mProjectRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mProjectRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if ("NORMAL".equals(type))
                            RetrofitUtil.getInstance().getProjectList(page, value).enqueue(new Callback<ProjectListBean>() {
                                @Override
                                public void onResponse(Call<ProjectListBean> call, Response<ProjectListBean> response) {
                                    ProjectListBean.DataBean data = response.body().getData();
                                    mAdapter.setNewData(data.getDatas());
                                    page = data.getCurPage() + 1;
                                    mProjectRefresh.finishRefresh(true);
                                }

                                @Override
                                public void onFailure(Call<ProjectListBean> call, Throwable t) {

                                }
                            });
                        else
                            RetrofitUtil.getInstance().getProjectNewList(0).enqueue(new Callback<ProjectListBean>() {
                                @Override
                                public void onResponse(Call<ProjectListBean> call, Response<ProjectListBean> response) {
                                    ProjectListBean.DataBean data = response.body().getData();
                                    mAdapter.setNewData(data.getDatas());
                                    page = data.getCurPage();
                                    mProjectRefresh.finishRefresh(true);
                                }

                                @Override
                                public void onFailure(Call<ProjectListBean> call, Throwable t) {

                                }
                            });

                    }
                }, 300);
            }
        });
    }



    @Override
    protected void loadData() {
//        Logger.e("zxd" + page);
        showLoading();
        if ("NORMAL".equals(type))
            RetrofitUtil.getInstance().getProjectList(1, value).enqueue(new Callback<ProjectListBean>() {
                @Override
                public void onResponse(Call<ProjectListBean> call, Response<ProjectListBean> response) {
                    ProjectListBean.DataBean data = response.body().getData();
                    mData.addAll(data.getDatas());
                    mAdapter.notifyDataSetChanged();
                    dismissLoading();
                    page = data.getCurPage() + 1;
                    //Logger.d(mData.size());
                }

                @Override
                public void onFailure(Call<ProjectListBean> call, Throwable t) {

                }
            });
        else
            RetrofitUtil.getInstance().getProjectNewList(value).enqueue(new Callback<ProjectListBean>() {
                @Override
                public void onResponse(Call<ProjectListBean> call, Response<ProjectListBean> response) {
                    ProjectListBean.DataBean data = response.body().getData();
                    mData.addAll(data.getDatas());
                    mAdapter.notifyDataSetChanged();
                    dismissLoading();
                    page = data.getCurPage();
                }

                @Override
                public void onFailure(Call<ProjectListBean> call, Throwable t) {

                }
            });
    }

    public PerProjectFragment newInstance(String type, int str) {
        PerProjectFragment baseFragment = new PerProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM, str);
        bundle.putString(TYPE, type);
        baseFragment.setArguments(bundle);
        return baseFragment;
    }

//    public PerProjectFragment newInstance(String type, int str, String name, int loc) {
//        PerProjectFragment baseFragment = new PerProjectFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(PARAM, str);
//        bundle.putString(TYPE, type);
//
//        bundle.putInt("loc", loc);
//        bundle.putString("name", name);
//        baseFragment.setArguments(bundle);
//        return baseFragment;
//    }

    public String getType() {
        return type;
    }

}
