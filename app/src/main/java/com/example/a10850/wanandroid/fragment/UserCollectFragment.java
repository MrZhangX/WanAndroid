package com.example.a10850.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.adapter.CollectAdapter;
import com.example.a10850.wanandroid.entity.CollectBean;
import com.example.a10850.wanandroid.entity.MultCollectEntity;
import com.example.a10850.wanandroid.ui.user.UserCenterContract;
import com.example.a10850.wanandroid.ui.user.UserCenterPresenter;
import com.example.common.base.BaseMvpFragment;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 创建时间：2020/3/9 22:14
 * 创建人：10850
 * 功能描述：多类型的列表
 */
public class UserCollectFragment extends BaseMvpFragment<UserCenterPresenter> implements UserCenterContract.View {

    @BindView(R.id.collect_header)
    MaterialHeader mCollectHeader;
    @BindView(R.id.collect_rv)
    RecyclerView mCollectRv;
    @BindView(R.id.collect_refresh)
    SmartRefreshLayout mCollectRefresh;
    Unbinder unbinder;

    private int page = 0;

    private CollectAdapter mAdapter;
    private List<MultCollectEntity> mList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();

        presenter.getCollectArt(page);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_collect, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void requestSuccess(CollectBean bean) {
        if (bean.getErrorCode() == 0) {
            initView(bean);
        } else {
            Toast.makeText(getActivity(), bean.getErrorMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(CollectBean bean) {
        List<CollectBean.DataBean.DatasBean> datas = bean.getData().getDatas();
        for (int i = 0; i < datas.size(); i++) {
            MultCollectEntity item;
            if (TextUtils.isEmpty(datas.get(i).getEnvelopePic()))
                item = new MultCollectEntity(MultCollectEntity.ART, datas.get(i));
            else
                item = new MultCollectEntity(MultCollectEntity.PRO, datas.get(i));
            mList.add(item);
        }
        page = bean.getData().getCurPage();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFailure(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestSuccessShare(Object o) {

    }

    @Override
    protected UserCenterPresenter createPresenter() {
        return new UserCenterPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCollectHeader.setColorSchemeResources(R.color.qcgreen);
        mAdapter = new CollectAdapter(mList);
        mCollectRv.setAdapter(mAdapter);
        mCollectRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCollectRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                presenter.refreshCollectArt(0);
                mCollectRefresh.finishRefresh(true);

            }
        });

        mCollectRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMoreCollectArt(page);
                mCollectRefresh.finishLoadMore(true);
            }
        });
    }
}
