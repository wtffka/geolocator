package com.mygeolocator.service.impl;

import com.mygeolocator.dto.CityDto;
import com.mygeolocator.model.City;
import com.mygeolocator.model.Country;
import com.mygeolocator.repository.CityRepository;
import com.mygeolocator.repository.CountryRepository;
import com.mygeolocator.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mygeolocator.utils.AppConstants.CITY_NAME_NOT_FOUND;
import static com.mygeolocator.utils.AppConstants.CITY_NOT_FOUND;


@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final CountryRepository countryRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public void addCity(String cityName, String countryName) {
        if (null != cityName) {
            Country country = countryRepository.findByName(countryName).get();
            CityDto cityDto = new CityDto(cityName, country);
            if (!checkCityRepositoryContainsData(cityName)) cityRepository.save(fromCityDto(cityDto));
        }
    }

    @Override
    public void deleteCity(String cityName) {
        if (null != cityName) {
            City city = cityRepository.findByName(cityName).get();
            cityRepository.delete(city);
        }
    }

    @Override
    public Iterable<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new RuntimeException(CITY_NOT_FOUND));
    }

    @Override
    public City getCityByName(String cityName) {
        return cityRepository.findByName(cityName)
                .orElseThrow(() -> new RuntimeException(CITY_NAME_NOT_FOUND));
    }

    private City fromCityDto(CityDto cityDto) {
        City city = new City();
        city.setName(cityDto.getName());
        city.setCountry(cityDto.getCountry());
        return city;
    }

    private boolean checkCityRepositoryContainsData(String cityName) {
        return cityRepository.findByName(cityName).isPresent();
    }
}
