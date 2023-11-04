package com.ies.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ies.movies.entity.Movie;
import com.ies.movies.entity.Quote;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByMovie(Movie movie);
    // Quote findQuoteById(Long quoteId);
}
