package com.kiryanov.weatherapp.map;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;

import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.data.Repository;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import javax.inject.Inject;

/**
 * Created by Evgeniy on 22.09.18.
 */

public class MyMapView extends MapView {

    @Inject
    Repository repository;

    public static double MIN_ZOOM = 7d;

    public WeatherOverlay weatherOverlay;

    public MyMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        BaseApplication.getAppComponent().inject(this);

        initMap();
        initOverlays();
    }

    private void initMap() {
        setBuiltInZoomControls(false);
        setMultiTouchControls(true);

        setMinZoomLevel(MIN_ZOOM);
        getController().setZoom(repository.mapSettings.getZoom());
        getController().setCenter(repository.mapSettings.getCenter());

        addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                repository.mapSettings.setCenter(getMapCenter());
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                repository.mapSettings.setZoom(getZoomLevelDouble());
                return false;
            }
        });
    }

    private void initOverlays() {
        weatherOverlay = new WeatherOverlay();
        getOverlays().add(weatherOverlay);
    }

    public static void coordinateToPixels(int viewWid, int viewHei,
                                          BoundingBox viewBBox,
                                          IGeoPoint pt1, Point mPositionPixels) {
        double dx = viewBBox.getLonEast() - viewBBox.getLonWest();
        double xRatio = pt1.getLongitude() - viewBBox.getLonWest();
        if (xRatio <= 0) return;

        double xPercent = xRatio / dx;
        int px = ((int) (viewWid * xPercent));

        double dy = viewBBox.getLatNorth() - viewBBox.getLatSouth();
        double yRatio = pt1.getLatitude() - viewBBox.getLatSouth();
        if (yRatio <= 0) return;

        double yPercent = yRatio / dy;
        int py = ((int) (viewHei * yPercent));

        mPositionPixels.set(px, viewHei - py);
    }

    public static Point coordinateToPixels(int viewWid, int viewHei,
                                           BoundingBox viewBBox,
                                           IGeoPoint pt1) {
        Point point = new Point();
        coordinateToPixels(viewWid, viewHei, viewBBox, pt1, point);

        return point;
    }
}
