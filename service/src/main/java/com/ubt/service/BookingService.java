package com.ubt.service;

import com.ubt.model.Booking;
import com.ubt.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public Booking getById(int id) {
        return bookingRepository.findById(id);
    }

    public void deleteById(int id) {
        bookingRepository.deleteById(id);
    }

    public void save(Booking b) {
        Booking booking = new Booking();

        booking.setUser(b.getUser());
        booking.setApartment(b.getApartment());
        booking.setStart_date(b.getStart_date());
        booking.setEnd_date(b.getEnd_date());
        booking.setNum_nights(b.getNum_nights());
        booking.setTotal_price(b.getTotal_price());

        bookingRepository.save(booking);
    }
}
