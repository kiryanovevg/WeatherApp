package com.kiryanov.weatherapp.di.modules;

import com.kiryanov.weatherapp.data.Api;
import com.kiryanov.weatherapp.data.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Evgeniy on 22.09.18.
 */

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public Repository provideRepository(Api api) {
        return new Repository(api);
    }
}
