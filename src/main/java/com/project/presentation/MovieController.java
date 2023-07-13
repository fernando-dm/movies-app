package com.project.presentation;

import com.project.application.useCase.movies.*;
import com.project.domain.movie.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movies")
public class MovieController {

    private final DeleteMovie deleteMovie;
    private final FindMoviesBySubscriptionPlan findMoviesBySubscriptionPlan;
    private final CreateMovie createMovie;
    private final UpdateMovie updateMovie;
    private final SearchMovieBy searchMovieBy;

    public MovieController(DeleteMovie deleteMovie, FindMoviesBySubscriptionPlan findMoviesBySubscriptionPlan, CreateMovie createMovie, UpdateMovie updateMovie, SearchMovieBy searchMovieBy) {
        this.deleteMovie = deleteMovie;
        this.findMoviesBySubscriptionPlan = findMoviesBySubscriptionPlan;
        this.createMovie = createMovie;
        this.updateMovie = updateMovie;
        this.searchMovieBy = searchMovieBy;
    }

    @Deprecated(since = "Service to get deprecated in next version", forRemoval = true)
    @GetMapping
    public ResponseEntity<List<Movie>> listMovies() {
        return new ResponseEntity<>(findMoviesBySubscriptionPlan.getMovies(), HttpStatus.OK);
    }

    @GetMapping("/premium/tenant/{tenantId}")
    public ResponseEntity<List<Movie>> listPremiumMoviesByTenant(
            @PathVariable("tenantId") String tenantId) {
        return new ResponseEntity<>(findMoviesBySubscriptionPlan.getMoviesByTenant(tenantId), HttpStatus.OK);
    }

    @GetMapping("/premium/tenant/{tenant}/company/{companyId}")
    public ResponseEntity<List<Movie>> listPremiumMoviesByTenantAndCompany(
            @PathVariable("tenant") String tenant,
            @PathVariable("companyId") String companyId) {
        return new ResponseEntity<>(findMoviesBySubscriptionPlan.getByTenantAndCompany(tenant, companyId), HttpStatus.OK);
    }

    @GetMapping("/premium/custom/tenant/{tenantId}")
    public ResponseEntity<List<Movie>> listPremiumMoviesByTenantWithCustomProperties(
            @PathVariable("tenantId") String tenantId) {
        return new ResponseEntity<>(findMoviesBySubscriptionPlan.getMoviesByTenantWithCustomProperties(tenantId), HttpStatus.OK);
    }

    @GetMapping("/premium/custom/tenant/{tenant}/company/{companyId}")
    public ResponseEntity<List<Movie>> listPremiumMoviesByTenantAndCompanyWithCustomProperties(
            @PathVariable("tenant") String tenant,
            @PathVariable("companyId") String companyId) {
        return new ResponseEntity<>(findMoviesBySubscriptionPlan.getByTenantAndCompanyWithCustomProperties(tenant, companyId), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<Movie> getMovieId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(searchMovieBy.getMovieBy(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void deleteMovie(@PathVariable("id") Integer id) {
        deleteMovie.deleteMovie(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMovie(@RequestBody Movie movie) {
        createMovie.addNewMovie(movie);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movieToUpdate) {
        return new ResponseEntity<>(updateMovie.updateMovie(movieToUpdate), HttpStatus.OK);
    }

}
