package com.example.sl_trip_planner.recyclerview;

public interface ExportButtonInterface {
    void onExportClick(
            boolean exported,
            String title,
            String description,
            String startTime,
            String endTime
    );
}
