package com.example.a10850.wanandroid;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10850.wanandroid.fragment.HomeFragment;
import com.example.a10850.wanandroid.fragment.MainFragment;
import com.example.a10850.wanandroid.fragment.MineFragment;
import com.example.a10850.wanandroid.fragment.ProjectsFragment;
import com.example.a10850.wanandroid.fragment.SquareFragment;
import com.example.a10850.wanandroid.fragment.SystemFragment;
import com.example.common.adapter.SubPagerAdapter;
import com.example.common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp_main)
    ViewPager mVpMain;
    @BindView(R.id.tab_main)
    TabLayout mTabMain;

    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    //未选择的图标
    private int[] tabIcons = new int[]{
            R.mipmap.main_unselected,
            R.mipmap.system_unselected,
            R.mipmap.project_unselected,
            R.mipmap.square_unselected,
            R.mipmap.mine_unselected
    };
    //已选择的图标
    private int[] tabIconSelected = new int[]{
            R.mipmap.main_selected,
            R.mipmap.system_selected,
            R.mipmap.project_selected,
            R.mipmap.square_selected,
            R.mipmap.mine_selected
    };

    //图标的变化
    private int[] tabIcon = new int[]{
            R.drawable.main_tab_selector_main,
            R.drawable.main_tab_selector_system,
            R.drawable.main_tab_selector_project,
            R.drawable.main_tab_selector_square,
            R.drawable.main_tab_selector_mine,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();
        initViewPager();
    }

    /***
     * 初始化ViewPager
     */
    private void initViewPager() {
        SubPagerAdapter madapter = new SubPagerAdapter(getSupportFragmentManager());
        madapter.setData(mFragments, mTitles);
        mVpMain.setAdapter(madapter);
        mVpMain.setOffscreenPageLimit(mFragments.size());
//        mVpMain.setAdapter(new MyPagerFragmentAdapter(getSupportFragmentManager()));
        //1、将tablayout与viewpager关联起来
        mTabMain.setupWithViewPager(mVpMain);
        //设置阴影高度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTabMain.setElevation(0);
        }
        //2、移除tablayout的所有tab
//        mTabMain.removeAllTabs();
        //3、重新添加tab
        for (int i = 0; i < mTitles.size(); i++) {
//            mTabMain.addTab(mTabMain.newTab().setText(mTitles.get(i)));
//            mTabMain.addTab(mTabMain.newTab().setIcon(tabIcon[i]).setText(mTitles.get(i)));
            mTabMain.getTabAt(i).setCustomView(getTabView(i));
        }

        changeTabStatus(mTabMain.getTabAt(0), true);
        mTabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabStatus(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabStatus(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("再次选中，显示对话框！")
                        .show();
            }
        });
    }

    /***
     * 初始化fragment和标题
     */
    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
//        mFragments.add(new MainFragment().newInstance("首页"));
        mFragments.add(new SystemFragment());
        mFragments.add(new ProjectsFragment().newInstance("项目"));
        mFragments.add(new SquareFragment().newInstance("广场"));
        mFragments.add(new MineFragment().newInstance("我的"));

        mTitles = new ArrayList<>();
        mTitles.add("首页");
        mTitles.add("体系");
        mTitles.add("项目");
        mTitles.add("广场");
        mTitles.add("我的");
    }

//    private class MyPagerFragmentAdapter extends FragmentPagerAdapter {
//
//        public MyPagerFragmentAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int i) {
//            return mFragments.get(i);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//    }


    //设置图标
    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView mTxt_title = view.findViewById(R.id.tab_text);
        mTxt_title.setText(mTitles.get(position));
        ImageView img_title = view.findViewById(R.id.tab_image);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }

    //改变状态
    private void changeTabStatus(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        TextView txtTitle = view.findViewById(R.id.tab_text);
        ImageView img_title = view.findViewById(R.id.tab_image);
        if (selected) {
            img_title.setImageResource(tabIconSelected[tab.getPosition()]);
            txtTitle.setTextColor(Color.parseColor("#03ce97"));
        } else {
            img_title.setImageResource(tabIcons[tab.getPosition()]);
            txtTitle.setTextColor(Color.parseColor("#333333"));
        }
    }
}
