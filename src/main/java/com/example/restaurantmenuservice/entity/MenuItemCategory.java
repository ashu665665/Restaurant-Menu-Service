package com.example.restaurantmenuservice.entity;

public enum MenuItemCategory {
    BREAKFAST("Breakfast"),
    MAIN_COURSE("Main Course"),
    DRINKS("Drinks"),
    DESSERTS("Desserts");

    private String displayName;

    private MenuItemCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}

