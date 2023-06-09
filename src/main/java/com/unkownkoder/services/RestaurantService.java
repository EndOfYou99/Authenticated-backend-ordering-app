package com.unkownkoder.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unkownkoder.models.Restaurant;
import com.unkownkoder.repository.RestaurantRepository;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	public Page<Restaurant> getAllRestaurants(Pageable pageable) {
		return restaurantRepository.findAll(pageable);
	}

	public Restaurant getRestaurantById(Long id) {
		Optional<Restaurant> existingRestaurant = restaurantRepository.findById(id);
		if (existingRestaurant.isPresent()) {
			Restaurant restaurant = existingRestaurant.get();
			return restaurant;
		}
		return null;

	}

	public Restaurant createRestaurant(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}

	public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
		Optional<Restaurant> existingRestaurant = restaurantRepository.findById(id);
		if (existingRestaurant.isPresent()) {
			Restaurant restaurant = existingRestaurant.get();
			restaurant.setLogo(updatedRestaurant.getLogo());
			restaurant.setName(updatedRestaurant.getName());
			restaurant.setLocation(updatedRestaurant.getLocation());
			restaurant.setCreationDate(updatedRestaurant.getCreationDate());
			restaurant.setIsOpen(updatedRestaurant.getIsOpen());
			return restaurantRepository.save(restaurant);
		} else {
			throw new IllegalArgumentException("Restaurant not found");
		}
	}

	public void deleteRestaurant(Long id) {
		restaurantRepository.deleteById(id);
	}

	public Restaurant getRestaurantByName(String name) {
		return restaurantRepository.getRestaurantByName(name);
	}
}