package com.ubt.app.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.ubt.model.User;
import com.ubt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @DeleteMapping(value = "/deleteUser/{user_ID}")
    public ResponseEntity<?> deleteById(@PathVariable("user_ID") int user_ID) {
        logger.info("Deleting user with id: {}", user_ID);
        User user = userService.getById(user_ID);
        if (user == null) {
            logger.info("User doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteById(user_ID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/updateUser/{user_ID}")
    public ResponseEntity<?> updateById(@PathVariable("user_ID") int user_ID, @RequestBody User user){
        logger.info("Updating user with id: {}", user_ID);
        User currUser = userService.getById(user_ID);
        currUser.setFirstname(user.getFirstname());
        currUser.setLastname(user.getLastname());
        currUser.setUsername(user.getUsername());
        currUser.setPassword(user.getPassword());
        currUser.setEmail(user.getEmail());
        userService.save(currUser);
        return new ResponseEntity<>(currUser, HttpStatus.OK);
    }

    //for testing purposes
    @GetMapping("/getAllUsers")
//    @PreAuthorize("hasRole(T(com.ubt.app.security.ApplicationUserRole).USER.name())")
    public ResponseEntity<List<User>> listAllUsers() {
        logger.info("List of all users");
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            logger.info("There are no users");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
