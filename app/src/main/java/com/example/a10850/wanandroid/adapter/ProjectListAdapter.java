package com.example.a10850.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.ProjectListBean;

import java.util.List;

/***
 * 创建时间：2020/2/5 13:24
 * 创建人：10850
 * 功能描述：
 */
public class ProjectListAdapter extends BaseQuickAdapter<ProjectListBean.DataBean.DatasBean, BaseViewHolder> {

    public ProjectListAdapter(int layoutResId, @Nullable List<ProjectListBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    public ProjectListAdapter(@Nullable List<ProjectListBean.DataBean.DatasBean> data) {
        super(data);
    }

    public ProjectListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProjectListBean.DataBean.DatasBean item) {
        Glide.with(helper.getView(R.id.project_list_iv)).load(item.getEnvelopePic()).into((ImageView) helper.getView(R.id.project_list_iv));
        helper.setText(R.id.project_list_author, item.getAuthor())
                .setText(R.id.project_list_title, item.getTitle())
                .setText(R.id.project_list_desc, item.getDesc())
                .setText(R.id.project_list_time, item.getNiceDate());
    }
}
