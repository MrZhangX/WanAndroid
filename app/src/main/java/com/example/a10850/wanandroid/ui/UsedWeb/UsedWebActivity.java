package com.example.a10850.wanandroid.ui.UsedWeb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.activity.WebActivity;
import com.example.a10850.wanandroid.entity.UsedWebBean;
import com.example.common.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mao.com.flexibleflowlayout.TagAdapter;
import mao.com.flexibleflowlayout.TagFlowLayout;

/***
 * 创建时间：2020/2/11 20:47
 * 创建人：10850
 * 功能描述：流式布局
 * 正确流程：请求完网络数据，加载适配器数据
 */
public class UsedWebActivity extends BaseMvpActivity<UsedWebPresenter> implements UsedWebContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.usedWebFL)
    TagFlowLayout mUsedWebFL;

    @BindView(R.id.usedWebTab)
    Toolbar mCommonTbar;

    private List<UsedWebBean> mList;
    private UsedWebBean mData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_used_web);
        ButterKnife.bind(this);
        setSupportActionBar(mCommonTbar);
        ActionBar actionBar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        initData();
        presenter.getUsedWebData();

        initView();
    }

    private void initView() {
        mTitle.setText("常用网站");
    }

    private void loadData() {
        if (mData != null)//点进去看看源码怎么写的
            mUsedWebFL.setAdapter(new TagAdapter() {
                @Override
                public int getItemCount() {
                    return mData.getData().size();
                }

                @Override
                public View createView(LayoutInflater inflater, ViewGroup parent, int position) {
                    return inflater.inflate(R.layout.used_web_item, parent, false);
                }

                @Override
                public void bindView(View view, final int position) {
                    TextView tv = view.findViewById(R.id.used_web_tv);
                    tv.setText(mData.getData().get(position).getName());
                    //tv.setBackgroundColor(Color.RED);
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
                            Intent intent = new Intent(getApplication(), WebActivity.class);
                            intent.putExtra("url", mData.getData().get(position).getLink());
                            intent.putExtra("title", mData.getData().get(position).getName());
                            startActivity(intent);
                            break;
                    }
                }

                @Override
                public void onItemSelected(View v, final int position) {
                    super.onItemSelected(v, position);
                    switch (v.getId()) {
                        case R.id.used_web_tv:
                            TextView tv = v.findViewById(R.id.used_web_tv);
//                            tv.setBackgroundColor(Color.RED);
                            tv.setBackground(getDrawable(R.drawable.tag_four));
                            break;
                    }

                }

//                @Override
//                public void onItemUnSelected(View v, final int position) {
//                    super.onItemUnSelected(v, position);
//                    TextView tv = v.findViewById(R.id.used_web_tv);
//                    tv.setBackgroundColor(Color.GRAY);
//
//                }
            });


    }

    private void initData() {
        mList = new ArrayList<>();
    }

    @Override
    public void requestSuccess(UsedWebBean webBean) {
        mList.add(webBean);
        mData = webBean;
        loadData();
    }

    @Override
    public void requestFailure(String msg) {
        showLoading(msg);
    }

    @Override
    protected UsedWebPresenter createPresenter() {
        return new UsedWebPresenter();
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
