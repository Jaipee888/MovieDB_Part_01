package com.example.android.moviedb_part_one;

import android.media.Image;

public class MovieData {

    private String originalTitle;
    private Image imagePoster;

    public MovieData(String title, Image imgP){

        originalTitle = title;
        imagePoster = imgP;

    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Image getImagePoster() {
        return imagePoster;
    }

    public void setImagePoster(Image imagePoster) {
        this.imagePoster = imagePoster;
    }
}
