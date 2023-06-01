package com.example.restaurantmenuservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "ITEM_NAME")
    private String name;

    @Column(name = "ITEM_PRICE")
    private Double price;

    @Column(name = "ITEM_DESC")
    private String description;

    @Column(name = "ITEM_CTG")
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

    public MenuItem(String name, Double price, String description, String category, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.restaurant= restaurant;
    }

    public MenuItem() {
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override public String toString() {
        return "MenuItem{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", description='" + description + '\'' +
            ", category='" + category + '\'' +
            ", restaurant=" + restaurant +
            '}';
    }
}


