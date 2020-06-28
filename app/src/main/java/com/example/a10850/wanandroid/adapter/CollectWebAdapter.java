package com.example.a10850.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.UsedWebBean;

import java.util.List;

/***
 * 创建时间：2020/3/11 9:26
 * 创建人：10850
 * 功能描述：
 */
public class CollectWebAdapter extends BaseQuickAdapter<UsedWebBean.DataBean, BaseViewHolder> {
    public CollectWebAdapter(int layoutResId, @Nullable List<UsedWebBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UsedWebBean.DataBean item) {
        helper.setText(R.id.cw_tv_title, item.getName())
                .setText(R.id.cw_tv_link, item.getLink())
                .addOnClickListener(R.id.cw_iv_edit);
    }
}
