package com.example.course_registration;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ScheduleEntryViewHolder extends RecyclerView.ViewHolder{

    public TextView courseName;
    public TextView courseType;
    public TextView courseLocation;
    public TextView courseTime;

    public ScheduleEntryViewHolder(View scheduleView){
        super(scheduleView);
        courseName = scheduleView.findViewById(R.id.scheduleCourseName);
        courseType = scheduleView.findViewById(R.id.scheduleCourseType);
        courseLocation = scheduleView.findViewById(R.id.scheduleCourseLocation);
        courseTime = scheduleView.findViewById(R.id.scheduleCourseTime);
    }


}
