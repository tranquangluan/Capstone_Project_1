package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
