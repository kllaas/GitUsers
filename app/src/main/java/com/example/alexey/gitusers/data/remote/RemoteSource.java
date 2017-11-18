package com.example.alexey.gitusers.data.remote;


import com.example.alexey.gitusers.data.entity.local.User;

import java.util.List;

import io.reactivex.Observable;

public interface RemoteSource {

    Observable<List<User>> fetchUsers(long page);

}
