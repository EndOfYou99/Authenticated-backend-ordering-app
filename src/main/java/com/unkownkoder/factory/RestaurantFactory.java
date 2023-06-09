package com.unkownkoder.factory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.unkownkoder.models.MenuItemDTO;
import com.unkownkoder.models.Restaurant;
import com.unkownkoder.models.RestaurantDTO;

@Component
public class RestaurantFactory {

	public static RestaurantDTO toEntity(Restaurant restaurant) {
		RestaurantDTO restaurantDTO = new RestaurantDTO();
		restaurantDTO.setId(restaurant.getId());
		restaurantDTO.setLogo(restaurant.getLogo());
		restaurantDTO.setName(restaurant.getName());
		restaurantDTO.setLocation(restaurant.getLocation());
		restaurantDTO.setOpen(restaurant.getIsOpen());
		restaurantDTO.setCreationDate(restaurant.getCreationDate());

		List<MenuItemDTO> menuItems = MenuItemFactory.toEntities(restaurant.getMenu());
		restaurantDTO.setMenuItems(menuItems);

		return restaurantDTO;
	}

	public static List<RestaurantDTO> toEntities(List<Restaurant> restaurants) {
		return restaurants.stream().map(RestaurantFactory::toEntity).collect(Collectors.toList());
	}

	public static Page<RestaurantDTO> convertToPage(List<Restaurant> restaurants, Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<RestaurantDTO> restaurantDTOs;

		if (startItem >= restaurants.size()) {
			restaurantDTOs = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, restaurants.size());
			List<Restaurant> sublist = restaurants.subList(startItem, toIndex);
			restaurantDTOs = sublist.stream().map(RestaurantFactory::toEntity).collect(Collectors.toList());
		}

		return new PageImpl<>(restaurantDTOs, pageable, restaurants.size());
	}

}