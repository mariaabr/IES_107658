package com.ies.movies;

import java.util.List;

import com.ies.movies.Quote;
import com.ies.movies.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    // public List<Quote> findByMovie(Movie movie);
    Quote findQuoteByText(String text);
    Quote findQuoteById(int id);
    String deleteQuoteById(int id);
    Quote getRandomQuote();
    Quote getRandomQuoteFromMovie(int id);

}