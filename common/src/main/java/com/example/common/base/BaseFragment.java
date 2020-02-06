package com.example.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    //    protected BaseActivity mActivity;
    protected Unbinder mUnbinder;
    protected View mRootView;

    protected abstract @LayoutRes
    int initLayoutRes();

    protected abstract void initData();

    protected abstract void initView();

    protected LoadingDialog loadingDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingDialog = new LoadingDialog(getActivity());
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mActivity = (BaseActivity) context;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(initLayoutRes(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        initView();
        return mRootView;
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroy();
    }

    public void showLoading() {
        showLoading("");
    }

    public void showLoading(String msg) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            if (!TextUtils.isEmpty(msg)) {
                loadingDialog.setTitleText(msg);
            }
            loadingDialog.show();
        }
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
