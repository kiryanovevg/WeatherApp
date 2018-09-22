package com.kiryanov.weatherapp;

import android.support.multidex.MultiDexApplication;

import com.kiryanov.weatherapp.di.AppComponent;
import com.kiryanov.weatherapp.di.DaggerAppComponent;
import com.kiryanov.weatherapp.di.modules.NetworkModule;
import com.kiryanov.weatherapp.di.modules.RepositoryModule;

/**
 * Created by Evgeniy on 22.09.18.
 */

public class BaseApplication extends MultiDexApplication {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private AppComponent initAppComponent() {
        return DaggerAppComponent.builder()
                .networkModule(new NetworkModule(getString(R.string.base_url)))
                .repositoryModule(new RepositoryModule())
                .build();
    }
}
