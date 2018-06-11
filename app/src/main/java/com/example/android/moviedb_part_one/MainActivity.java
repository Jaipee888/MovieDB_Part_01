package com.example.android.moviedb_part_one;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MovieDBData";
    private List<MovieData> MovieList;
    private RecyclerView mRecyclerView;
    private RecyclerViewMovieAdapter adapter;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        int numberOfColumns = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,numberOfColumns));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        String url =
                "http://api.themoviedb.org/3/movie/popular?api_key=";

        new DownloadTask().execute(url);


    }

    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
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
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new RecyclerViewMovieAdapter(MainActivity.this, MovieList);
                mRecyclerView.setAdapter(adapter);

                /**
                        Below ClickListener will launch a detailActivity window.
                 */
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(MovieData item) {

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        String detailUrl = item.getMovieId();
                        intent.putExtra(DetailActivity.EXTRA_MOVIEID, detailUrl);

                        startActivity(intent);

                    }
                });
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("results");
            MovieList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                MovieData item = new MovieData();
                item.setImagePoster("http://image.tmdb.org/t/p/w500/" + post.getString("poster_path"));
                item.setOriginalTitle(post.getString("title"));
                item.setMovieId("https://api.themoviedb.org/3/movie/" +post.getString("id") + "?api_key=");

                MovieList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
