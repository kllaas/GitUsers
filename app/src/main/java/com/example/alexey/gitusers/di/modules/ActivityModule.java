package com.example.alexey.gitusers.di.modules;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.di.PerActivity;
import com.example.alexey.gitusers.ui.user_list.UserListMvpContract;
import com.example.alexey.gitusers.ui.user_list.UserListPresenter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
@PerActivity
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    Context provideContext() {
        return activity;
    }

    @Provides
    UserListMvpContract.Presenter<UserListMvpContract.View> provideMainPresenter(
            UserListPresenter<UserListMvpContract.View> presenter) {
        return presenter;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    LinearLayoutManager provideLayoutManager() {
        return new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    List<User> provideUsers() {
        return new ArrayList<>();
    }

}
