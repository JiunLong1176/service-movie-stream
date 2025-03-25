package com.r2dbc.movie.stream.controller;

import com.r2dbc.movie.stream.model.Movie;
import com.r2dbc.movie.stream.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;

    @GetMapping(value = "/movies")
    private ResponseEntity<Flux<List<Movie>>> getAllMovies() {
        log.info("Fetching all movies");

        return ResponseEntity.ok(
                movieService.getAllMovies().collectList().flux()
        );
    }
}
