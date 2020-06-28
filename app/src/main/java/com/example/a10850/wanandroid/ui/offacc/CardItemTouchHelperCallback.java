package com.example.a10850.wanandroid.ui.offacc;

/***
 * 创建时间：2019/9/8 14:30
 * 创建人：10850
 * 功能描述：卡片布局管理器
 */

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.List;


public class CardItemTouchHelperCallback<T> extends ItemTouchHelper.Callback {

    private final RecyclerView.Adapter adapter;
    private List<T> dataList;
    private OnSwipeListener<T> mListener;

    public CardItemTouchHelperCallback(@NonNull RecyclerView.Adapter adapter, @NonNull List<T> dataList) {
        this.adapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
    }

    public CardItemTouchHelperCallback(@NonNull RecyclerView.Adapter adapter, @NonNull List<T> dataList, OnSwipeListener<T> listener) {
        this.adapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
        this.mListener = listener;
    }

    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    public void setOnSwipedListener(OnSwipeListener<T> mListener) {
        this.mListener = mListener;
    }

    /***
     * 设置滑动类型标记
     * 移动方向 滑动方向（向左和向右）
     * @param recyclerView
     * @param viewHolder
     * @return 返回一个整数类型的标识，用于判断Item那种移动行为是允许的
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof CardLayoutManager) {
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 拖拽切换Item的回调
     * 因为在上面我们对于 dragFlags 配置的是 0 ，
     * 所以在 onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) 中直接返回 false 即可。
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return 如果Item切换了位置，返回true；反之，返回false
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    /***
     * 滑动删除Item（滑动监听）
     * @param viewHolder
     * @param direction
     * Item滑动的方向
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // 移除之前设置的 onTouchListener, 否则触摸滑动会乱了
        viewHolder.itemView.setOnTouchListener(null);
        // 删除相对应的数据
        int layoutPosition = viewHolder.getLayoutPosition();
//        Log.i("zxd", "onSwiped: " + layoutPosition);
//        Log.i("zxd", "datalist: " + dataList.size());
//        Log.i("zxd", "onSwiped: " + (adapter instanceof OffAccAdapter));
        T remove = dataList.remove(layoutPosition);
        adapter.notifyDataSetChanged();
        // 卡片滑出后回调 OnSwipeListener 监听器
        if (mListener != null) {
            mListener.onSwiped(viewHolder, remove, direction == ItemTouchHelper.LEFT ? CardConfig.SWIPED_LEFT : CardConfig.SWIPED_RIGHT);
        }
        // 当没有数据时回调 OnSwipeListener 监听器
        if (adapter.getItemCount() == 0) {
            if (mListener != null) {
                mListener.onSwipedClear();
            }
        }
    }

    /***
     * Item是否支持滑动
     * 为了防止第二层和第三层卡片也能滑动，因此我们需要设置 isItemViewSwipeEnabled() 返回 false
     * @return true  支持滑动操作
     *          false 不支持滑动操作
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /***
     * 子view绘制
     * @param c 画布
     * @param recyclerView
     * @param viewHolder
     * @param dX X轴移动的距离
     * @param dY Y轴移动的距离
     * @param actionState 当前Item的状态
     * @param isCurrentlyActive 如果当前被用户操作为true，反之为false
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        Log.i("zxd", "onChildDraw: ");
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // 得到滑动的阀值
            //以中心点为坐标，dx的偏移量区间{-屏幕宽度，+屏幕宽度}
            //以6.0的模拟器，size:768*1280，向左{-768,0},向右:{0,768},最大值是2，最小值是-2
            float ratio = dX / getThreshold(recyclerView, viewHolder);
            Log.i("MainActivity", "x: " + dX);
            Log.i("MainActivity", "gudingzhi: " + getThreshold(recyclerView, viewHolder));
            Log.i("MainActivity", "ratio: " + ratio);
            // ratio 最大为 1 或 -1,目的是为了缩放比例在为-1和1时，后面可见的图都不在变化了
            if (ratio > 1) {
                ratio = 1;
            } else if (ratio < -1) {
                ratio = -1;
            }
            // 默认最大的旋转角度为 15 度
            itemView.setRotation(ratio * CardConfig.DEFAULT_ROTATE_DEGREE);
            //recyclerView的子view数量
            int childCount = recyclerView.getChildCount();
            // 当数据源个数大于最大显示数时
            if (childCount > CardConfig.DEFAULT_SHOW_ITEM) {
                //用childCount=4推算下
                //position  index
                //1 2   第3张 x、y轴0.8+ratio*0.1 向y轴负轴偏移 2-ratio*getMeasuredHeight/14
                //2 1   第2张 x、y轴0.9+ratio*0.1 向y轴负轴偏移 1-ratio*getMeasuredHeight/14
                for (int position = 1; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    // 和之前 onLayoutChildren 是一个意思，不过是做相反的动画
                    view.setScaleX(1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                }
            } else {
                // 当数据源个数小于或等于最大显示数时
                //用childCount=3推算下
                //position  index
                //0 2   第3张 x、y轴0.8+ratio*0.1 向y轴负轴偏移 2-ratio*getMeasuredHeight/14
                //1 1   第2张 x、y轴0.9+ratio*0.1 向y轴负轴偏移 1-ratio*getMeasuredHeight/14
                for (int position = 0; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - index * CardConfig.DEFAULT_SCALE + Math.abs(ratio) * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                }
            }
            // 回调监听器
            if (mListener != null) {
                if (ratio != 0) {
                    mListener.onSwiping(viewHolder, ratio, ratio < 0 ? CardConfig.SWIPING_LEFT : CardConfig.SWIPING_RIGHT);
                } else {
                    mListener.onSwiping(viewHolder, ratio, CardConfig.SWIPING_NONE);
                }
            }
        }
    }

    /***
     * getSwipeThreshold(viewHolder)=0.5f
     * @param recyclerView
     * @param viewHolder
     * @return recyclerView的一半宽度
     */
    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

    /**
     * 用户操作完毕或者动画完毕后会被调用
     * 第一层的卡片滑出去之后第二层的就莫名其妙地偏了。这正是因为 Item View 重用机制“捣鬼”。
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        // 操作完毕后恢复旋转角度为0
        viewHolder.itemView.setRotation(0f);
    }


}
