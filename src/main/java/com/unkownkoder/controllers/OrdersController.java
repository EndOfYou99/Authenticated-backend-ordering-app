package com.unkownkoder.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unkownkoder.factory.OrdersFactory;
import com.unkownkoder.models.Orders;
import com.unkownkoder.models.OrdersDTO;
import com.unkownkoder.services.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	private final OrdersService ordersService;
	@Autowired
	private OrdersFactory ordersFactory;

	@Autowired
	public OrdersController(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	@PostMapping("/{userId}")
	public ResponseEntity<Orders> createOrders(@PathVariable Integer userId) {
		Orders createdOrders = ordersService.createEmptyOrders(userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrders);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Orders> updateOrders(@PathVariable Long id, @RequestBody Orders orders) {
		Orders updatedOrders = ordersService.updateOrders(id, orders);
		return ResponseEntity.ok(updatedOrders);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrders(@PathVariable Long id) {
		ordersService.deleteOrders(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrdersDTO> getOrdersById(@PathVariable Long id) {
		Optional<Orders> existingOrders = ordersService.getOrdersById(id);
		if (existingOrders.isPresent()) {
			Orders orders = existingOrders.get();
			OrdersDTO ordrersDto = ordersFactory.toEntity(orders);

			return ResponseEntity.status(HttpStatus.CREATED).body(ordrersDto);

		}
		return null;

	}

	@GetMapping("user/{userId}")
	public ResponseEntity<List<OrdersDTO>> getAllOrdersByUser(@PathVariable Integer userId) {
		List<Orders> orders = ordersService.getAllOrdersByUser(userId);
		List<OrdersDTO> ordersDto = ordersFactory.toEntities(orders);
		return ResponseEntity.ok(ordersDto);
	}

	@GetMapping
	public ResponseEntity<List<OrdersDTO>> getAllOrders() {
		List<Orders> ordersList = ordersService.getAllOrders();
		List<OrdersDTO> ordersDto = ordersFactory.toEntities(ordersList);
		return ResponseEntity.ok(ordersDto);
	}

	@PostMapping("/{orderId}/{menuItemId}")
	public ResponseEntity<String> addMenuItemToOrder(@PathVariable Long orderId, @PathVariable Long menuItemId) {
		ordersService.addMenuItemToOrder(orderId, menuItemId);
		return ResponseEntity.ok("Menu item added to order successfully");
	}

	@DeleteMapping("/{orderId}/menuItems/{menuItemId}")
	public ResponseEntity<String> deleteMenuItemFromOrder(@PathVariable Long orderId, @PathVariable Long menuItemId)
			throws NotFoundException {
		try {
			ordersService.deleteMenuItemFromOrder(orderId, menuItemId);
			return ResponseEntity.ok("MenuItem deleted from order successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
