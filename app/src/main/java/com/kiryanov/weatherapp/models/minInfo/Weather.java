package com.kiryanov.weatherapp.models.minInfo;

import android.graphics.Bitmap;

/**
 * Created by Evgeniy on 22.09.18.
 */

public class Weather {

    private String description;
    private String icon;

    private Bitmap image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
