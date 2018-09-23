package com.kiryanov.weatherapp.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kiryanov.weatherapp.models.City;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Evgeniy on 22.09.18.
 */

public interface Api {

    @GET("weather")
    Observable<City> getWeatherByCity(@Query("q") String city);

//    bounding box [lon-left,lat-bottom,lon-right,lat-top,zoom]
    @GET("box/city")
    Single<JsonArray> getWeatherByBoundingBox(@Query("bbox") String bbox);
}
