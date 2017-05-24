package com.populi.testapp.testapplication.internal.network;

import java.util.List;

/**
 * Created by Alexander Gavrikov.
 */

public class City {
    public String id;
    public String name;
    public List<Tour> tours;

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
