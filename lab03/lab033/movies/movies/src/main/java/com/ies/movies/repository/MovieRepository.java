package com.ies.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ies.movies.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // List<Movie> findMovieByTitle(String title);
    Movie findMovieById(Long id);
}