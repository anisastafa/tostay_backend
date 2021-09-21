package com.ubt.app.controllers;

import com.ubt.model.Apartment;
import com.ubt.model.Host;
import com.ubt.model.Media;
import com.ubt.model.User;
import com.ubt.service.ApartmentService;
import com.ubt.service.HostService;
import com.ubt.service.MediaService;
import com.ubt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
public class OpenRequestsController {

    public static final Logger logger = LoggerFactory.getLogger(OpenRequestsController.class);

    @Autowired
    private HostService hostService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private MediaService mediaService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CrossOrigin("http://localhost:3000/login")
    public void login() {
        // Login default by spring security /login endpoint
    }

    @PostMapping(value = "/createHost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createHost(@ModelAttribute Host host,
                                        @RequestParam(value = "file") MultipartFile file) throws IOException {
        logger.info("Creating host: {}", host);
        List<Host> hosts = hostService.getAll();
        if(host == null || hosts.contains(host)){
            logger.info("Host is empty or exists");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("file original name: "+ file.getOriginalFilename());
        Media media = new Media(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        mediaService.save(media);
        host.setHostMedia(media);
        hostService.save(host);
        return new ResponseEntity<>(host, HttpStatus.CREATED);
    }


    @PostMapping(value = "/createUser", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder uriCBuilder) {
        logger.info("Creating user: {}", user);
        if ( userService.getById(user.getUser_ID()) != null) {
            logger.info("User exists or null");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userService.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriCBuilder.path("/api/user/{user_ID}").buildAndExpand(user.getUser_ID()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/getAllApartments")
    public ResponseEntity<List<Apartment>> listAllApartments() {
        logger.info("List of all apartments");
        List<Apartment> apartments = apartmentService.getAllApartments();
        if (apartments.isEmpty()) {
            logger.info("There are no apartments");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }
}
