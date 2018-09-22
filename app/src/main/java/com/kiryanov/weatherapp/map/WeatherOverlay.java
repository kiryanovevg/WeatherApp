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
import com.kiryanov.weatherapp.models.City;
import com.kiryanov.weatherapp.models.Coordinate;

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

                Bitmap icon = city.getWeather().get(0).getBitmap();
                if (icon != null) {
                    canvas.drawBitmap(
                            icon,
                            point.x,
                            point.y,
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
                            point.x,
                            point.y + icon.getHeight(),
                            textPaint
                    );

                    double temp = city.getMain().getTemp();
                    String sTemp = String.format("%.1f", temp);
                    sTemp = temp > 0 ? "+" + sTemp : "" + sTemp;

                    canvas.drawText(
                            sTemp,
                            point.x + textWidth + 8*density,
                            point.y + icon.getHeight(),
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
            Log.d("Overlay", "onSingleTapConfirmed: " + cities.get(closet));
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

        int width = 20;
        int height = 20;

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
}
