package com.example.alexey.gitusers.ui.user_list;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;

import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.ui.base.BasePresenter;
import com.example.alexey.gitusers.ui.base.BaseView;

import java.util.List;

public interface UserListMvpContract {

    interface View extends BaseView {

        void showFailureMessage(String message);

    }

    interface Presenter<V extends View> extends BasePresenter<V> {

        void refresh();

        RecyclerView.Adapter getAdapter();

        NestedScrollView.OnScrollChangeListener getOnScrollListener();

    }

}
