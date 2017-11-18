package com.example.alexey.gitusers.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.alexey.gitusers.data.entity.local.User;

/**
 * Created by alexey
 */

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO artistsDAO();

}