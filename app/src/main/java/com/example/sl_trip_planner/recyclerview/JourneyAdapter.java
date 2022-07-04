package com.example.sl_trip_planner.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sl_trip_planner.R;
import com.example.sl_trip_planner.data.JourneyList;

import java.util.ArrayList;

public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.ViewHolder> {
    private ArrayList<JourneyList> mJourney;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView originTV, destinationTV, departureTimeTV, arrivalTimeTV;

        public ViewHolder(View itemView) {
            super(itemView);
            originTV = itemView.findViewById(R.id.origin_text);
            destinationTV = itemView.findViewById(R.id.destination_text);
            arrivalTimeTV = itemView.findViewById(R.id.arrival_time_text);
            departureTimeTV = itemView.findViewById(R.id.departure_time_text);
        }
    }
    // initialize dataset of the adapter
    public JourneyAdapter(ArrayList<JourneyList> journeyItemsList) { mJourney = journeyItemsList; }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new item view todo fix correct layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journey_item, parent, false);
        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get elements from dataset & replace the contents of the view with that element
        JourneyRecycler currentJourney = (JourneyRecycler) mJourney.get(position);
        holder.originTV.setText(currentJourney.getTextOrigin());
        holder.destinationTV.setText(currentJourney.getTextDestination());
        holder.departureTimeTV.setText(currentJourney.getTextDepartureTime());
        holder.arrivalTimeTV.setText(currentJourney.getTextArrivalTime());
    }

    // return size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {
        return mJourney.size();
    }
}