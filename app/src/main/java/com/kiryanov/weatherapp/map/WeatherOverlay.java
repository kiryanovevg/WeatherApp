package com.kiryanov.weatherapp.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.models.minInfo.City;
import com.kiryanov.weatherapp.models.minInfo.Coordinate;

import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeniy on 22.09.18.
 */

public class WeatherOverlay extends Overlay {

    private List<City> cities = new ArrayList<>();
    private OnMapItemClickListener listener;

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if (shadow) return;

        BoundingBox viewBBox = mapView.getBoundingBox();

        for (City city : cities) {
            GeoPoint geoPoint = new GeoPoint(
                    city.getCoord().getLat(),
                    city.getCoord().getLon()
            );

            if (geoPoint.getLatitude() > viewBBox.getLatSouth()
                    && geoPoint.getLatitude() < viewBBox.getLatNorth()
                    && geoPoint.getLongitude() > viewBBox.getLonWest()
                    && geoPoint.getLongitude() < viewBBox.getLonEast()) {

                Point point = MyMapView.coordinateToPixels(
                        mapView.getWidth(),
                        mapView.getHeight(),
                        mapView.getBoundingBox(),
                        geoPoint
                );

                Bitmap icon = city.getWeather().get(0).getImage();
                if (icon != null) {
                    canvas.drawBitmap(
                            icon,
                            point.x - icon.getWidth() / 2,
                            point.y - icon.getHeight() / 2,
                            null
                    );

                    float density = BaseApplication.getDensity();
                    int textSizeInDp = 16;
                    float textSizeInPx = textSizeInDp * density;

                    Paint textPaint = new Paint();
                    textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    textPaint.setColor(Color.BLACK);
                    textPaint.setTextSize(textSizeInPx);

                    String cityName = city.getName();

                    float textWidth = textPaint.measureText(cityName);

                    canvas.drawText(
                            cityName,
                            point.x - icon.getWidth() / 2,
                            point.y + icon.getHeight() / 2,
                            textPaint
                    );

                    canvas.drawText(
                            city.getMain().getStringTemp(),
                            point.x + textWidth + 8*density - icon.getWidth() / 2,
                            point.y + icon.getHeight() / 2,
                            textPaint
                    );
                } else {
                    mapView.invalidate();
                }
            }
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event, MapView mapView) {
        int closet = findClosetPoint(event, mapView);

        if (closet != -1) {
            City city = cities.get(closet);
            if (listener != null) listener.onMapItemClick(city);
            Log.d("Overlay", "onSingleTapConfirmed: " + city);
        } else {
            Log.d("Overlay", "onSingleTapConfirmed: not found)");
        }

        return super.onSingleTapConfirmed(event, mapView);
    }

    private int findClosetPoint(MotionEvent event, MapView mapView) {
        float hyp;
        Float minHyp = null;
        int closest = -1;

//        int width = styler.getClusterImage().getWidth() + 10;
//        int height = styler.getClusterImage().getHeight() + 10;

        int width = 30;
        int height = 30;

        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i) == null) continue;
            Coordinate coordinate = cities.get(i).getCoord();

            Point tmp = MyMapView.coordinateToPixels(
                    mapView.getWidth(),
                    mapView.getHeight(),
                    mapView.getBoundingBox(),
                    new GeoPoint(coordinate.getLat(), coordinate.getLon())
            );

            if (Math.abs(event.getX() - tmp.x) > width
                    || Math.abs(event.getY() - tmp.y) > height) continue;

            hyp = (event.getX() - tmp.x) * (event.getX() - tmp.x)
                    + (event.getY() - tmp.y) * (event.getY() - tmp.y);

            if (minHyp == null || hyp < minHyp) {
                minHyp = hyp;
                closest = i;
            }
        }
        if (minHyp == null) return -1;

        return closest;
    }

    public void add(City city) {
        cities.add(city);
    }

    public void addAll(List<City> cities) {
        this.cities.addAll(cities);
    }

    public void clear() {
        cities.clear();
    }

    public void setListener(OnMapItemClickListener listener) {
        this.listener = listener;
    }
}
