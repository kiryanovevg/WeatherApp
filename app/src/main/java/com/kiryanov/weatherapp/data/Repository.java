package com.kiryanov.weatherapp.data;

import com.google.gson.JsonArray;
import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.models.minInfo.City;
import com.kiryanov.weatherapp.models.minInfo.Weather;
import com.kiryanov.weatherapp.models.moreInfo.WeatherInfo;
import com.kiryanov.weatherapp.utils.Utils;

import org.osmdroid.util.BoundingBox;

import java.util.List;

import dagger.Module;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Evgeniy on 22.09.18.
 */

@Module
public class Repository {

    public MapSettings mapSettings = new MapSettings();

    private Api api;

    public Repository(Api api) {
        this.api = api;
    }

    public Single<JsonArray> getWeatherByBoundingBox(BoundingBox boundingBox, int zoomLevel) {
        final String DIV = ",";

        String bbox = boundingBox.getLonWest() + DIV
                + boundingBox.getLatSouth() + DIV
                + boundingBox.getLonEast() + DIV
                + boundingBox.getLatNorth() + DIV
                + zoomLevel;

        return api.getWeatherByBoundingBox(bbox)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<City> getWeatherByCity(String city) {
        return api.getWeatherByCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<City> getWeatherByCity(List<String> cities) {
        return Observable
                .fromIterable(cities)
                .flatMap(city -> api.getWeatherByCity(city))
                .map(city -> {
                    Weather weather = city.getWeather().get(0);
                    weather.setImage(Utils.downloadWeatherIcon(weather.getIcon()));

                    return city;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<WeatherInfo> getCityWeatherInfo(String cityName) {
        return api.getCityWeatherInfo(cityName)
                .map(weatherWrapper -> {
                    for (WeatherInfo info : weatherWrapper.getList()) {
                        Weather weather = info.getWeather().get(0);
//                        weather.setImage(Utils.downloadWeatherIcon(weather.getIcon()));
                    }

                    return weatherWrapper;
                })
                .flatMap(weatherWrapper -> Observable.fromIterable(weatherWrapper.getList()))
                /*.filter(weatherInfo -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date(weatherInfo.getDt() * 1000));

                    boolean day = calendar.get(Calendar.DAY_OF_MONTH) ==
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                    Calendar now = Calendar.getInstance();
                    now.add(Calendar.DAY_OF_MONTH, 1);

                    int hour = Calendar.getInstance().get(Calendar.HOUR);

                    boolean time = hour >= 21 &&
                            calendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH);

                    return day || time;
                })*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
