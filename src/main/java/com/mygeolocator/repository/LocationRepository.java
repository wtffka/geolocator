package com.mygeolocator.repository;

import com.mygeolocator.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByCoordinates(String coordinates);

    List<Location> findLocationsByCountry(String countryName);
}
