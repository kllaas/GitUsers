package com.example.alexey.gitusers.di.components;

import android.app.Application;

import com.example.alexey.gitusers.App;
import com.example.alexey.gitusers.data.Repository;
import com.example.alexey.gitusers.di.modules.AppModule;
import com.example.alexey.gitusers.di.modules.FragmentModule;
import com.example.alexey.gitusers.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, FragmentModule.class})
public interface ApplicationComponent {

    void inject(App app);

    Repository getDataSource();

    SchedulerProvider getSchedulerProvider();

    Application getContext();

}
