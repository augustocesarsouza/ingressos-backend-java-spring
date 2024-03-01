package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_movie_theaters", schema = "public")
public class MovieTheater {
    @Id
    @Column(name = "movie_theater_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "movie_id")
    @JsonProperty("movieId")
    private UUID MovieId;
    @Column(name = "region_id")
    @JsonProperty("regionId")
    private UUID RegionId;
    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    public MovieTheater(UUID id, UUID movieId, UUID regionId) {
        Id = id;
        MovieId = movieId;
        RegionId = regionId;
    }

    public MovieTheater() {
    }

    public UUID getId() {
        return Id;
    }

    public UUID getMovieId() {
        return MovieId;
    }

    public UUID getRegionId() {
        return RegionId;
    }

    public Movie getMovie() {
        return movie;
    }
}
