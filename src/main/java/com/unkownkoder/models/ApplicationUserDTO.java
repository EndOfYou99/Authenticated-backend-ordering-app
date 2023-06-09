package com.unkownkoder.models;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class ApplicationUserDTO {
	private Integer userId;
	private String username;
	private Date birthDate;
	private String country;
	private double weight;
	private List<OrdersDTO> orders;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public List<OrdersDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrdersDTO> orders) {
		this.orders = orders;
	}

	public static List<OrdersDTO> mapOrdersToDTO(List<Orders> orders) {
		List<OrdersDTO> ordersDTOList = new LinkedList<>();
		for (Orders order : orders) {
			OrdersDTO orderDTO = new OrdersDTO();
			orderDTO.setOrderId(order.getOrderId());
			// Map other fields as needed
			ordersDTOList.add(orderDTO);
		}
		return ordersDTOList;
	}

}
