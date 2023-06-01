package com.example.restaurantmenuservice.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESTAURANT_ID")
    private Long id;

    @Column(name = "RESTAURANT_NAME", nullable = false)
    private String name;

    @Column(name = "RESTAURANT_TITLE")
    private String title;

    @Column(name = "RESTAURANT_ADD")
    private String address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> menuItems = new ArrayList<>();

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
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

    public String getTitle() {
        return title;
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


    public Restaurant() {
    }

    public Restaurant(String name, String title, String address, List<MenuItem> menuItems) {
        this.name = name;
        this.title = title;
        this.address = address;
        this.menuItems = menuItems;
    }

    @Override public String toString() {
        return "Restaurant{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", title='" + title + '\'' +
            ", address='" + address + '\'' +
            ", menuItems=" + menuItems +
            '}';
    }
}
