package com.kiryanov.weatherapp.di;

import com.kiryanov.weatherapp.di.modules.NetworkModule;
import com.kiryanov.weatherapp.di.modules.RepositoryModule;
import com.kiryanov.weatherapp.map.MyMapView;
import com.kiryanov.weatherapp.mvp.MainScreen.MainActivity;
import com.kiryanov.weatherapp.mvp.MainScreen.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Evgeniy on 22.09.18.
 */

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class})
public interface AppComponent {

    void inject(MainPresenter mainPresenter);

    void inject(MainActivity mainActivity);

    void inject(MyMapView myMapView);
}
