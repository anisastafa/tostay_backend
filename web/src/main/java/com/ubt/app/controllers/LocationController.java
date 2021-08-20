package com.ubt.app.controllers;

import com.ubt.model.Location;
import com.ubt.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(ApartmentController.class);

    @Autowired
    private LocationService locationService;

    @PostMapping(value = "/saveLocation")
    public ResponseEntity<?> saveLocation(@RequestBody Location location){
        logger.info("Creating location: {}", location);
        if(location == null){
            return new ResponseEntity<>("location is null", HttpStatus.NOT_FOUND);
        }
        locationService.save(location);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<?> getLocation(@PathVariable("id") int id){
        logger.info("Get location by id: {}", id);
        Location location = locationService.getLocationById(id);
        if(location == null){
            return new ResponseEntity<>("location doesnt exist", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping("/location/city/{city}")
    public ResponseEntity<List<Location>> getLocationsByCity(@PathVariable("city") String city){
        logger.info("Get location by city: {}", city);
        List<Location> location = locationService.getLocationByCity(city);
        if(city == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}
