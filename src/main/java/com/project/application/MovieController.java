package com.project.application;

import com.project.domain.movie.Movie;
import com.project.application.useCase.movies.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Deprecated(since = "Service to get deprecated in next version", forRemoval = true)
    @GetMapping
    public List<Movie> listMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/premium/tenant/{tenantId}")
    public List<Movie> listPremiumMoviesByTenant(
            @PathVariable("tenantId") String tenantId) {
        return movieService.getMoviesByTenant(tenantId);
    }

    @GetMapping("/premium/tenant/{tenant}/company/{companyId}")
    public List<Movie> listPremiumMoviesByTenantAndCompany(
            @PathVariable("tenant") String tenant,
            @PathVariable("companyId") String companyId) {
        return movieService.getByTenantAndCompany(tenant, companyId);
    }

    @GetMapping("/premium/custom/tenant/{tenantId}")
    public List<Movie> listPremiumMoviesByTenantWithCustomProperties(
            @PathVariable("tenantId") String tenantId) {
        return movieService.getMoviesByTenantWithCustomProperties(tenantId);
    }

    @GetMapping("/premium/custom/tenant/{tenant}/company/{companyId}")
    public List<Movie> listPremiumMoviesByTenantAndCompanyWithCustomProperties(
            @PathVariable("tenant") String tenant,
            @PathVariable("companyId") String companyId) {
        return movieService.getByTenantAndCompanyWithCustomProperties(tenant, companyId);
    }


    @GetMapping("{id}")
    public Movie getMovieId(@PathVariable("id") Integer id) {
        return movieService.getMovie(id);
    }


    @DeleteMapping("{id}")
    public void deleteMovie(@PathVariable("id") Integer id) {
        movieService.deleteMovie(id);
    }

    @PostMapping("/add")
    public ResponseEntity addMovie(@RequestBody Movie movie) {
        movieService.addNewMovie(movie);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update")
    public Movie updateMovie(@RequestBody Movie movieToUpdate){
        return movieService.updateMovie(movieToUpdate);
    }

}
