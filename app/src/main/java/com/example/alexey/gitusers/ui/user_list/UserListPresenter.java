package com.example.alexey.gitusers.ui.user_list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.alexey.gitusers.data.Repository;
import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.ui.base.BasePresenterImpl;
import com.example.alexey.gitusers.ui.user_list.adapter.ScrollListener;
import com.example.alexey.gitusers.ui.user_list.adapter.UsersAdapter;
import com.example.alexey.gitusers.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserListPresenter<V extends UserListMvpContract.View> extends BasePresenterImpl<V>
        implements UserListMvpContract.Presenter<V> {

    private static final int ID_START = 1;

    private boolean isLoading = false;

    private long lastId = ID_START;


    private ScrollListener scrollListener;

    private UsersAdapter adapter;

    @Inject
    UserListPresenter(Repository dataSource, SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable, UsersAdapter adapter) {
        super(dataSource, schedulerProvider, compositeDisposable);

        this.adapter = adapter;
    }

    @Override
    protected void onViewPrepared() {
        scrollListener = new PaginationScrollListener(getView().getLayoutManager());

        loadUsers(firstLoadedCallback);
    }

    private void loadUsers(UserLoadCallback callback) {
        getCompositeDisposable().add(getRepository()
                .fetchUsers(lastId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(callback::onSuccess,
                        throwable -> callback.onFailure(throwable.getMessage())
                ));
    }

    @Override
    public void refresh() {
        lastId = ID_START;

        adapter.clearAll();
        getCompositeDisposable().add(getRepository()
                .refresh(lastId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(users -> firstLoadedCallback.onSuccess(users)));
    }

    private void loadNextPage() {
        loadUsers(new UserLoadCallback() {
            @Override
            public void onSuccess(List<User> users) {
                adapter.removeLoadingFooter();
                isLoading = false;

                if (users == null || users.size() == 0) return;
                lastId = users.get(users.size() - 1).getId() + 1;
                adapter.addItems(users);
            }

            @Override
            public void onFailure(String message) {
                Log.d("onLoadingFailed", message);
            }
        });
    }


    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    public RecyclerView.OnScrollListener getOnScrollListener() {
        return scrollListener;
    }

    private UserLoadCallback firstLoadedCallback = new UserLoadCallback() {
        @Override
        public void onSuccess(List<User> users) {
            if (users == null || users.size() == 0) return;

            lastId = users.get(users.size() - 1).getId() + 1;
            adapter.updateDataSet(users);
        }

        @Override
        public void onFailure(String message) {
            getView().showFailureMessage(message);
        }
    };

    private class PaginationScrollListener extends ScrollListener {

        PaginationScrollListener(LinearLayoutManager layoutManager) {
            super(layoutManager);
        }

        @Override
        protected void loadMoreItems() {
            isLoading = true;

            adapter.addLoadingFooter();
            loadNextPage();
        }

        @Override
        public boolean isLoading() {
            return isLoading;
        }
    }
}
