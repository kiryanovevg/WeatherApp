package com.kiryanov.weatherapp.data;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.R;
import com.kiryanov.weatherapp.models.City;

import org.osmdroid.util.BoundingBox;

import java.util.List;

import dagger.Module;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Evgeniy on 22.09.18.
 */

@Module
public class Repository {

    private Api api;
    public MapSettings mapSettings = new MapSettings();

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
        return Observable.fromIterable(cities)
                .flatMap(city -> api.getWeatherByCity(city))
                .map(city -> {
                    int size = 50;

                    final int photoSizeInDp = 80;
                    final int photoSizeInPx = ((int) (photoSizeInDp * BaseApplication.getDensity()));

                    String url = String.format(
                            BaseApplication.getContext().getString(R.string.image_url),
                            city.getWeather().get(0).getIcon()
                    );

                    Bitmap image = Glide.with(BaseApplication.getContext())
                            .load(url)
                            .asBitmap()
                            .into(size, size)
                            .get();

                    image = Bitmap.createScaledBitmap(
                            image,
                            photoSizeInPx,
                            photoSizeInPx *
                                    image.getHeight() / image.getWidth(),
                            false
                    );

                    city.getWeather().get(0).setImage(image);

                    return city;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
