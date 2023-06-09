package com.unkownkoder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unkownkoder.factory.RestaurantFactory;
import com.unkownkoder.models.Restaurant;
import com.unkownkoder.models.RestaurantDTO;
import com.unkownkoder.services.RestaurantService;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "http://localhost:3000")
public class RestaurantController {

	private final RestaurantService restaurantService;
	@Autowired
	RestaurantFactory restaurantFactory;

	@Autowired
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	@GetMapping()
	public ResponseEntity<Page<RestaurantDTO>> getAllRestaurants(Pageable pageable) {
		Page<Restaurant> restaurantPage = restaurantService.getAllRestaurants(pageable);
		Page<RestaurantDTO> restaurantDTOPage = RestaurantFactory.convertToPage(restaurantPage.getContent(), pageable);
		return ResponseEntity.ok(restaurantDTOPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
		Restaurant restaurant = restaurantService.getRestaurantById(id);
		if (restaurant != null) {
			RestaurantDTO restaurantDto = restaurantFactory.toEntity(restaurant);
			System.out.println("hello");
			return ResponseEntity.ok(restaurantDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("name/{name}")
	public ResponseEntity<RestaurantDTO> getRestaurantByName(@PathVariable String name) {
		Restaurant restaurant = restaurantService.getRestaurantByName(name);
		if (restaurant != null) {
			RestaurantDTO restaurantDto = RestaurantFactory.toEntity(restaurant);
			return ResponseEntity.ok(restaurantDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
		Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id,
			@RequestBody Restaurant updatedRestaurant) {
		Restaurant restaurant = restaurantService.updateRestaurant(id, updatedRestaurant);
		if (restaurant != null) {
			return ResponseEntity.ok(restaurant);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
		restaurantService.deleteRestaurant(id);
		return ResponseEntity.noContent().build();
	}
}