package com.iamneo.hotelhub.repository;
import com.iamneo.hotelhub.model.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HotelRepository extends JpaRepository<Hotels,Integer> {

}
