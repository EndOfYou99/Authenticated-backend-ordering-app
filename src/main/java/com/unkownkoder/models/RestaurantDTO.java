package com.unkownkoder.models;

import java.sql.Date;
import java.util.List;

public class RestaurantDTO {

	private Long id;
	private String logo;
	private String name;
	private String location;
	private boolean isOpen;
	private Date creationDate;
	private List<MenuItemDTO> menuItems;

	public RestaurantDTO() {
		super();
	}

	public RestaurantDTO(Long id, String logo, String name, String location, boolean isOpen,
			List<MenuItemDTO> menuItems) {
		super();
		this.id = id;
		this.logo = logo;
		this.name = name;
		this.location = location;
		this.isOpen = isOpen;
		this.menuItems = menuItems;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public List<MenuItemDTO> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItemDTO> menuItems) {
		this.menuItems = menuItems;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "RestaurantDTO [id=" + id + ", logo=" + logo + ", name=" + name + ", location=" + location + ", isOpen="
				+ isOpen + ", menuItems=" + menuItems + "]";
	}

}
