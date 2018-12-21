package com.kiryanov.weatherapp.models.minInfo;

/**
 * Created by Evgeniy on 22.09.18.
 */

public class Temperature {

    private double temp;

    public double getTemp() {
        return temp;
    }

    private double getTempInCelsius() {
        return temp - 273.15;
    }

    public String getStringTemp() {
        int tempInCelsius = ((int) getTempInCelsius());
        String sTemp = tempInCelsius > 0 ? "+" + tempInCelsius : "" + tempInCelsius;

        return sTemp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
