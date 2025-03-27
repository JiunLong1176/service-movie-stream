package com.r2dbc.movie.stream.model;


import com.r2dbc.movie.stream.id.NumericIdGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.security.SecureRandom;

@Data
@Table("movies")
@Setter
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column("title")
    private String title;

    @Column("genre")
    private String genre;

    @Column("director")
    private String director;

    @Column("releaseYear")
    private int releaseYear;

    @Column("streamUrl")
    private String streamUrl;
}
