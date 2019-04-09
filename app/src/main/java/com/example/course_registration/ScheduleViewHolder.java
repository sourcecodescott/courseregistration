package com.example.course_registration;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class sets up the view for the schedule activity
 * authors: Pascha & Samath
 */
public class ScheduleViewHolder extends RecyclerView.ViewHolder  {

    public TextView coursename;
    public TextView courseinfo;
    public TextView startTime;
    public TextView endTime;
    public TextView days;
    public Button btndrop;


    public ScheduleViewHolder(View view)
    {
        super(view);
        coursename = view.findViewById(R.id.contactName);
        courseinfo = view.findViewById(R.id.contactEmail);
        startTime = view.findViewById(R.id.txt_start_time);
        endTime = view.findViewById(R.id.txt_end_time);
        days = view.findViewById(R.id.txt_days);
        btndrop = view.findViewById(R.id.btndrop);


    }





}
