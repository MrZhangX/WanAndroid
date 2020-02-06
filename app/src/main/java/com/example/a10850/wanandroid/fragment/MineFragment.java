package com.example.a10850.wanandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10850.wanandroid.R;
import com.example.common.base.LazyLoadFragment;

/***
 * 创建时间：2019/12/10 21:54
 * 创建人：10850
 * 功能描述：
 */
public class MineFragment extends LazyLoadFragment {

    private static String PARAM = "PARAM";
    private String value;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        value = arguments.getString(PARAM);
    }

    @Override
    protected int initLayoutRes() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public MineFragment newInstance(String str) {
        MineFragment baseFragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, str);
        baseFragment.setArguments(bundle);
        return baseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void loadData() {

    }
}
