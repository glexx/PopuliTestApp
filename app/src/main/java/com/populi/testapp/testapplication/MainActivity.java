package com.populi.testapp.testapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.populi.testapp.testapplication.internal.DataManager;
import com.populi.testapp.testapplication.internal.network.Tour;

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
        buttonRefreshTours = (Button) findViewById(R.id.refresh);
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
    }

    private void onRefreshToursClick(){
        TaskUpdateTours.getInstance(this).updateTours(new TaskUpdateTours.OnToursReadyListener() {
            @Override
            public void onToursUpdate() {
                List<Tour> tours = DataManager.getInstance().getAllTours();
                ToursAdapter tourAdapter = new ToursAdapter(MainActivity.this, tours);
                recyclerView.setAdapter(tourAdapter);
            }
        });
    }

}
