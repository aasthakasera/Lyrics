package com.example.mymusicapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class bookmark extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        ArrayList<String> arr = new ArrayList<String>();
        arr.add("Lata Mangeshaker");
        arr.add("Arijit Singh");
        arr.add("Shreya");
        arr.add("Neha");

        ArrayAdapter<String> item = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr);
        ListView listView = (ListView) findViewById(R.id.root);
        listView.setAdapter(item);

        /**int i=0;
        while(i<arr.size())
        {
            LinearLayout view = (LinearLayout) findViewById(R.id.root);
            TextView wordView = new TextView(this);
            wordView.setText(arr.get(i));
            view.addView(wordView);
            i++;
        }*/
    }
}
