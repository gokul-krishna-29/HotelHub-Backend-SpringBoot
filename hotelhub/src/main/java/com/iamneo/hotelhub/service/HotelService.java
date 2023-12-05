package com.iamneo.hotelhub.service;

import com.iamneo.hotelhub.repository.HotelRepository;
import com.iamneo.hotelhub.model.Hotels;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {

    @Autowired
    private final HotelRepository hotelRepository;

    public List<Hotels> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotels> getHotelById(Integer hotelId) {
        return hotelRepository.findById(hotelId);
    }

    public Hotels saveHotels(Hotels hotel) {
        return hotelRepository.save(hotel);
    }

    // You can add more service methods here if needed
}
