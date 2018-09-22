package com.kiryanov.weatherapp;

import android.content.Context;
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
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = initAppComponent();
        context = this;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private AppComponent initAppComponent() {
        return DaggerAppComponent.builder()
                .networkModule(new NetworkModule(
                        getString(R.string.base_url),
                        getString(R.string.api_key)))
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public static Context getContext() {
        return context;
    }

    public static float getDensity() {
        return context.getResources().getDisplayMetrics().density;
    }
}
