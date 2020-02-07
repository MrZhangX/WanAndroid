package com.example.a10850.wanandroid.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.ProjectBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;
import com.example.common.adapter.SubStatePagerAdapter;
import com.example.common.base.BaseFragment;
import com.example.common.base.LazyLoadFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * 创建时间：2019/12/10 21:53
 * 创建人：10850
 * 功能描述：问题1：切换后会不出数据
 */
public class ProjectsFragment extends LazyLoadFragment {

    private static String PARAM = "PARAM";
    @BindView(R.id.project_vp)
    ViewPager mProjectVp;
    @BindView(R.id.project_tab)
    TabLayout mProjectTab;
    @BindView(R.id.title)
    TextView mTitle;

    Unbinder unbinder;

    private String value;

    private List<ProjectBean.DataBean> mList;
    private List<String> mTitles;
    private List<BaseFragment> mFragments;
    private SubStatePagerAdapter mAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_projects;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mTitle.setText("项目");

        mAdapter = new SubStatePagerAdapter(getFragmentManager());
//        mProjectVp.setOffscreenPageLimit(mFragments.size());
        //1、将tablayout与viewpager关联起来
        mProjectTab.setupWithViewPager(mProjectVp);
        //设置阴影高度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProjectTab.setElevation(0);
        }
        //2、移除tablayout的所有tab
        //3、重新添加tab
        for (int i = 0; i < mTitles.size(); i++) {
            mProjectTab.addTab(mProjectTab.newTab().setText(mTitles.get(i)));
        }
    }

    @Override
    protected void loadData() {
        showLoading();
        RetrofitUtil.getInstance().getProject().enqueue(new Callback<ProjectBean>() {
            @Override
            public void onResponse(Call<ProjectBean> call, Response<ProjectBean> response) {
                List<ProjectBean.DataBean> data = response.body().getData();
                mList.addAll(data);
                for (int i = 0; i < data.size(); i++) {
                    mTitles.add(data.get(i).getName());
//                    mFragments.add(new PerProjectFragment().newInstance("NORMAL", data.get(i).getId(),
//                            data.get(i).getName(), i));
                    mFragments.add(new PerProjectFragment().newInstance("NORMAL", data.get(i).getId()));
                }
                mAdapter.setData(mFragments, mTitles);
                mProjectVp.setAdapter(mAdapter);
                dismissLoading();
            }

            @Override
            public void onFailure(Call<ProjectBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        value = arguments.getString(PARAM);
    }

    public ProjectsFragment newInstance(String str) {
        ProjectsFragment baseFragment = new ProjectsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, str);
        baseFragment.setArguments(bundle);
        return baseFragment;
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
