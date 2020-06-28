package com.example.a10850.wanandroid.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.CollectBean;
import com.example.a10850.wanandroid.entity.MultCollectEntity;
import com.example.a10850.wanandroid.widget.ZQImageViewRoundOval;

import java.util.List;

/***
 * 创建时间：2020/3/10 10:12
 * 创建人：10850
 * 功能描述：
 */
public class CollectAdapter extends BaseMultiItemQuickAdapter<MultCollectEntity, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CollectAdapter(List<MultCollectEntity> data) {
        super(data);
        addItemType(MultCollectEntity.ART, R.layout.home_contentlist_item);
        addItemType(MultCollectEntity.PRO, R.layout.project_list_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultCollectEntity item) {
        CollectBean.DataBean.DatasBean bean = item.getBean();
        switch (helper.getItemViewType()) {
            case MultCollectEntity.ART:
                //分类
                helper.setText(R.id.home_contentlist_category, "分类：" + bean.getChapterName());
                //分享人
                helper.setText(R.id.home_contentlist_tv4, "作者：" + bean.getAuthor());
                //时间+标题
                helper.setText(R.id.home_contentlist_tv5, "时间：" + bean.getNiceDate());
                helper.setText(R.id.home_contentlist_title, Html.fromHtml(bean.getTitle()).toString());
                break;
            case MultCollectEntity.PRO:
                ZQImageViewRoundOval iv = helper.getView(R.id.project_list_iv);
                iv.setType(ZQImageViewRoundOval.TYPE_ROUND);
                iv.setRoundRadius(16);
                Glide.with(mContext).load(bean.getEnvelopePic()).into(iv);

                helper.setText(R.id.project_list_author, bean.getAuthor())
                        .setText(R.id.project_list_title, bean.getTitle())
                        .setText(R.id.project_list_desc, Html.fromHtml(bean.getDesc()).toString())
                        .setText(R.id.project_list_time, bean.getNiceDate());

                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.user);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                TextView view = helper.getView(R.id.project_list_author);
                view.setCompoundDrawables(drawable, null, null, null);

                helper.addOnClickListener(R.id.project_list_chaptername);
                break;
        }
    }
}
