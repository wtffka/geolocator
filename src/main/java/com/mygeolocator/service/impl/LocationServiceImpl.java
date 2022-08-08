package com.mygeolocator.service.impl;

import com.mygeolocator.dto.LocationDto;
import com.mygeolocator.model.Location;
import com.mygeolocator.repository.LocationRepository;
import com.mygeolocator.service.LocationService;
import com.mygeolocator.utils.ConnectionToGeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static com.mygeolocator.utils.AppConstants.LOCATION_NOT_FOUND;
import static com.mygeolocator.utils.AppConstants.LOCALITY;
import static com.mygeolocator.utils.AppConstants.COUNTRY;
import static com.mygeolocator.utils.AppConstants.COORDINATES;
import static com.mygeolocator.utils.AppConstants.STREET;
import static com.mygeolocator.utils.AppConstants.HYDRO;
import static com.mygeolocator.utils.AppConstants.HOUSE;
import static com.mygeolocator.utils.AppConstants.OTHER_INFO;
import static com.mygeolocator.utils.AppConstants.PROVINCE;


@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final CountryServiceImpl countryService;

    private final CityServiceImpl cityService;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository,
                               CountryServiceImpl countryService,
                               CityServiceImpl cityService) {
        this.locationRepository = locationRepository;
        this.countryService = countryService;
        this.cityService = cityService;
    }

    @Override
    public Location addLocation(LocationDto locationDto) throws IOException {
        return locationRepository.save(fromDto(locationDto));
    }

    @Override
    public Iterable<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(LOCATION_NOT_FOUND));
    }

    @Override
    public Location getLocationByCoordinates(String coordinates) {
        return locationRepository.findByCoordinates(coordinates);
    }

    @Override
    public void deleteLocationById(Long id) {
        Location locationToDelete = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(LOCATION_NOT_FOUND));

        locationRepository.delete(locationToDelete);
        deleteNestedEntities(locationToDelete.getCountry(), locationToDelete.getLocality());
    }

    @Override
    public void deleteLocationByCoordinates(String coordinates) {
        Location locationToDelete = locationRepository.findByCoordinates(coordinates);
        if (null != locationToDelete) {
            locationRepository.delete(locationToDelete);
            deleteNestedEntities(locationToDelete.getCountry(), locationToDelete.getLocality());
        }
    }

    private Location fromDto(LocationDto locationDto) throws IOException {
        Location location = new Location();
        getLocationAttributes(locationDto.getLocation(), location);
        return location;
    }

    private void getLocationAttributes(String locationDto, Location locationToFill) throws IOException {
        Map<String, String> locationMap = ConnectionToGeolocationService.requestApi(locationDto);
        if (locationDto.matches("[^a-zA-Zа-яА-Я]+")) {
            locationToFill.setCoordinates(locationDto.replace(",", ""));
            setLocationInfo(locationToFill, locationMap);
        } else {
            locationToFill.setCoordinates(locationMap.get(COORDINATES));
            setLocationInfo(locationToFill, locationMap);
        }
        setNestedEntities(locationMap.get(COUNTRY), locationMap.get(LOCALITY));
    }

    private void setLocationInfo(Location locationToFill, Map<String, String> locationMap) {
        locationToFill.setCountry(locationMap.get(COUNTRY));
        locationToFill.setProvince(locationMap.get(PROVINCE));
        locationToFill.setLocality(locationMap.get(LOCALITY));
        locationToFill.setStreet(locationMap.get(STREET));
        locationToFill.setHouse(locationMap.get(HOUSE));
        if (locationMap.get(OTHER_INFO) != null) {
            locationToFill.setOtherDetails(locationMap.get(OTHER_INFO));
        } else {
            locationToFill.setOtherDetails(locationMap.get(HYDRO));
        }
    }

    private void setNestedEntities(String countryName, String cityName) {
        setCountryEntity(countryName);
        setCityEntity(cityName, countryName);
    }

    private void setCountryEntity(String countryName) {
        countryService.createCountry(countryName);
    }

    private void setCityEntity(String cityName, String countryName) {
        cityService.addCity(cityName, countryName);
    }

    private void deleteNestedEntities(String countryName, String cityName) {
        deleteCityEntity(cityName);
        deleteCountryEntity(countryName);
    }

    private void deleteCountryEntity(String countryName) {
        countryService.deleteCountry(countryName);
    }

    private void deleteCityEntity(String cityName) {
        cityService.deleteCity(cityName);
    }
}
