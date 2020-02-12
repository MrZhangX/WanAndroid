package com.example.a10850.wanandroid.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10850.wanandroid.R;
import com.example.common.adapter.SubPagerAdapter;
import com.example.common.base.BaseFragment;
import com.example.common.base.LazyLoadFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 创建时间：2020/2/5 17:02
 * 创建人：10850
 * 功能描述：
 */
public class HomeFragment extends LazyLoadFragment {

    @BindView(R.id.home_tab)
    TabLayout mHomeTab;
    @BindView(R.id.home_vp)
    ViewPager mHomeVp;

    Unbinder unbinder;

    private List<String> mTitles;
    private List<BaseFragment> mFragments;
    private SubPagerAdapter mAdapter;

    @Override
    protected void loadData() {
        //do nothing
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();

        mTitles.add("最新博文");
        mTitles.add("最新项目");

        mFragments.add(new MainFragment().newInstance("首页"));
        mFragments.add(new PerProjectFragment().newInstance("NEWEST", 0));
    }

    @Override
    protected void initView() {
        mAdapter = new SubPagerAdapter(getFragmentManager());
        mAdapter.setData(mFragments, mTitles);
        mHomeVp.setAdapter(mAdapter);
        mHomeVp.setOffscreenPageLimit(mFragments.size());
        //1、将tablayout与viewpager关联起来
        mHomeTab.setupWithViewPager(mHomeVp);
        //设置阴影高度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mHomeTab.setElevation(0);
        }
        mHomeTab.removeAllTabs();
        //3、重新添加tab
        for (int i = 0; i < mTitles.size(); i++) {
            mHomeTab.addTab(mHomeTab.newTab().setText(mTitles.get(i)));
        }
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
