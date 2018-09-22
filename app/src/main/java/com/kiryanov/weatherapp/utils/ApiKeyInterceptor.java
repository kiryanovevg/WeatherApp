package com.kiryanov.weatherapp.utils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Evgeniy on 22.09.18.
 */

public class ApiKeyInterceptor implements Interceptor {

    private String key;

    public ApiKeyInterceptor(String key) {
        this.key = key;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("appid", key)
                .addQueryParameter("lang", "ru")
                .build();

        return chain.proceed(original.newBuilder().url(url).build());
    }
}
