package com.ies.movies.service;

import com.ies.movies.entity.Movie;
import com.ies.movies.entity.Quote;

import java.util.*;

public interface QuoteService {
    Quote createNewQuote(Quote quote);
    List<Quote> getQuoteByMovie(Movie movie);
    Quote getRandomQuote(Movie movie);
}