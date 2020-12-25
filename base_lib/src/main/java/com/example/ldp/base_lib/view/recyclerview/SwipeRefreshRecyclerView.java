package com.example.ldp.base_lib.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ldp.base_lib.R;

/**
 * created by Da Peng ( SwipeRefreshLayout + RecyclerView )
 */
public class SwipeRefreshRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private onRefreshListener onRefreshListener;

    public SwipeRefreshRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * RecyclerView等可滑动控件默认的是会有滚动条以及滑动到边缘时的阴影（光晕）效果的，那么怎样去掉这两个默认属性呢，在这里简单的介绍一下滚动条效果
     * 1、通过xml文件设置
     * android:scrollbars=”“有三个属性
     * a.none:去掉滚动条
     * b.horizontal:设置水平的滚动条
     * c.vertical:设置垂直的滚动条
     * 2、通过java代码设置
     * a.RecyclerView.setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled);
     * b.RecyclerView.setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled);
     * 设置为true时有相应的滚动条，为false时无相应的滚动条 滚动到边缘的光晕效果
     * 1、通过xml文件设置
     * android:overScrollMode=”“同样有三个属性
     * a.none:去掉光晕效果
     * b.always:设置总是出现光晕效果
     * c.ifContentScrolls:设置此模式，如果recycleview里面的内容可以滑动，那么滑到边界后继续滑动会出现弧形光晕；如果recycleview里面的内容不可以滑动，那么滑到边界后继续滑动不会出现弧形光晕
     * 2、通过java代码设置
     * a.RecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER)同xml设置为none
     * b.RecyclerView.setOverScrollMode(View.OVER_SCROLL_ALWAYS)同xml设置为always
     * c.RecyclerView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS)同xml设置为ifContentScrolls 同时去掉滚动条和默认的光晕效果的完整xml代码。
     *
     * @param context content
     */
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.recycler_swiperefresh_view, this, true);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_IF_CONTENT_SCROLLS);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //** 防止swipeRefreshLayout下拉和recyclerView下拉冲突，recyclerView必须滑到顶部才可以触发下拉刷新 **  //
                int topPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topPosition >= 0);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initRefreshData();
    }

    @Override
    public void onRefresh() {
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh(true);
        }
    }

    public void setOnRefreshListener(onRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void initRefreshData() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    public void setRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        mRecyclerView.setItemAnimator(itemAnimator);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecyclerView.addItemDecoration(itemDecoration, index);
    }

    /**
     *  设置是否正在刷新
     * @param refreshing 刷新状态
     */
    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    public SwipeRefreshLayout getMySwipeRefreshLayout() {
        return this.mSwipeRefreshLayout;
    }

    /**
     *  recyclerView 滚动条
     * @param scrollBarEnable 是否显示
     */
    public void setScrollBarEnable(boolean scrollBarEnable) {
        mRecyclerView.setVerticalScrollBarEnabled(scrollBarEnable);
    }

    public RecyclerView getMyRecyclerView() {
        return this.mRecyclerView;
    }

    /**
     * setProgressViewEndTarget (boolean scale, int end)方法有两个参数，
     * 参数scale设置为true，则下拉过程，会自动缩放；参数end是下拉刷新的高度，也就是我们需要的参数，通过改变它的值，就能改变下拉刷新高度。
     * @param scale 自动缩放
     * @param end 下拉刷新的高度
     */
    public void setProgressViewEndTarget(boolean scale, int end) {
        mSwipeRefreshLayout.setProgressViewEndTarget(scale, end);
    }

    /**
     * scale设置为true，则下拉过程，控件会自动缩放；参数start是下拉刷新控件的起始位置，参数end是下拉刷新的最大高度；
     * 通过改变start和end的值，就能改变下拉刷的具体位置和高度
     * @param scale 自动缩放
     * @param start 下拉刷新控件的起始位置
     * @param end   下拉刷新的最大高度
     */
    public void setProgressViewOffset(boolean scale, int start, int end) {
        mSwipeRefreshLayout.setProgressViewOffset(scale, start, end);
    }

    /**
     * 用来设置手指在屏幕下拉多少距离才会触发SwipeRefreshLayout控件的刷新动画效果；setDistanceToTriggerSync(int distance)参数的distance就是手指下拉的具体高度值
     * @param distance 手指下拉的具体高度值
     */
    public void setProgressDistanceToTriggerSync(int distance) {
        mSwipeRefreshLayout.setDistanceToTriggerSync(distance);
    }
}
