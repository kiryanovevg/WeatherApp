package com.kiryanov.weatherapp.models.minInfo;

/**
 * Created by Evgeniy on 22.09.18.
 */

public class Temperature {

    private double temp;

    public double getTemp() {
        return temp;
    }

    public double getTempInCelsius() {
        return temp - 273.15;
    }

    public String getStringTemp() {
        String sTemp = String.format("%.0f", getTempInCelsius());
        sTemp = getTempInCelsius() > 0 ? "+" + sTemp : "" + sTemp;

        return sTemp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
