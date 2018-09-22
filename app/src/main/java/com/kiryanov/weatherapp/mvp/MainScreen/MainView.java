package com.kiryanov.weatherapp.mvp.MainScreen;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kiryanov.weatherapp.models.City;

/**
 * Created by Evgeniy on 22.09.18.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
interface MainView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String msg);

    void showOnMap(City city);
}
