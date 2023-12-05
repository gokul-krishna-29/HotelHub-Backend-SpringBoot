package com.iamneo.hotelhub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Bookings {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long bookingId;

        private String userEmail;

        @ManyToOne
        private Hotels hotel;

        @Column(name = "check_in_date")
        private LocalDate checkInDate;

        @Column(name = "check_out_date")
        private LocalDate checkOutDate;

        @Column(name = "number_of_rooms")
        private int numberOfRooms;

        @Column(name="cost_of_rooms")
        private Double costOfRooms;
}
