package com.example.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.common.R;
import com.example.common.widget.LoadingDialog;
import com.example.common.widget.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 需完善的地方：
 * 加载时的动画
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Context mContext;
    protected Unbinder mUnbinder;


    protected abstract @LayoutRes
    int initLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());
        mContext = this;
        mUnbinder = ButterKnife.bind(this);

        initData();
        initView();

        loadingDialog = new LoadingDialog(this);
    }

    @Override
    protected void onDestroy() {
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

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.main_color));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }
}
