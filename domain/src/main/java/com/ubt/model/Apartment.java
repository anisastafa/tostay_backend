package com.ubt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private Host host;

    @OneToMany(mappedBy = "apartment")
//            orphanRemoval = true)
    private List<Booking> BookingList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id")
    private Media media;

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

    @Column(name = "num_nights")
    private int num_nights;

    @Column(name = "total_price")
    private double total_price;

    @Column(name = "booked")
    private Boolean booked;
}
