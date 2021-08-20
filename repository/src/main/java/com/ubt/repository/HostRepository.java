package com.ubt.repository;

import com.ubt.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostRepository extends JpaRepository<Host, Integer> {
    Host findById(int id);

    Host findByEmail(String email);

}
