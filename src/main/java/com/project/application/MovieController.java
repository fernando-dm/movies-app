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

//    @GetMapping("/unleash/{tenantId}/{company}")
//    public List<Movie> listByTenantAndCompany(
//            @PathVariable ("tenantId") String tenantId,
//            @PathVariable ("company") String company) {
//        return movieService.getByTenantAndCompany(tenantId, company);
//    }

    @GetMapping("/unleash/tenant/{tenantId}")
    public List<Movie> listByTenant(
            @PathVariable("tenantId") String tenantId) {
        return movieService.getByTenant(tenantId);
    }

    @GetMapping("/unleash/company/{company}")
    public List<Movie> listByCompany(
            @PathVariable("company") String company) {
        return movieService.getByCompany(company);
    }

    @GetMapping("/unleash/tenant/{tenantId}/company/{company}")
    public List<Movie> listByTenantAndCompany(
            @PathVariable("tenantId") String tenantId,
            @PathVariable("company") String company) {
        return movieService.getByTenantAndCompany2(tenantId, company);
    }

    @GetMapping
    public List<Movie> listMovies() {
        return movieService.getMovies();
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
