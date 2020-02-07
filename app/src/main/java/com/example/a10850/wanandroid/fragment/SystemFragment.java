package com.example.a10850.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.adapter.SystemTreeAdapter;
import com.example.a10850.wanandroid.entity.SystemTreeBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;
import com.example.common.base.LazyLoadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * 创建时间：2019/12/10 21:51
 * 创建人：10850
 * 功能描述：用mvp
 */
public class SystemFragment extends LazyLoadFragment {


    @BindView(R.id.system_rv)
    RecyclerView mSystemRv;
    @BindView(R.id.system_refresh)
    SmartRefreshLayout mSystemRefresh;

    Unbinder unbinder;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;

    private List<SystemTreeBean.DataBean> mList;
    private SystemTreeAdapter mAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mTitle.setText("知识体系");

        mAdapter = new SystemTreeAdapter(R.layout.system_rv_item, mList);
        mSystemRv.setAdapter(mAdapter);
        mSystemRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }


    @Override
    protected void loadData() {
        showLoading();
        RetrofitUtil.getInstance().getSystemTree().enqueue(new Callback<SystemTreeBean>() {
            @Override
            public void onResponse(Call<SystemTreeBean> call, Response<SystemTreeBean> response) {
                SystemTreeBean body = response.body();
                List<SystemTreeBean.DataBean> data = body.getData();
                mList.addAll(data);
                mAdapter.notifyDataSetChanged();
                dismissLoading();
            }

            @Override
            public void onFailure(Call<SystemTreeBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
