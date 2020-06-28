package com.example.a10850.wanandroid.ui.offacc.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.activity.WebActivity;
import com.example.a10850.wanandroid.adapter.ContentListAdapter;
import com.example.a10850.wanandroid.entity.ContentBean;
import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.a10850.wanandroid.ui.search.SearchActivity;
import com.example.common.base.BaseMvpActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * todo.. 增加一个关于土司的工具类
 */
public class OffAccListActivity extends BaseMvpActivity<OffAccListPresenter> implements OffAccListContract.View {

    @BindView(R.id.off_acc_rv_list)
    RecyclerView mOffAccRvList;
    @BindView(R.id.off_acc_sr_list)
    SmartRefreshLayout mOffAccSrList;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;

    @BindView(R.id.off_acc_list_tbar)
    Toolbar mCommonTbar;

    private List<ContentBean> mList;
    private int page = 0;
    private ContentListAdapter mAdapter;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_acc_list);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra("id", 0);
        presenter.getOffAccData(id, 1);

        setSupportActionBar(mCommonTbar);
        ActionBar actionBar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        initData();
    }

    private void initData() {
        mTitle.setText(getIntent().getStringExtra("title"));
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OffAccListActivity.this, SearchActivity.class);
                intent.putExtra("type", "off");
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        mList = new ArrayList<>();
        mAdapter = new ContentListAdapter(R.layout.home_contentlist_item, mList);
        mOffAccRvList.setAdapter(mAdapter);
        mOffAccRvList.setLayoutManager(new LinearLayoutManager(this));

        mOffAccSrList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMore(id, page);
                mOffAccSrList.finishLoadMore(true);
            }
        });

        mOffAccSrList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                presenter.refresh(id);
                mOffAccSrList.finishRefresh(true);
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(OffAccListActivity.this, WebActivity.class);
                intent.putExtra("url", mAdapter.getData().get(position).getLink());
                intent.putExtra("title", mAdapter.getData().get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    protected OffAccListPresenter createPresenter() {
        return new OffAccListPresenter();
    }

    @Override
    public void requestSuccess(ContentListBean dataBean) {
        getDataFromNet(dataBean);
    }

    @Override
    public void requestFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshList(ContentListBean dataBean) {
        getDataFromNet(dataBean);
    }

    @Override
    public void loadMoreList(ContentListBean dataBean) {
        getDataFromNet(dataBean);
    }

    private void getDataFromNet(ContentListBean dataBean) {
        //errorCode
        //errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
        //errorCode = -1001 代表登录失效，需要重新登录。
        if (!TextUtils.isEmpty(dataBean.getErrorMsg())) {
            //请求有问题，弹出toast
            Toast.makeText(this, dataBean.getErrorMsg(), Toast.LENGTH_SHORT).show();
        } else {
            mList.addAll(dataBean.getData().getDatas());
            page = dataBean.getData().getCurPage() + 1;
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
