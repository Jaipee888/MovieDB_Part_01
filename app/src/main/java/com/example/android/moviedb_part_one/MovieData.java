package com.example.android.moviedb_part_one;

public class MovieData {

    private String originalTitle;
    private String  imagePoster;
    private String plotSynopsis;
    private String userRating;

    public MovieData(String title, String imgPoster, String plot, String uRating){

        originalTitle = title;
        imagePoster = imgPoster;
        plotSynopsis = plot;
        userRating = uRating;
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

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }
}
