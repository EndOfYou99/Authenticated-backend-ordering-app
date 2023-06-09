package com.unkownkoder.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unkownkoder.models.ApplicationUser;
import com.unkownkoder.models.MenuItem;
import com.unkownkoder.models.Orders;
import com.unkownkoder.repository.MenuItemRepository;
import com.unkownkoder.repository.OrdersRepository;
import com.unkownkoder.repository.UserRepository;

@Service
public class OrdersService {

	@Autowired
	private MenuItemRepository menuItemRepository;

	private final OrdersRepository ordersRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public OrdersService(OrdersRepository ordersRepository) {
		this.ordersRepository = ordersRepository;
	}

	public Orders createOrders(Orders orders) {
		return ordersRepository.save(orders);
	}

	private MenuItem findExistingMenuItem(MenuItem menuItem, List<MenuItem> existingMenuItems) {
		for (MenuItem existingItem : existingMenuItems) {
			if (existingItem.getId() != null && existingItem.getId().equals(menuItem.getId())) {
				return existingItem;
			}
		}
		return null;
	}

	public Orders updateOrders(Long id, Orders updatedOrders) {
		Optional<Orders> existingOrders = ordersRepository.findById(id);
		if (existingOrders.isPresent()) {
			Orders orders = existingOrders.get();
			orders.setUser(updatedOrders.getUser());
			orders.setMenuItems(updatedOrders.getMenuItems());

			return ordersRepository.save(orders);
		} else {
			throw new IllegalArgumentException("Orders not found");
		}
	}

	public void deleteOrders(Long id) {
		ordersRepository.deleteById(id);
	}

	public Optional<Orders> getOrdersById(Long id) {
		return ordersRepository.findById(id);
	}

	public List<Orders> getAllOrders() {
		return ordersRepository.findAll();
	}

	public List<Orders> getAllOrdersByUser(Integer userId) {
		Optional<ApplicationUser> user = userRepository.findById(userId);
		if (user.isPresent()) {
			ApplicationUser newUser = user.get();
			return ordersRepository.findAllByUser(newUser);
		} else {
			return null;
		}
	}

	public void addMenuItemToOrder(Long orderId, Long menuItemId) {
		Optional<Orders> optionalOrder = ordersRepository.findById(orderId);
		Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);

		if (optionalOrder.isPresent() && optionalMenuItem.isPresent()) {
			Orders order = optionalOrder.get();
			MenuItem menuItem = optionalMenuItem.get();

			order.getMenuItems().add(menuItem);
			menuItem.getOrders().add(order);

			ordersRepository.save(order);
			menuItemRepository.save(menuItem);
		} else {
			// Handle the case when the order or menu item is not found
		}
	}

	public Orders createEmptyOrders(int userId) {
		// Fetch the ApplicationUser from the database using the ID
		Optional<ApplicationUser> optionalUser = userRepository.findById(userId);
		if (optionalUser.isEmpty()) {
			// Handle the case when the user with the given ID does not exist
			throw new IllegalArgumentException("User not found with ID: " + userId);
		}

		ApplicationUser user = optionalUser.get();

		Orders orders = new Orders();
		orders.setUser(user);
		// Perform any additional initialization or default values for the orders
		return ordersRepository.save(orders);
	}

	public void deleteMenuItemFromOrder(Long orderId, Long menuItemId) {
		Optional<Orders> existingOrders = ordersRepository.findById(orderId);

		if (existingOrders.isPresent()) {
			Orders orders = existingOrders.get();
			List<MenuItem> menuItems = orders.getMenuItems();

			// Find the menuItem by its ID and remove it from the list
			menuItems.removeIf(menuItem -> menuItem.getId().equals(menuItemId));

			// Save the updated orders entity
			ordersRepository.save(orders);
		} else {
			System.out.println("not found");
		}
	}

}
