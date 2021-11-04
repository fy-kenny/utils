package com.example.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public final class JsonUtils {

    private JsonUtils() {}

    public final static Gson GSON = new Gson();

    public final static Gson GSON_CSHARP = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String jsonString, Class<T> classOfT) {
        return GSON.fromJson(jsonString, classOfT);
    }

    public static String toJson4CSharp(Object object) {
        return GSON_CSHARP.toJson(object);
    }

    public static <T> T fromJson4CSharp(String jsonString, Class<T> classOfT) {
        return GSON_CSHARP.fromJson(jsonString, classOfT);
    }

}
