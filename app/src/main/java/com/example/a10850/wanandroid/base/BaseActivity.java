package com.example.a10850.wanandroid.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.interfaces.IBaseView;
import com.example.a10850.wanandroid.proxy.ProxyActivity;


/***
 * 创建时间：2019/12/2 16:05
 * 创建人：10850
 * 功能描述：这里的 View 层就是 Activity，
 * 所以我们就得写一个 BaseActivity 基类，既然是基类，就要声明为抽象类，把共有的方法提取到基类中，这里使用到的就是模板模式
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    private ProxyActivity mProxyActivity;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);

        mProxyActivity = createProxyActivity();
        mProxyActivity.bindPresenter();

    }


    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErr() {
        showToast(getResources().getString(R.string.api_error_msg));
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxyActivity.unbindPresenter();
    }

    private ProxyActivity createProxyActivity() {
        if (mProxyActivity == null) {
            return new ProxyActivity(this);
        }
        return mProxyActivity;
    }


}
