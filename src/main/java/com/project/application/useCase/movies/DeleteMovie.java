package com.project.application.useCase.movies;

import com.project.domain.movie.Movie;
import com.project.domain.movie.MovieRepository;
import com.project.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteMovie {
    private final static Logger logger = LoggerFactory.getLogger(DeleteMovie.class);
    private final MovieRepository movieRepository;

    public DeleteMovie(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void deleteMovie(Integer id) {
        Movie movie = getMovie(id);
        movieRepository.deleteMovie(id);
        logger.info(String.format("Movie: %s, deleted", movie.name()));
    }

    private Movie getMovie(int id) {
        return movieRepository.selectMovieById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", id)));
    }
}
