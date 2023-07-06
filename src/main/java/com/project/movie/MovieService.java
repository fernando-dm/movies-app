package com.project.movie;

import com.project.config.unleash.UnleashConfiguration;
import com.project.exception.NotFoundException;
import io.getunleash.ActivationStrategy;
import io.getunleash.Unleash;
import io.getunleash.UnleashContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieDao movieDao;
    private final Unleash unleash;

    private final static Logger logger = LoggerFactory.getLogger(MovieService.class);

    public MovieService(MovieDao movieDao, Unleash unleash) {
        this.movieDao = movieDao;
        this.unleash = unleash;
    }

    public List<Movie> getMovies() {
        return null;
    }
    public List<Movie> getMovies2(String tenantId) {

        UnleashContext context = UnleashContext.builder()
                .appName("movies-app")
                .addProperty("tenant", tenantId)
                .build();

        // tenantId: [a,b,c]
        boolean toggleIsActive = unleash.isEnabled("mostrarTodo", context);

        List<ActivationStrategy> toggleStrategy = unleash.more().getFeatureToggleDefinition("mostrarTodo").get()
                .getStrategies();

        boolean result = toggleStrategy.stream()
                .filter(it -> it.getName().equals("tenant"))
                .findFirst()
                .map(obj -> obj.getParameters().values())
                .orElse(Collections.emptyList())
                .stream()
                .flatMap(value -> Arrays.stream(value.split(",")))
                .anyMatch(token -> token.trim().equals(tenantId));

//        Unleash unleash = new DefaultUnleash( new HcTenantsStrategy());
//        UnleashConfiguration un = new UnleashConfiguration();
//        boolean result2 = un.hcTenantsStrategy().isEnabled(context.getProperties(),context);
//                .unleash().isEnabled("mostrarTodo", context);

        if (toggleIsActive) {

            List<Movie> movies = movieDao.selectMovies();
            logger.info(" devuelvo " + movies);
            return movies;
        }

        return movieDao.selectMovies()
                .stream()
                .findFirst()
                .stream()
                .toList();
    }

//    private boolean isEnabled(String toggle, UnleashContext context, String tenantId) {
////        UnleashContext context = UnleashContext.builder()
////                .appName("movies-app")
////                .addProperty("tenantId", "test1")
////                .build();
//        UnleashConfig config = new UnleashConfig.Builder()
//                .appName("movies-app")
//                .instanceId("1")
//                .unleashAPI("http://localhost:4242/api")
//                .apiKey("default:development.1ee5fb49a9f0f124853a2deee73a2da98d0ee36846ea400eff06e1fc")
//                .build();
//        Unleash unleash = new DefaultUnleash(config, new HcTenantsStrategy());
//        boolean res = unleash.isEnabled("mostrarTodo", context);
//
//        return res;
//
//    }

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
