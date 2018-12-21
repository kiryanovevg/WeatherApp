package com.kiryanov.weatherapp.models.minInfo;

import java.util.List;

/**
 * Created by Evgeniy on 22.09.18.
 */
public class City {

    private String name;

    private Coordinate coord;

    private List<Weather> weather;

    private Temperature main;

    private long dt;

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Temperature getMain() {
        return main;
    }

    public void setMain(Temperature main) {
        this.main = main;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City: " + name;
    }
}
