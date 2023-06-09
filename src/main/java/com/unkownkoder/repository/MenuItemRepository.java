package com.unkownkoder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unkownkoder.models.MenuItem;
import com.unkownkoder.models.Restaurant;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

	Optional<MenuItem> findById(Long id);

	List<MenuItem> findAllByRestaurant(Restaurant restaurant);

}
