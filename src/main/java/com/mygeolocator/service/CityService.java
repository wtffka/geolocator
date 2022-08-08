package com.mygeolocator.service;

import com.mygeolocator.model.City;

public interface CityService {

    void addCity(String cityName, String countryName);

    void deleteCity(String cityName);

    Iterable<City> getAllCities();

    City getCityById(Long id);

    City getCityByName(String cityName);
}
