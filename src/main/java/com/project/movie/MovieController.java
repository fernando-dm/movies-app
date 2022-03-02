package com.project.movie;

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
