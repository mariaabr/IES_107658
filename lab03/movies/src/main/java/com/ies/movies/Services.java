package com.ies.movies;

// import org.hibernate.cfg.annotations.CollectionBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
public class Services {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private QuoteRepository quoteRepository;

    // Movie:

    public Movie saveMovie(Movie Movie) {
        return movieRepository.save(Movie);
    }

    public List<Movie> saveMovies(List<Movie> Movies) {
        return movieRepository.saveAll(Movies);
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie findMovieById(int id) {
        return movieRepository.findMovieById(id);
    }

    public Movie findMovieByTitle(String title) {
        return movieRepository.findMovieByTitle(title);
    }

    public String deleteMovieById(int id) {
        movieRepository.deleteMovieById(id);
        return "Movie removed !! " + id;
    }

    public Movie updateMovie(Movie Movie) {
        Movie existingMovie = movieRepository.findMovieById(Movie.getId()).orElse(null);
        existingMovie.setTitle(Movie.getTitle());
        existingMovie.setYear(Movie.getYear());
        return movieRepository.save(existingMovie);
    }

    // Quote:

    public Quote saveQuote(Quote Quote) {
        return quoteRepository.save(Quote);
    }

    public List<Quote> saveQuotes(List<Quote> Quotes) {
        return quoteRepository.saveAll(Quotes);
    }

    public List<Quote> getQuotes() {
        return quoteRepository.findAll();
    }

    public Quote getRandomQuote() {
        Random rand = new Random();
        Quote quote = quoteRepository.findAll().get(rand.nextInt(quoteRepository.findAll().size()));
        return quote;
    }

    public Quote findQuoteById(int id) {
        return quoteRepository.findQuoteById(id);
    }

    public Quote findQuoteByText(String text) {
        return quoteRepository.findQuoteByText(text);
    }

    public String deleteQuoteById(int id) {
        quoteRepository.deleteQuoteById(id);
        return "Quote removed !! " + id;
    }

    public Quote getRandomQuoteFromMovie(int id) {
        Movie movie = movieRepository.findMovieById(id);
        Random rand = new Random();
        Quote quote = movie.getQuotes().get(rand.nextInt(movie.getQuotes().size()));
        return quote;
    }

    public Quote updateQuote(Quote Quote) {
        Quote existingQuote = quoteRepository.findQuoteById(Quote.getId()).orElse(null);
        existingQuote.setText(Quote.getText());
        existingQuote.setMovie(Quote.getMovie());
        return quoteRepository.save(existingQuote);
    }

}
