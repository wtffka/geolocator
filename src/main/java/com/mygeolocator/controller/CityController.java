package com.mygeolocator.controller;


import com.mygeolocator.model.City;
import com.mygeolocator.service.impl.CityServiceImpl;
import com.mygeolocator.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/v1/cities")
public class CityController {

    private final CityServiceImpl cityService;

    @Autowired
    public CityController(CityServiceImpl cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/all")
    public Iterable<City> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @GetMapping()
    public City getCityByName(@RequestParam String cityName) {
        return cityService.getCityByName(Utils.transformStringToNormalView(cityName));
    }

}
