package com.backend.ingresso.application.dto.validations.cinemaMovieDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class CinemaMovieCreate {
    @JsonProperty("id")
    private UUID Id;
    @NotEmpty(message = "cinemaId should not be empty")
    @JsonProperty("cinemaId")
    private String CinemaId;
    @NotEmpty(message = "movieId should not be empty")
    @JsonProperty("movieId")
    private String MovieId;
    @JsonProperty("regionId")
    private String RegionId;
    @NotEmpty(message = "screeningSchedule should not be empty")
    @Column(name = "screeningSchedule")
    private String ScreeningSchedule;

    public CinemaMovieCreate(UUID id, String cinemaId, String movieId, String regionId, String screeningSchedule) {
        Id = id;
        CinemaId = cinemaId;
        MovieId = movieId;
        RegionId = regionId;
        ScreeningSchedule = screeningSchedule;
    }

    public CinemaMovieCreate() {
    }

    public UUID getId() {
        return Id;
    }

    public String getCinemaId() {
        return CinemaId;
    }

    public String getMovieId() {
        return MovieId;
    }

    public String getRegionId() {
        return RegionId;
    }

    public String getScreeningSchedule() {
        return ScreeningSchedule;
    }
}
