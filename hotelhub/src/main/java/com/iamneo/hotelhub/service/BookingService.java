package com.iamneo.hotelhub.service;

import com.iamneo.hotelhub.impl.BookingRequest;
import com.iamneo.hotelhub.model.Bookings;
import com.iamneo.hotelhub.model.Hotels;
import com.iamneo.hotelhub.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    @Autowired
    private final BookingRepository bookingRepository;

    @Autowired
    private final HotelService hotelService;

    public ResponseEntity<String> bookHotel(BookingRequest bookingRequest) {
        // Check if the hotel with the given ID exists
        Optional<Hotels> optionalHotel = hotelService.getHotelById(bookingRequest.getHotelId());
        if (optionalHotel.isEmpty()) {
            return ResponseEntity.badRequest().body("Hotel not found.");
        }

        Hotels hotel = optionalHotel.get();

        // Validate the number of rooms requested
        if (bookingRequest.getNumberOfRooms() <= 0 || bookingRequest.getNumberOfRooms() > hotel.getAvailableRooms()) {
            return ResponseEntity.badRequest().body("Invalid number of rooms requested.");
        }

        // Create a new booking
        Bookings booking = new Bookings();
        booking.setUserEmail(bookingRequest.getUserEmail());
        booking.setHotel(hotel);
        booking.setCheckInDate(bookingRequest.getCheckInDate());
        booking.setCheckOutDate(bookingRequest.getCheckOutDate());
        booking.setNumberOfRooms(bookingRequest.getNumberOfRooms());
        booking.setCostOfRooms(bookingRequest.getCostOfRooms());
        // Set other booking details (you can include payment-related information here)

        // Save the booking to the database
        bookingRepository.save(booking);

        // Update the available rooms count in the hotel
        hotel.setAvailableRooms(hotel.getAvailableRooms() - bookingRequest.getNumberOfRooms());
        hotelService.saveHotels(hotel);

        return ResponseEntity.ok("Booking successful!");
    }

    public boolean deleteBooking(Long bookingId) {
        // Check if the booking with the specified ID exists
        Optional<Bookings> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            // Get the booking object
            Bookings bookingEntity = booking.get();

            // Retrieve the associated hotel
            Hotels hotel = bookingEntity.getHotel();

            // Update the available rooms count in the hotel
            hotel.setAvailableRooms(hotel.getAvailableRooms() + bookingEntity.getNumberOfRooms());
            hotelService.saveHotels(hotel);

            // Delete the booking from the database
            bookingRepository.deleteById(bookingId);

            return true; // Deletion successful
        } else {
            return false; // Booking not found or couldn't be deleted
        }
    }

}
