package com.example.alexey.gitusers.data.local;


import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocalRepository implements LocalSource {

    private AppDatabase database;

    @Inject
    public LocalRepository(AppDatabase database) {
        this.database = database;
    }

    @Override
    public void saveUsers(List<User> users) {
        database.usersDAO().insertAll(users);
    }

    @Override
    public Observable<List<User>> getUsers(long idOffset) {
        return Observable.fromCallable(() ->
                database.usersDAO().getUsersWithOffset(idOffset, Constants.GitHub.DEFAULT_PAGINATION));
    }

    @Override
    public void deleteAll() {
        database.usersDAO().deleteAll();
    }

}
