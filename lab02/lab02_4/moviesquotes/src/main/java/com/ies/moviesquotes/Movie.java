package com.ies.servingwebcontent;

public class Movie {
    
    private final int id;
    private final String movie;
    // private final String character;
    private final String quote;

    public Movie(int id, String movie, String quote){
        this.id = id;
        this.movie = movie;
        this.quote = quote;
    }

    public int getId() {
        return this.id;
    }

    public String getMovie() {
        return this.movie;
    }

    public String getQuote() {
        return this.quote;
    }
}
