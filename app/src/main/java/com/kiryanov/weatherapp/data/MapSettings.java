package com.kiryanov.weatherapp.data;

import com.kiryanov.weatherapp.map.MyMapView;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

public class MapSettings {

    private IGeoPoint center = new GeoPoint(47.7, 41.2);
    private double zoom = MyMapView.MIN_ZOOM;

    public IGeoPoint getCenter() {
        return center;
    }

    public void setCenter(IGeoPoint center) {
        this.center = center;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
