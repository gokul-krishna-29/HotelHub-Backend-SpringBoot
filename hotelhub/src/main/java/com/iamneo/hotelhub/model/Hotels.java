package com.iamneo.hotelhub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotels")

public class Hotels{
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String location;
    private String description;
    private Integer availableRooms;
    private Double price;
    private Double rating;

}
