package com.ubt.repository;

import com.ubt.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    Location findById(int id);

    List<Location> findLocationByCity(String city);
}
