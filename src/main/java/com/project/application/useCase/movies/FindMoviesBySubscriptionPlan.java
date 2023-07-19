package com.project.application.useCase.movies;

import com.hcwork.config.unleash.UnleashConfiguration;
import com.hcwork.context.FeatureContextImpl;
import com.hcwork.service.FeatureToggleService;
import com.hcwork.service.UnleashFeatureToggleService;
import com.project.application.toggles.ToggleAdHoc;
import com.project.domain.movie.Movie;
import com.project.domain.movie.MovieRepository;
import io.getunleash.Unleash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class FindMoviesBySubscriptionPlan {
    private final static Logger logger = LoggerFactory.getLogger(FindMoviesBySubscriptionPlan.class);
    private final MovieRepository movieRepository;
    private final FeatureToggleService featureToggleService;
    private final UnleashFeatureToggleService unleashService;

    public FindMoviesBySubscriptionPlan(MovieRepository movieRepository,
                                        FeatureToggleService toggleService,
                                        UnleashConfiguration unleashConfiguration) {
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
    public List<Movie> getMoviesByTenant(String tenantId) { //nuevo
        boolean toggleIsActive = featureToggleService.isFeatureToggleActive(
                ToggleAdHoc.CUSTOM_TENANT_COMPANY_TOGGLE_NAME,
                new FeatureContextImpl(tenantId));

        return processMovies(toggleIsActive);
    }

    //Not recommended: Use of custom properties and toggles names
    public List<Movie> getMoviesByTenantWithCustomProperties(String tenantId) {
        boolean toggleIsActive = unleashService
                .isFeatureToggleActive(
                        ToggleAdHoc.CUSTOM_TENANT_COMPANY_TOGGLE_NAME,
                        Map.of("tenantId", tenantId));  // ad-hoc
        return processMovies(toggleIsActive);
    }


    // New feature: Premium service only for certain Tenants and companyIds
    public List<Movie> getByTenantAndCompany(String tenantId, String companyId) {
        boolean toggleIsActive = featureToggleService.isFeatureToggleActive(
                ToggleAdHoc.CUSTOM_TENANT_COMPANY_TOGGLE_NAME,
                new FeatureContextImpl(tenantId, companyId));

        return processMovies(toggleIsActive);
    }

    //Not recommended: Use of custom properties and toggles names
    public List<Movie> getByTenantAndCompanyWithCustomProperties(String tenantId, String companyId) {
        boolean toggleIsActive = unleashService.isFeatureToggleActive(
                ToggleAdHoc.CUSTOM_TENANT_COMPANY_TOGGLE_NAME,
                Map.of("tenantId", tenantId, "companyId", companyId));

        return processMovies(toggleIsActive);
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
