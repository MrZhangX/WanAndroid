package com.example.a10850.wanandroid.ui.system;

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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.activity.WebActivity;
import com.example.a10850.wanandroid.adapter.ListItemAdapter;
import com.example.a10850.wanandroid.entity.ContentBean;
import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.common.base.BaseMvpFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 创建时间：2020/2/12 17:53
 * 创建人：10850
 * 功能描述：
 * 1、webview加载慢
 * 2、点击体验不太好
 */
public class PerSystemFragment extends BaseMvpFragment<PerSystemPresenter> implements PerSystemContract.View {

    @BindView(R.id.system_per_rv)
    RecyclerView mSystemPerRv;

    Unbinder unbinder;

    @BindView(R.id.system_per_refresh)
    SmartRefreshLayout mSystemPerRefresh;

    private int page = 0;
    private int cid;

    private static String PARAM = "PARAM";

    private ListItemAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_per_system, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("cid", cid + "");
        map.put("page", page + "");
        return map;
    }

    @Override
    public void requestSuccess(ContentListBean contentListBean) {
        page = contentListBean.getData().getCurPage();
        loadData(contentListBean);
    }

    private void loadData(ContentListBean contentListBean) {
        mAdapter = new ListItemAdapter(R.layout.list_item, contentListBean.getData().getDatas());
        mSystemPerRv.setAdapter(mAdapter);
        mSystemPerRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        final List<ContentBean> datas = contentListBean.getData().getDatas();
        //设置点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", datas.get(position).getTitle());
                intent.putExtra("url", datas.get(position).getLink());
                startActivity(intent);
            }
        });
    }

    @Override
    public void registerFailure(String msg) {
        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected PerSystemPresenter createPresenter() {
        return new PerSystemPresenter();
    }

    public PerSystemFragment newInstance(int str) {
        PerSystemFragment baseFragment = new PerSystemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM, str);
        baseFragment.setArguments(bundle);
        return baseFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        cid = arguments.getInt(PARAM, 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getPerSystemData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
