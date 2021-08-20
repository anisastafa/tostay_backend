package com.ubt.service;

import com.ubt.model.Location;
import com.ubt.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    public Location getLocationById(int id) {
        return locationRepository.findById(id);
    }

    public List<Location> getLocationByCity(String city) {
        return locationRepository.findLocationByCity(city);
    }

    public void save(Location l) {
        Location location = new Location();

        location.setCity(l.getCity());
        location.setCountry(l.getCountry());
        location.setLatitude(l.getLatitude());
        location.setLongitude(l.getLongitude());

        locationRepository.save(location);
    }


}
