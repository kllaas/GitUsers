package com.example.alexey.gitusers.ui.base;

import com.example.alexey.gitusers.data.Repository;
import com.example.alexey.gitusers.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by alexey
 */

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    private Repository dataSource;
    private SchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    protected V view;

    public BasePresenterImpl(Repository dataSource, SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        this.dataSource = dataSource;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void takeView(V view) {
        this.view = view;
        onViewPrepared();
    }

    @Override
    public void onDispose() {
        view = null;
    }

    protected abstract void onViewPrepared();

    public Repository getRepository() {
        return dataSource;
    }

    protected SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public V getView() {
        return view;
    }

}
