package com.iamneo.hotelhub.controller;

import com.iamneo.hotelhub.model.Hotels;
import com.iamneo.hotelhub.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HotelController {

    @Autowired
    private final HotelService hotelService;

    @GetMapping("/hotels")
    public List<Hotels> getAllHotels() {
        return hotelService.getAllHotels();
    }
    // Add more endpoints and methods as needed
}
