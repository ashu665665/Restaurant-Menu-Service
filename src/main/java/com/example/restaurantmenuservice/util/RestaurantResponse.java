package com.example.restaurantmenuservice.util;

import javax.persistence.Column;

public class RestaurantResponse {
    private Long id;
    private String name;
    private String title;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public RestaurantResponse() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RestaurantResponse(Long id, String name, String title, String address) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.address = address;
    }
}
