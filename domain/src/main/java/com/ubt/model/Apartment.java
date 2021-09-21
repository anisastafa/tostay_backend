package com.ubt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Apartment", schema = "public")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int apartment_id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Host host;

    @OneToMany(mappedBy = "apartment")
//            orphanRemoval = true)
    private List<Booking> BookingList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Media> mediaList;

    @Column(name = "apartment_name")
    private String apartment_name;

    @Column(name = "description")
    private String description;

    @Column(name = "total_bedrooms")
    private int total_bedrooms;

    @Column(name = "total_bathrooms")
    private int total_bathrooms;

    @Column(name = "has_tv")
    private boolean has_tv;

    @Column(name = "has_internet")
    private boolean has_internet;

    @Column(name = "has_heating")
    private boolean has_heating;

    @Column(name = "price_per_night")
    private double price_per_night;

    @Column(name = "booked")
    private Boolean booked;

}
