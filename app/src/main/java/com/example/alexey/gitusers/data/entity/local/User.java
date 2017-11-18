package com.example.alexey.gitusers.data.entity.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.alexey.gitusers.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = Constants.DataBase.USERS_DB_NAME)
public class User implements Serializable {

    @PrimaryKey
    private long id;

    private String login;

    @SerializedName("avatar_url")
    private String urlToImage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

}
