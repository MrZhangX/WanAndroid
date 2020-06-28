package com.example.a10850.wanandroid.ui.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.fragment.UserWebFragment;
import com.example.common.adapter.SubMvpStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAllActivity extends AppCompatActivity {

    @BindView(R.id.collection_view_pager)
    ViewPager mCollectionViewPager;
//    @BindView(R.id.sr_refresh)
//    SmartRefreshLayout mSrRefresh;
    @BindView(R.id.collection_tab)
    TabLayout mCollectionTab;

    private List<String> mTitleList;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_all);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        mTitleList = new ArrayList<>();
        mTitleList.add("收藏文章");
        mTitleList.add("分享文章");
        mTitleList.add("收藏网站");

        mFragments = new ArrayList<>();
//        mFragments.add(new UserCollectFragment());
//        mFragments.add(new UserShareFragment());
        mFragments.add(new UserWebFragment());

        SubMvpStatePagerAdapter adapter = new SubMvpStatePagerAdapter(getSupportFragmentManager());
        adapter.setData(mFragments, mTitleList);
        mCollectionViewPager.setAdapter(adapter);
        mCollectionTab.setupWithViewPager(mCollectionViewPager);
    }


}
