package com.example.ldp.base_lib.view.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ldp.base_lib.base.ViewHolder;

import java.util.List;

/**
 * created by Da Peng at 2019/8/1
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    protected List<T> dataList;
    protected Context mContext;

    protected MyBaseAdapter(Context context, List<T> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(getItemLayoutRes(), parent, false);
            viewHolder = ViewHolder.init(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindView(viewHolder, dataList.get(position), position, convertView, parent);

        return convertView;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    protected abstract void bindView(ViewHolder viewHolder, T item, int position, View convertView, ViewGroup parent);

    protected abstract int getItemLayoutRes();
}
