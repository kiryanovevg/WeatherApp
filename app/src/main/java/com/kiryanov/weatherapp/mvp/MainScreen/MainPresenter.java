package com.kiryanov.weatherapp.mvp.MainScreen;

import android.support.design.widget.BottomSheetBehavior;

import com.arellomobile.mvp.InjectViewState;
import com.google.gson.JsonObject;
import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.data.Repository;
import com.kiryanov.weatherapp.models.minInfo.City;
import com.kiryanov.weatherapp.models.moreInfo.WeatherInfo;
import com.kiryanov.weatherapp.models.moreInfo.WeatherWrapper;
import com.kiryanov.weatherapp.mvp.BasePresenter;
import com.kiryanov.weatherapp.utils.BottomSheetStates;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.FlowableSubscriber;
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
    private City selectedCity;
    private boolean infoLoaded;

    private Disposable disposable;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        BaseApplication.getAppComponent().inject(this);

//        loadWeather();
        getLocalWeather();
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);

        getViewState().setBottomSheetState(bottomSheetState);
    }

    private void loadWeather() {
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

    private void getLocalWeather() {
        repository.getLocalWeather()
                .subscribe(new FlowableSubscriber<City>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(City city) {
                        getViewState().showOnMap(city);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                /*.subscribe(new Observer<City>() {
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
                });*/
    }

    private void uploadCityInfo() {
        repository.getCityWeatherInfo(selectedCity.getName())
                .subscribe(new Observer<WeatherInfo>() {

                    private List<WeatherInfo> result = new ArrayList<>();

                    @Override
                    public void onSubscribe(Disposable d) {
                        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
                        disposable = d;

                        unsubscribeOnDestroy(d);
                        getViewState().setInfoLoadingProgressVisibility(true);
                    }

                    @Override
                    public void onNext(WeatherInfo weatherInfo) {
                        result.add(weatherInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showMessage(e.getMessage());
                        getViewState().setInfoLoadingProgressVisibility(false);
                    }

                    @Override
                    public void onComplete() {
                        infoLoaded = true;
                        getViewState().showCityWeather(result);
                        getViewState().setInfoLoadingProgressVisibility(false);
                    }
                });
    }

    public void setBottomSheetState(@BottomSheetStates int state) {
        bottomSheetState = state;
        getViewState().setBottomSheetState(state);

        if (state == BottomSheetBehavior.STATE_EXPANDED
                && selectedCity != null && !infoLoaded) {
            uploadCityInfo();
        }
    }

    public void mapItemClicked(City city) {
        if (selectedCity != city) {
            selectedCity = city;
            getViewState().setSelectedCity(city);
            infoLoaded = false;

            getViewState().setInfoLoadingProgressVisibility(true);
        }

        setBottomSheetState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
