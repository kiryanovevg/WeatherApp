package com.kiryanov.weatherapp.models;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.R;

/**
 * Created by Evgeniy on 22.09.18.
 */

public class Weather {

    private String description;
    private String icon;

    private Bitmap image;
    private boolean loading;

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

    public Bitmap getBitmap() {
        if (!loading) {
            String url = String.format(
                    BaseApplication.getContext().getString(R.string.image_url),
                    icon
            );

            double density = BaseApplication.getDensity();

            final int photoSizeInDp = 80;
            final int photoSizeInPx = ((int) (photoSizeInDp * density));

            Glide.with(BaseApplication.getContext())
                    .load(url)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(photoSizeInPx, photoSizeInPx) {

                        @Override
                        public void onResourceReady(Bitmap bitmap,
                                                    GlideAnimation<? super Bitmap> glideAnimation) {

                            if (bitmap != null) {
                                image = Bitmap.createScaledBitmap(
                                        bitmap,
                                        photoSizeInPx,
                                        photoSizeInPx *
                                                bitmap.getHeight() / bitmap.getWidth(),
                                        false
                                );
                            }

                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            if (errorDrawable != null) {
                                super.onLoadFailed(e, errorDrawable);

                                if (errorDrawable instanceof BitmapDrawable) {
                                    image = ((BitmapDrawable) errorDrawable).getBitmap();
                                }
                                loading = false;
                            }
                        }
                    });
            loading = true;
        }

        return image;
    }
}
