package com.example.alexey.gitusers.ui.base.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.alexey.gitusers.data.entity.local.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseRecyclerViewAdapter<T, VH extends BaseViewHolder<T>>
        extends RecyclerView.Adapter<VH> {

    protected OnClick<T> onClick;

    protected List<T> items;

    public BaseRecyclerViewAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bind(items.get(position));
    }

    public void addItem(T item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void remove(T item) {
        int position = items.indexOf(item);
        if (position < 0) return;

        items.remove(position);
        notifyItemRemoved(position);
    }

    public void updateDataSet(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClick(OnClick<T> onItemClick) {
        this.onClick = onItemClick;
    }

    public void addItems(List<T> itemsToAdd) {
        int positionToInset = getItemCount() + 1;

        items.addAll(itemsToAdd);
        notifyItemRangeInserted(positionToInset, itemsToAdd.size());
    }
}