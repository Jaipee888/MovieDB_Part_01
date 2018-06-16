package com.example.android.moviedb_part_one;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MovieDBData";
    private List<MovieData> MovieList;
    private RecyclerView mRecyclerView;
    private RecyclerViewMovieAdapter adapter;
    private ProgressBar progressBar;

     private Spinner spinner;
     String originalLink = "http://api.themoviedb.org/3/movie/popular?api_key=8b6bf3486420893634f897e59f3f5edb";
     String popularityLink = "https://api.themoviedb.org/3/discover/movie?api_key=8b6bf3486420893634f897e59f3f5edb&language=en-US&sort_by=popularity.asc&include_adult=false&include_video=false&page=1";
     String voteLink="https://api.themoviedb.org/3/discover/movie?api_key=8b6bf3486420893634f897e59f3f5edb&language=en-US&sort_by=vote_count.asc&include_adult=false&include_video=false&page=1";
     String[] paths = new String[]{originalLink,popularityLink,voteLink};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        int numberOfColumns = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,numberOfColumns));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


               // String url ="http://api.themoviedb.org/3/movie/popular?api_key=8b6bf3486420893634f897e59f3f5edb";

       // new DownloadTask().execute(url);

       // spinner.setOnItemSelectedListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.default_sort,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listener);


    }

    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };






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
                        intent.putExtra("backdrop_path",item.getPosterThumbnail());
                        intent.putExtra("overview",item.getOverView());
                        intent.putExtra("release_date",item.getReleaseDate());
                        intent.putExtra("vote_average",item.getUserRating());

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
                item.setMovieId("https://api.themoviedb.org/3/movie/" +post.getString("id") + "?api_key=8b6bf3486420893634f897e59f3f5edb");
                item.setPosterThumbnail("http://image.tmdb.org/t/p/w500/" + post.getString("backdrop_path"));
                item.setUserRating(post.getString("vote_average"));
                item.setReleaseDate(post.getString("release_date"));
                item.setOverView(post.getString("overview"));

                MovieList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
