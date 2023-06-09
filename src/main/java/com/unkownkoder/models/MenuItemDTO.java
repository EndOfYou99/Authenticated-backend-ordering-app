package com.unkownkoder.models;

public class MenuItemDTO {

	private Long id;
	private String logo;
	private String name;
	private String description;
	private int preparingTime;
	private double quantity;
	private Long restaurantId;

	public MenuItemDTO() {
		super();
	}

	public MenuItemDTO(String logo, String name, String description, int preparingTime, double quantity,
			Restaurant restaurant) {
		super();
		this.logo = logo;
		this.name = name;
		this.description = description;
		this.preparingTime = preparingTime;
		this.quantity = quantity;
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

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

}
