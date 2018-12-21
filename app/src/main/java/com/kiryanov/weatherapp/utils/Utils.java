package com.kiryanov.weatherapp.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Utils {

    public static Bitmap downloadWeatherIcon(String iconId)
            throws ExecutionException, InterruptedException {

        int size = 50;

        final int photoSizeInDp = 80;
        final int photoSizeInPx = ((int) (photoSizeInDp * BaseApplication.getDensity()));

        String url = String.format(
                BaseApplication.getContext().getString(R.string.image_url),
                iconId
        );

        Bitmap image = Glide.with(BaseApplication.getContext())
                .load(url)
                .asBitmap()
                .into(size, size)
                .get();

        image = Bitmap.createScaledBitmap(
                image,
                photoSizeInPx,
                photoSizeInPx *
                        image.getHeight() / image.getWidth(),
                false
        );

        return image;
    }

    @BindingAdapter("weather_icon")
    public static void setWeatherIcon(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @BindingAdapter("weather_icon_url")
    public static void setWeatherIconFromUrl(ImageView imageView, String iconId) {
        String url = String.format(
                BaseApplication.getContext().getString(R.string.image_url),
                iconId
        );

        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    @BindingAdapter("time")
    public static void setTime(TextView textView, long dt) {
        Date date = new Date(dt * 1000);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        textView.setText(dateFormat.format(date));
    }

    @BindingAdapter("date")
    public static void setDate(TextView textView, long dt) {
        Date date = new Date(dt * 1000);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM");

        textView.setText(dateFormat.format(date));
    }
}
