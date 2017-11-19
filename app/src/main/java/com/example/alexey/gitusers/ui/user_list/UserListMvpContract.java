package com.example.alexey.gitusers.ui.user_list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alexey.gitusers.ui.base.BasePresenter;
import com.example.alexey.gitusers.ui.base.BaseView;
import com.example.alexey.gitusers.ui.user_list.adapter.UsersAdapter;

public interface UserListMvpContract {

    interface View extends BaseView {

    }

    interface Presenter<V extends View> extends BasePresenter<V> {

        void refresh();

        UsersAdapter getAdapter();

        RecyclerView.OnScrollListener getOnScrollListener();

        LinearLayoutManager getLayoutManager();

        long getLastId();

        void setLastId(long lastId);
    }

}
