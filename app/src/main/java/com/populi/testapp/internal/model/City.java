package com.populi.testapp.internal.model;


import java.util.List;

/**
 * Created by Alexander Gavrikov.
 */

public class City {

    // Serialized fields
    private String id;
    private String name;
    private List<Tour> tours;

    // Other fields
    private Country country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
