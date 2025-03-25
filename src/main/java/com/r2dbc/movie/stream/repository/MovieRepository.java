package com.r2dbc.movie.stream.repository;

import com.r2dbc.movie.stream.model.Movie;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends R2dbcRepository<Movie, String> {

}
