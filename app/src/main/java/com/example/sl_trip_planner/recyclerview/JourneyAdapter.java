package com.example.sl_trip_planner.recyclerview;

import android.content.Intent;
import android.icu.util.Calendar;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sl_trip_planner.R;
import com.example.sl_trip_planner.data.JourneyList;

import java.util.ArrayList;

public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.ViewHolder> {
    private ArrayList<JourneyList> mJourney;
    private RecyclerViewInterface mRecyclerViewInterface;
    private ExportButtonInterface mExportButtonInterface;
    private static boolean rtData = false;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView departureTimeTV, arrivalTimeTV,
                        transportListTV, deltaTTV,
                        lineTV1, directionTV1,
                        startTimeTV1, stopTimeTV1, startStopTV1, stopStopTV1,
                        lineTV2, directionTV2,
                        startTimeTV2, stopTimeTV2, startStopTV2, stopStopTV2,
                        lineTV3, directionTV3,
                        startTimeTV3, stopTimeTV3, startStopTV3, stopStopTV3,
                        lineTV4, directionTV4,
                        startTimeTV4, stopTimeTV4, startStopTV4, stopStopTV4;
        public LinearLayout stringLL, line1LL, line2LL, line3LL, line4LL;
        public ImageButton btn_export;

        public ViewHolder(View itemView, RecyclerViewInterface mRecyclerViewInterface) {
            super(itemView);

            stringLL = itemView.findViewById(R.id.ll_string);
            line1LL = itemView.findViewById(R.id.ll_line1);
            line2LL = itemView.findViewById(R.id.ll_line2);
            line3LL = itemView.findViewById(R.id.ll_line3);
            line4LL = itemView.findViewById(R.id.ll_line4);

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

            lineTV2 = itemView.findViewById(R.id.line_nr_2);
            directionTV2 = itemView.findViewById(R.id.direction_2);
            startTimeTV2 = itemView.findViewById(R.id.start_time_2);
            stopTimeTV2 = itemView.findViewById(R.id.stop_time_2);
            startStopTV2 = itemView.findViewById(R.id.start_stop_2);
            stopStopTV2 = itemView.findViewById(R.id.stop_stop_2);

            lineTV3 = itemView.findViewById(R.id.line_nr_3);
            directionTV3 = itemView.findViewById(R.id.direction_3);
            startTimeTV3 = itemView.findViewById(R.id.start_time_3);
            stopTimeTV3 = itemView.findViewById(R.id.stop_time_3);
            startStopTV3 = itemView.findViewById(R.id.start_stop_3);
            stopStopTV3 = itemView.findViewById(R.id.stop_stop_3);

            lineTV4 = itemView.findViewById(R.id.line_nr_4);
            directionTV4 = itemView.findViewById(R.id.direction_4);
            startTimeTV4= itemView.findViewById(R.id.start_time_4);
            stopTimeTV4 = itemView.findViewById(R.id.stop_time_4);
            startStopTV4 = itemView.findViewById(R.id.start_stop_4);
            stopStopTV4 = itemView.findViewById(R.id.stop_stop_4);

            btn_export = itemView.findViewById(R.id.img_btn_export);

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
    public JourneyAdapter(
            ArrayList<JourneyList> journeyItemsList,
            RecyclerViewInterface mRecyclerViewInterface,
            ExportButtonInterface mExportButtonInterface) {
        this.mJourney = journeyItemsList;
        this.mRecyclerViewInterface = mRecyclerViewInterface;
        this.mExportButtonInterface = mExportButtonInterface;
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

        // get amount of different bus lines to take
        int lengthLines = currentJourney.getTextLineList().size();
        holder.stringLL.setVisibility(View.GONE);
        holder.line2LL.setVisibility(View.GONE);
        holder.line3LL.setVisibility(View.GONE);
        holder.line4LL.setVisibility(View.GONE);

        // get data to export
        String departureTime = currentJourney.getTextDepartureTime();
        String arrivalTime = currentJourney.getTextArrivalTime();
        String departureDate = currentJourney.getTextDepartureDate();
        String arrivalDate = currentJourney.getTextArrivalDate();

        // Start & stop data, time
        if (!rtData) {
            holder.departureTimeTV.setText(departureTime);
            holder.arrivalTimeTV.setText(arrivalTime);
            String sDelta = "Total: " + currentJourney.getTextDeltaT();
            holder.deltaTTV.setText(sDelta);
        } else {
            holder.departureTimeTV.setText(currentJourney.getTextRtDepartureTime());
            holder.arrivalTimeTV.setText(currentJourney.getTextRtArrivalTime());
            String sDelta = "Total (rt): " + currentJourney.getTextRtDeltaT();
            holder.deltaTTV.setText(sDelta);
        }

        // 1st line data
        if (lengthLines >= 1) {
            holder.lineTV1.setText(String.valueOf(currentJourney.getTextLineList().get(0)));
            holder.directionTV1.setText(currentJourney.getTextDestinationList().get(0));
            if (!rtData) {
                holder.startTimeTV1.setText(currentJourney.getTextTimeList().get(0));
                holder.stopTimeTV1.setText(currentJourney.getTextTimeList().get(1));
            } else {
                holder.startTimeTV1.setText(currentJourney.getTextRtTimeList().get(0));
                holder.stopTimeTV1.setText(currentJourney.getTextRtTimeList().get(1));
            }
            holder.startStopTV1.setText(currentJourney.getTextStopList().get(0));
            holder.stopStopTV1.setText(currentJourney.getTextStopList().get(1));
        }

        // multiple line data
        if (lengthLines >= 2) {
            holder.line2LL.setVisibility(View.VISIBLE);
            holder.lineTV2.setText(String.valueOf(currentJourney.getTextLineList().get(1)));
            holder.directionTV2.setText(currentJourney.getTextDestinationList().get(1));
            if (!rtData) {
                holder.startTimeTV2.setText(currentJourney.getTextTimeList().get(2));
                holder.stopTimeTV2.setText(currentJourney.getTextTimeList().get(3));
            } else {
                holder.startTimeTV2.setText(currentJourney.getTextRtTimeList().get(2));
                holder.stopTimeTV2.setText(currentJourney.getTextRtTimeList().get(3));
            }
            holder.startStopTV2.setText(currentJourney.getTextStopList().get(2));
            holder.stopStopTV2.setText(currentJourney.getTextStopList().get(3));
        }
        if (lengthLines >= 3) {
            holder.line3LL.setVisibility(View.VISIBLE);
            holder.lineTV3.setText(String.valueOf(currentJourney.getTextLineList().get(2)));
            holder.directionTV3.setText(currentJourney.getTextDestinationList().get(2));
            if (!rtData) {
                holder.startTimeTV3.setText(currentJourney.getTextTimeList().get(4));
                holder.stopTimeTV3.setText(currentJourney.getTextTimeList().get(5));
            } else {
                holder.startTimeTV3.setText(currentJourney.getTextRtTimeList().get(4));
                holder.stopTimeTV3.setText(currentJourney.getTextRtTimeList().get(5));
            }
            holder.startStopTV3.setText(currentJourney.getTextStopList().get(4));
            holder.stopStopTV3.setText(currentJourney.getTextStopList().get(5));
        }
        if (lengthLines == 4) {
            holder.line4LL.setVisibility(View.VISIBLE);
            holder.lineTV4.setText(String.valueOf(currentJourney.getTextLineList().get(3)));
            holder.directionTV4.setText(currentJourney.getTextDestinationList().get(3));
            if (!rtData) {
                holder.startTimeTV4.setText(currentJourney.getTextTimeList().get(6));
                holder.stopTimeTV4.setText(currentJourney.getTextTimeList().get(7));
            } else {
                holder.startTimeTV4.setText(currentJourney.getTextRtTimeList().get(6));
                holder.stopTimeTV4.setText(currentJourney.getTextRtTimeList().get(7));
            }
            holder.startStopTV4.setText(currentJourney.getTextStopList().get(6));
            holder.stopStopTV4.setText(currentJourney.getTextStopList().get(7));
        }

        if (lengthLines > 4) {
            holder.line1LL.setVisibility(View.GONE);
            holder.line2LL.setVisibility(View.GONE);
            holder.line3LL.setVisibility(View.GONE);
            holder.line4LL.setVisibility(View.GONE);
            holder.stringLL.setVisibility(View.VISIBLE);
            if(!rtData) {
                holder.transportListTV.setText(currentJourney.getTextCombinedData());
            } else {
                holder.transportListTV.setText(currentJourney.getTextRtCombinedData());
            }
        }

        // exporting trip to calendar
        holder.btn_export.setOnClickListener(v -> {
            //exportToExternalCalendar();
            mExportButtonInterface.onExportClick(
                    true,
                    "insert title",
                    "description",
                    departureTime,
                    arrivalTime,
                    departureDate,
                    arrivalDate
                    );
        });
    }

    // return size of dataset (invoked by layout manager)
    @Override
    public int getItemCount() {
        return mJourney.size();
    }
}
