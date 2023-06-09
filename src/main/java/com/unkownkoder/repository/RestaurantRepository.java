package com.unkownkoder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unkownkoder.models.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	// Optional<Restaurant> findByName(String name);

	Optional<Restaurant> findById(Long id);

	// void deleteByName(String name);

	void deleteById(Long id);

	Restaurant getRestaurantByName(String name);

	Restaurant findRestaurantById(Long restaurantId);

}
