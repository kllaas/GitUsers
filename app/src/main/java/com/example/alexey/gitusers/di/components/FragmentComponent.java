package com.example.alexey.gitusers.di.components;

import com.example.alexey.gitusers.di.PerFragment;
import com.example.alexey.gitusers.di.modules.FragmentModule;
import com.example.alexey.gitusers.ui.user_list.UserListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(UserListFragment activity);

}
