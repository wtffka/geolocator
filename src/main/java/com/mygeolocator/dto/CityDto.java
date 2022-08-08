package com.mygeolocator.dto;

import com.mygeolocator.model.Country;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityDto {

    private String name;

    private Country country;
}
