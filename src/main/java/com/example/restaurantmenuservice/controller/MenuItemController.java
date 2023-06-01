package com.example.restaurantmenuservice.controller;

import com.example.restaurantmenuservice.entity.MenuItem;
import com.example.restaurantmenuservice.entity.Restaurant;
import com.example.restaurantmenuservice.exception.ResourceNotFoundException;
import com.example.restaurantmenuservice.repository.MenuRepository;
import com.example.restaurantmenuservice.repository.RestaurantRepository;
import com.example.restaurantmenuservice.util.MenuItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/{restaurantId}/menu")
public class MenuItemController {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping("/create")
    public String createMenuItem(@PathVariable(value = "restaurantId") Long restaurantId,
        @RequestBody MenuItem menuItem) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if(restaurant.isPresent()){
            restaurant.get().getMenuItems().add(menuItem);
            return menuItem.toString() + "added";
        }else{
            throw new ResourceNotFoundException("Restaurant not found with id " + restaurantId);
        }
    }

    @PutMapping("/update/{id}")
    public MenuItemResponse updateMenuItem(@PathVariable(value = "id") Long id,
        @PathVariable(value = "restaurantId") Long restaurantId,
        @RequestBody MenuItem menuItemRequest) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant not found with id " + restaurantId);
        }

        return menuRepository.findById(id).map(menuItem -> {
            if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
                throw new ResourceNotFoundException("MenuItem not found with id " + id + " for restaurant with id " + restaurantId);
            }
            menuItem.setName(menuItemRequest.getName());
            menuItem.setPrice(menuItemRequest.getPrice());
            menuItem.setDescription(menuItemRequest.getDescription());
            menuItem.setCategory(menuItemRequest.getCategory());
            MenuItem updatedmenuitem = menuRepository.save(menuItem);
            return new MenuItemResponse(updatedmenuitem.getId(),updatedmenuitem.getName(),updatedmenuitem.getPrice(),updatedmenuitem.getCategory(),updatedmenuitem.getDescription());
        }).orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id " + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable(value = "id") Long id,
        @PathVariable(value = "restaurantId") Long restaurantId) {
        return menuRepository.findByIdAndRestaurantId(id, restaurantId).map(menuItem -> {
            menuRepository.delete(menuItem);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id " + id + " for restaurant with id " + restaurantId));
    }
}

