package com.mygeolocator.service;

import com.mygeolocator.model.Country;

public interface CountryService {

    void createCountry(String name);

    Iterable<Country> getAllCountries();

    Country getCountryById(Long id);

    Country getCountryByName(String countryName);

    void deleteCountry(String name);

}
