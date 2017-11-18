package com.example.alexey.gitusers.ui.user_list;

import android.support.v4.widget.NestedScrollView;
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

    private static final int PAGE_START = 1;

    private boolean isLoading = false;

    private long lastId = PAGE_START;


    private ScrollListener scrollListener;

    private UsersAdapter adapter;

    @Inject
    public UserListPresenter(Repository dataSource, SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable, UsersAdapter adapter) {
        super(dataSource, schedulerProvider, compositeDisposable);

        this.adapter = adapter;
    }

    @Override
    protected void onViewPrepared() {
        scrollListener = new PaginationScrollListener();

        loadUsers(new UserLoadCallback() {
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
        });
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
    public NestedScrollView.OnScrollChangeListener getOnScrollListener() {
        return scrollListener;
    }

    private class PaginationScrollListener extends ScrollListener {

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
