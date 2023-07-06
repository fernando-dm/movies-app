package com.project.application.useCase.movies;

import com.project.domain.movie.Movie;
import com.project.domain.repository.MovieDao;
import com.project.exception.NotFoundException;
import com.project.utils.toggles.service.FeatureToggleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieDao movieDao;
    private final FeatureToggleService featureToggleService;

    private final static Logger logger = LoggerFactory.getLogger(MovieService.class);

    public MovieService(MovieDao movieDao, FeatureToggleService featureToggleService) {
        this.movieDao = movieDao;
        this.featureToggleService = featureToggleService;
    }

    public List<Movie> getByTenant(String tenant) {
        boolean toggleIsActive = featureToggleService.isFeatureToggleActive2("tenantToggle",
                Map.of("tenant", tenant));

        return processMovies(toggleIsActive);
    }

    public List<Movie> getByCompany(String company) {
        boolean toggleIsActive = featureToggleService.isFeatureToggleActive2("companyToggle",
                Map.of("company", company));

        return processMovies(toggleIsActive);
    }

    public List<Movie> getByTenantAndCompany(String tenantId, String company) {
        boolean toggleIsActive = featureToggleService.isFeatureToggleActive2("tenantCompanyToggle",
                Map.of("tenant", tenantId, "company", company));

        return processMovies(toggleIsActive);
    }

    private List<Movie> processMovies(boolean toggleIsActive) {
        if (toggleIsActive) {
            logger.info("SE ACTIVO EL TOGGLE");
            List<Movie> movies = movieDao.selectMovies();
            logger.info(" devuelvo " + movies);
            return movies;
        }

        logger.info("NOOOO SE ACTIVO EL TOGGLE");

        return movieDao.selectMovies()
                .stream()
                .findFirst()
                .stream()
                .toList();
    }

    public List<Movie> getMovies() {
        return null;
    }

    public void addNewMovie(Movie movie) {
        // TODO: check if movie exists
        boolean movieExists = movieDao.selectMovies()
                .stream()
                .anyMatch(movie1 -> movie.name().equals(movie1.name()));
        if (movieExists)
            throw new IllegalStateException(String.format("Movie: %s, already exists", movie.name()));

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
