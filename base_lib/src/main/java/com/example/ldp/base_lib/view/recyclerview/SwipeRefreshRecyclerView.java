package com.example.ldp.base_lib.view.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

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
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_swiperefresh_view, null, false);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_IF_CONTENT_SCROLLS);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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

    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    public SwipeRefreshLayout getMySwipeRefreshLayout() {
        return this.mSwipeRefreshLayout;
    }

    public void setScrollBarEnable(boolean scrollBarEnable) {
        mRecyclerView.setVerticalScrollBarEnabled(scrollBarEnable);
    }

    public RecyclerView getMyRecyclerView() {
        return this.mRecyclerView;
    }
}
