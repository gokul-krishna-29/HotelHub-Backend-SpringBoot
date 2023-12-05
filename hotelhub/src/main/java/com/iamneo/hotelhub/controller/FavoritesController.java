package com.iamneo.hotelhub.controller;

import com.iamneo.hotelhub.model.Hotels;
import com.iamneo.hotelhub.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;

    @Autowired
    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFavorite(@RequestParam String userEmail, @RequestParam Long hotelId) {
        favoritesService.addFavorite(userEmail, hotelId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Hotel added to favorites successfully.");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFavorite(@RequestParam String userEmail, @RequestParam Long hotelId) {
        favoritesService.removeFavorite(userEmail, hotelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getFavorites(@RequestParam String userEmail) {
        List<Hotels> favoriteHotels = favoritesService.getFavoriteHotels(userEmail);
        return ResponseEntity.ok(favoriteHotels);
    }
}
