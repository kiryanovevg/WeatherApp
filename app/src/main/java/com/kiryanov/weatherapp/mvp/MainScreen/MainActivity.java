package com.kiryanov.weatherapp.mvp.MainScreen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kiryanov.weatherapp.BaseApplication;
import com.kiryanov.weatherapp.R;
import com.kiryanov.weatherapp.adapters.RecyclerViewAdapter;
import com.kiryanov.weatherapp.data.Repository;
import com.kiryanov.weatherapp.databinding.BottomSheetCityInfoBinding;
import com.kiryanov.weatherapp.map.MyMapView;
import com.kiryanov.weatherapp.map.OnMapItemClickListener;
import com.kiryanov.weatherapp.models.minInfo.City;
import com.kiryanov.weatherapp.models.moreInfo.WeatherInfo;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends MvpAppCompatActivity implements MainView, OnMapItemClickListener {

    private RecyclerViewAdapter<WeatherInfo, MainPresenter> adapter;

    @Inject
    Repository repository;

    @InjectPresenter
    MainPresenter presenter;

    private BottomSheetCityInfoBinding cityInfoBinding;

    private ProgressBar progressBar;
    private MyMapView mapView;
    private Button button;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseApplication.getAppComponent().inject(this);
        cityInfoBinding = DataBindingUtil.bind(findViewById(R.id.bottom_sheet));

        mapView = findViewById(R.id.map_view);
        button = findViewById(R.id.btn);
        progressBar = findViewById(R.id.progress_bar_main);

        mapView.weatherOverlay.setListener(this);

        bottomSheetBehavior = BottomSheetBehavior.from(cityInfoBinding.bottomSheet);
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


        adapter = new RecyclerViewAdapter<>(R.layout.item_weather_info, presenter);
        cityInfoBinding.rv.setAdapter(adapter);
        cityInfoBinding.rv.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        ));

        button.setOnClickListener(view -> {});
    }

    @Override
    public void onMapItemClick(City city) {
        presenter.mapItemClicked(city);
    }

    @Override
    public void setSelectedCity(City city) {
        cityInfoBinding.setCity(city);
    }

    @Override
    public void showOnMap(City city) {
        mapView.weatherOverlay.add(city);
        mapView.invalidate();
    }

    @Override
    public void showCityWeather(List<WeatherInfo> list) {
        cityInfoBinding.rv.scrollToPosition(0);
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void setInfoLoadingProgressVisibility(boolean visibility) {
        cityInfoBinding.progress.setVisibility(visibility ? View.VISIBLE : View.GONE);
        cityInfoBinding.rv.setVisibility(visibility ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void setCityLoadingProgressVisibility(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
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
