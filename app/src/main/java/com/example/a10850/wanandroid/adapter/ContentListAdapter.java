package com.example.a10850.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.ContentBean;

import java.util.List;

/***
 * 创建时间：2019/12/12 11:11
 * 创建人：10850
 * 功能描述：内容列表的适配器，加上header
 */
public class ContentListAdapter extends BaseQuickAdapter<ContentBean, BaseViewHolder> {


    public ContentListAdapter(int layoutResId, @Nullable List<ContentBean> data) {
        super(layoutResId, data);
    }

    public ContentListAdapter(@Nullable List<ContentBean> data) {
        super(data);
    }

    public ContentListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ContentBean item) {
        //分类
        helper.setText(R.id.home_contentlist_category, "分类：" + item.getSuperChapterName() + "/" + item.getChapterName());
        //分享人
        if (!TextUtils.isEmpty(item.getShareUser()))
            helper.setText(R.id.home_contentlist_tv4, "作者：" + item.getShareUser());
        else
            helper.setText(R.id.home_contentlist_tv4, "作者：" + item.getAuthor());
        //时间+标题
        helper.setText(R.id.home_contentlist_tv5, "时间：" + item.getNiceDate());
        helper.setText(R.id.home_contentlist_title, Html.fromHtml(item.getTitle()).toString());
        //收藏
        if (item.isCollect()) {
            helper.setImageResource(R.id.home_contentlist_iv, R.drawable.heart_red);
        } else {
            helper.setImageResource(R.id.home_contentlist_iv, R.drawable.heart_gray);
        }
        //新
        if (item.isFresh()) {
            helper.getView(R.id.home_contentlist_tv2).setVisibility(View.VISIBLE);
        } else
            helper.getView(R.id.home_contentlist_tv2).setVisibility(View.GONE);

        //问答、公众号
        List<ContentBean.TagsBean> tags = item.getTags();
        if (tags.size() != 0) {
            if (tags.size() == 2) {
                helper.getView(R.id.home_contentlist_tv3).setVisibility(View.VISIBLE);
                helper.getView(R.id.home_contentlist_tv31).setVisibility(View.VISIBLE);
            } else {
                for (int i = 0; i < tags.size(); i++) {
                    if ("问答".equals(tags.get(i).getName()))
                        helper.getView(R.id.home_contentlist_tv3).setVisibility(View.VISIBLE);
                    else if ("公众号".equals(tags.get(i).getName()))
                        helper.getView(R.id.home_contentlist_tv31).setVisibility(View.VISIBLE);
                }
            }
        } else {
            helper.getView(R.id.home_contentlist_tv3).setVisibility(View.GONE);
            helper.getView(R.id.home_contentlist_tv31).setVisibility(View.GONE);
        }

        //置顶是另外一个url
        if (item.getType() == 1) {
            helper.getView(R.id.home_contentlist_tv1).setVisibility(View.VISIBLE);
        } else
            helper.getView(R.id.home_contentlist_tv1).setVisibility(View.GONE);

        // TODO: 2020/2/12 爱心、分类的点击
//        helper.addOnClickListener(R.id.home_contentlist_iv)
//                .addOnClickListener(R.id.)
    }
}