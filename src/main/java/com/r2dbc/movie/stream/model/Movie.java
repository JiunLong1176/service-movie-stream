package com.r2dbc.movie.stream.model;


import com.r2dbc.movie.stream.annotation.IdentifierGenerator;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("movies")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    private String id;

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

    public static Movie create(String title, String genre, String director, int releaseYear, String streamUrl) {
        return new Movie(UUID.randomUUID().toString(), title, genre, director, releaseYear, streamUrl);
    }
}
