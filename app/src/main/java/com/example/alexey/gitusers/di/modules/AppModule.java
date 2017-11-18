package com.example.alexey.gitusers.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.alexey.gitusers.BuildConfig;
import com.example.alexey.gitusers.data.entity.mapper.UserDeserializer;
import com.example.alexey.gitusers.data.entity.remote.UserRemote;
import com.example.alexey.gitusers.data.local.AppDatabase;
import com.example.alexey.gitusers.data.local.UserDAO;
import com.example.alexey.gitusers.utils.network.NetworkUtils;
import com.example.alexey.gitusers.utils.rx.AppSchedulerProvider;
import com.example.alexey.gitusers.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AppModule {

    private Application application;
    private AppDatabase appDatabase;

    public AppModule(Application application) {
        this.application = application;

        appDatabase = Room.databaseBuilder(application, AppDatabase.class, "gitusers-db").build();
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    SchedulerProvider provideAppSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(UserRemote.class, new UserDeserializer())
                .create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder().build();
    }

    @Singleton
    @Provides
    AppDatabase provideRoomDatabase() {
        return appDatabase;
    }

    @Singleton
    @Provides
    UserDAO provideArtistsDao(AppDatabase db) {
        return db.artistsDAO();
    }

    @Singleton
    @Provides
    NetworkUtils provideNetworkUtils() {
        return new NetworkUtils(application);
    }

}
