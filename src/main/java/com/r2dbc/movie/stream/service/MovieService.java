package com.r2dbc.movie.stream.service;

import com.r2dbc.movie.stream.controller.response.ResponseCode;
import com.r2dbc.movie.stream.model.Movie;
import com.r2dbc.movie.stream.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Flux<Movie> getAllMovies() {
        return movieRepository.findAll()
                .switchIfEmpty(Mono.error(ResponseCode.RECORD_NOT_FOUND.asBusinessException()))
                .flatMap(Mono::just);
    }
}
