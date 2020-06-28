package com.example.a10850.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.adapter.ContentListAdapter;
import com.example.a10850.wanandroid.entity.CollectBean;
import com.example.a10850.wanandroid.entity.ContentBean;
import com.example.a10850.wanandroid.entity.SquareShareListBean;
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
 * 功能描述：
 * error:
 * 1.android.content.res.Resources$NotFoundException: Resource ID #0x0
 * adapter初始化有问题，没写R.layout.home_contentlist_item
 * 2.item变一下把
 */
public class UserShareFragment extends BaseMvpFragment<UserCenterPresenter>
        implements UserCenterContract.View {

    @BindView(R.id.share_mheader)
    MaterialHeader mShareMheader;
    @BindView(R.id.share_rv)
    RecyclerView mShareRv;
    @BindView(R.id.share_srl)
    SmartRefreshLayout mShareSrl;

    Unbinder unbinder;

    private ContentListAdapter mAdapter;
    private List<ContentBean> mList;
    private int page = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();

        presenter.getShareArtData(page);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_share, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShareMheader.setColorSchemeResources(R.color.qcgreen);

        mAdapter = new ContentListAdapter(R.layout.share_art_item, mList);
        mShareRv.setAdapter(mAdapter);
        mShareRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mShareSrl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMoreArtData(page);
                mShareSrl.finishLoadMore(true);
            }
        });

        mShareSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                presenter.refreshArtData(1);
                mShareSrl.finishRefresh(true);

            }
        });
    }

    @Override
    public void requestSuccess(CollectBean bean) {

    }

    @Override
    public void requestFailure(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestSuccessShare(Object o) {
        SquareShareListBean bean = (SquareShareListBean) o;
        if (bean.getErrorCode() == 0) {
            mList.addAll(bean.getData().getShareArticles().getDatas());
            mAdapter.notifyDataSetChanged();
            page = bean.getData().getShareArticles().getCurPage() + 1;
        } else if (bean.getErrorCode() == 0) {
            Toast.makeText(getActivity(), bean.getErrorMsg(), Toast.LENGTH_SHORT).show();
        }
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
}
