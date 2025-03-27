package com.r2dbc.movie.stream.controller;

import com.r2dbc.movie.stream.controller.request.MovieRequest;
import com.r2dbc.movie.stream.exception.BusinessException;
import com.r2dbc.movie.stream.model.Movie;
import com.r2dbc.movie.stream.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;

    @GetMapping(value = "/movies")
    private Mono<ResponseEntity<List<Movie>>> getAllMovies() {
        log.info("Fetching all movies");

        return movieService.getAllMovies()
                .collectList()
                .map(ResponseEntity::ok)
                .onErrorResume(
                        BusinessException.class,
                        e -> {
                            log.info("Business error while getting all movies: " + e.getMessage(), e);
                            return Mono.error(e);
                        })
                .onErrorResume(
                        Exception.class,
                        e -> {
                            log.error("Error while getting all movies: " + e.getMessage(), e);
                            return Mono.error(e);
                        });
    }

    @GetMapping(value = "/movies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<ResponseEntity<Movie>> getMovieById(@PathVariable(required = true) String id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .onErrorResume(
                        BusinessException.class,
                        e -> {
                            log.info("Business error while getting movie by id: " + e.getMessage(), e);
                            return Mono.error(e);
                        })
                .onErrorResume(
                        Exception.class,
                        e -> {
                            log.error("Error while getting movie by id: " + e.getMessage(), e);
                            return Mono.error(e);
                        });
    }

    @PostMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<ResponseEntity<Object>> createMovie(@RequestBody(required = true) MovieRequest movieRequest) {
        return movieService.createMovies(movieRequest)
                .then(Mono.just(ResponseEntity.ok((Object) Map.of("message", "Movie created successfully"))))
                .onErrorResume(
                        BusinessException.class,
                        e -> {
                            log.info("Business error while creating movie: " + e.getMessage(), e);
                            return Mono.error(e);
                        })
                .onErrorResume(
                        Exception.class,
                        e -> {
                            log.error("Error while creating movie: " + e.getMessage(), e);
                            return Mono.error(e);
                        });
    }

    @PutMapping(value = "/movies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<ResponseEntity<Object>> updateMovie(@PathVariable(required = true) String id, @RequestBody MovieRequest movieRequest){
        return movieService.updateMovies(id, movieRequest)
                .then(Mono.just(ResponseEntity.ok((Object) Map.of("message", "Movie updated successfully"))))
                .onErrorResume(
                        BusinessException.class,
                        e -> {
                            log.info("Business error while updating movie: " + e.getMessage(), e);
                            return Mono.error(e);
                        }
                )
                .onErrorResume(
                        Exception.class,
                        e -> {
                            log.error("Error while updating movie: " + e.getMessage(), e);
                            return Mono.error(e);
                        }
                );
    }

    @DeleteMapping(value = "/movies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private Mono<ResponseEntity<Object>> deleteMovie(@PathVariable(required = true) String id, @RequestBody MovieRequest movieRequest){
        return movieService.deleteMovies(id, movieRequest)
                .then(Mono.just(ResponseEntity.ok((Object) Map.of("message", "Movie deleted successfully"))))
                .onErrorResume(
                        BusinessException.class,
                        e -> {
                            log.info("Business error while deleting movie: " + e.getMessage(), e);
                            return Mono.error(e);
                        }
                )
                .onErrorResume(
                        Exception.class,
                        e -> {
                            log.error("Error while deleting movie: " + e.getMessage(), e);
                            return Mono.error(e);
                        }
                );

    }
}
