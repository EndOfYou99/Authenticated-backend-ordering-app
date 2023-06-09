package com.unkownkoder.controllers;

import java.util.List;
import java.util.Optional;

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

import com.unkownkoder.factory.MenuItemFactory;
import com.unkownkoder.models.MenuItem;
import com.unkownkoder.models.MenuItemDTO;
import com.unkownkoder.models.Restaurant;
import com.unkownkoder.services.MenuItemService;
import com.unkownkoder.services.RestaurantService;

@RestController
@RequestMapping("/menuItems")
@CrossOrigin(origins = "http://localhost:3000")
public class MenuItemController {

	@Autowired
	private MenuItemService menuItemService;

	@Autowired
	private MenuItemFactory menuItemFactory;

	@Autowired
	private RestaurantService restaurantService;

	@GetMapping("rest/{restaurantId}")
	public ResponseEntity<List<MenuItemDTO>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
		List<MenuItem> menuItems = menuItemService.getAllMenuItemsByRestaurant(restaurantId);
		List<MenuItemDTO> menuItemsDto = menuItemFactory.toEntities(menuItems);
		return ResponseEntity.ok(menuItemsDto);
	}

	@PostMapping("/{restaurantId}")
	public ResponseEntity<MenuItemDTO> createMenuItem(@RequestBody MenuItem menuItem, @PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		menuItem.setRestaurant(restaurant);
		MenuItem createdMenuItem = menuItemService.createMenuItem(menuItem);
		MenuItemDTO menuItemDto = menuItemFactory.toEntity(createdMenuItem);
		return ResponseEntity.status(HttpStatus.CREATED).body(menuItemDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MenuItemDTO> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
		Optional<MenuItem> existingMenuItem = menuItemService.getMenuItemById(id);
		if (existingMenuItem.isPresent()) {
			menuItem.setId(id);
			MenuItem updatedMenuItem = menuItemService.updateMenuItem(id, menuItem);
			MenuItemDTO menuItemDto = menuItemFactory.toEntity(updatedMenuItem);
			return ResponseEntity.ok(menuItemDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
		Optional<MenuItem> existingMenuItem = menuItemService.getMenuItemById(id);
		if (existingMenuItem.isPresent()) {
			menuItemService.deleteMenuItem(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Long id) {
		Optional<MenuItem> existingMenuItem = menuItemService.getMenuItemById(id);
		if (existingMenuItem.isPresent()) {
			MenuItem menuItem = existingMenuItem.get();
			MenuItemDTO menuItemDto = menuItemFactory.toEntity(menuItem);
			return ResponseEntity.ok(menuItemDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity<Page<MenuItemDTO>> getAllMenuItems(Pageable pageable) {
		List<MenuItem> menuItems = menuItemService.getAllMenuItems();
		Page<MenuItemDTO> menuItemDtos = menuItemFactory.convertToPage(menuItems, pageable);
		return ResponseEntity.ok(menuItemDtos);
	}
}