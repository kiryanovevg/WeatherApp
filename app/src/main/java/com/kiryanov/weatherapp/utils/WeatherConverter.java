package com.kiryanov.weatherapp.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kiryanov.weatherapp.models.minInfo.Weather;

import java.lang.reflect.Type;
import java.util.List;

public class WeatherConverter {

    private Type listType = new TypeToken<List<Weather>>(){}.getType();
    private Gson gson = new Gson();

    @TypeConverter
    public String from(List<Weather> weathers) {
        String s = gson.toJson(weathers, listType);

        //TODO тут пиздец

        return s;
    }

    @TypeConverter
    public List<Weather> to(String data) {
        return gson.fromJson(data, listType);
    }
}
