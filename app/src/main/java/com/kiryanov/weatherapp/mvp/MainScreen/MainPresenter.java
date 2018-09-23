package com.kiryanov.weatherapp.mvp.MainScreen;

import android.support.design.widget.BottomSheetBehavior;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;
import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.data.Repository;
import com.kiryanov.weatherapp.models.City;
import com.kiryanov.weatherapp.mvp.BasePresenter;
import com.kiryanov.weatherapp.utils.BottomSheetStates;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by Evgeniy on 22.09.18.
 */

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    Repository repository;

    private int bottomSheetState = BottomSheetBehavior.STATE_HIDDEN;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        BaseApplication.getAppComponent().inject(this);

        load();
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);

        getViewState().setBottomSheetState(bottomSheetState);
    }

    private void load() {
        repository.getWeatherByCity(new ArrayList<String>() {{
            add("Salsk");
            add("Krasnodar");
            add("Moscow");
            add("Stavropol");
            add("Volgodonsk");
            add("Elista");
            add("Rostov-na-Donu");
//            add("Azov");
//            add("Taganrog");
//            add("Yeysk");
//            add("Zernograd");
        }})
                .subscribe(new Observer<City>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        unsubscribeOnDestroy(d);
                    }

                    @Override
                    public void onNext(City city) {
                        getViewState().showOnMap(city);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showMessage(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void setBottomSheetState(@BottomSheetStates int state) {
        bottomSheetState = state;
        getViewState().setBottomSheetState(state);
    }
}
