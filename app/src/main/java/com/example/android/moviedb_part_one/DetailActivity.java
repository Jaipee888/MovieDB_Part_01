package com.example.android.moviedb_part_one;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DetailActivity extends AppCompatActivity {

   public static final String EXTRA_MOVIEID = "MovieId";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);


        Intent movieIntent = getIntent();
        String movieUrl = movieIntent.getExtras().getString(EXTRA_MOVIEID);

        // Populate TextViews of Movie Details.
        TextView movieDetailTextView = (TextView) findViewById(R.id.detail_textView);
        movieDetailTextView.setText(movieUrl);



    }



}
