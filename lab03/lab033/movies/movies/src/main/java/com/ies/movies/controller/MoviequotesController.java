package com.ies.movies.controller;

import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ies.movies.entity.Movie;
import com.ies.movies.entity.Quote;
import com.ies.movies.service.MovieService;
import com.ies.movies.service.QuoteService;

@RestController
@AllArgsConstructor
public class MoviequotesController {

    private MovieService movieService;
    private QuoteService quoteService;

    // add a movie
   @PostMapping("/shows")
   public ResponseEntity<Movie> createNewMovie(@RequestBody Movie movie) {
       Movie new_movie = movieService.createNewMovie(movie);
       return new ResponseEntity<>(new_movie, HttpStatus.CREATED);
   }

    // add a quote
    @PostMapping("/quote")
    public ResponseEntity<Quote> createNewQuote(@RequestBody Quote quote) {
        Quote new_quote = quoteService.createNewQuote(quote);
        return new ResponseEntity<>(new_quote, HttpStatus.CREATED);
    }

    // list of all available movies, (from which some quotes exists).
    @GetMapping("/shows")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.CREATED);
    }

    // list a random quote wihtout specifing the movie
    @GetMapping("/quote")
	public ResponseEntity<Quote>  getQuote() {
		Quote randomQuote = quoteService.getRandomQuote(movieService.getRandomMovie());
        return new ResponseEntity<>(randomQuote, HttpStatus.OK);
	}

    // return a quote by the movie choosen by the user
    @GetMapping("/quotes/{id}")
	public ResponseEntity<List<Quote>> getQuotebyMovie(@PathVariable("id") Long movie_id) {
        List<Quote> quotes = quoteService.getQuoteByMovie(movieService.getMovieById(movie_id));
        return new ResponseEntity<>(quotes, HttpStatus.CREATED);
	}
}
