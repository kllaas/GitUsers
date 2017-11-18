package com.example.alexey.gitusers.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.alexey.gitusers.data.entity.local.User;

import java.util.List;

/**
 * Created by alexey
 */

@Dao
public interface UserDAO {

    @Query("SELECT * FROM users WHERE id >= :offset LIMIT :count")
    List<User> getUsersWithOffset(long offset, int count);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> items);

    @Query("DELETE FROM users")
    void deleteAll();
}
