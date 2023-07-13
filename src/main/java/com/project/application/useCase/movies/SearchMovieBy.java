package com.project.application.useCase.movies;

import com.project.domain.movie.Movie;
import com.project.domain.movie.MovieRepository;
import com.project.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SearchMovieBy {

    private final MovieRepository movieRepository;

    public SearchMovieBy(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie getMovieBy(int id) {
        return movieRepository.selectMovieById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", id)));
    }
}
