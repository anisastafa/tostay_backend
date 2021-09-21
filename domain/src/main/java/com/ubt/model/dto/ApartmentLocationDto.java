package com.ubt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApartmentLocationDto {

    private int apartment_id;
    private String apartment_name;
    private double price_per_night;
    private int total_bedrooms;
    private int total_bathrooms;
    private boolean has_tv;
    private boolean has_internet;
    private boolean has_heating;
    private Boolean booked;
    private MultipartFile file;
    private String country;
    private String city;
}
