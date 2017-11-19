package com.example.alexey.gitusers.di.modules;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.di.PerFragment;
import com.example.alexey.gitusers.ui.user_list.UserListMvpContract;
import com.example.alexey.gitusers.ui.user_list.UserListPresenter;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
@PerFragment
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    Context provideContext() {
        return fragment.getContext();
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
        return new LinearLayoutManager(fragment.getContext(), LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    List<User> provideUsers() {
        return new ArrayList<>();
    }

}
