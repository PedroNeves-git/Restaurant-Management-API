package br.com.Restaurant.Management.API.restaurant.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Entity
@Table(name = "restaurant")
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    @Column(name = "opening_hours")
    private LocalTime openingTime;
    @Column(name = "closing_time")
    private LocalTime closingTime;
    private Long cuisineTypeId;
    @Column(name = "user_id")
    private Long restaurantOwnerId;

}
