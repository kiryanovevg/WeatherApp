package com.kiryanov.weatherapp.mvp.MainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kiryanov.weatherapp.models.minInfo.City;
import com.kiryanov.weatherapp.models.moreInfo.WeatherInfo;
import com.kiryanov.weatherapp.utils.BottomSheetStates;

import java.util.List;

/**
 * Created by Evgeniy on 22.09.18.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
interface MainView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String msg);

    @StateStrategyType(AddToEndStrategy.class)
    void showOnMap(City city);

    void setBottomSheetState(@BottomSheetStates int state);

    void setSelectedCity(City city);

    void showCityWeather(List<WeatherInfo> list);

    void setInfoLoadingProgressVisibility(boolean visibility);
}
