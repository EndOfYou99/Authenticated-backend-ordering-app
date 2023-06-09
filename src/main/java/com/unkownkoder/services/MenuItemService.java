package com.unkownkoder.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unkownkoder.models.MenuItem;
import com.unkownkoder.models.Restaurant;
import com.unkownkoder.repository.MenuItemRepository;
import com.unkownkoder.repository.RestaurantRepository;

@Service
public class MenuItemService {

	private final MenuItemRepository menuItemRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	public MenuItemService(MenuItemRepository menuItemRepository) {
		this.menuItemRepository = menuItemRepository;
	}

	public MenuItem createMenuItem(MenuItem menuItem) {
		return menuItemRepository.save(menuItem);

	}

	public MenuItem updateMenuItem(Long id, MenuItem updatedMenuItem) {
		Optional<MenuItem> existingMenuItem = menuItemRepository.findById(id);
		if (existingMenuItem.isPresent()) {
			MenuItem menuItem = existingMenuItem.get();
			// Update the menuItem fields with the updatedMenuItem fields
			menuItem.setName(updatedMenuItem.getName());
			menuItem.setLogo(updatedMenuItem.getLogo());
			menuItem.setDescription(updatedMenuItem.getDescription());
			menuItem.setPreparingTime(updatedMenuItem.getPreparingTime());
			menuItem.setQuantity(updatedMenuItem.getQuantity());

			Restaurant existingRestaurant = menuItem.getRestaurant();
			Restaurant updatedRestaurant = updatedMenuItem.getRestaurant();

			// Update the restaurant fields if the updated restaurant is not null
			if (updatedRestaurant != null) {
				existingRestaurant.setName(updatedRestaurant.getName());
				// Update other restaurant fields as needed
			}

			return menuItemRepository.save(menuItem);
		} else {
			throw new IllegalArgumentException("MenuItem not found");
		}
	}

	public void deleteMenuItem(Long id) {
		menuItemRepository.deleteById(id);
	}

	public Optional<MenuItem> getMenuItemById(Long id) {
		return menuItemRepository.findById(id);
	}

	public List<MenuItem> getAllMenuItemsByRestaurant(Long restaurantId) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
		if (restaurant.isPresent()) {
			Restaurant newR = restaurant.get();
			return menuItemRepository.findAllByRestaurant(newR);
		} else {
			return null;
		}
	}

	public List<MenuItem> getAllMenuItems() {
		return menuItemRepository.findAll();
	}
}
