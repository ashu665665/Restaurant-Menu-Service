package com.example.restaurantmenuservice.controller;

import com.example.restaurantmenuservice.entity.MenuItem;
import com.example.restaurantmenuservice.entity.Restaurant;
import com.example.restaurantmenuservice.exception.ResourceNotFoundException;
import com.example.restaurantmenuservice.repository.MenuRepository;
import com.example.restaurantmenuservice.repository.RestaurantRepository;
import com.example.restaurantmenuservice.util.MenuItemResponse;
import com.example.restaurantmenuservice.util.RestaurantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class RestaurantMenuController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuItemRepository;

    @GetMapping("/{id}")
    public RestaurantResponse getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            return new RestaurantResponse(
                optionalRestaurant.get().getId(),
                optionalRestaurant.get().getName(),
                optionalRestaurant.get().getTitle(),
                optionalRestaurant.get().getAddress()
            );
        } else {
            throw new ResourceNotFoundException("Restaurant not found with id " + id);
        }
    }

    @GetMapping("/{id}/menu-items")
    public List<MenuItemResponse> getMenuItemsByRestaurantId(@PathVariable Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            List<MenuItemResponse> response = new ArrayList<>();
            return optionalRestaurant.get().getMenuItems().stream().map(item ->
                new MenuItemResponse(item.getId(),item.getName(),item.getPrice(),item.getDescription(),item.getCategory())).collect(
                Collectors.toList());
        } else {
            throw new ResourceNotFoundException("Restaurant not found with id " + id);
        }
    }

    @GetMapping("/{id}/menu-items/{category}")
    public List<MenuItemResponse> getMenuItemsByRestaurantIdAndCategory(@PathVariable Long id, @PathVariable String category) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            return menuItemRepository.findByRestaurantIdAndCategory(id, category).stream().map(item ->
                new MenuItemResponse(item.getId(),item.getName(),item.getPrice(),item.getDescription(),item.getCategory())).collect(
                Collectors.toList());

        } else {
            throw new ResourceNotFoundException("Restaurant not found with id " + id);
        }
    }

}

