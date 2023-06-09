package com.unkownkoder.factory;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unkownkoder.models.ApplicationUser;
import com.unkownkoder.models.ApplicationUserDTO;
import com.unkownkoder.models.OrdersDTO;

@Component
public class ApplicationUserFactory {

	@Autowired
	private OrdersFactory ordersFactory;

	public static ApplicationUserDTO toEntity(ApplicationUser entity) {
		ApplicationUserDTO dto = new ApplicationUserDTO();
		dto.setUserId(entity.getUserId());
		dto.setUsername(entity.getUsername());
		dto.setBirthDate(entity.getBirthDate());
		dto.setCountry(entity.getCountry());
		dto.setWeight(entity.getWeight());

		List<OrdersDTO> ordersDTOList = ApplicationUserDTO.mapOrdersToDTO(entity.getOrders());
		dto.setOrders(ordersDTOList);
		return dto;
	}

	public static List<ApplicationUserDTO> toEntities(List<ApplicationUser> entities) {
		List<ApplicationUserDTO> dtos = new LinkedList<>();
		for (ApplicationUser entity : entities) {
			dtos.add(toEntity(entity));
		}
		return dtos;
	}
}