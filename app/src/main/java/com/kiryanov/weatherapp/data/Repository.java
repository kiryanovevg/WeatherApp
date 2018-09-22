package com.kiryanov.weatherapp.data;

import com.google.gson.JsonObject;
import com.kiryanov.weatherapp.models.City;

import org.osmdroid.util.BoundingBox;

import dagger.Module;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Evgeniy on 22.09.18.
 */

@Module
public class Repository {

    private Api api;

    public Repository(Api api) {
        this.api = api;
    }

    /*public Single<JsonObject> getWeatherByBoundingBox(BoundingBox boundingBox) {
        final String DIV = ",";

        String bbox = boundingBox.getLonWest() + DIV
                + boundingBox.getLatSouth() + DIV
                + boundingBox.getLonEast() + DIV
                + boundingBox.getLatNorth() + DIV
                + 10;

        return api.getWeatherByBoundingBox(bbox)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    public Single<City> getWeatherByCity(String city) {
        return api.getWeatherByCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
