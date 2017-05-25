package com.populi.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.populi.testapp.helpers.ToursAdapter;
import com.populi.testapp.internal.DataManager;
import com.populi.testapp.internal.model.Tour;
import com.populi.testapp.R;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button buttonRefreshTours;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews(){
        // Init buttons
        buttonRefreshTours = (Button) findViewById(R.id.updateTours);
        buttonRefreshTours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefreshToursClick();
            }
        });

        // Init list
        recyclerView = (RecyclerView)findViewById(R.id.tourList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        updateList();
    }

    private void onRefreshToursClick(){
        DataManager.getInstance().updateTours(new DataManager.OnToursReadyListener() {
            @Override
            public void onToursUpdate(boolean result) {
                if (result) {
                    // Push to gui
                    updateList();
                    Toast.makeText(MainActivity.this, R.string.tours_successful, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, R.string.tours_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateList(){
        List<Tour> tours = DataManager.getInstance().getAllTours();
        ToursAdapter tourAdapter = new ToursAdapter(MainActivity.this, tours);
        recyclerView.setAdapter(tourAdapter);
    }
}
