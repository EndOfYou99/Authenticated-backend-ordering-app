package com.unkownkoder.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class MenuItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 500000)
	private String logo;

	@Column(length = 50, unique = true)
	private String name;

	@Column(length = 500)
	private String description;

	private int preparingTime;

	private double quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id")
	Restaurant restaurant;

	@ManyToMany(mappedBy = "menuItems", cascade = CascadeType.ALL)
	private Set<Orders> orders = new HashSet<>();

	public MenuItem() {
		super();
	}

	public MenuItem(String logo, String name, String description, int preparingTime, double quantity,
			Restaurant restaurant) {
		super();
		this.logo = logo;
		this.name = name;
		this.description = description;
		this.preparingTime = preparingTime;
		this.quantity = quantity;
		this.restaurant = restaurant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPreparingTime() {
		return preparingTime;
	}

	public void setPreparingTime(int preparingTime) {
		this.preparingTime = preparingTime;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}

}
