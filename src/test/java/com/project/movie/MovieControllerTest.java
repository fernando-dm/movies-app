package com.project.movie;

import com.project.infrastructure.controller.movies.MovieController;
import com.project.application.useCase.movies.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

class MovieControllerTest {

    @Mock
    private MovieService movieService;

    private MovieController underTest;

    @BeforeEach
    void setUp() {
        underTest = new MovieController(movieService);
    }
}