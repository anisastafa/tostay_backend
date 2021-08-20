package com.ubt.service;

import com.ubt.model.Apartment;
import com.ubt.model.Host;
import com.ubt.repository.ApartmentRepository;
import com.ubt.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class HostService {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Host> getAll() {
        return hostRepository.findAll();
    }

    public Host getByEmail(String email) {
        return hostRepository.findByEmail(email);
    }

    public Host getById(int id) {
        return hostRepository.findById(id);
    }

    public void save(Host h) {
        Host host = new Host();
        if (host.getUuid() == null) {
            h.setUuid(UUID.randomUUID());
        }

        host.setUuid(h.getUuid());
        host.setFirstname(h.getFirstname());
        host.setLastname(h.getLastname());
        host.setPassword(passwordEncoder.encode(h.getPassword()));
        host.setUsername(h.getUsername());
        host.setEmail(h.getEmail());
        hostRepository.save(host);
    }

    public void deleteById(int id) {
        hostRepository.deleteById(id);
    }
}
