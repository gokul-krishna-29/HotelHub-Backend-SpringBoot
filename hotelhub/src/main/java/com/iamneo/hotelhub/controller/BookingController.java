package com.iamneo.hotelhub.controller;

import com.iamneo.hotelhub.impl.BookingRequest;
import com.iamneo.hotelhub.model.Bookings;
import com.iamneo.hotelhub.repository.BookingRepository;
import com.iamneo.hotelhub.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/user/{userEmail}")
    public ResponseEntity<List<Bookings>> getUserBookings(@PathVariable String userEmail) {
        List<Bookings> userBookings = bookingRepository.findByUserEmail(userEmail);
        return ResponseEntity.ok(userBookings);
    }



    @PostMapping("/bookHotel")
    public ResponseEntity<String> bookHotel(@RequestBody BookingRequest bookingRequest) {
        return bookingService.bookHotel(bookingRequest);
    }


    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
        // Call the service to delete the booking
        boolean deleted = bookingService.deleteBooking(bookingId);

        if (deleted) {
            return ResponseEntity.ok("Booking deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found or couldn't be deleted.");
        }
    }


}
