package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

import java.util.UUID;

public class CinemaMovieDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("cinemaId")
    private UUID CinemaId;
    @JsonProperty("cinema")
    private CinemaDTO cinema;
    @JsonProperty("movieId")
    private UUID MovieId;
    @JsonProperty("movie")
    private MovieDTO movie;
    @JsonProperty("regionId")
    private UUID RegionId;
    @JsonProperty("region")
    private RegionDTO region;
    @Column(name = "screening_schedule")
    private String ScreeningSchedule;

    public CinemaMovieDTO(UUID id, UUID cinemaId, CinemaDTO cinema, UUID movieId, MovieDTO movie, UUID regionId, RegionDTO region, String screeningSchedule) {
        Id = id;
        CinemaId = cinemaId;
        this.cinema = cinema;
        MovieId = movieId;
        this.movie = movie;
        RegionId = regionId;
        this.region = region;
        ScreeningSchedule = screeningSchedule;
    }

    public CinemaMovieDTO() {
    }

    public UUID getId() {
        return Id;
    }

    public UUID getCinemaId() {
        return CinemaId;
    }

    public CinemaDTO getCinema() {
        return cinema;
    }

    public UUID getMovieId() {
        return MovieId;
    }

    public MovieDTO getMovie() {
        return movie;
    }

    public UUID getRegionId() {
        return RegionId;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public String getScreeningSchedule() {
        return ScreeningSchedule;
    }
}
