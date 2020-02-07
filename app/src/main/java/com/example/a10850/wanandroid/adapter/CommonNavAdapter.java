package com.example.a10850.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.NavBean;

import java.util.List;

import mao.com.flexibleflowlayout.TagAdapter;
import mao.com.flexibleflowlayout.TagFlowLayout;

/***
 * 创建时间：2020/2/6 20:38
 * 创建人：10850
 * 功能描述：
 */
public class CommonNavAdapter extends BaseQuickAdapter<NavBean.DataBean, BaseViewHolder> {

    public CommonNavAdapter(int layoutResId, @Nullable List<NavBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, final NavBean.DataBean item) {
        helper.setText(R.id.nav_tv, item.getName());

        TagFlowLayout view = helper.getView(R.id.nav_ff);
        view.setAdapter(new TagAdapter() {
            @Override
            public int getItemCount() {
                return item.getArticles().size();
            }

            @Override
            public View createView(LayoutInflater inflater, ViewGroup parent, int position) {
                return inflater.inflate(R.layout.system_flow_text_layout, parent, false);
            }

            @Override
            public void bindView(View view, int position) {
                TextView textView = view.findViewById(R.id.system_tag_text);
                textView.setText(item.getArticles().get(position).getTitle());
            }
        });

    }
}
