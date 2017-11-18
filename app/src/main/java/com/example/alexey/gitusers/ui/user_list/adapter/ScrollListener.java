package com.example.alexey.gitusers.ui.user_list.adapter;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class ScrollListener implements NestedScrollView.OnScrollChangeListener {

    @Override
    public void onScrollChange(NestedScrollView view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (isLoading() || view.getChildAt(view.getChildCount() - 1) == null) return;

        if ((scrollY >= (view.getChildAt(view.getChildCount() - 1).getMeasuredHeight() / 4 - view.getMeasuredHeight())) &&
                scrollY > oldScrollY) {

            loadMoreItems();
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLoading();

}
