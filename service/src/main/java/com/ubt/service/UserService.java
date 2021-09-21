package com.ubt.service;

import com.ubt.model.User;
import com.ubt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(int id) {
        return userRepository.findById(id);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }


    public void save(User u){
        User user = new User();
        if (u.getUuid() == null){
            user.setUuid(UUID.randomUUID());
        }
        user.setUuid(u.getUuid());
        user.setUsername(u.getUsername());
        user.setFirstname(u.getFirstname());
        user.setLastname(u.getLastname());
        user.setPassword(passwordEncoder.encode(u.getPassword()));
        user.setEmail(u.getEmail());
        userRepository.save(user);
    }
}
