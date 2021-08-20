package com.ubt.service;

import com.ubt.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ubt.repository.ApartmentRepository;

import java.util.List;

@Service
public class ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Apartment getById(int id) {
        return apartmentRepository.findById(id);
    }

    public void deleteById(int id) {
        apartmentRepository.deleteById(id);
    }

    public void save(Apartment a) {
        Apartment apartment = new Apartment();

        apartment.setHost(a.getHost());
        apartment.setLocation(a.getLocation());
        apartment.setMedia(a.getMedia());
        apartment.setTotal_price(a.getTotal_price());
        apartment.setTotal_bedrooms(a.getTotal_bedrooms());
        apartment.setTotal_bathrooms(a.getTotal_bathrooms());
        apartment.setPrice_per_night(a.getPrice_per_night());
        apartment.setNum_nights(a.getNum_nights());
        apartment.setHas_tv(a.isHas_tv());
        apartment.setHas_internet(a.isHas_internet());
        apartment.setHas_heating(a.isHas_heating());
        apartment.setBooked(a.getBooked());

        apartmentRepository.save(apartment);
    }

}
