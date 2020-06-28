package com.example.a10850.wanandroid.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.activity.NavigationActivity;
import com.example.a10850.wanandroid.customview.LSettingItem;
import com.example.a10850.wanandroid.ui.UsedWeb.UsedWebActivity;
import com.example.a10850.wanandroid.ui.login.LoginActivity;
import com.example.a10850.wanandroid.ui.offacc.OffAccActivity;
import com.example.a10850.wanandroid.ui.question.QuestionActivity;
import com.example.a10850.wanandroid.ui.user.MyAllActivity;
import com.example.a10850.wanandroid.utils.SPUtils;
import com.example.common.base.LazyLoadFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/***
 * 创建时间：2019/12/10 21:54
 * 创建人：10850
 * 功能描述：问题1：这个自定义layout不支持黄油刀，将线先放弃，采用expandRv试试
 * 问题2：
 *
 * 注册个广播接受，登录成功返回回来的数据，
 */
public class MineFragment extends LazyLoadFragment {

    private static String PARAM = "PARAM";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.item_offacc)
    LSettingItem mItemOffacc;
    @BindView(R.id.logo)
    CircleImageView mLogo;
    @BindView(R.id.user)
    TextView mUser;
    @BindView(R.id.user_infor)
    TextView mUserInfor;
    @BindView(R.id.item_net)
    LSettingItem mItemNet;
    @BindView(R.id.item_question)
    LSettingItem mItemQuestion;
    @BindView(R.id.user_container)
    RelativeLayout mLayout;

    Unbinder unbinder;


    private LSettingItem mItemNavigation;
    private String value;

    private LocalBroadcastManager broadcastManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        value = arguments.getString(PARAM);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTitle.setText("我");
        mSearch.setVisibility(View.GONE);


        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("请登录".equals(mUser.getText().toString())) {
                    receiveLogin();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    // TODO: 2020/3/1 跳转到详情页 包含我的收藏文章，网站、我的积分、我的分享、
                    Intent intent = new Intent(getActivity(), MyAllActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    public MineFragment newInstance(String str) {
        MineFragment baseFragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM, str);
        baseFragment.setArguments(bundle);
        return baseFragment;
    }

    @Override
    protected void loadData() {

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("zxd", "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);

        if ((int) SPUtils.get(getActivity(), "level", 0) != 0)
            mUserInfor.setText("等级:" + SPUtils.get(getActivity(), "level", 0)
                    + "  排名" + SPUtils.get(getActivity(), "rank", 0));

        if (!TextUtils.isEmpty(SPUtils.get(getActivity(), "username", "").toString())) {
            mUser.setText(SPUtils.get(getActivity(), "username", "请登录").toString());
        }


        mItemNavigation = view.findViewById(R.id.item_navigation);
        mItemNavigation.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
            }
        });

        mItemNet.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent(getActivity(), UsedWebActivity.class);
                startActivity(intent);
            }
        });


        mItemOffacc.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent(getActivity(), OffAccActivity.class);
                startActivity(intent);
            }
        });

        mItemQuestion.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 注册广播接收器
     */
    private void receiveLogin() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LOGIN");
        broadcastManager.registerReceiver(mLoginReceiver, intentFilter);
    }

    BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            //这里接收到广播和数据，进行处理就是了
            mUserInfor.setText("等级:" + intent.getIntExtra("level", 0)
                    + "  排名" + intent.getIntExtra("rank", 0));

            SPUtils.put(getActivity(), "level", intent.getIntExtra("level", 0));
            SPUtils.put(getActivity(), "rank", intent.getIntExtra("rank", 0));

            mUser.setText(intent.getStringExtra("username"));
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //本地广播没初始化导致的，崩溃
        if (broadcastManager != null)
            broadcastManager.unregisterReceiver(mLoginReceiver);
    }
}
