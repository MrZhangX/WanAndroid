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
import com.example.a10850.wanandroid.entity.SystemTreeBean;

import java.util.ArrayList;
import java.util.List;

import mao.com.flexibleflowlayout.TagAdapter;
import mao.com.flexibleflowlayout.TagFlowLayout;

/***
 * 创建时间：2020/2/4 19:34
 * 创建人：10850
 * 功能描述：
 */
public class SystemTreeAdapter extends BaseQuickAdapter<SystemTreeBean.DataBean, BaseViewHolder> {

    private List<String> dataList;

    public SystemTreeAdapter(int layoutResId, @Nullable List<SystemTreeBean.DataBean> data) {
        super(layoutResId, data);
        dataList = new ArrayList<>();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SystemTreeBean.DataBean item) {

        helper.setText(R.id.system_item_title, item.getName());
        dataList.clear();
        for (int i = 0; i < item.getChildren().size(); i++) {
            dataList.add(item.getChildren().get(i).getName());
        }

        TagFlowLayout tagFlowLayout = helper.getView(R.id.system_tag);
        tagFlowLayout.setAdapter(new TagAdapter() {
            @Override
            public int getItemCount() {
                return dataList.size();
            }

            @Override
            public View createView(LayoutInflater inflater, ViewGroup parent, int position) {
                return inflater.inflate(R.layout.system_flow_text_layout, parent, false);
            }

            @Override
            public void bindView(View view, int position) {
                TextView textView = view.findViewById(R.id.system_tag_text);
                textView.setText(dataList.get(position));
            }
        });
    }
}
