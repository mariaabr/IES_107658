package com.ies.movies;

import java.util.List;

import com.ies.movies.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
  // public List<Movie> findByName(String name);
  Movie findMovieByTitle(String title);
  Movie findMovieById(int id);
  String deleteMovieById(int id);
}