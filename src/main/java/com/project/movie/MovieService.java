package com.project.movie;

import com.project.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieDao movieDao;

    public MovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public List<Movie> getMovies() {
        return movieDao.selectMovies();
    }

    public void addNewMovie(Movie movie) {
        // TODO: check if movie exists
        boolean movieExists = movieDao.selectMovies()
                .stream()
                .anyMatch(movie1 -> movie.name().equals(movie1.name()));
        if (movieExists)
            throw new IllegalStateException(String.format("Movie: %s, already exists",movie.name()));

        int result = movieDao.insertMovie(movie);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteMovie(Integer id) {
        Optional<Movie> movies = movieDao.selectMovieById(id);
        movies.ifPresentOrElse(movie -> {
                    int result = movieDao.deleteMovie(id);
                    if (result != 1)
                        throw new IllegalStateException("oops could not delete movie");
                },
                () -> {
                    throw new NotFoundException(String.format("Movie with id %s not found", id));
                });
    }

    public Movie getMovie(int id) {
        return movieDao.selectMovieById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", id)));
    }

    public Movie updateMovie(Movie movieToUpdate) {
        Optional<Movie> movie = Optional.ofNullable(movieDao.selectMovieById(movieToUpdate.id())
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", movieToUpdate.id()))));

        movie.ifPresentOrElse(movie1 -> {
                    movieDao.update(movieToUpdate);
                },
                () -> {
                    throw new NotFoundException(String.format("Movie with id %s cant not be updated", movieToUpdate.id()));
                }
        );
        return movieToUpdate;
    }
}
