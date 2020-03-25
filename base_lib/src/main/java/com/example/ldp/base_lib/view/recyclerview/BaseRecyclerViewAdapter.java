package com.example.ldp.base_lib.view.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ldp.base_lib.view.RecyclerViewHolder;

import java.util.List;

/**
 * created by Da Peng at 2019/9/25
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    public List<T> list;
    public Context content;

    public BaseRecyclerViewAdapter(Context context, List<T> list) {
        this.content = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (getItemLayoutResId() == 0) {
            View emptyView = new View(content);
            emptyView.setVisibility(View.GONE);
            emptyView.setLayoutParams(new FrameLayout.LayoutParams(0, 0));
            return new RecyclerViewHolder(emptyView);
        }

        View view = LayoutInflater.from(content).inflate(getItemLayoutResId(), parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        bindItemViewHolder(holder,position);
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    protected abstract int getItemLayoutResId();

    protected abstract void bindItemViewHolder(RecyclerViewHolder holder, int position);
}
