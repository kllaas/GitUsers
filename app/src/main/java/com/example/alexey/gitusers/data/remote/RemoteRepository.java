package com.example.alexey.gitusers.data.remote;

import com.example.alexey.gitusers.BuildConfig;
import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.data.remote.service.GitHubService;
import com.example.alexey.gitusers.data.remote.service.ServiceGenerator;
import com.example.alexey.gitusers.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RemoteRepository implements RemoteSource {

    private ServiceGenerator serviceGenerator;

    private GitHubService gitHubService;

    @Inject
    public RemoteRepository(ServiceGenerator serviceGenerator) {
        this.serviceGenerator = serviceGenerator;

        this.gitHubService = serviceGenerator.createService(GitHubService.class, BuildConfig.BASE_URL);
    }

    @Override
    public Observable<List<User>> fetchUsers(long since) {
        return gitHubService.getUsers(since, Constants.GitHub.DEFAULT_PAGINATION);
    }

}
