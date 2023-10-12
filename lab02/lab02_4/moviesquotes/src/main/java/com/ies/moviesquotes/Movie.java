package com.ies.moviesquotes;

import java.util.ArrayList;

public class Movie {
    
    private final int id;
    private final String movie;
    private final Quote quote;

    public Movie(int id, String movie, Quote quote){
        this.id = id;
        this.movie = movie;
        this.quote = quote;
    }

    public int getId() {
        return id;
    }

    public String getMovie() {
        return movie;
    }

    public Quote getQuote() {
        return quote;
    }
}
