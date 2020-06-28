package com.example.a10850.wanandroid.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/***
 * 创建时间：2020/3/10 10:27
 * 创建人：10850
 * 功能描述：
 */
public class MultCollectEntity implements MultiItemEntity {

    public static final int ART = 1;//文章
    public static final int PRO = 2;//项目

    private int itemType;

    public CollectBean.DataBean.DatasBean getBean() {
        return mBean;
    }

    private CollectBean.DataBean.DatasBean mBean;


    public MultCollectEntity(int itemType, CollectBean.DataBean.DatasBean collectBean) {
        this.itemType = itemType;
        this.mBean = collectBean;
    }

    @Override
    public int getItemType() {
        return itemType;
    }


}
