package com.kiryanov.weatherapp.mvp.MainScreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.R;
import com.kiryanov.weatherapp.data.Repository;
import com.kiryanov.weatherapp.map.MyMapView;
import com.kiryanov.weatherapp.map.OnMapItemClickListener;
import com.kiryanov.weatherapp.models.City;

import javax.inject.Inject;

public class MainActivity extends MvpAppCompatActivity implements MainView, OnMapItemClickListener {

    @Inject
    Repository repository;

    @InjectPresenter
    MainPresenter presenter;

    private MyMapView mapView;
    private Button button;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseApplication.getAppComponent().inject(this);

        mapView = findViewById(R.id.map_view);
        button = findViewById(R.id.btn);

        mapView.weatherOverlay.setListener(this);

        LinearLayout bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN
                        || newState == BottomSheetBehavior.STATE_COLLAPSED
                        || newState == BottomSheetBehavior.STATE_EXPANDED) {
                    presenter.setBottomSheetState(newState);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        button.setOnClickListener(view -> {

        });
    }

    @Override
    public void onMapItemClick(City city) {
        presenter.setBottomSheetState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void showOnMap(City city) {
        mapView.weatherOverlay.add(city);
        mapView.invalidate();
    }

    @Override
    public void setBottomSheetState(int state) {
        bottomSheetBehavior.setState(state);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
