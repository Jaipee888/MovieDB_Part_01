package com.example.android.moviedb_part_one;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "MovieDBData";
    public static final String EXTRA_MOVIEID = "MovieId";
    private ProgressBar DetailprogressBar;
    private RecyclerViewMovieAdapter detailAdapter;
    private RecyclerView dRecyclerView;
    private List<MovieData> MovieList;
    protected TextView overViewText;





    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

       // dRecyclerView = (RecyclerView) findViewById(R.id.detail_recycler_view);
       // dRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DetailprogressBar = (ProgressBar) findViewById(R.id.detail_progress_bar);
        Intent movieIntent = getIntent();
        String movieUrl = movieIntent.getExtras().getString(EXTRA_MOVIEID);

        new DetailDownloadTask().execute(movieUrl);

    }

    public class DetailDownloadTask extends AsyncTask<String, Void, Integer> {

       @Override
        protected void onPreExecute() {
            DetailprogressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseDetailResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        /*@Override
        protected void onPostExecute(Integer result) {
            DetailprogressBar.setVisibility(View.GONE);

            if (result == 1) {
                //detailAdapter = new RecyclerViewMovieAdapter(DetailActivity.this, MovieList);
               // dRecyclerView.setAdapter(detailAdapter);





            } else {
                Toast.makeText(DetailActivity.this, "Failed to fetch Movie Details !", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    private void parseDetailResult(String detailResult) {
        try {
            JSONObject detailResponse = new JSONObject(detailResult);
            String summary = detailResponse.getString("overview");
            String relDate = detailResponse.getString("release_date");
            String vote = detailResponse.getString("vote_average");
            String backdrop = detailResponse.getString("backdrop_path");

            MovieData item = new MovieData();
            item.setPosterThumbnail("http://image.tmdb.org/t/p/w500/" + detailResponse.getString(backdrop));
            item.setReleaseDate(detailResponse.getString(relDate));
            item.setOverView(summary);
            item.setUserRating(vote);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
