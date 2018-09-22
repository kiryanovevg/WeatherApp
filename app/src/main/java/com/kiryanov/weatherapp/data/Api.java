package com.kiryanov.weatherapp.data;

import com.kiryanov.weatherapp.models.City;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Evgeniy on 22.09.18.
 */

public interface Api {

    @GET("weather")
    Single<City> getWeatherByCity(@Query("q") String city);

//    bounding box [lon-left,lat-bottom,lon-right,lat-top,zoom]
//    @GET("box/city")
//    Single<City> getWeatherByBoundingBox(@Query("bbox") String bbox);
}
