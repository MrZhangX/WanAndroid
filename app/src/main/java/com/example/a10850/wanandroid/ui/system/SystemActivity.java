package com.example.a10850.wanandroid.ui.system;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.ContentListBean;
import com.example.common.adapter.SubMvpStatePagerAdapter;
import com.example.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***
 * 遇到问题
 * 1、请保持冷静
 * 2、定位错误，首先打log
 * 3、无log打印，先去看实现流程写的对不，有没有设置适配器等
 * 4、如果有网络请求，先看请求的url对不
 * 5、。。。
 */
public class SystemActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.ss_tab)
    TabLayout mSsTab;
    @BindView(R.id.ss_vp)
    ViewPager mSsVp;

    @BindView(R.id.ssbar)
    Toolbar mToolbar;

    private List<ContentListBean.DataBean> mList;
    private List<String> mTitles;
    private List<Fragment> mFragments;
    private SubMvpStatePagerAdapter mAdapter;

    private List<Integer> mIds;

    private int cid;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_system;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
        mIds = new ArrayList<>();

        mTitles = getIntent().getStringArrayListExtra("list");
        mIds = getIntent().getIntegerArrayListExtra("ids");
//        cid = getIntent().getIntExtra("cid", 0);//不对

        for (int i = 0; i < mTitles.size(); i++) {
            PerSystemFragment perSystemFragment = new PerSystemFragment().newInstance(mIds.get(i));
            mFragments.add(perSystemFragment);
        }
    }

    @Override
    protected void initView() {
        mTitle.setText(getIntent().getStringExtra("title"));

        mAdapter = new SubMvpStatePagerAdapter(getSupportFragmentManager());
        mAdapter.setData(mFragments, mTitles);
        mSsVp.setAdapter(mAdapter);
        //1、将tablayout与viewpager关联起来
        mSsTab.setupWithViewPager(mSsVp);
        //设置阴影高度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSsTab.setElevation(0);
        }
        //2、移除tablayout的所有tab
        mSsTab.removeAllTabs();
        //3、重新添加tab
        for (int i = 0; i < mTitles.size(); i++) {
            mSsTab.addTab(mSsTab.newTab().setText(mTitles.get(i)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
