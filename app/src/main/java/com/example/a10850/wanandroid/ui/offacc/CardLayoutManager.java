package com.example.a10850.wanandroid.ui.offacc;

import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/***
 * 创建时间：2019/9/6 21:58
 * 创建人：10850
 * 功能描述：https://github.com/yuqirong/CardSwipeLayout
 * https://yuqirong.me/2017/03/05/玩转仿探探卡片式滑动效果/
 * https://www.ctolib.com/fashare2015-StackLayout.html
 * https://www.baidu.com/s?tn=80035161_2_dg&wd=yuqirong%2FCardSwipeLayout
 * // TODO: 2019/9/8 需要研究下 getWidth、getDecoratedMeasuredWidth、getMeasuredHeight的区别与使用
 */
public class CardLayoutManager extends RecyclerView.LayoutManager {

    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;

    public CardLayoutManager(@NonNull RecyclerView recyclerView, @NonNull ItemTouchHelper itemTouchHelper) {
        this.mRecyclerView = checkIsNull(recyclerView);
        this.mItemTouchHelper = checkIsNull(itemTouchHelper);
    }

    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    /**
     * 描述：需要提供一个默认的LayoutParams，为我们的每个ItemView提供默认的LayoutParams，
     * 所以它能够直接影响到我们的布局效果，这里我们设置成WRAP_CONTENT，让ItemView获得决定权。
     *
     * @return
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    /**
     * view布局
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
        detachAndScrapAttachedViews(recycler);
        //item总和
        int itemCount = getItemCount();
        // 当数据源个数大于最大显示数时,可见的view
        if (itemCount > CardConfig.DEFAULT_SHOW_ITEM) {
            for (int position = CardConfig.DEFAULT_SHOW_ITEM; position >= 0; position--) {
                //从缓存里面取出view
                final View view = recycler.getViewForPosition(position);
                Log.e("zxd", "itemCount: " + itemCount);
                Log.e("zxd", "onLayoutChildren: " + position);
                //将View加入到RecyclerView中
                addView(view);
                //对子View进行测量
                measureChildWithMargins(view, 0, 0);
                //把宽高拿到
                //getDecoratedMeasuredWidth得到的宽度包含ItemDecorate的尺寸
                //getDecoratedMeasuredHeight得到的宽度包含ItemDecorate的尺寸
                // 所以 widthSpace 就是除了 Item View 剩余的值
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // recyclerview 布局，将子view进行布局
                // 在这里默认布局是放在 RecyclerView 中心
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));
                // 其实屏幕上有四张卡片，但是我们把第三张和第四张卡片重叠在一起，这样看上去就只有三张
                // 第四张卡片主要是为了保持动画的连贯性

                if (position == CardConfig.DEFAULT_SHOW_ITEM) {
                    // 按照一定的规则缩放，并且偏移Y轴。
                    // CardConfig.DEFAULT_SCALE 默认为0.1f，CardConfig.DEFAULT_TRANSLATE_Y 默认为14
                    //当position为3时是第4张图片，x、y缩放比例是0.8，y轴偏移量是2*getMeasuredHeight()/14
                    view.setScaleX(1 - (position - 1) * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - (position - 1) * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY((position - 1) * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else if (position > 0) {
                    //当position为2是第3张图片，x、y缩放比例是0.8，y轴偏移量是2*getMeasuredHeight()/14
                    //当position为1是第2张图片，x、y缩放比例是0.9，y轴偏移量是1*getMeasuredHeight()/14
                    //当position为0是第1张图片，不走这个
                    view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    // 设置 mTouchListener 的意义就在于我们想让处于顶层的卡片是可以随意滑动的
                    // 而第二层、第三层等等的卡片是禁止滑动的
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        } else {
            // 当数据源个数小于或等于最大显示数时（设置的是3，默认的就是3、2、1张图片。假设数量是3，进行如下）
            for (int position = itemCount - 1; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // recyclerview 布局
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));

                if (position > 0) {
                    //当position为2是第3张图片，x、y缩放比例是0.8，y轴偏移量是2*getMeasuredHeight()/14
                    //当position为1是第2张图片，x、y缩放比例是0.9，y轴偏移量是1*getMeasuredHeight()/14
                    //当position为0是第1张图片，不走这个
                    view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }

    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            // 把触摸事件交给 mItemTouchHelper，让其处理卡片滑动事件
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mItemTouchHelper.startSwipe(childViewHolder);
            }
            return false;
        }
    };
}
