package com.ies.movies.service;

import com.ies.movies.entity.Movie;

import java.util.*;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie createNewMovie(Movie movie);
    Movie getMovieById(Long movieID);
    Movie getRandomMovie();
}
