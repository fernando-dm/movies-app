package com.project.application.useCase.movies;

import com.project.application.config.unleash.UnleashConfiguration;
import com.project.application.toggles.ToggleAdHoc;
import com.project.domain.movie.Movie;
import com.project.domain.movie.MovieRepository;
import com.project.exception.NotFoundException;
import com.workia.application.FeatureContextImpl;
import com.workia.application.FeatureToggleService;
import com.workia.application.TogglesNamesEnum;
import com.workia.application.UnleashFeatureToggleService;
import io.getunleash.Unleash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MovieService {
    private final static Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieRepository movieRepository;
    private final FeatureToggleService featureToggleService;
    private final UnleashFeatureToggleService unleashService;

    //TODO crear clase de service para unleash
    public MovieService(MovieRepository movieRepository, FeatureToggleService toggleService, UnleashConfiguration unleashConfiguration) {
        this.movieRepository = movieRepository;
        this.featureToggleService = toggleService;

        Unleash unleash = unleashConfiguration.unleash();
        this.unleashService = new UnleashFeatureToggleService(unleash);
    }

    // Start service: all users and location get premium service
    @Deprecated(since = "Service to get deprecated in next version", forRemoval = true)
    public List<Movie> getMovies() {
        return movieRepository.selectMovies();
    }

    // New feature: Premium service only for certain Tenants
    public List<Movie> getMoviesByTenant(String tenant) { //nuevo
        boolean toggleIsActive = featureToggleService.isFeatureToggleActive(
                TogglesNamesEnum.PREMIUM_SUBSCRIPTION_TENANT_TOGGLE,
                new FeatureContextImpl(tenant));

        return processMovies(toggleIsActive);
    }

    //Not recommended: Use of custom properties and toggles names
    public List<Movie> getMoviesByTenantWithCustomProperties(String tenant) {
        boolean toggleIsActive = unleashService
                .isFeatureToggleActive(
                        ToggleAdHoc.CUSTOM_TENANT_TOGGLE_NAME.getToggleName(),
                        Map.of("tenant", tenant));  // ad-hoc
        return processMovies(toggleIsActive);
    }


    // New feature: Premium service only for certain Tenants and companyIds
    public List<Movie> getByTenantAndCompany(String tenant, String companyId) {
        boolean toggleIsActive = featureToggleService.isFeatureToggleActive(
                TogglesNamesEnum.PREMIUM_SUBSCRIPTION_TENANT_COMPANY_TOGGLE,
                new FeatureContextImpl(tenant, companyId));

        return processMovies(toggleIsActive);
    }

    //Not recommended: Use of custom properties and toggles names
    public List<Movie> getByTenantAndCompanyWithCustomProperties(String tenant, String companyId) {
        boolean toggleIsActive = unleashService.isFeatureToggleActive(
                ToggleAdHoc.CUSTOM_TENANT_COMPANY_TOGGLE_NAME.getToggleName(),
                Map.of("tenant", tenant, "company", companyId));

        return processMovies(toggleIsActive);
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
        Movie movie = getMovie(id);
        movieRepository.deleteMovie(id);
        logger.info(String.format("Movie: %s, deleted", movie.name()));
    }

    public Movie getMovie(int id) {
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

    private List<Movie> processMovies(boolean toggleIsActive) {
        if (toggleIsActive) {
            logger.info("PREMIUM SERVICE");
            List<Movie> movies = movieRepository.selectMovies();
            logger.info(" MOVIES " + movies);
            return movies;
        }

        logger.info("BASIC SERVICE");
        return movieRepository.selectMovies()
                .stream()
                .findFirst()
                .stream()
                .toList();
    }
}
