package com.example.alexey.gitusers.data.entity.mapper;


import com.example.alexey.gitusers.data.entity.local.User;
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

public class UserDeserializer implements JsonDeserializer<User> {

    @Override
    public User deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
        Type listType = new TypeToken<User>() {}.getType();

        return new Gson().fromJson(json, listType);
    }

}
