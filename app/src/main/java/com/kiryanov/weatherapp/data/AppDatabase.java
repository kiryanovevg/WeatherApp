package com.kiryanov.weatherapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.kiryanov.weatherapp.models.DAO.CityDAO;
import com.kiryanov.weatherapp.models.minInfo.City;

@Database(entities = {City.class}, version = 1)
abstract class AppDatabase extends RoomDatabase {
    public abstract CityDAO cityDAO();
}
