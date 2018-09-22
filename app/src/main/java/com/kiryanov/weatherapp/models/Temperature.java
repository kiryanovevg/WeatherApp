package com.kiryanov.weatherapp.models;

/**
 * Created by Evgeniy on 22.09.18.
 */

public class Temperature {

    private double temp;

    public double getTemp() {
        return temp - 273.15;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
