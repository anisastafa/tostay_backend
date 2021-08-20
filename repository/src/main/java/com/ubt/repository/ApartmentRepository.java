package com.ubt.repository;

import com.ubt.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
    Apartment findById(int id);
}
