package com.ies.movies.service.impl;

import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ies.movies.entity.Movie;
import com.ies.movies.entity.Quote;
import com.ies.movies.repository.QuoteRepository;
import com.ies.movies.service.QuoteService;

@Service
public class QuoteServiceImpl implements QuoteService {
    
    @Autowired
    private QuoteRepository quotesRepository;

    private Random random = new Random();
    

    @Override
    public Quote createNewQuote(Quote quote) {
        return quotesRepository.save(quote);
    }
    
    @Override
    public List<Quote> getQuoteByMovie(Movie movie) {
        return quotesRepository.findByMovie(movie);
    }

    @Override
    public Quote getRandomQuote(Movie movie) {
        List<Quote> quotes = quotesRepository.findByMovie(movie);
        if (quotes.size() > 0) {
            Integer randomNumber = random.nextInt(quotes.size());
            return quotes.get(randomNumber);
        }
        return null;
    }
}
