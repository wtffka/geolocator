package com.mygeolocator.controller;

import com.mygeolocator.model.Country;
import com.mygeolocator.service.impl.CountryServiceImpl;
import com.mygeolocator.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1/countries")
public class CountryController {

    private final CountryServiceImpl countryService;

    @Autowired
    public CountryController(CountryServiceImpl countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public Iterable<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable Long id) {
        return countryService.getCountryById(id);
    }

    @GetMapping()
    public Country getCountryByName(@RequestParam String countryName) {
        return countryService.getCountryByName(Utils.transformStringToNormalView(countryName));
    }
}
