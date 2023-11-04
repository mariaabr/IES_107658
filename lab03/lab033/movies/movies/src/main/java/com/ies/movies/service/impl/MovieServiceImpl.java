package com.ies.movies.service.impl;

import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.ies.movies.entity.Movie;
import com.ies.movies.repository.MovieRepository;
import com.ies.movies.service.MovieService;

@Service
// @AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    
    @Autowired
    private MovieRepository movieRepository;

    private Random random = new Random();

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie createNewMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie getMovieById(Long movieID) {
        return movieRepository.findMovieById(movieID);
    }

    @Override
    public Movie getRandomMovie() {
        Integer numberMovies = movieRepository.findAll().size();
        Integer randomNumber = random.nextInt(numberMovies-1)+1;
        return movieRepository.findMovieById(Long.valueOf(randomNumber));
    }
}
