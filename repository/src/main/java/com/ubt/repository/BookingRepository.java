package com.ubt.repository;

import com.ubt.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking findById(int id);

    List<Booking> findAll();
}
