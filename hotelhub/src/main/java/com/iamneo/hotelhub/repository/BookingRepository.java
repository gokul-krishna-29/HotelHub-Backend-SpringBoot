package com.iamneo.hotelhub.repository;

import com.iamneo.hotelhub.model.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Bookings,Long> {
    List<Bookings> findByUserEmail(String userEmail);
    void deleteById(Long id);
}
