package com.kiryanov.weatherapp.di;

import com.kiryanov.weatherapp.di.modules.NetworkModule;
import com.kiryanov.weatherapp.di.modules.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Evgeniy on 22.09.18.
 */

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class})
public interface AppComponent {
}
