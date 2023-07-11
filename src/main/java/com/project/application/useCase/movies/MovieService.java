package com.project.application.useCase.movies;

import com.project.domain.movie.Movie;
import com.project.domain.movie.MovieRepository;
import com.project.exception.NotFoundException;
import com.project.utils.toggles.features.FeatureToggleService;
import com.project.utils.toggles.features.FeatureContext;
import com.project.utils.toggles.features.TogglesNamesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final FeatureToggleService featureToggleService;
    private final static Logger logger = LoggerFactory.getLogger(MovieService.class);

    public MovieService(MovieRepository movieRepository, FeatureToggleService toggleService) {
        this.movieRepository = movieRepository;
        this.featureToggleService = toggleService;
    }

    public List<Movie> getByTenant(String tenant) { //DEPRECAR mapa
        boolean toggleIsActive = featureToggleService
                .isFeatureToggleActive(
                        "tenantToggle",
                        Map.of("tenant", tenant));

        return processMovies(toggleIsActive);
    }

    public List<Movie> getByCompany(String company) {
        boolean toggleIsActive = featureToggleService.isFeatureToggleActive("companyToggle",
                Map.of("company", company));

        return processMovies(toggleIsActive);
    }


    //    public List<Movie> getByTenantAndCompany(String tenant, String companyId) {
//        boolean toggleIsActive = featureToggleService
//        .isFeatureToggleActive("tenantCompanyToggle",
//                Map.of("tenant", tenant, "company", companyId));
//
//        return processMovies(toggleIsActive);
//    }
    public List<Movie> getByTenant2(String tenant) { //nuevo
        boolean toggleIsActive = featureToggleService
                .isFeatureToggleActive(
                        TogglesNamesEnum.TENANT_COMPANY_TOGGLE,
//                        "tenantToggle",
                        new FeatureContext(tenant));

        return processMovies(toggleIsActive);
    }

    public List<Movie> getByTenantAndCompany2(String tenant, String companyId) {
        boolean toggleIsActive = featureToggleService
                .isFeatureToggleActive(TogglesNamesEnum.TENANT_COMPANY_TOGGLE,
                        new FeatureContext(tenant, companyId));
        return processMovies(toggleIsActive);
    }

    private List<Movie> processMovies(boolean toggleIsActive) {
        if (toggleIsActive) {
            logger.info("SE ACTIVO EL TOGGLE");
            List<Movie> movies = movieRepository.selectMovies();
            logger.info(" devuelvo " + movies);
            return movies;
        }

        logger.info("NOOOO SE ACTIVO EL TOGGLE");

        return movieRepository.selectMovies()
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

    public void deleteMovie(Integer id) {
        Optional<Movie> movies = movieRepository.selectMovieById(id);
        movies.ifPresentOrElse(movie -> {
                    int result = movieRepository.deleteMovie(id);
                    if (result != 1)
                        throw new IllegalStateException("oops could not delete movie");
                },
                () -> {
                    throw new NotFoundException(String.format("Movie with id %s not found", id));
                });
    }

    public Movie getMovie(int id) {
        return movieRepository.selectMovieById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", id)));
    }

    public Movie updateMovie(Movie movieToUpdate) {
        Optional<Movie> movie = Optional.ofNullable(movieRepository.selectMovieById(movieToUpdate.id())
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", movieToUpdate.id()))));

        movie.ifPresentOrElse(movie1 -> {
                    movieRepository.update(movieToUpdate);
                },
                () -> {
                    throw new NotFoundException(String.format("Movie with id %s cant not be updated", movieToUpdate.id()));
                }
        );
        return movieToUpdate;
    }
}
