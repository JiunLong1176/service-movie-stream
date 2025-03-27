package com.r2dbc.movie.stream.service;

import com.r2dbc.movie.stream.controller.request.MovieRequest;
import com.r2dbc.movie.stream.controller.response.ResponseCode;
import com.r2dbc.movie.stream.model.Movie;
import com.r2dbc.movie.stream.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;

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

    public Mono<Movie> getMovieById(String id){
        return movieRepository.findById(id)
                .switchIfEmpty(Mono.error(ResponseCode.RECORD_NOT_FOUND.asBusinessException()))
                .flatMap(Mono::just);
    }

    public Mono<Movie> createMovies(MovieRequest request){
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .director(request.getDirector())
                .releaseYear(request.getReleaseYear())
                .streamUrl(request.getStreamUrl())
                .build();

        return movieRepository.save(movie);
    }

    public Mono<Movie> updateMovies(String id, MovieRequest request){
        return movieRepository.findById(id)
                .switchIfEmpty(Mono.error(ResponseCode.RECORD_NOT_FOUND.asBusinessException()))
                .flatMap(movie -> {
                    movie.setTitle(request.getTitle());
                    movie.setGenre(request.getGenre());
                    movie.setDirector(request.getDirector());
                    movie.setReleaseYear(request.getReleaseYear());
                    movie.setStreamUrl(request.getStreamUrl());
                    return movieRepository.save(movie);
                });
    }

    public Mono<Void> deleteMovies(String id, MovieRequest request){
        return movieRepository.findById(id)
                .switchIfEmpty(Mono.error(ResponseCode.RECORD_NOT_FOUND.asBusinessException()))
                .flatMap(movie -> {
                    movie.setId(BigInteger.valueOf(Long.parseLong(id)));
                    movie.setTitle(request.getTitle());
                    movie.setGenre(request.getGenre());
                    movie.setDirector(request.getDirector());
                    movie.setReleaseYear(request.getReleaseYear());
                    movie.setStreamUrl(request.getStreamUrl());
                    return movieRepository.delete(movie);
                });
    }
}
