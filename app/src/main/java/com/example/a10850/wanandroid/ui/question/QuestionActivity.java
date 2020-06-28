package com.example.a10850.wanandroid.ui.question;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.example.common.base.BaseMvpActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionActivity extends BaseMvpActivity<QuestionPresenter> implements QuestionContract.View {

    @BindView(R.id.questionRv)
    RecyclerView mQuestionRv;
    @BindView(R.id.srlRv)
    SmartRefreshLayout mSrlRv;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.questionTbar)
    Toolbar mCommonTbar;

    private List<ContentBean> mList;

    private int page = 1;

    private ContentListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);

        setSupportActionBar(mCommonTbar);
        ActionBar actionBar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        mList = new ArrayList<>();
        presenter.getQuestionData(page);
        initView();
    }

    @Override
    protected QuestionPresenter createPresenter() {
        return new QuestionPresenter();
    }

    @Override
    public void registerSuccess(List<ContentBean> list) {
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mTitle.setText("问答");
        mSearch.setVisibility(View.GONE);

        mAdapter = new ContentListAdapter(R.layout.home_contentlist_item, mList);
        mQuestionRv.setAdapter(mAdapter);
        mQuestionRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(QuestionActivity.this, WebActivity.class);
                intent.putExtra("url", mAdapter.getData().get(position).getLink());
                intent.putExtra("title", mAdapter.getData().get(position).getTitle());
                startActivity(intent);
            }
        });

        mSrlRv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.loadMoreData(page);
                mSrlRv.finishLoadMore(true);
            }
        });

        mSrlRv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                page = 1;
                presenter.refreshData(page);
                mSrlRv.finishRefresh(true);
            }
        });
    }

    @Override
    public void registerFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getRefreshData(List<ContentBean> list) {
        mList = list;
        mAdapter.setNewData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<ContentBean> list) {
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getCurPage(int curPage) {
        page = curPage;
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
