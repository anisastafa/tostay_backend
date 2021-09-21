package com.ubt.app.controllers;


import com.ubt.app.util.Utils;
import com.ubt.model.Apartment;
import com.ubt.model.Booking;
import com.ubt.model.User;
import com.ubt.service.ApartmentService;
import com.ubt.service.BookingService;
import com.ubt.service.UserService;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(ApartmentController.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApartmentService apartmentService;

    // Save booking with specific start_date and when it reaches end_date remove that booking
    @PostMapping(value = "/saveBooking/{apartment_id}/startDate/{start_date}/endDate/{end_date}",
            produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveBooking(@RequestBody Booking booking,
                                         @PathVariable("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start_date,
                                         @PathVariable("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end_date,
                                         @PathVariable("apartment_id") int apartment_id,
                                         Principal principal) {
        logger.info("Creating apartment: {}", booking);

        if (bookingService.getById(booking.getApartment().getApartment_id()) == null) {
            logger.error("Apartment with id: " + booking.getBooking_ID() + " already exists.");
            return new ResponseEntity<>(new Utils("enable to create apartment with id"
                    + booking.getBooking_ID()), HttpStatus.CONFLICT);
        }
        String username = principal.getName();
        User user = userService.getByUsername(username);
        booking.setUser(user);
        booking.setStart_date(start_date);
        booking.setEnd_date(end_date);
        Apartment apartment = apartmentService.getById(apartment_id);

        apartment.setBooked(true);

        booking.setApartment(apartment);
        bookingService.save(booking);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/allBookings")
    public ResponseEntity<List<Booking>> getAllBookings(Principal principal) {
        logger.info("List all the bookings");
        String username = principal.getName();
        User user = userService.getByUsername(username);
        List<Booking> bookings = user.getBookingList();
        if (bookings.isEmpty()) {
            logger.info("There are no bookings");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBooking/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") int id) {
        logger.info("Delete booking by id: " + id);
        Booking booking = bookingService.getById(id);
        if (booking == null) {
            return new ResponseEntity<>("Booking doesnt exist or null value", HttpStatus.NOT_FOUND);
        }
        bookingService.deleteById(id);
        return new ResponseEntity<Booking>(HttpStatus.NO_CONTENT);
    }
}
