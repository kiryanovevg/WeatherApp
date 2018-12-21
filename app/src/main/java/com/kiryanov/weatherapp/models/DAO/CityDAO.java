package com.kiryanov.weatherapp.models.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kiryanov.weatherapp.models.minInfo.City;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CityDAO {

    @Query("SELECT * FROM city")
    List<City> getAll();

    @Query("SELECT * FROM city WHERE name = :name")
    City getByName(String name);

    @Insert
    void insert(City city);

    @Update
    void update(City city);

    @Delete
    void delete(City city);

}
