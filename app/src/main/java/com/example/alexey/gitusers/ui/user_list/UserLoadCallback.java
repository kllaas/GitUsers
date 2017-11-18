package com.example.alexey.gitusers.ui.user_list;

import com.example.alexey.gitusers.data.entity.local.User;

import java.util.List;

public interface UserLoadCallback {

    void onSuccess(List<User> users);

    void onFailure(String message);

}
