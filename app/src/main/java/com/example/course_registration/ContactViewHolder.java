package com.example.course_registration;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class ContactViewHolder extends RecyclerView.ViewHolder  {

    public TextView name;
    public TextView email;
    public Button detailsButton;

    public ContactViewHolder(View view)
    {
        super(view);
        name = view.findViewById(R.id.contactName);
        email = view.findViewById(R.id.contactEmail);

        detailsButton = view.findViewById(R.id.goDetails);

    }





}
