package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
}
