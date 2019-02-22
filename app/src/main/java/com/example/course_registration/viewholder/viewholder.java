package com.example.course_registration.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class viewholder extends RecyclerView.ViewHolder  {

    public TextView textData.
    public Button detailsButton;

    public viewholder(View view){
        super(view);

        textData = view.findViewById(R.id.text_view_data);
        detailsButton = view.findViewById(R.id.goDetails);
    }

