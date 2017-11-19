package com.example.alexey.gitusers.ui.user_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alexey.gitusers.App;
import com.example.alexey.gitusers.data.Repository;
import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.ui.base.BasePresenterImpl;
import com.example.alexey.gitusers.ui.user_list.adapter.ScrollListener;
import com.example.alexey.gitusers.ui.user_list.adapter.UsersAdapter;
import com.example.alexey.gitusers.utils.rx.SchedulerProvider;
import com.l4digital.support.rxloader.RxLoader;
import com.l4digital.support.rxloader.RxLoaderCallbacks;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class UserListPresenter<V extends UserListMvpContract.View> extends BasePresenterImpl<V>
        implements UserListMvpContract.Presenter<V>, PaginationCallback {

    private static final int ID_START = 1;
    private static final int ID_LOADER = 1;

    private boolean isLoading = false;

    private long lastId = ID_START;


    private ScrollListener scrollListener;

    private UsersAdapter adapter;

    private LinearLayoutManager layoutManager;

    private Context context;


    @Inject
    UserListPresenter(Repository dataSource, SchedulerProvider schedulerProvider,
                      CompositeDisposable compositeDisposable, UsersAdapter adapter,
                      LinearLayoutManager layoutManager, Context context) {
        super(dataSource, schedulerProvider, compositeDisposable);

        this.adapter = adapter;
        this.layoutManager = layoutManager;
        this.scrollListener = new PaginationScrollListener(layoutManager);
        this.context = context;
    }

    @Override
    protected void onViewPrepared() {
        loadUsers();

        adapter.setCallback(this);
    }

    private void loadUsers() {
        Observable<List<User>> requestObservable = getRepository()
                .fetchUsers(lastId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui());

        setUpLoader(requestObservable);
    }

    @Override
    public void refresh() {
        lastId = ID_START;

        adapter.clearAll();

        destroyLoader();
        Observable<List<User>> requestObservable = getRepository()
                .refresh(lastId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui());

        setUpLoader(requestObservable);
    }

    @Override
    public void onRetryClick() {
        destroyLoader();
        loadUsers();
    }

    private void setUpLoader(Observable<List<User>> requestObservable) {
        RxLoader<List<User>> loader = new RxLoader<>(App.appComponent.getContext(), requestObservable);
        RxLoaderCallbacks<List<User>> callbacks = new RxLoaderCallbacks<>(loader);
        if (adapter.isErrorOccurred()) adapter.toggleRetry(false);

        callbacks.getObservable().subscribe(this::onSuccess, throwable -> adapter.toggleRetry(true));

        ((AppCompatActivity) context).getSupportLoaderManager().initLoader(ID_LOADER, Bundle.EMPTY, callbacks);
    }

    private void onSuccess(List<User> users) {
        adapter.removeLoadingFooter();
        isLoading = false;

        if (users == null || users.size() == 0) return;
        lastId = users.get(users.size() - 1).getId() + 1;
        adapter.addItems(users);

        destroyLoader();
    }

    private void destroyLoader() {
        ((AppCompatActivity) context).getSupportLoaderManager().destroyLoader(ID_LOADER);
    }

    @Override
    public UsersAdapter getAdapter() {
        return adapter;
    }

    @Override
    public RecyclerView.OnScrollListener getOnScrollListener() {
        return scrollListener;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    @Override
    public long getLastId() {
        return lastId;
    }

    @Override
    public void setLastId(long lastId) {
        this.lastId = lastId;
    }

    private class PaginationScrollListener extends ScrollListener {

        PaginationScrollListener(LinearLayoutManager layoutManager) {
            super(layoutManager);
        }

        @Override
        protected void loadMoreItems() {
            isLoading = true;

            if (!adapter.isErrorOccurred())
                adapter.addLoadingFooter();
            loadUsers();
        }

        @Override
        public boolean isLoading() {
            return isLoading;
        }
    }
}
