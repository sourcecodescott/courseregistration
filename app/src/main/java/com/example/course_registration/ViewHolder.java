package com.example.course_registration;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.course_registration.R;

public class ViewHolder extends RecyclerView.ViewHolder  {

    public TextView coursename;
    public TextView coursecode;
    public Button detailsButton;

    public ViewHolder(View view)
    {
        super(view);
        coursename = view.findViewById(R.id.contactName);
        coursecode = view.findViewById(R.id.contactEmail);

        detailsButton = view.findViewById(R.id.goDetails);

    }





}
