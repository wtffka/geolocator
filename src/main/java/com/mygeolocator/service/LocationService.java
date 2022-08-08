package com.mygeolocator.service;

import com.mygeolocator.dto.LocationDto;
import com.mygeolocator.model.Location;

import java.io.IOException;

public interface LocationService {

    Location addLocation(LocationDto locationDto) throws IOException;

    Iterable<Location> getAllLocations();

    Location getLocationById(Long id);

    Location getLocationByCoordinates(String coordinates);

    void deleteLocationById(Long id);

    void deleteLocationByCoordinates(String coordinates);

}
