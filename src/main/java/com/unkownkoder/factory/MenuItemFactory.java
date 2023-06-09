package com.unkownkoder.factory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.unkownkoder.models.MenuItem;
import com.unkownkoder.models.MenuItemDTO;

@Component
public class MenuItemFactory {

	public static MenuItemDTO toEntity(MenuItem menuItem) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		menuItemDTO.setId(menuItem.getId());
		menuItemDTO.setLogo(menuItem.getLogo());
		menuItemDTO.setName(menuItem.getName());
		menuItemDTO.setDescription(menuItem.getDescription());
		menuItemDTO.setPreparingTime(menuItem.getPreparingTime());
		menuItemDTO.setQuantity(menuItem.getQuantity());

		menuItemDTO.setRestaurantId(menuItem.getRestaurant().getId());
		return menuItemDTO;
	}

	public static List<MenuItemDTO> toEntities(List<MenuItem> menuItems) {
		List<MenuItemDTO> result = new LinkedList<>();
		for (MenuItem menuItem : menuItems) {
			MenuItemDTO menuItemDTO = toEntity(menuItem);
			result.add(menuItemDTO);
		}
		return result;
	}

	public Page<MenuItemDTO> convertToPage(List<MenuItem> menuItems, Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<MenuItemDTO> menuItemDTOs;

		if (menuItems.size() < startItem) {
			menuItemDTOs = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, menuItems.size());
			List<MenuItem> sublist = menuItems.subList(startItem, toIndex);
			menuItemDTOs = sublist.stream().map(MenuItemFactory::toEntity).collect(Collectors.toList());
		}

		return new PageImpl<>(menuItemDTOs, pageable, menuItems.size());
	}

}