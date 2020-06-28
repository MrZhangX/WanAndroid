package com.example.a10850.wanandroid.ui.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.activity.WebActivity;
import com.example.a10850.wanandroid.adapter.ContentListAdapter;
import com.example.a10850.wanandroid.app.MyApplication;
import com.example.a10850.wanandroid.dao.HistorySearchDao;
import com.example.a10850.wanandroid.entity.ContentBean;
import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.a10850.wanandroid.entity.SearchHotBean;
import com.example.a10850.wanandroid.greenDao.db.HistorySearchDaoDao;
import com.example.a10850.wanandroid.utils.RetrofitUtil;
import com.example.a10850.wanandroid.widget.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mao.com.flexibleflowlayout.TagAdapter;
import mao.com.flexibleflowlayout.TagFlowLayout;

/**
 * 1.问题：搜索键盘不隐藏
 * 2.列表显示&amp;等一些特殊字符(很强)
 * https://blog.csdn.net/zice_/article/details/51683556
 * https://blog.csdn.net/north_easter/article/details/7468658
 * 3.插入历史数据
 * 3.1 热搜：判断有没有历史，没有历史直接插入。有历史需要判断是否当前搜索在历史中存在，存在就不插入，不存在就插入
 * 3.2 历史搜索：点击历史搜索，只是赋值，无任何数据库的操作
 * 3.3 ET直接输入文字查找数据，和热搜是类似的
 * <p>
 * 4.RV位置问题
 * 问题描述：我在RV滑动了一段距离，退出，重新进入RV，会保留之前的滑动状态，不是从0开始
 * 每次网络请求前把page=0
 * <p>
 * 5.搜索历史的顺序问题
 * 第一个item是最后一次查询的内容
 * 思路1：更改item显示的顺序，就得改表结构
 * 思路2：删除那个搜索的历史，然后重新插入一条,倒序显示(选择这个)
 * <p>
 * 6.多个搜索的问题
 * 6.1 url不同
 * 6.2 需要区分吗？暂时不需要
 * <p>
 * 三种类型：
 * https://www.wanandroid.com/article/query/0/json 搜索
 * https://wanandroid.com/article/list/0/json?author=鸿洋 按照作者昵称搜索文章（体系下的）
 * https://wanandroid.com/wxarticle/list/405/1/json?k=Java 按照作者昵称搜索文章
 */
public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.history_search)
    TagFlowLayout mHistorySearch;
    @BindView(R.id.clear_search_history)
    TextView mClearSearchHistory;
    @BindView(R.id.hot_search)
    TagFlowLayout mHotSearch;
    @BindView(R.id.et_search)
    ClearEditText mEtSearch;
    @BindView(R.id.cancel_search)
    TextView mCancelSearch;
    @BindView(R.id.search_rv)
    RecyclerView mSearchRv;
    @BindView(R.id.search_header)
    LinearLayout mSearchHeader;
    @BindView(R.id.search_refresh)
    SmartRefreshLayout mSearchRefresh;

    private List<SearchHotBean> mHotList;
    private List<String> mTitleList;

    private ContentListAdapter mAdapter;
    private List<ContentBean> mList;

    private int page = 0;
    private List<HistorySearchDao> mHistorySearchDaos;
    private TagAdapter mTagAdapter;

    private String type = "normal";
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            id = getIntent().getIntExtra("id", 0);
            type = getIntent().getStringExtra("type");
        }

        initData();
        initView();
    }

    private void initView() {
        mAdapter = new ContentListAdapter(R.layout.home_contentlist_item, mList);
        mSearchRv.setAdapter(mAdapter);
        mSearchRv.setLayoutManager(new LinearLayoutManager(this));

        mCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mSearchRefresh.setVisibility(View.GONE);
                    mSearchHeader.setVisibility(View.VISIBLE);
                    page = 0;
                    //显示搜索历史：通过查找本地数据库的数据，然后显示出来（正序）
//                    mHistorySearchDaos = MyApplication.getDaoSession().loadAll(HistorySearchDao.class);
                    //倒序
                    QueryBuilder<HistorySearchDao> builder = MyApplication.getDaoSession().queryBuilder(HistorySearchDao.class);
                    mHistorySearchDaos = builder.orderDesc(HistorySearchDaoDao.Properties.Id).list();
                    mTagAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i("zxd", "Action: " + actionId);
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftKeyboard(SearchActivity.this);
                    Log.i("zxd", "onEditorAction: " + mEtSearch.getText().toString());
                    page = 0;
                    doSearch(mEtSearch.getText().toString());
                    mSearchRefresh.setVisibility(View.VISIBLE);
                    mSearchHeader.setVisibility(View.GONE);

                    mSearchRv.scrollToPosition(0);

                    //插入本地sqlite数据库，需要判断数据库中是否存在这个搜索历史，如果有就不插入，没有就插入
                    HistorySearchDao dao = new HistorySearchDao();
                    dao.setHistory(mEtSearch.getText().toString());

                    QueryBuilder<HistorySearchDao> builder = MyApplication.getDaoSession().queryBuilder(HistorySearchDao.class);
                    builder.where(HistorySearchDaoDao.Properties.History.eq(mEtSearch.getText().toString()))
                            .orderAsc(HistorySearchDaoDao.Properties.History);
                    List<HistorySearchDao> daoList = builder.list(); //查出当前对应的数据
                    if (daoList.size() == 0) {
                        MyApplication.getDaoSession().insert(dao);
                    } else {
                        MyApplication.getDaoSession().delete(daoList.get(0));
                        MyApplication.getDaoSession().insert(dao);
                    }
                }
                return false;
            }
        });

        mSearchRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                doSearch(mEtSearch.getText().toString());
            }
        });
        mSearchRefresh.setEnableRefresh(false);
        //搜索历史
        mTagAdapter = new TagAdapter() {
            @Override
            public int getItemCount() {
                return mHistorySearchDaos.size();
            }

            @Override
            public View createView(LayoutInflater inflater, ViewGroup parent, int position) {
                return inflater.inflate(R.layout.used_web_item, parent, false);
            }

            @Override
            public void bindView(View view, final int position) {
                TextView tv = view.findViewById(R.id.used_web_tv);
                tv.setText(mHistorySearchDaos.get(position).getHistory());
                if (position % 3 == 0) {
                    tv.setBackground(getDrawable(R.drawable.tag_one));
                } else if (position % 3 == 1) {
                    tv.setBackground(getDrawable(R.drawable.tag_two));
                } else {
                    tv.setBackground(getDrawable(R.drawable.tag_three));
                }
            }

            @Override
            public void onTagItemViewClick(View v, final int position) {
                super.onTagItemViewClick(v, position);
                switch (v.getId()) {
                    case R.id.used_web_tv:
                        //给EditText赋值
                        mEtSearch.setText(mHistorySearchDaos.get(position).getHistory());
                        mSearchRefresh.setVisibility(View.VISIBLE);
                        mSearchHeader.setVisibility(View.GONE);
                        page = 0;
                        //同时进行网络请求
                        doSearch(mEtSearch.getText().toString());
                        hideSoftKeyboard(SearchActivity.this);
                        //点历史数据不需要插入数据
                        mSearchRv.scrollToPosition(0);

                        HistorySearchDao dao = new HistorySearchDao();
                        dao.setHistory(mEtSearch.getText().toString());

                        QueryBuilder<HistorySearchDao> builder = MyApplication.getDaoSession().queryBuilder(HistorySearchDao.class);
                        builder.where(HistorySearchDaoDao.Properties.History.eq(mEtSearch.getText().toString()))
                                .orderAsc(HistorySearchDaoDao.Properties.History);
                        List<HistorySearchDao> daoList = builder.list(); //查出当前对应的数据
                        if (daoList.size() == 0) {
                            MyApplication.getDaoSession().insert(dao);
                        } else {
                            MyApplication.getDaoSession().delete(daoList.get(0));
                            MyApplication.getDaoSession().insert(dao);
                        }

                        break;
                }
            }
        };
        mHistorySearch.setAdapter(mTagAdapter);
        //清空历史
        mClearSearchHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getDaoSession().deleteAll(HistorySearchDao.class);
                mHistorySearchDaos.clear();
                mTagAdapter.notifyDataSetChanged();
            }
        });
        //点击进入Web界面
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SearchActivity.this, WebActivity.class);
                intent.putExtra("url", mAdapter.getData().get(position).getLink());
                intent.putExtra("title", mAdapter.getData().get(position).getTitle());
                startActivity(intent);
            }
        });

    }

    private void initData() {
        mHotList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mList = new ArrayList<>();
//        mHistorySearchDaos = MyApplication.getDaoSession().loadAll(HistorySearchDao.class);

        QueryBuilder<HistorySearchDao> builder = MyApplication.getDaoSession().queryBuilder(HistorySearchDao.class);
        mHistorySearchDaos = builder.orderDesc(HistorySearchDaoDao.Properties.Id).list();
        loadData();
    }

    private void loadData() {
        RetrofitUtil.getInstance().getHotSearch().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SearchHotBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SearchHotBean searchHotBean) {
                mHotList.add(searchHotBean);

                for (int i = 0; i < searchHotBean.getData().size(); i++) {
                    mTitleList.add(searchHotBean.getData().get(i).getName());
                }

                mHotSearch.setAdapter(new TagAdapter() {
                    @Override
                    public int getItemCount() {
                        return mTitleList.size();
                    }

                    @Override
                    public View createView(LayoutInflater inflater, ViewGroup parent, int position) {
                        return inflater.inflate(R.layout.used_web_item, parent, false);
                    }

                    @Override
                    public void bindView(View view, final int position) {
                        TextView tv = view.findViewById(R.id.used_web_tv);
                        tv.setText(mTitleList.get(position));
                        if (position % 3 == 0) {
                            tv.setBackground(getDrawable(R.drawable.tag_one));
                        } else if (position % 3 == 1) {
                            tv.setBackground(getDrawable(R.drawable.tag_two));
                        } else {
                            tv.setBackground(getDrawable(R.drawable.tag_three));
                        }
                    }

                    @Override
                    public void onTagItemViewClick(View v, final int position) {
                        super.onTagItemViewClick(v, position);
                        switch (v.getId()) {
                            case R.id.used_web_tv:
                                //给EditText赋值
                                mEtSearch.setText(mTitleList.get(position));
                                mSearchRefresh.setVisibility(View.VISIBLE);
                                mSearchHeader.setVisibility(View.GONE);
                                mSearchRv.scrollToPosition(0);
                                page = 0;
                                //同时进行网络请求
                                doSearch(mTitleList.get(position));
                                hideSoftKeyboard(SearchActivity.this);
                                //插入本地sqlite数据库，先判断有没有这个历史，没有才能插入
                                HistorySearchDao dao = new HistorySearchDao();
                                dao.setHistory(mTitleList.get(position));

                                if (mHistorySearchDaos.size() == 0) {
                                    MyApplication.getDaoSession().insert(dao);
                                } else {
                                    QueryBuilder<HistorySearchDao> builder = MyApplication.getDaoSession().queryBuilder(HistorySearchDao.class);
                                    builder.where(HistorySearchDaoDao.Properties.History.eq(mEtSearch.getText().toString()))
                                            .orderAsc(HistorySearchDaoDao.Properties.History);
                                    List<HistorySearchDao> daoList = builder.list(); //查出当前对应的数据
                                    if (daoList.size() == 0) {
                                        MyApplication.getDaoSession().insert(dao);
                                    } else {
                                        MyApplication.getDaoSession().delete(daoList.get(0));
                                        MyApplication.getDaoSession().insert(dao);
                                    }
                                }
                                break;
                        }
                    }

                    @Override
                    public void onItemSelected(View v, final int position) {
                        super.onItemSelected(v, position);
                        /*switch (v.getId()) {
                            case R.id.used_web_tv:
                                TextView tv = v.findViewById(R.id.used_web_tv);
                                tv.setBackground(getDrawable(R.drawable.tag_four));
                                break;
                        }*/

                    }

                });

                mHotSearch.onDataChanged();

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void doSearch(String content) {
        if ("normal".equals(type)) {
            Log.i("zxd", "page: " + page);
            RetrofitUtil.getInstance().getSearchList(page, content).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ContentListBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ContentListBean bean) {
                    //rv list列表获取数据
                    Log.e("zxd", "onNext: " + bean.getData());
                    Log.e("zxd", "onNext: " + bean.getData().getDatas().size());
                    Log.e("zxd", "onNext:page " + page);
                    // TODO: 2020/2/28 好像能得到数据了，需要验证正确性
                    List<ContentBean> datas = bean.getData().getDatas();
                    if (page == 0) {
                        mList.clear();
                    } else {
                        Log.e("zxd", "onNext: " + mSearchRefresh.getState());
                        mSearchRefresh.finishLoadMore(true);
                    }
                    mList.addAll(datas);
                    mAdapter.notifyDataSetChanged();
                    page = bean.getData().getCurPage();


                    if (datas.size() == 0) {
                        Toast.makeText(SearchActivity.this, "真的没有了!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        } else if ("off".equals(type)) {
            if (page == 0)
                page = 1;
            RetrofitUtil.getInstance().getOffAccSearchList(id, page, mEtSearch.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ContentListBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ContentListBean contentListBean) {
                            List<ContentBean> datas = contentListBean.getData().getDatas();
                            if (page == 1) {
                                mList.clear();
                            } else {
                                Log.e("zxd", "onNext: " + mSearchRefresh.getState());
                                mSearchRefresh.finishLoadMore(true);
                            }
                            mList.addAll(datas);
                            mAdapter.notifyDataSetChanged();
                            page = contentListBean.getData().getCurPage() + 1;


                            if (datas.size() == 0) {
                                Toast.makeText(SearchActivity.this, "真的没有了!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else if ("sys".equals(type)) {
            RetrofitUtil.getInstance().getSystemSearch(mEtSearch.getText().toString()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ContentListBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ContentListBean bean) {
                            List<ContentBean> datas = bean.getData().getDatas();
                            if (page == 0) {
                                mList.clear();
                            } else {
                                Log.e("zxd", "onNext: " + mSearchRefresh.getState());
                                mSearchRefresh.finishLoadMore(true);
                            }
                            mList.addAll(datas);
                            mAdapter.notifyDataSetChanged();
                            page = bean.getData().getCurPage();


                            if (datas.size() == 0) {
                                Toast.makeText(SearchActivity.this, "真的没有了!", Toast.LENGTH_SHORT).show();
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

    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
