package com.example.alexey.gitusers.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.data.entity.mapper.UserDeserializer;
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
@Singleton
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
    SchedulerProvider provideAppSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(User.class, new UserDeserializer())
                .create();
    }

    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder().build();
    }

    @Provides
    AppDatabase provideRoomDatabase() {
        return appDatabase;
    }

    @Provides
    UserDAO provideArtistsDao(AppDatabase db) {
        return db.usersDAO();
    }

    @Provides
    NetworkUtils provideNetworkUtils() {
        return new NetworkUtils(application);
    }

}
