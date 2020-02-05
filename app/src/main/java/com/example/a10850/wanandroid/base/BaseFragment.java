package com.example.a10850.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a10850.wanandroid.interfaces.IBaseView;
import com.example.a10850.wanandroid.proxy.ProxyFragment;

/***
 * 创建时间：2019/12/5 9:15
 * 创建人：10850
 * 功能描述：
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

    private ProxyFragment mProxyFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //因为这里我没有进行view的创建，所以反射只能用在view创建完成之后
        mProxyFragment = createProxyFragment();
        mProxyFragment.bindPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxyFragment.unbindPresenter();
    }

    private ProxyFragment createProxyFragment() {
        if (mProxyFragment == null) {
            return new ProxyFragment<>(this);
        }
        return mProxyFragment;
    }
}