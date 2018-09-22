package com.kiryanov.weatherapp.data;

import dagger.Module;

/**
 * Created by Evgeniy on 22.09.18.
 */

@Module
public class Repository {

    private Api api;

    public Repository(Api api) {
        this.api = api;
    }
}
