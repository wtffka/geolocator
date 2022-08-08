package com.mygeolocator.controller;

import com.mygeolocator.dto.LocationDto;
import com.mygeolocator.model.Location;
import com.mygeolocator.service.impl.LocationServiceImpl;
import com.mygeolocator.utils.ConnectionToGeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Map;

import static com.mygeolocator.utils.AppConstants.COORDINATES;


@RestController
@RequestMapping("/v1/locations")
public class LocationController {

    private final LocationServiceImpl locationService;

    @Autowired
    public LocationController(LocationServiceImpl locationService) {
        this.locationService = locationService;
    }

    @PostMapping()
    public Location addLocation(@RequestBody LocationDto locationDto) throws IOException {
        return checkLocationRepositoryContainsData(locationDto).containsKey(true)
                ? locationService.getLocationByCoordinates(checkLocationRepositoryContainsData(locationDto).get(true))
                : locationService.addLocation(locationDto);

    }

    @GetMapping
    public Iterable<Location> getLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public Location getLocation(@PathVariable long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("/2ndway")
    public Location getLocationByCoordinates(@RequestParam String coordinates) {
        return locationService.getLocationByCoordinates(coordinates);
    }

    @DeleteMapping("/{id}")
    public void deleteLocationById(@PathVariable Long id) {
        locationService.deleteLocationById(id);
    }

    @DeleteMapping()
    public void deleteLocationByCoordinates(@RequestParam String coordinates) {
       locationService.deleteLocationByCoordinates(coordinates);
    }



    private Map<Boolean, String> checkLocationRepositoryContainsData(LocationDto locationDto) throws IOException {
        Map<String, String> locationMap = ConnectionToGeolocationService.requestApi(locationDto.getLocation());
        String coordinates;
        if (locationDto.getLocation().matches("[^a-zA-Zа-яА-Я]+")) {
            coordinates = locationDto.getLocation();
        } else {
            coordinates = locationMap.get(COORDINATES);
        }
        return getResultMap(coordinates);
    }

    private Map<Boolean, String> getResultMap(String coordinates) {
        if (null != locationService.getLocationByCoordinates(coordinates)) {
            return Map.of(true, coordinates);
        }
        return Map.of(false, coordinates);
    }
}