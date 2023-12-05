package com.iamneo.hotelhub.repository;

import com.iamneo.hotelhub.model.Favorites;
import com.iamneo.hotelhub.model.Hotels;
import com.iamneo.hotelhub.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    boolean existsByUserAndHotel(Users user, Hotels hotel);
    Favorites findByUserAndHotel(Users user, Hotels hotel);
    List<Favorites> findByUser(Users user);
    void deleteByUser(Users user);


}
