package com.example.a10850.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.ContentBean;

import java.util.List;

/***
 * 创建时间：2020/2/12 22:09
 * 创建人：10850
 * 功能描述：
 */
public class ListItemAdapter extends BaseQuickAdapter<ContentBean, BaseViewHolder> {

    public ListItemAdapter(int layoutResId, @Nullable List<ContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ContentBean item) {

        if (!TextUtils.isEmpty(item.getAuthor()))
            helper.setText(R.id.list_author, item.getAuthor());
        else
            helper.setText(R.id.list_author, item.getShareUser());

        if (item.isCollect()) {
            helper.setImageResource(R.id.list_collect, R.drawable.heart_red);
        } else {
            helper.setImageResource(R.id.list_collect, R.drawable.heart_gray);
        }

        helper.setText(R.id.list_title, item.getTitle())
                .setText(R.id.list_link1, item.getSuperChapterName())
                .setText(R.id.list_link2, item.getChapterName())
                .setText(R.id.list_time, item.getNiceDate());
    }
}
