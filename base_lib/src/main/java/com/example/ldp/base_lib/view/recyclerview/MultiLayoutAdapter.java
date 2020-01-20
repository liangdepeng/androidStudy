package com.example.ldp.base_lib.view.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.RecyclerView;

import com.example.ldp.base_lib.view.MyRecyclerViewViewHolder;

import java.util.List;

/**
 * created by Da Peng at 2019/8/1
 */
public abstract class MultiLayoutAdapter<T> extends RecyclerView.Adapter<MyRecyclerViewViewHolder> {

    private List<T> datas;
    private Context mContext;
    private int layoutResId;
    private MultiLayoutType<T> mMultiLayoutType;

    public MultiLayoutAdapter(Context context, List<T> datas, int layoutResId, MultiLayoutType<T> multiLayoutType) {
        this.datas = datas;
        this.mContext = context;
        this.layoutResId = layoutResId;
        this.mMultiLayoutType = multiLayoutType;
        //        if (!(mContext instanceof Activity))
        //            throw new RuntimeException("mContext must instance of BaseActivity.");
    }

    @NonNull
    @Override
    public MyRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mMultiLayoutType != null) {
            layoutResId = viewType;
        }

        if (layoutResId == 0) {
            View view = new View(mContext);
            view.setVisibility(View.GONE);
            view.setLayoutParams(new FrameLayout.LayoutParams(0, 0));
            return new MyRecyclerViewViewHolder(view);
        }

        View itemView = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
        return new MyRecyclerViewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewViewHolder holder, final int position) {
        onBindMyViewHolder(holder, datas.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongItemClick(v, position);
                return true;
            }
        });
    }

    protected abstract void onLongItemClick(View view, int position);

    protected abstract void onItemClick(View view, int position);

    protected abstract void onBindMyViewHolder(MyRecyclerViewViewHolder holder, T item, int position);

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiLayoutType != null) {
            return mMultiLayoutType.getLayoutResType(datas.get(position), position);
        }
        return super.getItemViewType(position);
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

}
