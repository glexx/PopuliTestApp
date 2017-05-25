package com.populi.testapp.internal.model;

import java.util.List;

/**
 * Created by Alexander Gavrikov.
 */

public class Country {

    // Serialized fields
    private String id;
    private String name;
    private List<City> cities;


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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
