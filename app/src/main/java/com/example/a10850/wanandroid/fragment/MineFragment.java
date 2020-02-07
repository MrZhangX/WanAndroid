package com.example.a10850.wanandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.activity.NavigationActivity;
import com.example.a10850.wanandroid.customview.LSettingItem;
import com.example.common.base.LazyLoadFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/***
 * 创建时间：2019/12/10 21:54
 * 创建人：10850
 * 功能描述：问题1：这个自定义layout不支持黄油刀，将线先放弃，采用expandRv试试
 * 问题2：
 */
public class MineFragment extends LazyLoadFragment {

    private static String PARAM = "PARAM";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;
    Unbinder unbinder;


    private LSettingItem mItemNavigation;
    @BindView(R.id.item_net)
    LSettingItem mItemNet;
    @BindView(R.id.item_collect)
    LSettingItem mItemCollect;

    private String value;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        value = arguments.getString(PARAM);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTitle.setText("我的");
        mSearch.setVisibility(View.GONE);
    }

    public MineFragment newInstance(String str) {
        MineFragment baseFragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, str);
        baseFragment.setArguments(bundle);
        return baseFragment;
    }

    @Override
    protected void loadData() {

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mItemNavigation = view.findViewById(R.id.item_navigation);
        mItemNavigation.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
            }
        });


    }
}
