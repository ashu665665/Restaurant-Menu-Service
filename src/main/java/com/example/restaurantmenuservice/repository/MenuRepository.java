package com.example.restaurantmenuservice.repository;

import com.example.restaurantmenuservice.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurantId(Long restaurantId);

    Optional<MenuItem> findByIdAndRestaurantId(Long id, Long restaurantId);

    List<MenuItem> findByRestaurantIdAndCategory(Long id, String category);
}

