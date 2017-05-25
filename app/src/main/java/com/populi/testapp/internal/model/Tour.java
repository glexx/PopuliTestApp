package com.populi.testapp.internal.model;


/**
 * Created by Alexander Gavrikov.
 */

public class Tour {

    // Serialized fields
    private String uid;
    private String title;
    private String image;
    private String desc;

    // Other fields
    private City city;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity(){
        return city;
    }

    public Country getCountry(){
        return city.getCountry();
    }
}
