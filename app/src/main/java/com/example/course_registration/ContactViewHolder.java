package com.example.course_registration;
/**
 * @author Dan and Pascha
 * Viewholder class taken from Assignment 3.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 *
 */
public class ContactViewHolder extends RecyclerView.ViewHolder  {

    public TextView name;
    public TextView email;
    public Button detailsButton;

    /**
     * Used in recyclerview to showcase the infomation on the coursepage
     * @param view
     */
    public ContactViewHolder(View view)
    {
        super(view);
        name = view.findViewById(R.id.contactName);
        email = view.findViewById(R.id.contactEmail);
        detailsButton = view.findViewById(R.id.goDetails);

    }





}
