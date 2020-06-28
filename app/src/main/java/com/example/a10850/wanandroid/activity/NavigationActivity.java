package com.example.a10850.wanandroid.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.adapter.CommonNavAdapter;
import com.example.a10850.wanandroid.entity.NavBean;
import com.example.a10850.wanandroid.utils.RetrofitUtil;
import com.example.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/***
 * 创建时间：2020/2/6 15:05
 * 创建人：10850
 * 功能描述：https://www.jianshu.com/p/a9010346453b
 * 提交githup，去查看代码没有变化，原因是仓库中的代码只是提交到本地仓库，没有push到远程仓库。
 * https://www.cnblogs.com/shiwei-bai/p/4991132.html
 *
 * https://www.jianshu.com/p/e2f54b5b648a这篇文章思路不错,将tbar边框有间隔的
 */
public class NavigationActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.nav_vt)
    VerticalTabLayout mNavVt;
    @BindView(R.id.nav_rv)
    RecyclerView mNavRv;
    @BindView(R.id.navbar)
    Toolbar mCommonTbar;


    private List<String> mTitles;
    private TabAdapter mAdapter;

    private CommonNavAdapter mNavAdapter;

    private LinearLayoutManager mLayout;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mCommonTbar);
        ActionBar actionBar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionBar.setDisplayHomeAsUpEnabled(true);
        //显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        //显示标题
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("");

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

    @Override
    protected void initData() {
        mTitles = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        RetrofitUtil.getInstance().getNav().enqueue(new Callback<NavBean>() {

            @Override
            public void onResponse(Call<NavBean> call, Response<NavBean> response) {
                NavBean body = response.body();
                if (body.getErrorCode() == 0) {
                    List<NavBean.DataBean> data = body.getData();
                    for (int i = 0; i < data.size(); i++) {
                        mTitles.add(data.get(i).getName());
                    }
//                    mNavVt.addTab(new QTabView(geta).setTitle());

                    mAdapter = new TabAdapter() {
                        @Override
                        public int getCount() {
                            return mTitles.size();
                        }

                        @Override
                        public ITabView.TabBadge getBadge(int position) {
                            return null;
                        }

                        @Override
                        public ITabView.TabIcon getIcon(int position) {
                            return null;
                        }

                        @Override
                        public ITabView.TabTitle getTitle(int position) {
                            QTabView.TabTitle title = new QTabView.TabTitle.Builder()
                                    .setTextColor(R.color.black, R.color.black)
                                    .setContent(mTitles.get(position))//设置数据   也有设置字体颜色的方法
                                    .build();
                            return title;
                        }

                        @Override
                        public int getBackground(int position) {
                            return R.drawable.nav_selector;//选中的背景颜色;
                        }
                    };

                    mNavVt.setTabAdapter(mAdapter);
//                    mNavVt.setAnimation();

                    mNavAdapter = new CommonNavAdapter(R.layout.fragment_per_nav, data);
                    mNavRv.setAdapter(mNavAdapter);
                    mLayout = new LinearLayoutManager(getApplication());
                    mNavRv.setLayoutManager(mLayout);
                } else {
                    Toast.makeText(NavigationActivity.this, "请求失败!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NavBean> call, Throwable t) {

            }
        });
    }

    @Override
    protected void initView() {
        mSearch.setVisibility(View.GONE);
        mTitle.setText("导航");

        mNavVt.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                mLayout.scrollToPositionWithOffset(position, 0);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        mNavRv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        int firstCompletelyVisibleItemPosition = mLayout.findFirstCompletelyVisibleItemPosition();
                        int firstVisibleItemPosition = mLayout.findFirstVisibleItemPosition();


//                        if (firstCompletelyVisibleItemPosition > firstVisibleItemPosition) {
//                            mNavVt.setTabSelected(firstVisibleItemPosition);
//                            mNavRv.smoothScrollToPosition(firstVisibleItemPosition);
//                        } else {
//                            pos = firstVisibleItemPosition;
//                        }

                        if (firstVisibleItemPosition != -1 && firstCompletelyVisibleItemPosition != -1)
                            if (firstCompletelyVisibleItemPosition > firstVisibleItemPosition) {
                                if (mNavVt != null)
                                    mNavVt.setTabSelected(firstVisibleItemPosition);
//                                mNavRv.scrollToPosition(firstVisibleItemPosition);

//                                int tabMinus = mTitles.size() - 1 - firstVisibleItemPosition;
                                int tabMinus = mTitles.size() - 1 - mNavVt.getSelectedTabPosition();
                                int rvMinus = mLayout.getItemCount() - 1 - firstVisibleItemPosition;

//                                Log.i("zxd", "tabMinus: " + tabMinus + "-rvMinus:" + rvMinus);
//                                Log.i("zxd", "mNavVt: " + mNavVt.getSelectedTabPosition());
                                if (tabMinus > rvMinus)
                                    mLayout.scrollToPositionWithOffset(firstVisibleItemPosition, 0);

                            } else {
                                mNavVt.setTabSelected(firstVisibleItemPosition);
                            }
//                        Log.i("zxd", "onScrollStateChanged: " + firstVisibleItemPosition);
//                        Log.i("zxd", "firstCompletelyVisibleItemPosition: " + firstCompletelyVisibleItemPosition);


//                            mNavVt.setTabSelected(pos);
                        break;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

}
