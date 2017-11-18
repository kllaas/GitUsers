package com.example.alexey.gitusers.di.components;

import com.example.alexey.gitusers.di.PerActivity;
import com.example.alexey.gitusers.di.modules.ActivityModule;
import com.example.alexey.gitusers.ui.user_list.UserListActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(UserListActivity activity);

}
