package com.example.course_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        String user_id = "Fld9C5XdTAPhPT85WkFl";

        String[] ListElements = new String[] {
                "Android",
                "PHP",
                "adsads",
                "Android",
                "PHP",
                "adsads",                "Android",
                "PHP",
                "adsads",                "Android",
                "PHP",
                "adsads",                "Android",
                "PHP",
                "adsads",                "Android",
                "PHP",
                "adsads",                "Android",
                "PHP",
                "adsads"
        };

        ListView course_listview = (ListView) findViewById(R.id.listview_dynamic);

        final List < String > ListElementsArrayList = new ArrayList < String >
                (Arrays.asList(ListElements));


        final ArrayAdapter < String > adapter = new ArrayAdapter < String >
                (ScrollingActivity.this, android.R.layout.simple_list_item_1,
                        ListElementsArrayList);

        course_listview.setAdapter(adapter);

        Button btn= (Button) findViewById(R.id.go_back_to_menu);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScrollingActivity.this, MainActivity.class));
            }

        });
    }
}
