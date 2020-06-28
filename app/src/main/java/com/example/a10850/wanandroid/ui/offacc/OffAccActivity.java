package com.example.a10850.wanandroid.ui.offacc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.adapter.OffAccAdapter;
import com.example.a10850.wanandroid.entity.ProjectBean;
import com.example.a10850.wanandroid.ui.offacc.list.OffAccListActivity;
import com.example.common.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * 界面组成
 * 1.主界面是卡片
 * 2.次界面是列表
 */
public class OffAccActivity extends BaseMvpActivity<OffAccPresenter> implements OffAccContract.View {

    @BindView(R.id.off_acc_rv)
    RecyclerView mOffAccRv;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;

    @BindView(R.id.off_acc_tbar)
    Toolbar mCommonTbar;

    private List<ProjectBean.DataBean> mList;
    private OffAccAdapter mAdapter;
    private String[] mStringArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_acc);
        ButterKnife.bind(this);
        presenter.getOffAccData();
        initData();

        setSupportActionBar(mCommonTbar);
        ActionBar actionBar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        mStringArray = getResources().getStringArray(R.array.off_acc_title);
    }

    private void initData() {
        mList = new ArrayList<>();
    }

    private void initView() {
        mTitle.setText("公众号");
        mSearch.setVisibility(View.GONE);

        mAdapter = new OffAccAdapter(this, mList);
        //设置默认动画
        mOffAccRv.setItemAnimator(new DefaultItemAnimator());
        mOffAccRv.setAdapter(mAdapter);
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(mOffAccRv.getAdapter(), mList);
        cardCallback.setOnSwipedListener(new OnSwipeListener<ProjectBean.DataBean>() {

            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                //根据左右滑动，将喜欢与讨厌的圆圈，动态的改变透明度
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, ProjectBean.DataBean o, int direction) {
                //滑动结束的时候条用，重置子view、两个view的透明度
                viewHolder.itemView.setAlpha(1f);
//                Toast.makeText(OffAccActivity.this, direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() {
                //当显示空白页，3s后重置数据
                mOffAccRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getOffAccData();
                        mOffAccRv.getAdapter().notifyDataSetChanged();
                    }
                }, 1000L);
            }

        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        CardLayoutManager cardLayoutManager = new CardLayoutManager(mOffAccRv, touchHelper);
        //设置布局
        mOffAccRv.setLayoutManager(cardLayoutManager);
        //设置recyclerView的触摸事件
        touchHelper.attachToRecyclerView(mOffAccRv);

//        mOffAccRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new OffAccAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(OffAccActivity.this, OffAccListActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                intent.putExtra("title", mList.get(position).getName());
                startActivity(intent);
            }
        });
    }

    @Override
    protected OffAccPresenter createPresenter() {
        return new OffAccPresenter();
    }

    @Override
    public void requestSuccess(ProjectBean contentListBean) {
        mList.addAll(contentListBean.getData());
        initView();
    }

    @Override
    public void registerFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
