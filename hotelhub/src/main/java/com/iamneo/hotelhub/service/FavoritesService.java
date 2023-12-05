package com.iamneo.hotelhub.service;

import com.iamneo.hotelhub.model.Favorites;
import com.iamneo.hotelhub.model.Hotels;
import com.iamneo.hotelhub.model.Users;
import com.iamneo.hotelhub.repository.FavoritesRepository;
import com.iamneo.hotelhub.repository.HotelRepository;
import com.iamneo.hotelhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    @Autowired
    public FavoritesService(FavoritesRepository favoritesRepository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.favoritesRepository = favoritesRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    // Add a hotel to a user's favorites
    public void addFavorite(String userEmail, Long hotelId) {
        Optional<Users> user = userRepository.findByEmail(userEmail);
        Optional<Hotels> hotel = hotelRepository.findById(Math.toIntExact(hotelId));

        if (user.isPresent() && hotel.isPresent()) {
            if (!favoritesRepository.existsByUserAndHotel(user.get(), hotel.get())) {
                Favorites favorite = new Favorites();
                favorite.setUser(user.get());
                favorite.setHotel(hotel.get());
                favoritesRepository.save(favorite);
            }
        }
    }

    @Transactional
    public void removeFavorite(String userEmail, Long hotelId) {
        Optional<Users> user = userRepository.findByEmail(userEmail);

        if (user.isEmpty()) {
            // Handle the case where the user is not found
            // You can throw an exception, log an error, or handle it in an appropriate way.
            return;
        }

        Optional<Hotels> hotel = hotelRepository.findById(Math.toIntExact(hotelId));

        if (hotel.isEmpty()) {
            // Handle the case where the hotel is not found
            // You can throw an exception, log an error, or handle it in an appropriate way.
            return;
        }

        Favorites favorite = favoritesRepository.findByUserAndHotel(user.get(), hotel.get());

        if (favorite != null) {
            // Delete the specific favorite record for the user and hotel
            favoritesRepository.delete(favorite);
        }
    }





    // Get a user's favorite hotels
    public List<Hotels> getFavoriteHotels(String userEmail) {
        Optional<Users> user = userRepository.findByEmail(userEmail);
        if (user.isPresent()) {
            List<Favorites> userFavorites = favoritesRepository.findByUser(user.get());
            return userFavorites.stream()
                    .map(Favorites::getHotel)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
