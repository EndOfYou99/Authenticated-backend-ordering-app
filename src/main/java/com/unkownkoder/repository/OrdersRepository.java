package com.unkownkoder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unkownkoder.models.ApplicationUser;
import com.unkownkoder.models.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
	List<Orders> findAllByUser(ApplicationUser user);
}