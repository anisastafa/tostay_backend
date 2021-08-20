package com.ubt.app.controllers;

import com.ubt.app.util.Utils;
import com.ubt.model.Apartment;
import com.ubt.model.Host;
import com.ubt.service.ApartmentService;
import com.ubt.service.HostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    private static final Logger logger = LoggerFactory.getLogger(ApartmentController.class);

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private HostService hostService;

    @PostMapping(value = "/createApartment", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createApartment(@RequestBody Apartment apartment, Principal principal) {
        logger.info("Creating apartment: {}", apartment);
        String email = principal.getName();
        Host host = hostService.getByEmail(email);
        apartment.setHost(host);
        apartmentService.save(apartment);

        return new ResponseEntity<>(apartment, HttpStatus.CREATED);
    }

    @GetMapping("/getHostsApartments")
    public ResponseEntity<List<Apartment>> listHostsApartments(Principal principal) {
        logger.info("List of all the apartments of a specific host");
        String user_email = principal.getName();
        Host host = hostService.getByEmail(user_email);
        List<Apartment> apartments = host.getApartments();
        if (apartments.isEmpty()) {
            logger.info("There are no apartments");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }


    @GetMapping("/getApartment/{id}")
    public ResponseEntity<Apartment> getApartment(@PathVariable("id") int id) {
        logger.info("Fetching apartment with id: {}", id);
        Apartment apartment = apartmentService.getById(id);
        if (apartment == null) {
            logger.info("apartment with id: " + id + " doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(apartment, HttpStatus.OK);
    }

    @DeleteMapping("/deleteApartment/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        logger.info("Fetching & Deleting apartment with id {}", id);
        Apartment apartment = apartmentService.getById(id);
        if (apartment == null) {
            logger.error("Unable to delete. user with id {} not found.", id);
            return new ResponseEntity<>(new Utils("Unable to delete apartment with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        apartmentService.deleteById(id);
        return new ResponseEntity<Apartment>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/updateApartment/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateApartment(@PathVariable("id") int id, @RequestBody Apartment apartment) {
        logger.info("Updating apartment with id: {}", id);
        Apartment updatedApartment = apartmentService.getById(id);
        if (apartment == null) {
            logger.info("Apartment with id " + id + " doesn't exist");
            return new ResponseEntity<>(new Utils("unable to update apartment"), HttpStatus.NOT_FOUND);
        }
//        Location location = locationService.getLocationById(apartment.getLocation().getLocation_ID());
//        Media media = mediaService.getById(apartment.getMedia().getMedia_ID());
//        updatedApartment.setLocation(location);
//        updatedApartment.setMedia(media);
        updatedApartment.setLocation(apartment.getLocation());
        updatedApartment.setMedia(apartment.getMedia());
        updatedApartment.setHas_heating(apartment.isHas_heating());
        updatedApartment.setHas_internet(apartment.isHas_internet());
        updatedApartment.setHas_tv(apartment.isHas_tv());
        updatedApartment.setNum_nights(apartment.getNum_nights());
        updatedApartment.setPrice_per_night(apartment.getPrice_per_night());
        updatedApartment.setTotal_bathrooms(apartment.getTotal_bathrooms());
        updatedApartment.setTotal_bedrooms(apartment.getTotal_bedrooms());
        updatedApartment.setTotal_price(apartment.getTotal_price());
        apartmentService.save(updatedApartment);
        return new ResponseEntity<>(updatedApartment, HttpStatus.OK);
    }
}
