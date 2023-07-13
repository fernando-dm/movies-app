package com.project.application.useCase.movies;

import com.project.domain.movie.Movie;
import com.project.domain.movie.MovieRepository;
import com.project.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateMovie {

    private final static Logger logger = LoggerFactory.getLogger(DeleteMovie.class);
    private final MovieRepository movieRepository;

    public UpdateMovie(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    private Movie getMovie(int id) {
        return movieRepository.selectMovieById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", id)));
    }

    public Movie updateMovie(Movie movieToUpdate) {
        Movie movie = getMovie(movieToUpdate.id());
        try {
            movieRepository.update(movieToUpdate);
        } catch (Exception e) {
            throw new NotFoundException(String.format("Movie with id %s cant not be updated", movieToUpdate.id()));
        }
        logger.info(String.format("Movie: %s, updated", movie.name()));
        return movieToUpdate;
    }
}
