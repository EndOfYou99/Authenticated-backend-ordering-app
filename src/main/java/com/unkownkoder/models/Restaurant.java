package com.unkownkoder.models;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 500)
	private String logo;

	@Column(length = 50, unique = true)
	private String name;

	@Column(length = 500)
	private String location;

	private Date creationDate;

	private boolean isOpen;

	@OneToMany(mappedBy = "restaurant", cascade = { CascadeType.ALL })
	List<MenuItem> menuItem;

	public Restaurant() {
		super();
	}

	public Restaurant(String logo, String name, String location, Date creationDate, boolean isOpen) {
		super();
		this.logo = logo;
		this.name = name;
		this.location = location;
		this.creationDate = creationDate;
		this.isOpen = isOpen;
	}

	public Restaurant(Long id, String logo, String name, String location, Date creationDate, boolean isOpen) {
		super();
		this.id = id;
		this.logo = logo;
		this.name = name;
		this.location = location;
		this.creationDate = creationDate;
		this.isOpen = isOpen;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public List<MenuItem> getMenu() {
		return menuItem;
	}

	public void setMenu(List<MenuItem> menu) {
		this.menuItem = menu;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public List<MenuItem> getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(List<MenuItem> menuItem) {
		this.menuItem = menuItem;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", logo=" + logo + ", name=" + name + ", location=" + location
				+ ", creationDate=" + creationDate + ", isOpen=" + isOpen + "]";
	}

}
