package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieTheaterDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("movieId")
    private UUID MovieId;
    @JsonProperty("regionId")
    private UUID RegionId;

    public MovieTheaterDTO(UUID id, UUID movieId, UUID regionId) {
        Id = id;
        MovieId = movieId;
        RegionId = regionId;
    }

    public MovieTheaterDTO(UUID id) {
        Id = id;
    }

    public MovieTheaterDTO() {
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

    public void setId(UUID id) {
        Id = id;
    }
}
