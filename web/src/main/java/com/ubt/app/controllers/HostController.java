package com.ubt.app.controllers;

import com.ubt.model.Host;
import com.ubt.model.User;
import com.ubt.service.HostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/hosts")
public class HostController {

    private static final Logger logger = LoggerFactory.getLogger(HostController.class);

    @Autowired
    private HostService hostService;

    @GetMapping(value = "/getCurrentHost")
//    @PreAuthorize("hasAuthority('host:read')")
    public String getCurrentHost(Principal principal){
        return principal.getName();
    }

    //for testing purposes
    @GetMapping("/getAllHosts")
//    @PreAuthorize("hasRole(T(com.ubt.app.security.ApplicationUserRole).HOST.name())")
    public ResponseEntity<List<Host>> listAllHosts() {
        logger.info("List all hosts");
        List<Host> hosts = hostService.getAll();
        if (hosts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hosts, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteHost/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        logger.info("Deleting user with id: {}", id);
        Host host = hostService.getById(id);
        if (host == null) {
            logger.info("unable to delete. host with id "+id+" not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        hostService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/updateHost/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") int id, @RequestBody Host host){
        logger.info("Updating user with id: {}", id);
        Host currHost = hostService.getById(id);
        currHost.setMedia(host.getMedia());
        currHost.setFirstname(host.getFirstname());
        currHost.setLastname(host.getLastname());
        currHost.setUsername(host.getUsername());
        currHost.setPassword(host.getPassword());
        currHost.setEmail(host.getEmail());
        hostService.save(currHost);
        return new ResponseEntity<>(currHost, HttpStatus.OK);
    }
}
