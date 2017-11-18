package com.example.alexey.gitusers.data.entity.mapper;


import com.example.alexey.gitusers.data.entity.remote.UserRemote;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by alexey
 */

public class UserDeserializer implements JsonDeserializer<UserRemote> {

    @Override
    public UserRemote deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        Type listType = new TypeToken<UserRemote>() {}.getType();

        return new Gson().fromJson(json, listType);
    }

}
