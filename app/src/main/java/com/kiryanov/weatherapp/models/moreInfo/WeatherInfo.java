package com.kiryanov.weatherapp.models.moreInfo;

import com.kiryanov.weatherapp.models.minInfo.Temperature;
import com.kiryanov.weatherapp.models.minInfo.Weather;

import java.util.List;

public class WeatherInfo {

    private long dt;
    private Temperature main;
    private List<Weather> weather;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Temperature getMain() {
        return main;
    }

    public void setMain(Temperature main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
