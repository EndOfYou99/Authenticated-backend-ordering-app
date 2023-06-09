package com.unkownkoder.factory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.unkownkoder.models.MenuItemDTO;
import com.unkownkoder.models.Orders;
import com.unkownkoder.models.OrdersDTO;

@Component
public class OrdersFactory {

	public OrdersDTO toEntity(Orders orders) {
		OrdersDTO ordersDTO = new OrdersDTO();
		ordersDTO.setOrderId(orders.getOrderId());
		ordersDTO.setUser(orders.getUser());
		List<MenuItemDTO> menuItemDTOs = MenuItemFactory.toEntities(orders.getMenuItems());
		ordersDTO.setMenuItems(menuItemDTOs);
		return ordersDTO;
	}

	public Page<OrdersDTO> convertToPage(List<Orders> ordersList, Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<OrdersDTO> ordersDTOs;

		if (ordersList.size() < startItem) {
			ordersDTOs = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, ordersList.size());
			List<Orders> sublist = ordersList.subList(startItem, toIndex);
			ordersDTOs = sublist.stream().map(this::toEntity).collect(Collectors.toList());
		}

		return new PageImpl<>(ordersDTOs, pageable, ordersList.size());
	}

	public List<OrdersDTO> toEntities(List<Orders> orders) {
		List<OrdersDTO> result = new LinkedList<>();
		for (Orders order : orders) {
			OrdersDTO orderDTO = toEntity(order);
			result.add(orderDTO);
		}
		return result;
	}
}