package com.example.alexey.gitusers.data.local;

import com.example.alexey.gitusers.data.entity.local.User;

import java.util.List;

import io.reactivex.Observable;

public interface LocalSource {

    void saveUsers(List<User> users);

    Observable<List<User>> getUsers(long page);

}
