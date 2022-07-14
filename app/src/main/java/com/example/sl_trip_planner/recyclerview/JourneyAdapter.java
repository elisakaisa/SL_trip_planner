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
    private RecyclerViewInterface mRecyclerViewInterface;
    private static boolean rtData = false;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView departureTimeTV, arrivalTimeTV,
                        transportListTV, deltaTTV,
                        lineTV1, directionTV1,
                        startTimeTV1, stopTimeTV1,
                        startStopTV1, stopStopTV1;

        public ViewHolder(View itemView, RecyclerViewInterface mRecyclerViewInterface) {
            super(itemView);
            arrivalTimeTV = itemView.findViewById(R.id.arrival_time_text);
            departureTimeTV = itemView.findViewById(R.id.departure_time_text);
            transportListTV = itemView.findViewById(R.id.transport_list);
            deltaTTV = itemView.findViewById(R.id.total_time);
            lineTV1 = itemView.findViewById(R.id.line_nr_1);
            directionTV1 = itemView.findViewById(R.id.direction_1);
            startTimeTV1 = itemView.findViewById(R.id.start_time_1);
            stopTimeTV1 = itemView.findViewById(R.id.stop_time_1);
            startStopTV1 = itemView.findViewById(R.id.start_stop_1);
            stopStopTV1 = itemView.findViewById(R.id.stop_stop_1);

            // for clicking on item
            itemView.setOnClickListener(view -> {
                if (mRecyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        mRecyclerViewInterface.onItemClick(position);
                        rtData = !rtData;
                        notifyItemChanged(position);

                    }
                }
            });
        }
    }
    // initialize dataset of the adapter
    public JourneyAdapter(ArrayList<JourneyList> journeyItemsList, RecyclerViewInterface mRecyclerViewInterface) {
        this.mJourney = journeyItemsList;
        this.mRecyclerViewInterface = mRecyclerViewInterface;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journey_item, parent, false);
        return new ViewHolder(itemView, mRecyclerViewInterface);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get elements from dataset & replace the contents of the view with that element
        JourneyRecycler currentJourney = (JourneyRecycler) mJourney.get(position);
        if (!rtData) {
            holder.departureTimeTV.setText(currentJourney.getTextDepartureTime());
            holder.arrivalTimeTV.setText(currentJourney.getTextArrivalTime());
            holder.deltaTTV.setText(currentJourney.getTextDeltaT());
            holder.transportListTV.setText(currentJourney.getTextCombinedData());

        } else {
            holder.departureTimeTV.setText(currentJourney.getTextRtDepartureTime());
            holder.arrivalTimeTV.setText(currentJourney.getTextRtArrivalTime());
            holder.deltaTTV.setText(currentJourney.getTextRtDeltaT());
            holder.transportListTV.setText(currentJourney.getTextRtCombinedData());
        }

        holder.lineTV1.setText(String.valueOf(currentJourney.getTextLineList().get(0)));
        holder.directionTV1.setText(currentJourney.getTextDestinationList().get(0));
        holder.startTimeTV1.setText(currentJourney.getTextTimeList().get(0));
        holder.stopTimeTV1.setText(currentJourney.getTextTimeList().get(1));
        holder.startStopTV1.setText(currentJourney.getTextStopList().get(0));
        holder.stopStopTV1.setText(currentJourney.getTextStopList().get(1));
    }

    // return size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {
        return mJourney.size();
    }
}
