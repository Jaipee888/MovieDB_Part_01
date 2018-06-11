package com.example.android.moviedb_part_one;

import android.media.Image;

public class MovieData {

    private String originalTitle;
    private String imagePoster;
    private String movieId;
    private String posterThumbnail;
    private String releaseDate;
    private String overView;
    private String userRating;

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterThumbnail() {
        return posterThumbnail;
    }

    public void setPosterThumbnail(String posterThumbnail) {
        this.posterThumbnail = posterThumbnail;
    }

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
