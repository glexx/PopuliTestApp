package com.populi.testapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.populi.testapp.internal.model.Tour;
import com.populi.testapp.testapplication.R;

import java.util.List;

/**
 * Created by Alexander Gavrikov.
 */

public class ToursAdapter extends RecyclerView.Adapter<TourHolder>{

    private Context context;
    private List<Tour> tours;

    public ToursAdapter(Context context, List<Tour> list) {
        this.context = context;
        this.tours = list;
    }

    void setTours(List<Tour> list){
        tours = list;
        notifyDataSetChanged();
    }

    @Override
    public TourHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new TourHolder(inflater.inflate(R.layout.view_item_tour, parent, false));
    }

    @Override
    public void onBindViewHolder(TourHolder holder, int position) {
        holder.bindTour(tours.get(position));
    }

    @Override
    public int getItemCount() {
        return tours.size();
    }
}