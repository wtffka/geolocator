package com.mygeolocator.service.impl;

import com.mygeolocator.dto.CountryDto;
import com.mygeolocator.model.Country;
import com.mygeolocator.repository.CountryRepository;
import com.mygeolocator.repository.LocationRepository;
import com.mygeolocator.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mygeolocator.utils.AppConstants.COUNTRY_NAME_NOT_FOUND;
import static com.mygeolocator.utils.AppConstants.COUNTRY_NOT_FOUND;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    private final LocationRepository locationRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository,
                              LocationRepository locationRepository) {
        this.countryRepository = countryRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void createCountry(String name) {
        if (null != name) {
            CountryDto countryDto = new CountryDto(name);
            if (!checkCountryRepositoryContainsData(name)) countryRepository.save(fromCountryDto(countryDto));
        }
    }

    @Override
    public Iterable<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public void deleteCountry(String countryName) {
        if (null != countryName) {
            Country country = countryRepository.findByName(countryName).get();
            if (locationRepository.findLocationsByCountry(countryName).size() == 0) {
                countryRepository.delete(country);
            }
        }
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(COUNTRY_NOT_FOUND));
    }

    @Override
    public Country getCountryByName(String countryName) {
        return countryRepository.findByName(countryName)
                .orElseThrow(() -> new RuntimeException(COUNTRY_NAME_NOT_FOUND));
    }

    private Country fromCountryDto(CountryDto countryDto) {
        Country country = new Country();
        country.setName(countryDto.getName());
        return country;
    }

    private boolean checkCountryRepositoryContainsData(String countryName) {
        return countryRepository.findByName(countryName).isPresent();
    }
}
