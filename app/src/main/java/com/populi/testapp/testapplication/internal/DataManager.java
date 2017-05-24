package com.populi.testapp.testapplication.internal;

import android.util.Log;

import com.populi.testapp.testapplication.internal.network.City;
import com.populi.testapp.testapplication.internal.network.Country;
import com.populi.testapp.testapplication.internal.network.Tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander Gavrikov.
 */

public final class DataManager {

    private static final String TAG = "DataManager";

    private static DataManager instance = new DataManager();

    private HashMap<String, Tour> tours = new HashMap<>();

    public static DataManager getInstance() {
        return instance;
    }

    public synchronized Tour getTour(String id){
        return tours.get(id);
    }

    public synchronized List<Tour> getAllTours(){
        return new ArrayList<>(tours.values());
    }

    public synchronized void updateTour(List<Country> countries){
        tours.clear();

        try {
            for (Country country : countries) {

                for (City city : country.getCities()) {
                    city.setCountry(country);

                    for (Tour tour : city.getTours()) {
                        tour.setCity(city);
                        tours.put(tour.getUid(), tour);
                    }
                }
            }
        }
        catch (Exception e){
            Log.e(TAG, "Issues with reading the model after update", e);
        }

        // TODO: update tours in database
    }

    private void initialize(){
        // TODO: load from the database
    }

}
