package com.populi.testapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.populi.testapp.TourDetailActivity;
import com.populi.testapp.internal.model.Tour;
import com.populi.testapp.R;

/**
 * Created by Alexander Gavrikov.
 */

public class TourHolder extends RecyclerView.ViewHolder{

    private Context context;
    private TextView titleView;
    private TextView locationView;
    private ImageView iconView;
    private Tour tour;

    TourHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        titleView = (TextView)itemView.findViewById(R.id.title);
        locationView = (TextView)itemView.findViewById(R.id.location);
        iconView = (ImageView) itemView.findViewById(R.id.icon);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tour != null){
                    openTourDetailsActivity();
                }
            }
        });
    }

    void bindTour(Tour tour){
        this.tour = tour;

        titleView.setText(tour.getTitle());
        locationView.setText(tour.getCountry().getName() + ", " + tour.getCity().getName());

        // TODO: implement loading icons with Picasso
    }

    private void openTourDetailsActivity(){
        Intent intent = new Intent(context, TourDetailActivity.class);
        intent.putExtra(TourDetailActivity.PARAM_TOUR_ID, tour.getUid());
        context.startActivity(intent);
    }
}
