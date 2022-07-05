package com.example.sl_trip_planner.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sl_trip_planner.R;
import com.example.sl_trip_planner.data.StopList;

import java.util.ArrayList;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder>{
    private ArrayList<StopList> mStops;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stopNameTV;

        public ViewHolder(View itemView) {
            super(itemView);
            stopNameTV = itemView.findViewById(R.id.stopName);
        }
    }
    public StopAdapter(ArrayList<StopList> stopItemsList) { mStops = stopItemsList; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop_item, parent, false);
        return new StopAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StopAdapter.ViewHolder holder, int position) {
        StopRecycler currentStop = (StopRecycler) mStops.get(position);
        holder.stopNameTV.setText(currentStop.getTextStop());
    }

    @Override
    public int getItemCount() {
        return mStops.size();
    }
}
