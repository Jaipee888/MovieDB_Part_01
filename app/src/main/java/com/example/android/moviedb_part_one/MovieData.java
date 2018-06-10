package com.example.android.moviedb_part_one;

import android.media.Image;

public class MovieData {

    private String originalTitle;
    private String imagePoster;
    private String movieId;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getOriginalTitle() {

        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {

        this.originalTitle = originalTitle;
    }

    public String getImagePoster() {
        return imagePoster;
    }

    public void setImagePoster(String imagePoster) {
        this.imagePoster = imagePoster;
    }
}
