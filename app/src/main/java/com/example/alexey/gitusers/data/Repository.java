package com.example.alexey.gitusers.data;

import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.data.local.LocalRepository;
import com.example.alexey.gitusers.data.remote.RemoteRepository;
import com.example.alexey.gitusers.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


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

    public Observable<List<User>> fetchUsers(long since) {
        return getLocalUsersObservable(since)
                .observeOn(schedulerProvider.io())
                .flatMap(localUsers -> {

                    if (localUsers.size() != 0) {
                        return Observable.fromCallable(() -> localUsers);
                    }

                    return getRemoteUsersObservable(since);
                });
    }

    private Observable<List<User>> getRemoteUsersObservable(long since) {
        return remoteRepository.fetchUsers(since)
                .subscribeOn(schedulerProvider.io())
                .doOnNext(artists -> localRepository.saveUsers(artists));
    }

    private Observable<List<User>> getLocalUsersObservable(long since) {
        return localRepository.getUsers(since)
                .subscribeOn(schedulerProvider.computation());
    }

    public Observable<List<User>> refresh(long since) {
        return Observable.fromCallable(() -> {
            localRepository.deleteAll();
            return since;
        }).observeOn(schedulerProvider.io())
                .flatMap(var -> getRemoteUsersObservable(since));
    }
}
