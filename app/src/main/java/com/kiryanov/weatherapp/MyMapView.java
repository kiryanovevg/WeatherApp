package com.kiryanov.weatherapp;

import android.content.Context;
import android.util.AttributeSet;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * Created by Evgeniy on 22.09.18.
 */

public class MyMapView extends MapView {

    private static double MIN_ZOOM = 4d;

    public MyMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        setBuiltInZoomControls(false);
        setMultiTouchControls(true);

        setMinZoomLevel(MIN_ZOOM);
        getController().setZoom(MIN_ZOOM);
        getController().setCenter(new GeoPoint(47.7, 47.1));
    }
}
