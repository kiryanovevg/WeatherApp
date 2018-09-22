package com.kiryanov.weatherapp.mvp.MainScreen;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;
import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.data.Repository;
import com.kiryanov.weatherapp.models.City;
import com.kiryanov.weatherapp.mvp.BasePresenter;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by Evgeniy on 22.09.18.
 */

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    Repository repository;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        BaseApplication.getAppComponent().inject(this);

        load();
    }

    private void load() {
        repository.getWeatherByCity("Krasnodar")
                .subscribe(new SingleObserver<City>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        unsubscribeOnDestroy(d);
                    }

                    @Override
                    public void onSuccess(City city) {
                        getViewState().showOnMap(city);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showMessage(e.getMessage());
                    }
                });
    }
}
