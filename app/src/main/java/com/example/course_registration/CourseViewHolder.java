package com.example.course_registration;
/**
 * @author Dan and Pascha
 * Viewholder class taken from Assignment 3.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Dan and Pascha
 * Course View holder to display course information
 */
public class CourseViewHolder extends RecyclerView.ViewHolder  {

    public TextView coursename;
    public TextView courseinfo;
    public Button detailsButton;

    /**
     * Used in recyclerview to showcase the infomation on the coursepage
     * @param view
     */
    public CourseViewHolder(View view)
    {
        super(view);
        coursename = view.findViewById(R.id.contactName);
        courseinfo = view.findViewById(R.id.contactEmail);
        detailsButton = view.findViewById(R.id.goDetails);

    }





}
