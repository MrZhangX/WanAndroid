package com.example.a10850.wanandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.adapter.ContentListAdapter;
import com.example.a10850.wanandroid.entity.ContentBean;
import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.a10850.wanandroid.ui.square.SquareShareActivity;
import com.example.a10850.wanandroid.utils.RetrofitUtil;
import com.example.common.base.LazyLoadFragment;
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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/***
 * 创建时间：2019/12/10 21:52
 * 创建人：10850
 * 功能描述：广场
 */
public class SquareFragment extends LazyLoadFragment {

    private static String PARAM = "PARAM";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.square_rv)
    RecyclerView mSquareRv;
    @BindView(R.id.square_refresh)
    SmartRefreshLayout mSquareRefresh;
    @BindView(R.id.squareheader)
    MaterialHeader mSrlHeader;

    Unbinder unbinder;

    private String value;
    //页码
    private int page = 0;


    private List<ContentBean> mList;
    private ContentListAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        value = arguments.getString(PARAM);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_square;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mSrlHeader.setColorSchemeResources(R.color.qcgreen);
        mTitle.setText("广场");
        mSearch.setImageResource(R.mipmap.jiahao);

        mAdapter = new ContentListAdapter(R.layout.home_contentlist_item, mList);
        mSquareRv.setAdapter(mAdapter);
        mSquareRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mSquareRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSquareRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.clear();
                        page = 0;
                        getDataFromNet();
                        mSquareRefresh.finishRefresh(true);
                    }
                }, 200);
            }
        });

        mSquareRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSquareRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDataFromNet();
                        mSquareRefresh.finishLoadMore(true);
                    }
                }, 200);
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到分享的界面
                Intent intent = new Intent(getActivity(), SquareShareActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getDataFromNet() {
        RetrofitUtil.getInstance().getSquareList(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ContentListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ContentListBean contentListBean) {
                        if (contentListBean.getErrorCode() == 0) {
                            mList.addAll(contentListBean.getData().getDatas());
                            page = contentListBean.getData().getCurPage();
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), contentListBean.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public SquareFragment newInstance(String str) {
        SquareFragment baseFragment = new SquareFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, str);
        baseFragment.setArguments(bundle);
        return baseFragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void loadData() {
        getDataFromNet();
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
