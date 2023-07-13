package com.project.application.useCase.movies;

import com.project.domain.movie.Movie;
import com.project.domain.movie.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateMovie {
    private final MovieRepository movieRepository;

    public CreateMovie(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void addNewMovie(Movie movie) {
        boolean movieExists = movieRepository.selectMovies()
                .stream()
                .anyMatch(movie1 -> movie.name().equals(movie1.name()));

        if (movieExists)
            throw new IllegalStateException(String.format("Movie: %s, already exists", movie.name()));

        int result = movieRepository.insertMovie(movie);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }
}
