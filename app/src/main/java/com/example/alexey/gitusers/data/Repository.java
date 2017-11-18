package com.example.alexey.gitusers.data;

import android.util.Log;

import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.data.local.LocalRepository;
import com.example.alexey.gitusers.data.remote.RemoteRepository;
import com.example.alexey.gitusers.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private SchedulerProvider schedulerProvider;

    @Inject
    public Repository(LocalRepository localRepository,
                      RemoteRepository remoteRepository,
                      SchedulerProvider schedulerProvider) {

        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.schedulerProvider = schedulerProvider;
    }

    public Observable<List<User>> fetchUsers(long page) {
        return getLocalAlbumsObservable(page)
                .observeOn(schedulerProvider.io())
                .flatMap(localUsers -> {

                    if (localUsers.size() != 0) {
                        return Observable.fromCallable(() -> localUsers);
                    }

                    return getRemoteUsersObservable(page);
                });
    }

    private Observable<List<User>> getRemoteUsersObservable(long page) {
        return remoteRepository.fetchUsers(page)
                .subscribeOn(schedulerProvider.io())
                .doOnNext(artists -> localRepository.saveUsers(artists))
                .onErrorResumeNext(Observable.empty());
    }

    private Observable<List<User>> getLocalAlbumsObservable(long page) {
        return localRepository.getUsers(page)
                .subscribeOn(schedulerProvider.computation());
    }

}
