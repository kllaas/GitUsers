package com.example.alexey.gitusers.data.remote.service;

import com.example.alexey.gitusers.data.entity.local.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alexey
 */

public interface GitHubService {

    @GET("/users")
    Observable<List<User>> getUsers(@Query("since") long page,
                                    @Query("per_page") int perPage);

}
