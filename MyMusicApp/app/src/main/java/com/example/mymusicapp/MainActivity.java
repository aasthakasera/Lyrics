package com.example.mymusicapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity<artistName> extends AppCompatActivity {

    String artist = "Coldplay";
    String song = "Adventure of a Lifetime";

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        Button text = (Button) findViewById (R.id.search);
        //String artistName = text.getText().toString();
        text.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent k = new Intent (MainActivity.this, Lyrics.class);
                startActivity (k);
            }
        });

        CheckBox b = (CheckBox) findViewById (R.id.favourite);
        b.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent f = new Intent (MainActivity.this, bookmark.class);
                startActivity (f);
            }
        });
    }
}
