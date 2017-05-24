package com.populi.testapp.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.populi.testapp.testapplication.internal.DataManager;
import com.populi.testapp.testapplication.internal.network.Tour;

public class TourDetailActivity extends AppCompatActivity {

    private static final String TAG = "TourDetailActivity";
    public static final String PARAM_TOUR_ID = "tour_id";

    private Tour tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        Intent intent = getIntent();
        if (intent == null || intent.getExtras() == null){
            finish();
            Log.d(TAG, "Empty intent");
            return;
        }

        String tourId = intent.getExtras().getString(PARAM_TOUR_ID);
        tour = DataManager.getInstance().getTour(tourId);
        if (tour == null){
            finish();
            Log.d(TAG, "Tour is not found. Id: " + tourId);
            return;
        }

        initViews();
    }

    private void initViews(){
        // Init Views
        TextView titleView = (TextView)findViewById(R.id.title);
        TextView descriptionView = (TextView)findViewById(R.id.description);
        TextView cityView = (TextView)findViewById(R.id.city);
        TextView countryView = (TextView)findViewById(R.id.country);

        // Bind Tour to views
        titleView.setText(tour.getTitle());
        descriptionView.setText(tour.getDescription());
        cityView.setText(tour.getCity().getName());
        countryView.setText(tour.getCity().getCountry().getName());
    }
}
