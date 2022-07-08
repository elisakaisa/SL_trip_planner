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
    private final RecyclerViewInterface mRecyclerViewInterface;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stopNameTV;

        public ViewHolder(View itemView, RecyclerViewInterface mRecyclerViewInterface) {
            super(itemView);
            stopNameTV = itemView.findViewById(R.id.stopName);

            itemView.setOnClickListener(view -> {
                if (mRecyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        mRecyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }

    //constructor
    public StopAdapter(ArrayList<StopList> stopItemsList, RecyclerViewInterface mRecyclerViewInterface) {
        this.mStops = stopItemsList;
        this.mRecyclerViewInterface = mRecyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop_item, parent, false);
        return new StopAdapter.ViewHolder(itemView, mRecyclerViewInterface);
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
