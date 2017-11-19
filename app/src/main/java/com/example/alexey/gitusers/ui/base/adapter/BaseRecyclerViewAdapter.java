package com.example.alexey.gitusers.ui.base.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends BaseViewHolder<T>>
        extends RecyclerView.Adapter<VH> {

    protected OnClick<T> onClick;

    protected List<T> items;

    protected RecyclerView recyclerView;

    public BaseRecyclerViewAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bind(items.get(position));
    }

    public void addItem(T item) {
        items.add(item);

        recyclerView.post(() -> notifyItemInserted(items.size() - 1));
    }

    public void remove(T item) {
        int position = items.indexOf(item);
        if (position < 0) return;

        items.remove(position);
        recyclerView.post(() -> notifyItemRemoved(position));
    }

    public void updateDataSet(List<T> items) {
        this.items = items;
        recyclerView.post(this::notifyDataSetChanged);
    }

    public List<T> getAll() {
        return items;
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

        recyclerView.post(() -> notifyItemRangeInserted(positionToInset, itemsToAdd.size()));
    }

    public void clearAll() {
        items.clear();

        recyclerView.post(this::notifyDataSetChanged);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}