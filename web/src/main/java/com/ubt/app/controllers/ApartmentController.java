package com.ubt.app.controllers;

import com.ubt.app.util.Utils;
import com.ubt.model.Apartment;
import com.ubt.model.Host;
import com.ubt.model.Location;
import com.ubt.model.Media;
import com.ubt.service.ApartmentService;
import com.ubt.service.HostService;
import com.ubt.service.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    private static final Logger logger = LoggerFactory.getLogger(ApartmentController.class);

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private HostService hostService;

    @Autowired
    private MediaService mediaService;


    @PostMapping(value = "/createApartment",
//            produces = MediaType.MULTIPART_FORM_DATA_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createApartment(@ModelAttribute Apartment apartment,
                                             @RequestParam(value = "files", required = false) MultipartFile[] files,
                                             Location location,
                                             Principal principal) {
        logger.info("Creating apartment: {}", apartment);
        String email = principal.getName();
        Host host = hostService.getByEmail(email);
        apartment.setHost(host);
        apartment.setLocation(location);

        List<Media> mediaList = new ArrayList<>(Collections.singletonList(new Media()));
        Arrays.stream(files)
                .forEach(file -> {
                    try {
                        Media media = new Media(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                        mediaService.save(media);
                        mediaList.add(media);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        mediaList.remove(0);
        apartment.setMediaList(mediaList);
        apartmentService.save(apartment);
        return new ResponseEntity<>(apartment, HttpStatus.CREATED);
    }

    @GetMapping("/getHostsApartments")
    @Transactional
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

    @DeleteMapping("/{apartment_id}")
    public ResponseEntity<?> deleteById(@PathVariable("apartment_id") int apartment_id) {
        logger.info("Fetching & Deleting apartment with id {}", apartment_id);
        Apartment apartment = apartmentService.getById(apartment_id);
        if (apartment == null) {
            logger.error("Unable to delete. user with id {} not found.", apartment_id);
            return new ResponseEntity<>(new Utils("Unable to delete apartment with id " + apartment_id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        apartmentService.deleteById(apartment_id);
        return new ResponseEntity<Apartment>(HttpStatus.OK);
    }


    @PutMapping(value = "/updateApartment/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateApartment(@PathVariable("id") int id, @RequestBody Apartment apartment) {
        logger.info("Updating apartment with id: {}", id);
        Apartment updatedApartment = apartmentService.getById(id);
        if (apartment == null) {
            logger.info("Apartment with id " + id + " doesn't exist");
            return new ResponseEntity<>(new Utils("unable to update apartment"), HttpStatus.NOT_FOUND);
        }
//        updatedApartment.setMedia(apartment.getMedia());
        updatedApartment.setLocation(apartment.getLocation());
        updatedApartment.setMediaList(apartment.getMediaList());
        updatedApartment.setHas_heating(apartment.isHas_heating());
        updatedApartment.setHas_internet(apartment.isHas_internet());
        updatedApartment.setHas_tv(apartment.isHas_tv());
        updatedApartment.setPrice_per_night(apartment.getPrice_per_night());
        updatedApartment.setTotal_bathrooms(apartment.getTotal_bathrooms());
        updatedApartment.setTotal_bedrooms(apartment.getTotal_bedrooms());
        apartmentService.save(updatedApartment);
        return new ResponseEntity<>(updatedApartment, HttpStatus.OK);
    }
}
