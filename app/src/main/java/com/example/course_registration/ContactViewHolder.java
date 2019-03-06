package com.example.course_registration;
//viewholder guide taken form Assigment 3 
//Creator Juliano Franz 
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//Viewholder Class to display course name and number

public class ContactViewHolder extends RecyclerView.ViewHolder  {

    public TextView coursename;
    public TextView coursenum;
    public Button detailsButton;

    public ContactViewHolder(View view)
    {
        super(view);
        coursename = view.findViewById(R.id.contactName);
        coursenum = view.findViewById(R.id.contactEmail);

        detailsButton = view.findViewById(R.id.goDetails);

    }
}
