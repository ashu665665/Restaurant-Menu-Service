package com.example.restaurantmenuservice.controller;

import com.example.restaurantmenuservice.entity.MenuItem;
import com.example.restaurantmenuservice.entity.Restaurant;
import com.example.restaurantmenuservice.exception.ResourceNotFoundException;
import com.example.restaurantmenuservice.repository.MenuRepository;
import com.example.restaurantmenuservice.repository.RestaurantRepository;
import com.example.restaurantmenuservice.service.QRCodeService;
import com.example.restaurantmenuservice.util.RestaurantResponse;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private QRCodeService qrCodeService;

    // Get all restaurants
    @GetMapping("/all")
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
            .map(restaurant -> new RestaurantResponse(restaurant.getId(),restaurant.getName(),restaurant.getTitle(),restaurant.getAddress())).collect(
                Collectors.toList());
    }


    // Create a new restaurant
    @PostMapping("/create")
    public RestaurantResponse createRestaurant(@RequestBody Restaurant restaurant) {
        List<MenuItem> menuItems = restaurant.getMenuItems();
        if (menuItems != null) {
            for (MenuItem menuItem : menuItems) {
                menuItem.setRestaurant(restaurant);
            }
        }
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        try {
            BufferedImage qrCodeImage = qrCodeService.generateQRCodeImage("https://myrestaurant.com/" + savedRestaurant.getId(), 250, 250);
            // Save the QR code image to the file system or upload to the cloud storage
            // Save the QR code image to the local file system
            File qrCodeFile = new File("C:\\TESTING2\\qr_code_" + savedRestaurant.getId() + "_" + savedRestaurant.getTitle() + ".png");
            ImageIO.write(qrCodeImage, "png", qrCodeFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RestaurantResponse(savedRestaurant.getId(),savedRestaurant.getName(),savedRestaurant.getTitle(),savedRestaurant.getAddress());
    }

    // Update a restaurant
    @PutMapping("/update/{id}")
    public RestaurantResponse updateRestaurant(@PathVariable(value = "id") Long restaurantId,
        @RequestBody Restaurant restaurantDetails) throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantId));

        restaurant.setName(restaurantDetails.getName());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setTitle(restaurantDetails.getTitle());

        final Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return new RestaurantResponse(updatedRestaurant.getId(),updatedRestaurant.getName(),updatedRestaurant.getTitle(),updatedRestaurant.getAddress());
    }

    // Delete a restaurant and their menuitems also
    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteRestaurant(@PathVariable(value = "id") Long restaurantId)
        throws ResourceNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantId));

        restaurantRepository.delete(restaurant);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

