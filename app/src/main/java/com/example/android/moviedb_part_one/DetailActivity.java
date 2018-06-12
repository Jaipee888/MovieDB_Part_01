package com.example.android.moviedb_part_one;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;



public class DetailActivity extends AppCompatActivity {

  

   private TextView overViewText;
   private ImageView thumbnailImage;
   private TextView vote;
   private TextView release;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        String overViewSummary = getIntent().getStringExtra("overview");
        String image = getIntent().getStringExtra("backdrop_path");
        String relD = getIntent().getStringExtra("release_date");
        String voteAverage = getIntent().getStringExtra("vote_average");

        overViewText = (TextView) findViewById(R.id.detail_textView);
        thumbnailImage = (ImageView) findViewById(R.id.detailphoto);
        vote = (TextView) findViewById(R.id.votingAverage);
        release = (TextView) findViewById(R.id.releaseDate);


        overViewText.setText(Html.fromHtml(overViewSummary));
        vote.setText(Html.fromHtml(voteAverage));
        release.setText(Html.fromHtml(relD));
        Picasso.get().load(image).into(thumbnailImage);


        }


}
